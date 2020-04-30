package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 * 表名 card_user
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-04-28 15:10:37
 */
public class CardEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer cardId;
    //
    private Integer userId;
    //
    private Integer companyId;
    private String companyName;
    private String nickname;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 设置：
     */
    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    /**
     * 获取：
     */
    public Integer getCardId() {
        return cardId;
    }
    /**
     * 设置：
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取：
     */
    public Integer getUserId() {
        return userId;
    }
    /**
     * 设置：
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取：
     */
    public Integer getCompanyId() {
        return companyId;
    }
    /**
     * 设置：
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * 获取：
     */
    public String getRealname() {
        return realname;
    }
    /**
     * 设置：
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * 获取：
     */
    public String getPhoto() {
        return photo;
    }
    /**
     * 设置：
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 获取：
     */
    public String getPosition() {
        return position;
    }
    /**
     * 设置：
     */
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    /**
     * 获取：
     */
    public String getWechat() {
        return wechat;
    }
    /**
     * 设置：
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取：
     */
    public String getEmail() {
        return email;
    }
    /**
     * 设置：
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * 获取：
     */
    public String getProfile() {
        return profile;
    }
    /**
     * 设置：
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取：
     */
    public String getProvince() {
        return province;
    }
    /**
     * 设置：
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取：
     */
    public String getCity() {
        return city;
    }
    /**
     * 设置：
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * 获取：
     */
    public String getCounty() {
        return county;
    }
    /**
     * 设置：
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取：
     */
    public String getAddress() {
        return address;
    }
    /**
     * 设置：
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取：
     */
    public String getTelephone() {
        return telephone;
    }
    /**
     * 设置：
     */
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    /**
     * 获取：
     */
    public String getQrCode() {
        return qrCode;
    }
    /**
     * 设置：
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：
     */
    public Date getCreateTime() {
        return createTime;
    }
}
