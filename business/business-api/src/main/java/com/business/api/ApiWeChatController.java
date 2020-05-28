package com.business.api;

import com.business.annotation.IgnoreAuth;
import com.business.util.ApiBaseAction;
import com.business.util.QRCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/5/26
 * @time 创建时间: 16:30
 */
@Api(tags = "会员验证")
@RestController
@RequestMapping("/api/wechat")
public class ApiWeChatController extends ApiBaseAction {

    private static String token = "caiqing";

    @IgnoreAuth
    @RequestMapping("/signature")
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("========WechatController========= ");
        logger.info("-----来自微信的请求----");

        Enumeration pNames = request.getParameterNames();
        while (pNames.hasMoreElements()) {
            String name = (String) pNames.nextElement();
            String value = request.getParameter(name);
            //查看微信的请求都带了哪些参数
            String log = "name =" + name + "  value =" + value;
            logger.error(log);
        }

        String signature = request.getParameter("signature");/// 微信加密签名
        String timestamp = request.getParameter("timestamp");/// 时间戳
        String nonce = request.getParameter("nonce"); /// 随机数
        String echostr = request.getParameter("echostr"); // 随机字符串
        PrintWriter out = response.getWriter();

        if (checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }

        out.print(token);

        out.close();
        out = null;

    }

    /**
     * 校验签名
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        System.out.println("signature:" + signature + "timestamp:" + timestamp + "nonc:" + nonce);
        String[] arr = new String[]{token, timestamp, nonce};
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        System.out.println(tmpStr.equals(signature.toUpperCase()));
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }


    /**
     * 生成二维码
     */
    @ApiOperation(value = "生成二维码")
    @IgnoreAuth
    @GetMapping("createQrCode")
    public Object createQrCode(String param, String path, String fileDir, String fileName) {
        try {
            String accessToken = tokenService.getAccessToken();
            String imgStr = QRCodeUtils.createQrCodeToUrl(accessToken, param, path, fileDir, fileName);
            return toResponsSuccess(imgStr);
        } catch (Exception e) {
            logger.error("wechat=>createQrCode.param=>" + param, e);
        }
        return toResponsFail("生成二维码错误");
    }
}
