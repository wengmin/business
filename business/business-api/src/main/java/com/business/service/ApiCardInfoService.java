package com.business.service;

import com.business.dao.ApiCardInfoMapper;
import com.business.entity.CardInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/23
 * @time 创建时间: 12:25
 */
@Service
public class ApiCardInfoService {
    @Autowired
    private ApiCardInfoMapper cardInfoDao;

    public CardInfoVo queryObject(Integer id) {
        return cardInfoDao.queryObject(id);
    }

    public CardInfoVo queryDefault(Integer userId) {
        return cardInfoDao.queryDefault(userId);
    }

    public List<CardInfoVo> queryByUserId(Integer id) {
        return cardInfoDao.queryByUserId(id);
    }

    public void save(CardInfoVo userVo) {
        cardInfoDao.save(userVo);
    }

    public void update(CardInfoVo user) {
        cardInfoDao.update(user);
    }

    public Integer collectCount(Integer touserId) {
        return cardInfoDao.collectCount(touserId);
    }

    public Integer recordCount(Integer touserId) {
        return cardInfoDao.recordCount(touserId);
    }

    public Integer recordTodayCount(Integer touserId) {
        return cardInfoDao.recordTodayCount(touserId);
    }

    public Integer shareCount(Integer userId) {
        return cardInfoDao.shareCount(userId);
    }
}
