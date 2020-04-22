package com.business.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.annotation.IgnoreAuth;
import com.business.entity.CustomerUserVo;
import com.business.entity.LoginInfo;
import com.business.oss.OSSFactory;
import com.business.service.ApiCustomerUserService;
import com.business.service.ApiUserService;
import com.business.service.MlsUserSer;
import com.business.service.TokenService;
import com.business.util.ApiBaseAction;
import com.business.util.ApiUserUtils;
import com.business.util.QRCodeUtils;
import com.business.utils.R;
import com.business.validator.Assert;
import com.qiniu.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * API登录授权
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-03-23 15:31
 */
@Api(tags = "API登录授权接口")
@RestController
@RequestMapping("/api/auth")
public class ApiAuthController extends ApiBaseAction {
    private Logger logger = Logger.getLogger(getClass());
    @Autowired
    private ApiUserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MlsUserSer mlsUserSer;
    @Autowired
    private ApiCustomerUserService customerUserService;

    /**
     * 登录
     */
    @IgnoreAuth
    @PostMapping("login")
    @ApiOperation(value = "登录接口")
    public R login(String mobile, String password) {
        Assert.isBlank(mobile, "手机号不能为空");
        Assert.isBlank(password, "密码不能为空");

        //用户登录
        long userId = userService.login(mobile, password);

        //生成token
        Map<String, Object> map = tokenService.createToken(userId);

        return R.ok(map);
    }

    /**
     * 登录
     */
    @ApiOperation(value = "登录")
    @IgnoreAuth
    @PostMapping("login_by_weixin")
    public Object loginByWeixin(@RequestBody LoginInfo loginInfo, HttpServletRequest request) {

        //获取openid
        String requestUrl = ApiUserUtils.getWebAccess(loginInfo.getCode());//通过自定义工具类组合出小程序需要的登录凭证 code
        logger.info("》》》组合token为：" + requestUrl);
        String res = restTemplate.getForObject(requestUrl, String.class);
        logger.info("res==" + res);
        JSONObject sessionData = JSON.parseObject(res);
        String openid = sessionData.getString("openid");
        logger.info("》》》promoterId：" + loginInfo.getPromoterId());
        String session_key = sessionData.getString("session_key");//不知道啥用。

        if (null == sessionData || StringUtils.isNullOrEmpty(openid)) {
            return toResponsFail("登录失败");
        }
        //验证用户信息完整性 防止攻击
//        String sha1 = CommonUtil.getSha1(fullUserInfo.getRawData() + sessionData.getString("session_key"));
//        if (!fullUserInfo.getSignature().equals(sha1)) {
//        	 logger.info("登录失败---验证用户信息完整性"+fullUserInfo.getSignature());
//        	 logger.info("登录失败---验证用户信息完整性 sha1"+sha1);
//            return toResponsFail("登录失败");
//        }
        Date nowTime = new Date();
        CustomerUserVo customerUserVo = customerUserService.queryByOpenId(openid);
        if (null == customerUserVo) {
            customerUserVo = new CustomerUserVo();

            //customerUserVo.setName(Base64.encode(loginInfo.getNickName()));
            customerUserVo.setNickname(loginInfo.getNickName());
            customerUserVo.setWeixin_openid(openid);
            customerUserVo.setCreateTime(nowTime);
            //customerUserVo.setRegister_ip(this.getClientIp());
            //userVo.setLast_login_ip(userVo.getRegister_ip());
            //userVo.setLast_login_time(userVo.getRegister_time());
            customerUserVo.setAvatar(loginInfo.getAvatarUrl());
            customerUserVo.setGender(loginInfo.getGender()); // //性别 0：未知、1：男、2：女
            customerUserService.save(customerUserVo);
        }
        //生成推广二维码
        if (StringUtils.isNullOrEmpty(customerUserVo.getQrCode())) {
            CustomerUserVo uVo = new CustomerUserVo();
            String accessToken = tokenService.getAccessToken();
            uVo.setUserId(customerUserVo.getUserId());
            uVo.setQrCode(QRCodeUtils.getCardQrCode(accessToken, String.valueOf(customerUserVo.getUserId())));
            customerUserService.update(uVo);
        }

        Map<String, Object> tokenMap = tokenService.createToken(customerUserVo.getUserId());
        String token = MapUtils.getString(tokenMap, "token");

        if (StringUtils.isNullOrEmpty(token)) {
            return toResponsFail("登录失败");
        }
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //resultObj.put("openid", openid);
        resultObj.put("userVo", customerUserVo);
        return toResponsSuccess(resultObj);
    }


    /**
     * 登录
     */
    @ApiOperation(value = "生产二维码")
    @IgnoreAuth
    @GetMapping("createCode")
    public Object createCode(String scene) {

        System.out.println(tokenService.getAccessToken());


        return toResponsSuccess(null);
    }


    /**
     * 上传文件
     *
     * @param file 文件
     * @return R
     * @throws Exception 异常
     */
    @ApiOperation(value = "上传文件")
    @IgnoreAuth
    @PostMapping("upload")
    public Object upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return toResponsFail("上传文件不能为空");
        }
        try {
            //上传文件
            String url = OSSFactory.build().upload(file);
            //保存文件信息
            //SysOssEntity ossEntity = new SysOssEntity();
            //ossEntity.setUrl(url);
            //ossEntity.setCreateDate(new Date());
            //sysOssService.save(ossEntity);

            return toResponsSuccess(url);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return toResponsFail("上传文件不能为空");
    }
}
