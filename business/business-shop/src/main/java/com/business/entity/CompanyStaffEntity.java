package com.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    //对应后台账号
    private Integer sysUserId;
    //名片
    private Integer cardId;
    //是否管理员
    private Integer isAdmin;
    //
    private Date createTime;
    //
    private Date updateTime;

    private List<CompanyEntity> company;
    public List<CompanyEntity> getCompanyList() {
        return company;
    }
    public void setCompanyList(List<CompanyEntity> company) {
        this.company = company;
    }

    private List<CardEntity> card;
    public List<CardEntity> getCardList() {
        return card;
    }
    public void setCardList(List<CardEntity> card) {
        this.card = card;
    }

    private List<SysUserEntity> sysUser;
    public List<SysUserEntity> getSysUserList() {
        return sysUser;
    }
    public void setSysUserList(List<SysUserEntity> sysUser) {
        this.sysUser = sysUser;
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
    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * 获取：对应后台账号
     */
    public Integer getSysUserId() {
        return sysUserId;
    }
    /**
     * 设置：名片
     */
    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    /**
     * 获取：名片
     */
    public Integer getCardId() {
        return cardId;
    }
    /**
     * 设置：是否管理员
     */
    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * 获取：是否管理员
     */
    public Integer getIsAdmin() {
        return isAdmin;
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
