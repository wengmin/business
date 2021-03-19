package com.business.dao;

import com.business.entity.CardInfoVo;

import java.util.List;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/23
 * @time 创建时间: 12:24
 */
public interface ApiCardInfoMapper extends BaseDao<CardInfoVo> {
    CardInfoVo queryObject(Integer cardId);

    CardInfoVo queryDefault(Integer userId);

    List<CardInfoVo> queryByUserId(Integer userId);

    Integer collectCount(Integer touserId);

    Integer recordCount(Integer touserId);

    Integer recordTodayCount(Integer touserId);

    Integer shareCount(Integer userId);
}
