package com.business.dao;

import com.business.entity.WeChatMessageVo;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/6/2
 * @time 创建时间: 12:22
 */
public interface ApiWeChatMapper extends BaseDao<WeChatMessageVo>{
    int saveMessage(WeChatMessageVo message);
    int updateMessage(WeChatMessageVo message);
}
