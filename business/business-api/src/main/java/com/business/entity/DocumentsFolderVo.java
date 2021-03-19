package com.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2021/3/18
 * @time 创建时间: 16:22
 */
public class DocumentsFolderVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer folderId;
    private String name;
    private Integer userId;
    //0公开，1隐私
    private Integer isOpen;
    //
    private String password;
    private Integer sortValue;
    //
    private Date createTime;
    //
    private Date updateTime;

    private List<DocumentsVo> docList;

    public List<DocumentsVo> getDocList() {
        return docList;
    }

    public void setDocList(List<DocumentsVo> docList) {
        this.docList = docList;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSortValue() {
        return sortValue;
    }

    public void setSortValue(Integer sortValue) {
        this.sortValue = sortValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
