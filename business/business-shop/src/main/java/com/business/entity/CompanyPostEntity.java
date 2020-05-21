package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 * 表名 company_post
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-18 17:09:25
 */
public class CompanyPostEntity  implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer postId;
    //
    private Integer companyId;
    private String companyName;
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    //
    private String photo;
    //
    private String name;
    //
    private String position;
    //
    private String mobile;
    //
    private String telephone;
    //
    private String wechat;
    //
    private String email;
    //
    private String profile;
    //
    private String qrCode;
    //0启用，1禁用，2删除
    private Integer status;
    //
    private Date createTime;
    //
    private Date updateTime;

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
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：
     */
    public String getName() {
        return name;
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
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取：
     */
    public String getMobile() {
        return mobile;
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
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    /**
     * 获取：
     */
    public String getQrCode() {
        return qrCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    /**
     * 设置：
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：
     */
    public Date getUpdateTime() {
        return updateTime;
    }
}
