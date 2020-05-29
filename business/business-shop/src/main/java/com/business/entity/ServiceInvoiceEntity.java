package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 * 表名 service_invoice
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-29 10:24:34
 */
public class ServiceInvoiceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private String companyName;
    //
    private String roomName;
    //
    private String nickname;
    //
    private String staffName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    //
    private Integer invoiceId;
    //
    private Integer companyId;
    //
    private Integer roomId;
    //
    private Integer userId;
    //
    private Integer staffId;
    //发票内容
    private String content;
    //类型：个人、单位
    private Integer titletype;
    //发票抬头、单位名称
    private String titlename;
    //发票税号
    private String taxno;
    //注册地址
    private String registaddress;
    //注册电话
    private String registphone;
    //开户银行
    private String bank;
    //银行账号
    private String bankaccount;
    //开票状态：invoiceStatus
    private Integer status;
    //备注
    private String remark;
    //回复
    private String reply;
    //
    private Date createTime;
    //
    private Date updateTime;

    /**
     * 设置：
     */
    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * 获取：
     */
    public Integer getInvoiceId() {
        return invoiceId;
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
     * 设置：发票内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取：发票内容
     */
    public String getContent() {
        return content;
    }
    /**
     * 设置：类型：个人、单位
     */
    public void setTitletype(Integer titletype) {
        this.titletype = titletype;
    }

    /**
     * 获取：类型：个人、单位
     */
    public Integer getTitletype() {
        return titletype;
    }
    /**
     * 设置：发票抬头、单位名称
     */
    public void setTitlename(String titlename) {
        this.titlename = titlename;
    }

    /**
     * 获取：发票抬头、单位名称
     */
    public String getTitlename() {
        return titlename;
    }
    /**
     * 设置：发票税号
     */
    public void setTaxno(String taxno) {
        this.taxno = taxno;
    }

    /**
     * 获取：发票税号
     */
    public String getTaxno() {
        return taxno;
    }
    /**
     * 设置：注册地址
     */
    public void setRegistaddress(String registaddress) {
        this.registaddress = registaddress;
    }

    /**
     * 获取：注册地址
     */
    public String getRegistaddress() {
        return registaddress;
    }
    /**
     * 设置：注册电话
     */
    public void setRegistphone(String registphone) {
        this.registphone = registphone;
    }

    /**
     * 获取：注册电话
     */
    public String getRegistphone() {
        return registphone;
    }
    /**
     * 设置：开户银行
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * 获取：开户银行
     */
    public String getBank() {
        return bank;
    }
    /**
     * 设置：银行账号
     */
    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    /**
     * 获取：银行账号
     */
    public String getBankaccount() {
        return bankaccount;
    }
    /**
     * 设置：开票状态：invoiceStatus
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：开票状态：invoiceStatus
     */
    public Integer getStatus() {
        return status;
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
     * 设置：回复
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     * 获取：回复
     */
    public String getReply() {
        return reply;
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
