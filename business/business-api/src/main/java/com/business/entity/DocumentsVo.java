package com.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体
 * 表名 documents
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2021-03-04 10:53:30
 */
public class DocumentsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //
    private Integer folderId;
    //
    private String cover;
    //
    private String name;
    //
    private String profiles;
    //0公开，1隐私
    private Integer isOpen;
    //
    private String password;
    //
    private Integer userId;
    //
    private Date createTime;
    //
    private Date updateTime;
    //
    private List<DocumentsPageVo> pageList;

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

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    /**
     * 设置：
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * 获取：
     */
    public String getCover() {
        return cover;
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

    public String getProfiles() {
        return profiles;
    }

    public void setProfiles(String profiles) {
        this.profiles = profiles;
    }

    /**
     * 设置：0公开，1隐私
     */
    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * 获取：0公开，1隐私
     */
    public Integer getIsOpen() {
        return isOpen;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：
     */
    public Date getCreateTime() {
        return createTime;
    }

    public List<DocumentsPageVo> getPageList() {
        return pageList;
    }

    public void setPageList(List<DocumentsPageVo> pageList) {
        this.pageList = pageList;
    }
}
