package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/23
 * @time 创建时间: 9:17
 */
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    //手机号码
    private String mobile;
    //只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
    private String unionid;
    //用户的标识，对当前公众号唯一
    private String openidGzh;
    //用户的标识，对当前小程序唯一
    private String openidXcx;
    //用户昵称
    private String nickname;
    //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private Integer sex;
    //省份
    private String province;
    //用户所在城市
    private String city;
    //国家
    private String country;
    //用户的语言，简体中文为zh_CN
    private String language;
    //用户头像
    private String headimgurl;
    //用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
    private Integer subscribe;
    //用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
    private Date subscribeTime;
    //返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他
    private String subscribeScene;
    //公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
    private String remark;
    //用户所在的分组ID（兼容旧的用户分组接口）
    private Integer groupid;
    //用户被打上的标签ID列表
    private String tagidList;
    //二维码扫码场景（开发者自定义）
    private Integer qrScene;
    //二维码扫码场景描述（开发者自定义）
    private String qrSceneStr;

    private Date createTime;
    private Date updateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenidGzh() {
        return openidGzh;
    }

    public void setOpenidGzh(String openidGzh) {
        this.openidGzh = openidGzh;
    }

    public String getOpenidXcx() {
        return openidXcx;
    }

    public void setOpenidXcx(String openidXcx) {
        this.openidXcx = openidXcx;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getSubscribeScene() {
        return subscribeScene;
    }

    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getTagidList() {
        return tagidList;
    }

    public void setTagidList(String tagidList) {
        this.tagidList = tagidList;
    }

    public Integer getQrScene() {
        return qrScene;
    }

    public void setQrScene(Integer qrScene) {
        this.qrScene = qrScene;
    }

    public String getQrSceneStr() {
        return qrSceneStr;
    }

    public void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
