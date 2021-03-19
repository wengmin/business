package com.business.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/6/1
 * @time 创建时间: 17:40
 */
public class WeChatTemplateVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String touser;
    private String template_id;
    private String page;
    private List<WeChatTemplateDataVo> templateParamList;
    private Map<String, WeChatTemplateDataVo> data;//推送文字


    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Map<String, WeChatTemplateDataVo> getData() {
        return data;
    }

    public void setData(Map<String, WeChatTemplateDataVo> data) {
        this.data = data;
    }
}
