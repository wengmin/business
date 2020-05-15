package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/5/13
 * @time 创建时间: 18:06
 */
public class CompanyEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    //
    private Integer companyId;
    //
    private String name;
    //
    private String province;
    //
    private String city;
    //
    private String county;
    //
    private String address;
    //
    private String phone;
    //
    private String logo;
    //规模
    private Integer scale;
    //行业
    private Integer trade;
    //公司简介
    private String introduction;
    //
    private Date createTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    private Date updateTime;

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
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取：
     */
    public String getPhone() {
        return phone;
    }
    /**
     * 设置：
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 获取：
     */
    public String getLogo() {
        return logo;
    }
    /**
     * 设置：规模
     */
    public void setScale(Integer scale) {
        this.scale = scale;
    }

    /**
     * 获取：规模
     */
    public Integer getScale() {
        return scale;
    }
    /**
     * 设置：行业
     */
    public void setTrade(Integer trade) {
        this.trade = trade;
    }

    /**
     * 获取：行业
     */
    public Integer getTrade() {
        return trade;
    }
    /**
     * 设置：公司简介
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 获取：公司简介
     */
    public String getIntroduction() {
        return introduction;
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
