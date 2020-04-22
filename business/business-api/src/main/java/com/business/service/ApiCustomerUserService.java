package com.business.service;

import com.business.dao.ApiCustomerUserMapper;
import com.business.entity.CustomerUserVo;
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
public class ApiCustomerUserService {
    @Autowired
    private ApiCustomerUserMapper customerUserDao;

    public CustomerUserVo queryByOpenId(String openId) {
        return customerUserDao.queryByOpenId(openId);
    }

    public CustomerUserVo queryByMobile(String mobile) {
        return customerUserDao.queryByMobile(mobile);
    }

    public CustomerUserVo queryObject(Integer id) {
        return customerUserDao.queryObject(id);
    }

    public void save(CustomerUserVo userVo) {
        customerUserDao.save(userVo);
    }

    public void update(CustomerUserVo user) {
        customerUserDao.update(user);
    }

    public void updateByOpenId(CustomerUserVo user) {
        customerUserDao.updateByOpenId(user);
    }

}
