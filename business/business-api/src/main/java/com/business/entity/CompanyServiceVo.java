package com.business.entity;

import java.io.Serializable;

/**
 * 实体
 * 表名 company_service
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-21 17:42:29
 */
public class CompanyServiceVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer serviceId;
    //
    private Integer companyId;
    //服务类型
    private String serviceClass;
    //标签
    private String serviceTag;

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
    /**
     * 设置：标签
     */
    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }

    /**
     * 获取：标签
     */
    public String getServiceTag() {
        return serviceTag;
    }
}
