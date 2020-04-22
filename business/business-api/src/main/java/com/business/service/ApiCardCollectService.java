package com.business.service;

import com.business.dao.ApiCardCollectMapper;
import com.business.entity.CardCollectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/16
 * @time 创建时间: 17:18
 */
@Service
public class ApiCardCollectService {
    @Autowired
    private ApiCardCollectMapper collectDao;

    public Integer save(CardCollectVo recordVo) {
        return collectDao.save(recordVo);
    }

    public List<CardCollectVo> queryList(Map<String, Object> map) {
        return collectDao.queryList(map);
    }

    public Integer queryTotal(Map<String, Object> map) {
        return collectDao.queryTotal(map);
    }

    public void delete(Integer id) {
        collectDao.delete(id);
    }

    public void deleteBatch(Integer[] ids) {
        collectDao.deleteBatch(ids);
    }

    public Integer queryHasUser(Integer userId, Integer touserId) {
        return collectDao.queryHasUser(userId, touserId);
    }

    public void deleteByUserID(Integer userId, Integer touserId) {
        collectDao.deleteByUserID(userId, touserId);
    }
}
