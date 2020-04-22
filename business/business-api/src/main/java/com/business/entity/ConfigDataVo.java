package com.business.entity;

import java.io.Serializable;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/6
 * @time 创建时间: 10:38
 */
public class ConfigDataVo  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String key;
    private String value;
    private String remark;
    private String type;
    private Integer status;
    private String icon;

    public Integer getOrder() {
        return order_value;
    }

    public void setOrder(Integer order_value) {
        this.order_value = order_value;
    }

    private Integer order_value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
