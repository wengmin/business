package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 * 表名 service_room_log
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-29 10:24:34
 */
public class ServiceRoomLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer logId;
    //
    private Integer serviceId;
    //
    private Integer staffId;
    //
    private Integer status;
    //
    private String remark;
    //
    private Date createTime;

    /**
     * 设置：
     */
    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    /**
     * 获取：
     */
    public Integer getLogId() {
        return logId;
    }
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
    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    /**
     * 获取：
     */
    public Integer getStaffId() {
        return staffId;
    }
    /**
     * 设置：
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * 设置：
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取：
     */
    public String getRemark() {
        return remark;
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
