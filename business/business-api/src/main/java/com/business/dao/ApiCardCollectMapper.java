package com.business.dao;

import com.business.entity.CardCollectVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/16
 * @time 创建时间: 17:12
 */
public interface ApiCardCollectMapper extends BaseDao<CardCollectVo>{
    int queryHasUser(@Param("userId") Integer userId,@Param("touserId") Integer touserId);
    void deleteByUserID(@Param("userId") Integer userId,@Param("touserId") Integer touserId);
}
