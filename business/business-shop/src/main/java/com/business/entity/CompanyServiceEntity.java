package com.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体
 * 表名 company_service
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-14 13:46:23
 */
public class CompanyServiceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer serviceId;
    //
    private Integer companyId;

    private String companyName;
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    //服务类型
    private String serviceClass;
    //服务类型
    private String serviceTag;
    //标签
    private List<String> listServiceTag;
    //0启用，1禁用，2删除
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

    public String getServiceTag() {
        return serviceTag;
    }

    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }

    public List<String> getListServiceTag() {
        return listServiceTag;
    }

    public void setListServiceTag(List<String> listServiceTag) {
        this.listServiceTag = listServiceTag;
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
