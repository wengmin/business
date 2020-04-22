package com.business.dao;

import com.business.entity.CardRecordVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/16
 * @time 创建时间: 17:04
 */
public interface ApiCardRecordMapper extends BaseDao<CardRecordVo> {
    CardRecordVo queryByToUserId(@Param("userId") Integer userId, @Param("touserId") Integer touserId);
    void updateTime(Integer id);
}
