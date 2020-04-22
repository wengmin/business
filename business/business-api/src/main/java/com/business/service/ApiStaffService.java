package com.business.service;

import com.business.dao.ApiStaffMapper;
import com.business.entity.StaffVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/2/27
 * @time 创建时间: 13:52
 */
@Service
public class ApiStaffService {
    @Autowired
    private ApiStaffMapper staffDao;


    public StaffVo queryObject(Integer id) {
        return staffDao.queryObject(id);
    }

    public StaffVo queryByOpenId(String openId) {
        return staffDao.queryByOpenId(openId);
    }

    public List<StaffVo> colleague(Integer id) {
        return staffDao.colleague(id);
    }

    public StaffVo queryByMobile(String mobile) {
        return staffDao.queryByMobile(mobile);
    }

    public int bingOpenIdByMobile(String openId,String mobile) {
        return staffDao.bingOpenIdByMobile(openId,mobile);
    }
}
