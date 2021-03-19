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
public class CardInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String param;

    //
    private String unionid;
    //
    private String openidXcx;
    //
    private String openidGzh;
    //
    private Integer cardId;
    //
    private Integer userId;
    //
    private Integer companyId;
    //
    private String companyName;
    //
    private String realname;
    //
    private String mobile;
    //
    private String photo;
    //
    private String photoRemark;
    //
    private String position;
    //
    private String wechat;
    //
    private String email;
    //
    private String profile;
    //
    private String qrCode;
    //
    private Integer isDefault;
    //
    private Date createTime;
    //
    private Date updateTime;
    //
    private CompanyVo company;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenidXcx() {
        return openidXcx;
    }

    public void setOpenidXcx(String openidXcx) {
        this.openidXcx = openidXcx;
    }

    public String getOpenidGzh() {
        return openidGzh;
    }

    public void setOpenidGzh(String openidGzh) {
        this.openidGzh = openidGzh;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoRemark() {
        return photoRemark;
    }

    public void setPhotoRemark(String photoRemark) {
        this.photoRemark = photoRemark;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
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

    public CompanyVo getCompany() {
        return company;
    }

    public void setCompany(CompanyVo company) {
        this.company = company;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}
