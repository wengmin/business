package com.business.dao;

import com.business.entity.CardUserVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/23
 * @time 创建时间: 12:24
 */
public interface ApiCardUserMapper  extends BaseDao<CardUserVo>{
    CardUserVo queryByOpenId(@Param("openId") String openId);
    CardUserVo queryByUserId(Integer userId);
    int updateByOpenId(CardUserVo user);
    int collectCount(Integer touserId);
    int recordCount(Integer touserId);
    int recordTodayCount(Integer touserId);
    int shareCount(Integer userId);
}
