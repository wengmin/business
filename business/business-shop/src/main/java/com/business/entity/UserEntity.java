package com.business.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 会员实体
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-16 15:02:28
 */
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer userId;
    //
    private String openid;
    //
    private String mobile;
    //
    private String nickname;
    //
    private String avatar;
    //
    private Integer gender;
    //
    private String country;
    //
    private String province;
    //
    private String city;
    //
    private Date createTime;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getOpenid() {
        return openid;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getMobile() {
        return mobile;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public Integer getGender() {
        return gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getCountry() {
        return country;
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

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getCreateTime() {
        return createTime;
    }
}
