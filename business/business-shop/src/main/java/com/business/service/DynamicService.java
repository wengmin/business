package com.business.service;

import com.business.entity.DynamicEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/24
 * @time 创建时间: 10:04
 */
public interface DynamicService {

    DynamicEntity queryObject(Long dynamicId);

    List<DynamicEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(DynamicEntity dynamic);

    void update(DynamicEntity dynamic);

    void deleteBatch(Long[] dynamicIds);
}
