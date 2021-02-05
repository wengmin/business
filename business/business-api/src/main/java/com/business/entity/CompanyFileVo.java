package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 * 表名 company_file
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-21 17:42:29
 */
public class CompanyFileVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //
    private Integer companyId;
    //
    private String fileurl;
    //
    private String name;
    //0启用，1禁用，2删除
    private Integer status;
    //
    private Date createTime;

    /**
     * 设置：
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：
     */
    public Integer getId() {
        return id;
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
    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    /**
     * 获取：
     */
    public String getFileurl() {
        return fileurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 设置：0启用，1禁用，2删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：0启用，1禁用，2删除
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
}
