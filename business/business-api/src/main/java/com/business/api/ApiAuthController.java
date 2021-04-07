package com.business.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.annotation.IgnoreAuth;
import com.business.annotation.LoginUser;
import com.business.entity.LoginInfo;
import com.business.entity.UserVo;
import com.business.service.ApiUserService;
import com.business.service.ApiWeChatService;
import com.business.service.TokenService;
import com.business.util.ApiBaseAction;
import com.business.util.ApiUserUtils;
import com.business.util.QRCodeUtils;
import com.business.utils.CookieCode;
import com.business.utils.CookieUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private ApiWeChatService wechatService;
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
     * 自动登录接口
     */
    @IgnoreAuth
    @GetMapping("loginAuto")
    @ApiOperation(value = "自动登录接口")
    public Object loginAuto(String code) {
        String requestUrl = ApiUserUtils.getWebAccess(code);//通过自定义工具类组合出小程序需要的登录凭证 code
        logger.info("》》》组合token为：" + requestUrl);
        String res = restTemplate.getForObject(requestUrl, String.class);
        logger.info("loginByMiniPro json==" + res);
        JSONObject sessionData = JSON.parseObject(res);
        String openid = sessionData.getString("openid");
        String unionid = sessionData.getString("unionid");
        String session_key = sessionData.getString("session_key");//会话密钥

        UserVo user = userService.queryByUnionId(unionid);
        if (user != null) {
            user = userService.queryByOpenIdXcx(openid);
        } else {
            user = new UserVo();
            user.setOpenidXcx(openid);
            user.setUnionid(unionid);
            userService.saveEdit(user);
        }

        Map<String, Object> resultObj = new HashMap<String, Object>();
        resultObj.put("unionid", unionid);
        resultObj.put("openid", openid);
        resultObj.put("user", user);
        resultObj.put("sessionKey", session_key);
        return toResponsSuccess(resultObj);
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
        logger.info("loginByWeixin json==" + res);
        JSONObject sessionData = JSON.parseObject(res);
        String openid = sessionData.getString("openid");
        String unionid = sessionData.getString("unionid");
        logger.info("》》》promoterId：" + loginInfo.getPromoterId());
        String session_key = sessionData.getString("session_key");//不知道啥用。

        if (null == sessionData || StringUtils.isNullOrEmpty(openid) || StringUtils.isNullOrEmpty(unionid)) {
            logger.info("loginByWeixin.登录失败，sessionData=》" + sessionData + "，openid=》" + openid + "，unionid=》" + unionid);
            return toResponsFail("登录失败");
        }
        //验证用户信息完整性 防止攻击
        //String sha1 = CommonUtil.getSha1(fullUserInfo.getRawData() + sessionData.getString("session_key"));
        //if (!fullUserInfo.getSignature().equals(sha1)) {
        //	 logger.info("登录失败---验证用户信息完整性"+fullUserInfo.getSignature());
        //	 logger.info("登录失败---验证用户信息完整性 sha1"+sha1);
        //    return toResponsFail("登录失败");
        //}

        UserVo userVo = new UserVo();
        userVo.setUnionid(unionid);
        userVo.setNickname(loginInfo.getNickName());
        userVo.setOpenidXcx(openid);
        //userVo.setRegister_ip(this.getClientIp());
        //userVo.setLast_login_ip(userVo.getRegister_ip());
        //userVo.setLast_login_time(userVo.getRegister_time());
        userVo.setHeadimgurl(loginInfo.getAvatarUrl());
        userVo.setSex(loginInfo.getGender()); // //性别 0：未知、1：男、2：女
        userVo.setProvince(loginInfo.getProvince());
        userVo.setCity(loginInfo.getCity());
        userVo.setCountry(loginInfo.getCountry());
        userVo.setLanguage(loginInfo.getLanguage());
        userService.saveEdit(userVo);

        UserVo user = userService.queryByOpenIdXcx(openid);

        Map<String, Object> tokenMap = tokenService.createToken(user.getUserId());
        String token = MapUtils.getString(tokenMap, "token");

        if (StringUtils.isNullOrEmpty(token)) {
            return toResponsFail("登录失败");
        }
        Map<String, Object> resultObj = new HashMap<String, Object>();
        resultObj.put("unionid", unionid);
        resultObj.put("openid", openid);
        resultObj.put("userVo", userVo);
        resultObj.put("sessionKey", session_key);
        return toResponsSuccess(resultObj);
    }

    /**
     * 无感知登录
     */
    @ApiOperation(value = "无感知登录")
    @IgnoreAuth
    @GetMapping("loginBySilence")
    public Object loginBySilence(@RequestParam("code") String code) {
        //获取openid
        String requestUrl = ApiUserUtils.getWebAccess(code);//通过自定义工具类组合出小程序需要的登录凭证 code
        String res = restTemplate.getForObject(requestUrl, String.class);
        JSONObject sessionData = JSON.parseObject(res);
        String openid = sessionData.getString("openid");
        String unionid = sessionData.getString("unionid");
        String session_key = sessionData.getString("session_key");

        if (null == sessionData || StringUtils.isNullOrEmpty(openid)) {
            logger.info("loginBySilence.登录失败=》" + sessionData);
            return toResponsFail("登录失败");
        }

        UserVo userVo = userService.queryByOpenIdXcx(openid);
        if (StringUtils.isNullOrEmpty(unionid) && userVo != null) {
            unionid = userVo.getUnionid();
        }
        Map<String, Object> tokenMap = tokenService.createToken(userVo.getUserId());
        String token = MapUtils.getString(tokenMap, "token");

        if (StringUtils.isNullOrEmpty(token)) {
            return toResponsFail("登录失败");
        }
        Map<String, Object> resultObj = new HashMap<String, Object>();
        resultObj.put("unionid", unionid);
        resultObj.put("openid", openid);
        resultObj.put("userVo", userVo);
        resultObj.put("sessionKey", session_key);
        return toResponsSuccess(resultObj);
    }

    /**
     * 解析电话号码
     */
    @ApiOperation(value = "解析电话号码")
    @GetMapping("getPhoneNumber")
    public Object getPhoneNumber(@LoginUser UserVo loginUser, String sessionKey, String encryptedData, String iv) {
        try {
            if (StringUtils.isNullOrEmpty(sessionKey) || StringUtils.isNullOrEmpty(encryptedData) || StringUtils.isNullOrEmpty(iv)) {
                return toResponsFail("参数错误");
            }
            JSONObject object = wechatService.analysis(sessionKey, encryptedData, iv);
            String mobile = object.getString("phoneNumber");
            if (!StringUtils.isNullOrEmpty(mobile)) {
                UserVo user = new UserVo();
                user.setUnionid(loginUser.getUnionid());
                user.setMobile(mobile);
                userService.updateByUnionId(user);
                return toResponsSuccess(mobile);
            }
        } catch (Exception e) {
            logger.error("手机号码获取异常:" + e.getMessage());
        }
        return toResponsFail("手机号码获取失败");
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

    /**
     * 用户授权微信登录接口
     *
     * @throws IOException
     */
    @ApiOperation(value = "用户授权微信登录接口")
    @IgnoreAuth
    @RequestMapping("gzhAuth")
    public void gzhAuth(HttpServletResponse response, HttpServletRequest request) throws IOException {
        //这里是回调的url
        String pageUrl = request.getRequestURL() + "?" + request.getQueryString();
        System.out.println("gzhAuth OK! url=》" + pageUrl);
        CookieUtils.setCookie(response, CookieCode.Url, pageUrl);
        // Cookie cookie =response.addCookie("cookiename","cookievalue");
        String redirect_uri = java.net.URLEncoder.encode("http://emiaoweb.com/business/api/auth/callBack", "UTF-8");
        String url = ApiUserUtils.getGzhCode(redirect_uri, "snsapi_userinfo");
        response.sendRedirect(url);
    }

    /**
     * Tea微信授权成功的回调函数
     *
     * @param request
     * @param response
     */
    @ApiOperation(value = "微信授权回调接口")
    @IgnoreAuth
    @RequestMapping("/callBack")
    protected void callBack(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //获取回调地址中的code
        String code = request.getParameter("code");
        //拼接url
        String url = ApiUserUtils.getGzhWebAccessToken(code);
        String res = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSON.parseObject(res);
        //1.获取微信用户的openid
        String openid = jsonObject.getString("openid");
        //2.获取获取access_token
        String access_token = jsonObject.getString("access_token");

        String infoUrl = ApiUserUtils.getUserMessage(access_token, openid);
        //3.获取微信用户信息
        String resUser = restTemplate.getForObject(infoUrl, String.class);
        JSONObject userInfo = JSON.parseObject(resUser);
        //String openid = userInfo.getString("openid");
        String nickname = userInfo.getString("nickname");
        Integer sex = userInfo.getInteger("sex");
        String province = userInfo.getString("province");
        String city = userInfo.getString("city");
        String country = userInfo.getString("country");
        String headimgurl = userInfo.getString("headimgurl");
        String privilege = userInfo.getString("privilege");
        String unionId = userInfo.getString("unionid");
        //去数据库查询此微信是否绑定过手机
        UserVo user = new UserVo();
        user.setNickname(nickname);
        user.setOpenidGzh(openid);
        user.setUnionid(unionId);
        user.setSex(sex);
        user.setProvince(province);
        user.setCity(city);
        user.setCountry(country);
        user.setHeadimgurl(headimgurl);
        userService.saveEdit(user);

        String pageUrl = CookieUtils.getCookieValue(request, CookieCode.Url);
        CookieUtils.removeCookie(request, response, CookieCode.Url);
        System.out.println("callBack OK! url=》" + pageUrl);
        response.sendRedirect(pageUrl);
    }
}
