package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 * 表名 documents_view
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2021-03-04 10:53:30
 */
public class DocumentsViewVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //
    private Integer documentsId;
    //
    private Integer pageId;
    //
    private Long viewTime;
    //
    private Integer userId;
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

    public Integer getDocumentsId() {
        return documentsId;
    }

    public void setDocumentsId(Integer documentsId) {
        this.documentsId = documentsId;
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
    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    /**
     * 获取：
     */
    public Integer getPageId() {
        return pageId;
    }
    /**
     * 设置：
     */
    public void setViewTime(Long viewTime) {
        this.viewTime = viewTime;
    }

    /**
     * 获取：
     */
    public Long getViewTime() {
        return viewTime;
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
