package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/23
 * @time 创建时间: 11:44
 */
public class CardUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    //
    private Integer cardId;
    //
    private Integer userId;
    //
    private Integer companyId;
    private String companyName;
    private String openid;
    //
    private String avatar;
    //
    private String mobile;
    //
    private String realname;
    //
    private String photo;
    //
    private String position;
    //
    private String wechat;
    //
    private String email;
    //
    private String profile;
    //
    private String province;
    //
    private String city;
    //
    private String county;
    //
    private String address;
    //
    private String telephone;
    //
    private String qrCode;
    //
    private Date createTime;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }
    public Integer getCardId() {
        return cardId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    public Integer getCompanyId() {
        return companyId;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getRealname() {
        return realname;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return position;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
    public String getWechat() {
        return wechat;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
    public String getProfile() {
        return profile;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getProvince() {
        return province;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return city;
    }

    public void setCounty(String county) {
        this.county = county;
    }
    public String getCounty() {
        return county;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return telephone;
    }

    public String getQrCode() {
        return qrCode;
    }
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
