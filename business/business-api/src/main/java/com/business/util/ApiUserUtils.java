package com.business.util;

import com.business.utils.ResourceUtil;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:58<br>
 * 描述: ApiUserUtils <br>
 */
public class ApiUserUtils {

    //替换字符串
    public static String getWebAccess(String CODE) {
        return String.format(ResourceUtil.getConfigByName("wx.webAccessTokenhttps"),
                ResourceUtil.getConfigByName("wx.appId"),
                ResourceUtil.getConfigByName("wx.secret"),
                CODE);
    }

    //替换字符串
    public static String getAccessToken() {
        return String.format(ResourceUtil.getConfigByName("wx.getAccessToken"),
                ResourceUtil.getConfigByName("wx.appId"),
                ResourceUtil.getConfigByName("wx.secret"));
    }

    public static String getWXACodeUnlimit(String accessToken, String scene) {
        return String.format(ResourceUtil.getConfigByName("wx.getWXACodeUnlimit"), accessToken, scene);
    }

    //替换字符串（小程序发送微信消息）
    public static String getSendSubscribe(String access_token) {
        return String.format(ResourceUtil.getConfigByName("wx.sendSubscribe"), access_token);
    }

    //替换字符串
    public static String getUserMessage(String access_token, String openid) {
        return String.format(ResourceUtil.getConfigByName("wx.userMessage"), access_token, openid);
    }



    //公众号（获取基础接口的token）
    public static String getGzhCode(String redirect_uri, String scope) {
        return String.format(ResourceUtil.getConfigByName("wx.gzh.auth"), ResourceUtil.getConfigByName("wx.gzh.appId"), redirect_uri, scope);
    }

    //公众号（获取基础接口的token）
    public static String getGzhWebAccessToken(String code) {
        return String.format(ResourceUtil.getConfigByName("wx.gzh.getWebAccessToken"),
                ResourceUtil.getConfigByName("wx.gzh.appId"), ResourceUtil.getConfigByName("wx.gzh.secret"), code);
    }

    //公众号（获取基础接口的token）
    public static String getGzhAccessToken() {
        return String.format(ResourceUtil.getConfigByName("wx.getAccessToken"),
                ResourceUtil.getConfigByName("wx.gzh.appId"), ResourceUtil.getConfigByName("wx.gzh.secret"));
    }

    //公众号（获取用户信息）
    public static String getGzhUserInfo(String accessToken, String openId) {
        return String.format(ResourceUtil.getConfigByName("wx.gzh.getUserInfo"),
                ResourceUtil.getConfigByName("wx.gzh.appId"), ResourceUtil.getConfigByName("wx.gzh.secret"));
    }
}