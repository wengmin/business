package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 * 表名 company_staff
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-14 13:46:23
 */
public class CompanyStaffEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer staffId;
    //企业编号
    private Integer companyId;
    private String companyName;
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    //对应后台账号
    private String name;
    //名片
    private String mobile;
    //0未绑定，1待审核，2已审核，3离职
    private String post;

    private Integer userId;
    //0未绑定，1待审核，2删除，3已审核，4离职
    private Integer status;
    //
    private Date createTime;
    //
    private Date updateTime;

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
     * 设置：企业编号
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取：企业编号
     */
    public Integer getCompanyId() {
        return companyId;
    }
    /**
     * 设置：对应后台账号
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：对应后台账号
     */
    public String getName() {
        return name;
    }
    /**
     * 设置：名片
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取：名片
     */
    public String getMobile() {
        return mobile;
    }
    /**
     * 设置：是否管理员0否1是
     */
    public void setPost(String post) {
        this.post = post;
    }

    /**
     * 获取：是否管理员0否1是
     */
    public String getPost() {
        return post;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 设置：0在职，1离职
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：0在职，1离职
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
