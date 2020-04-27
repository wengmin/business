package com.business.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.annotation.IgnoreAuth;
import com.business.entity.LoginInfo;
import com.business.entity.UserVo;
import com.business.service.ApiUserService;
import com.business.service.TokenService;
import com.business.util.ApiBaseAction;
import com.business.util.ApiUserUtils;
import com.business.utils.Base64;
import com.business.utils.R;
import com.business.validator.Assert;
import com.qiniu.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    @Autowired
    private ApiUserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RestTemplate restTemplate;

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
        long userId = 0;//userService.login(mobile, password);

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
            logger.info("loginByWeixin.登录失败=》" + sessionData);
            return toResponsFail("登录失败");
        }
        //验证用户信息完整性 防止攻击
        //String sha1 = CommonUtil.getSha1(fullUserInfo.getRawData() + sessionData.getString("session_key"));
        //if (!fullUserInfo.getSignature().equals(sha1)) {
        //	 logger.info("登录失败---验证用户信息完整性"+fullUserInfo.getSignature());
        //	 logger.info("登录失败---验证用户信息完整性 sha1"+sha1);
        //    return toResponsFail("登录失败");
        //}
        Date nowTime = new Date();
        UserVo userVo = userService.queryByOpenId(openid);
        if (null == userVo) {
            userVo = new UserVo();
            userVo.setNickname(Base64.encode(loginInfo.getNickName()));
            userVo.setOpenid(openid);
            userVo.setCreateTime(nowTime);
            //userVo.setRegister_ip(this.getClientIp());
            //userVo.setLast_login_ip(userVo.getRegister_ip());
            //userVo.setLast_login_time(userVo.getRegister_time());
            userVo.setAvatar(loginInfo.getAvatarUrl());
            userVo.setGender(loginInfo.getGender()); // //性别 0：未知、1：男、2：女
            userVo.setProvince(loginInfo.getProvince());
            userVo.setCity(loginInfo.getCity());
            userVo.setCountry(loginInfo.getCountry());
            userService.save(userVo);
        } else {
            userVo.setNickname(Base64.encode(loginInfo.getNickName()));
            userVo.setOpenid(openid);
            userVo.setAvatar(loginInfo.getAvatarUrl());
            userVo.setGender(loginInfo.getGender()); // //性别 0：未知、1：男、2：女
            userVo.setProvince(loginInfo.getProvince());
            userVo.setCity(loginInfo.getCity());
            userVo.setCountry(loginInfo.getCountry());
            userService.updateByOpenId(userVo);
        }
        ////生成推广二维码
        //if (StringUtils.isNullOrEmpty(userVo.getQrCode())) {
        //    UserVo uVo = new UserVo();
        //    String accessToken = tokenService.getAccessToken();
        //    uVo.setUserId(userVo.getUserId());
        //    uVo.setQrCode(QRCodeUtils.getCardQrCode(accessToken, String.valueOf(userVo.getUserId())));
        //    userService.update(uVo);
        //}

        Map<String, Object> tokenMap = tokenService.createToken(userVo.getUserId());
        String token = MapUtils.getString(tokenMap, "token");

        if (StringUtils.isNullOrEmpty(token)) {
            return toResponsFail("登录失败");
        }
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //resultObj.put("openid", openid);
        resultObj.put("userVo", userVo);
        return toResponsSuccess(resultObj);
    }

    /**
     * 无感知登录
     */
    @ApiOperation(value = "无感知登录")
    @IgnoreAuth
    @PostMapping("loginBySilence")
    public Object loginBySilence(@RequestParam("code") String code) {
        //获取openid
        String requestUrl = ApiUserUtils.getWebAccess(code);//通过自定义工具类组合出小程序需要的登录凭证 code
        String res = restTemplate.getForObject(requestUrl, String.class);
        JSONObject sessionData = JSON.parseObject(res);
        String openid = sessionData.getString("openid");

        if (null == sessionData || StringUtils.isNullOrEmpty(openid)) {
            logger.info("loginBySilence.登录失败=》" + sessionData);
            return toResponsFail("登录失败");
        }

        UserVo userVo = userService.queryByOpenId(openid);
        if (null == userVo) {
            userVo = new UserVo();
            userVo.setOpenid(openid);
            userVo.setCreateTime(new Date());
            userService.save(userVo);
        }

        Map<String, Object> tokenMap = tokenService.createToken(userVo.getUserId());
        String token = MapUtils.getString(tokenMap, "token");

        if (StringUtils.isNullOrEmpty(token)) {
            return toResponsFail("登录失败");
        }
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //resultObj.put("openid", openid);
        resultObj.put("userVo", userVo);
        return toResponsSuccess(resultObj);
    }

}
