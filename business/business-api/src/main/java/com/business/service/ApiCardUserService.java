package com.business.service;

import com.business.dao.ApiCardUserMapper;
import com.business.entity.CardUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/23
 * @time 创建时间: 12:25
 */
@Service
public class ApiCardUserService {
    @Autowired
    private ApiCardUserMapper cardUserDao;

    public CardUserVo queryByOpenId(String openId) {
        return cardUserDao.queryByOpenId(openId);
    }

    public CardUserVo queryByUserId(Integer id) {
        return cardUserDao.queryByUserId(id);
    }

    public void save(CardUserVo userVo) {
        cardUserDao.save(userVo);
    }

    public void update(CardUserVo user) {
        cardUserDao.update(user);
    }

    public void updateByOpenId(CardUserVo user) {
        cardUserDao.updateByOpenId(user);
    }
}
