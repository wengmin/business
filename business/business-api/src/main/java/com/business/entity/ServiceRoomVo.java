package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 * 表名 service_room
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-21 17:42:29
 */
public class ServiceRoomVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer serviceId;
    //
    private Integer companyId;
    //房间号
    private Integer roomId;
    //房间号
    private String roomName;
    //
    private Integer userId;
    //服务类型
    private String serviceClass;
    //项
    private String serviceTag;
    //备注
    private String remark;
    //预约时间
    private Date appointmentTime;
    //状态
    private Integer status;
    //
    private Date createTime;
    //
    private Date updateTime;

    /**
     * 设置：
     */
    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * 获取：
     */
    public Integer getServiceId() {
        return serviceId;
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
     * 设置：房间号
     */
    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    /**
     * 获取：房间号
     */
    public Integer getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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
     * 设置：服务类型
     */
    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    /**
     * 获取：服务类型
     */
    public String getServiceClass() {
        return serviceClass;
    }
    /**
     * 设置：项
     */
    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }

    /**
     * 获取：项
     */
    public String getServiceTag() {
        return serviceTag;
    }
    /**
     * 设置：备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取：备注
     */
    public String getRemark() {
        return remark;
    }
    /**
     * 设置：预约时间
     */
    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * 获取：预约时间
     */
    public Date getAppointmentTime() {
        return appointmentTime;
    }
    /**
     * 设置：状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：状态
     */
    public Integer getStatus() {
        return status;
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
