package com.business.service;

import com.business.dao.ApiUserMapper;
import com.business.entity.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ApiUserMapper UserDao;

    public UserVo queryByOpenId(String openId) {
        return UserDao.queryByOpenId(openId);
    }

    public UserVo queryByMobile(String mobile) {
        return UserDao.queryByMobile(mobile);
    }

    public UserVo queryObject(Integer id) {
        return UserDao.queryObject(id);
    }

    public void save(UserVo userVo) {
        UserDao.save(userVo);
    }

    public void update(UserVo user) {
        UserDao.update(user);
    }

    public void updateByOpenId(UserVo user) {
        UserDao.updateByOpenId(user);
    }

}
