package com.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/14
 * @time 创建时间: 18:06
 */
public class CardCollectVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer collectId;
    private Integer userId;
    private String userName;

    private String touserAvatar;
    private Integer touserId;
    private String touserName;
    private String touserComName;

    private Date createTime;

    public String getTouserAvatar() {
        return touserAvatar;
    }

    public void setTouserAvatar(String touserAvatar) {
        this.touserAvatar = touserAvatar;
    }

    public String getTouserComName() {
        return touserComName;
    }

    public void setTouserComName(String touserComName) {
        this.touserComName = touserComName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTouserName() {
        return touserName;
    }

    public void setTouserName(String touserName) {
        this.touserName = touserName;
    }

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTouserId() {
        return touserId;
    }

    public void setTouserId(Integer touserId) {
        this.touserId = touserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
