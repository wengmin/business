package com.business.entity;

import java.io.Serializable;

/**
 * 实体
 * 表名 company_room
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-21 17:42:29
 */
public class CompanyRoomVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private String servicePhone;

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    //
    private Integer roomId;
    //
    private Integer companyId;
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
}
