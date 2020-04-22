package com.business.service.impl;

import com.business.dao.DynamicDao;
import com.business.entity.DynamicEntity;
import com.business.service.DynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/24
 * @time 创建时间: 10:05
 */
@Service("dynamicService")
public class DynamicImpl implements DynamicService {
    @Autowired
    private DynamicDao sysDynamicDao;

    @Override
    public DynamicEntity queryObject(Long dynamicId) {
        return sysDynamicDao.queryObject(dynamicId);
    }

    @Override
    public List<DynamicEntity> queryList(Map<String, Object> map) {
        return sysDynamicDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysDynamicDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(DynamicEntity dynamic) {
        //dynamic.setCreateTime(new Date());

        sysDynamicDao.save(dynamic);
    }

    @Override
    @Transactional
    public void update(DynamicEntity dynamic) {
        sysDynamicDao.update(dynamic);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] dynamicId) {
        sysDynamicDao.deleteBatch(dynamicId);
    }
}
