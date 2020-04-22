package com.business.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.annotation.IgnoreAuth;
import com.business.service.ApiUserService;
import com.business.util.ApiBaseAction;
import com.business.util.ApiUserUtils;
import com.business.utils.WeChatDecryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/20
 * @time 创建时间: 19:00
 */

@Api(tags = "小程序手机号码解密验证")
@RestController
@RequestMapping("/api/mobile")
public class ApiMobileController extends ApiBaseAction {

    @Autowired
    private ApiUserService userService;


    //@ApiOperation(value = "获取手机号码")
    //@IgnoreAuth
    //@RequestMapping(value = "/getPhoneNumber2")
    //public Object getPhoneNumber2(Integer staffId, String code, String iv, String encryptedData) throws Exception {
    //    try {
    //        //登录凭证不能为空
    //        if (code == null || code.length() == 0) {
    //            return ResponseEntity.badRequest().body("code 不能为空");
    //        }
    //        RestTemplate restTemplate = new RestTemplate();
    //        //获取openid
    //        String requestUrl = ApiUserUtils.getWebAccess(code);//通过自定义工具类组合出小程序需要的登录凭证 code
    //        String res = restTemplate.getForObject(requestUrl, String.class);
    //        JSONObject sessionData = JSON.parseObject(res);
    //        String openid = sessionData.getString("openid");
    //        String session_key = sessionData.getString("session_key");//不知道啥用。
    //        if (session_key == null) {
    //            return ResponseEntity.badRequest().body("用户编码解密失败:");
    //        }
    //        JSONObject json = phoneNumber(session_key, encryptedData, iv);//解密电话号码
    //        String mobile = "";
    //        if (json.containsKey("phoneNumber")) {
    //            mobile = json.getString("phoneNumber");
    //        }
    //        if (!StringUtils.isNotBlank(mobile)) {
    //            return toResponsFail("用户未绑定手机号");
    //        }
    //        int s = userService.updateByOpenId(openid, mobile);
    //        if (s == 0) {
    //            return toResponsFail("绑定失败");
    //        }
    //        int s2 = userService.saveMail(staffId, mobile);
    //        if (s2 == 0) {
    //            return toResponsFail("绑定失败2");
    //        }
    //        return toResponsSuccess("谢谢支持");
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        return toResponsFail(e.getMessage());
    //    }
    //}


    @ApiOperation(value = "获取手机号码")
    @IgnoreAuth
    @RequestMapping(value = "/phoneNumber")
    //解析电话号码
    public Object phoneNumber(String encryptedData, String iv) {

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        //HttpSession session = request.getSession();
        String session_key =(String)session.getAttribute("session_key");
        //String session_key = ShiroUtils.getSessionAttribute("WeChatKey").toString();
        System.out.println("session_key：" + session_key + ",encryptedData：" + encryptedData + ",iv：" + iv);
        if (session_key==null||session_key.length()==0){
            return toResponsFail("登录已过期，请重新登录");
        }
        byte[] dataByte = org.bouncycastle.util.encoders.Base64.decode(encryptedData);

        byte[] keyByte = org.bouncycastle.util.encoders.Base64.decode(session_key);

        byte[] ivByte = org.bouncycastle.util.encoders.Base64.decode(iv);
        try {

            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "获取手机号码")
    @IgnoreAuth
    @RequestMapping(value = "/getPhoneNumbers")
    public Object getPhoneNumbers(Integer staffId, String code, String iv, String encryptedData) throws Exception {
        try {
            RestTemplate restTemplate = new RestTemplate();
            //获取openid
            String requestUrl = ApiUserUtils.getWebAccess(code);//通过自定义工具类组合出小程序需要的登录凭证 code
            String res = restTemplate.getForObject(requestUrl, String.class);
            JSONObject sessionData = JSON.parseObject(res);
            String openid = sessionData.getString("openid");
            String session_key = sessionData.getString("session_key");//不知道啥用。
            if (null == sessionData || com.qiniu.util.StringUtils.isNullOrEmpty(openid)) {
                return toResponsFail("登录失败");
            }
            String mobile = "";
            String mobileStr = WeChatDecryptUtil.decryptData(encryptedData, session_key, iv);
            JSONObject json = JSONObject.parseObject(mobileStr);
            if (json.containsKey("phoneNumber")) {
                mobile = json.getString("phoneNumber");
            }
            if (!StringUtils.isNotBlank(mobile)) {
                return toResponsFail("用户未绑定手机号");
            }
            int s = userService.updateByOpenId(openid, mobile);
            if (s == 0) {
                return toResponsFail("绑定失败");
            }
            int s2 = userService.saveMail(staffId, mobile);
            if (s2 == 0) {
                return toResponsFail("绑定失败2");
            }
            return toResponsSuccess("谢谢支持");
        } catch (Exception e) {
            e.printStackTrace();
            return toResponsFail(e.getMessage());
        }
    }
    //解析电话号码
    public JSONObject getPhoneNumber(String session_key, String encryptedData, String iv) {
        byte[] dataByte = Base64.decode(encryptedData);

        byte[] keyByte = Base64.decode(session_key);

        byte[] ivByte = Base64.decode(iv);
        try {

            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
