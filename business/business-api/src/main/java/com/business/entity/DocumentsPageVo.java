package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 * 表名 documents_page
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2021-03-04 10:53:30
 */
public class DocumentsPageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer pageId;
    //
    private Integer documentsId;
    //
    private String name;
    //
    private String url;
    //
    private Integer sortValue;
    //
    private Date createTime;

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

    public Integer getDocumentsId() {
        return documentsId;
    }

    public void setDocumentsId(Integer documentsId) {
        this.documentsId = documentsId;
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
    /**
     * 设置：
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取：
     */
    public String getUrl() {
        return url;
    }

    public Integer getSortValue() {
        return sortValue;
    }

    public void setSortValue(Integer sortValue) {
        this.sortValue = sortValue;
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
