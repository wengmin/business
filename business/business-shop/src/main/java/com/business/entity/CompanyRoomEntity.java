package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/5/13
 * @time 创建时间: 18:19
 */
public class CompanyRoomEntity  implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer roomId;
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
    private String name;
    //
    private String tung;
    //
    private String floor;
    //
    private String wifiname;
    //
    private String wifipass;
    //
    private String wifimac;
    //
    private String qrcode;
    //
    private Date createTime;
    //房价二维码
    private Date updateTime;

    /**
     * 设置：
     */
    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    /**
     * 获取：
     */
    public Integer getRoomId() {
        return roomId;
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
    public void setTung(String tung) {
        this.tung = tung;
    }

    /**
     * 获取：
     */
    public String getTung() {
        return tung;
    }
    /**
     * 设置：
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * 获取：
     */
    public String getFloor() {
        return floor;
    }
    /**
     * 设置：
     */
    public void setWifiname(String wifiname) {
        this.wifiname = wifiname;
    }

    /**
     * 获取：
     */
    public String getWifiname() {
        return wifiname;
    }
    /**
     * 设置：
     */
    public void setWifipass(String wifipass) {
        this.wifipass = wifipass;
    }

    /**
     * 获取：
     */
    public String getWifipass() {
        return wifipass;
    }
    /**
     * 设置：
     */
    public void setWifimac(String wifimac) {
        this.wifimac = wifimac;
    }

    /**
     * 获取：
     */
    public String getWifimac() {
        return wifimac;
    }
    /**
     * 设置：
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    /**
     * 获取：
     */
    public String getQrcode() {
        return qrcode;
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
     * 设置：房价二维码
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：房价二维码
     */
    public Date getUpdateTime() {
        return updateTime;
    }
}
