package com.business.entity;

import java.io.Serializable;

/**
 * 实体
 * 表名 company_post
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-21 17:42:29
 */
public class CompanyPostVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer postId;
    //
    private Integer companyId;
    //照片
    private String photo;
    //姓名
    private String name;
    //职位
    private String position;
    //手机号码
    private String mobile;
    //固定电话
    private String telephone;
    //微信号
    private String wechat;
    //电子邮箱
    private String email;
    //职位简介
    private String profile;
    //二维码
    private String qrCode;

    /**
     * 设置：
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    /**
     * 获取：
     */
    public Integer getPostId() {
        return postId;
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
     * 设置：照片
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * 获取：照片
     */
    public String getPhoto() {
        return photo;
    }
    /**
     * 设置：姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：姓名
     */
    public String getName() {
        return name;
    }
    /**
     * 设置：职位
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 获取：职位
     */
    public String getPosition() {
        return position;
    }
    /**
     * 设置：手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取：手机号码
     */
    public String getMobile() {
        return mobile;
    }
    /**
     * 设置：固定电话
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取：固定电话
     */
    public String getTelephone() {
        return telephone;
    }
    /**
     * 设置：微信号
     */
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    /**
     * 获取：微信号
     */
    public String getWechat() {
        return wechat;
    }
    /**
     * 设置：电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取：电子邮箱
     */
    public String getEmail() {
        return email;
    }
    /**
     * 设置：职位简介
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * 获取：职位简介
     */
    public String getProfile() {
        return profile;
    }
    /**
     * 设置：二维码
     */
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    /**
     * 获取：二维码
     */
    public String getQrCode() {
        return qrCode;
    }
}
