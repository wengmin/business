package com.business.entity;

import java.io.Serializable;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/6/1
 * @time 创建时间: 17:13
 */
public class WeChatTemplateDataVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String value;//

    public WeChatTemplateDataVo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
