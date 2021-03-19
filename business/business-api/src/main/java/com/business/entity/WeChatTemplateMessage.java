package com.business.entity;

import java.util.TreeMap;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：公众号发送消息模板（跳转小程序）
 * @date 创建日期：2020/7/29
 * @time 创建时间: 19:36
 */
public class WeChatTemplateMessage {
    private String touser; // 接收者openid

    private String template_id; // 模板ID

    private String url; // 模板跳转链接

    private TreeMap<String, TreeMap<String, String>> data; // data数据

    // 新增
    private TreeMap<String, String> miniprogram; // 跳小程序所需数据，不需跳小程序可不用传该数据

    /**
     * 参数
     *
     * @param value
     * @param color
     *            可不填
     * @return
     */
    public static TreeMap<String, String> item(String value, String color) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("value", value);
        params.put("color", color);
        return params;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TreeMap<String, TreeMap<String, String>> getData() {
        return data;
    }

    public void setData(TreeMap<String, TreeMap<String, String>> data) {
        this.data = data;
    }

    public TreeMap<String, String> getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(TreeMap<String, String> miniprogram) {
        this.miniprogram = miniprogram;
    }
}
