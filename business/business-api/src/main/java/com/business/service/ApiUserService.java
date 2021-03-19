package com.business.service;

import com.business.dao.ApiUserMapper;
import com.business.entity.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/14
 * @time 创建时间: 18:11
 */

@Service
public class ApiUserService {
    @Autowired
    private ApiUserMapper userDao;

    public UserVo queryObject(Long id) {
        return userDao.queryObject(id);
    }

    public void save(UserVo userVo) {
        userDao.save(userVo);
    }

    public void update(UserVo user) {
        userDao.update(user);
    }

    public void saveEdit(UserVo userVo) {
        if (userDao.queryByUnionId(userVo.getUnionid()) != null) {
            userDao.updateByUnionId(userVo);
        } else if (userDao.queryByOpenIdGzh(userVo.getOpenidGzh()) != null) {
            userDao.updateByOpenIdGzh(userVo);
        } else if (userDao.queryByOpenIdGzh(userVo.getOpenidXcx()) != null) {
            userDao.updateByOpenIdXcx(userVo);
        } else {
            userVo.setCreateTime(new Date());
            userDao.save(userVo);
        }
    }

    public UserVo queryByUnionId(String unionId) {
        return userDao.queryByUnionId(unionId);
    }

    public UserVo queryByOpenIdGzh(String openId) {
        return userDao.queryByOpenIdGzh(openId);
    }

    public UserVo queryByOpenIdXcx(String openId) {
        return userDao.queryByOpenIdXcx(openId);
    }

    public UserVo queryByMobile(String openId) {
        return userDao.queryByMobile(openId);
    }

    public void updateByOpenIdXcx(UserVo user) {
        userDao.updateByOpenIdXcx(user);
    }

    public void updateByOpenIdGzh(UserVo user) {
        userDao.updateByOpenIdGzh(user);
    }

    public void updateByUnionId(UserVo user) {
        userDao.updateByUnionId(user);
    }

}
