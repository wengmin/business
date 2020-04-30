package com.business.service.impl;

import com.business.dao.CardDao;
import com.business.entity.CardEntity;
import com.business.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-04-28 15:10:37
 */
@Service("cardUserService")
public class CardServiceImpl implements CardService {
    @Autowired
    private CardDao cardDao;

    @Override
    public CardEntity queryObject(Integer cardId) {
        return cardDao.queryObject(cardId);
    }

    @Override
    public List<CardEntity> queryList(Map<String, Object> map) {
        return cardDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return cardDao.queryTotal(map);
    }

    @Override
    public int save(CardEntity cardUser) {
        return cardDao.save(cardUser);
    }

    @Override
    public int update(CardEntity cardUser) {
        return cardDao.update(cardUser);
    }

    @Override
    public int delete(Integer cardId) {
        return cardDao.delete(cardId);
    }

    @Override
    public int deleteBatch(Integer[] cardIds) {
        return cardDao.deleteBatch(cardIds);
    }
}
