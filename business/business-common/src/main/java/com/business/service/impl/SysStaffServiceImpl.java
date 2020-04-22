package com.business.service.impl;

import com.business.dao.SysStaffDao;
import com.business.entity.SysStaffEntity;
import com.business.page.Page;
import com.business.page.PageHelper;
import com.business.service.SysStaffService;
import com.business.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/3
 * @time 创建时间: 14:06
 */
@Service("sysStaffService")
public class SysStaffServiceImpl implements SysStaffService {

    @Autowired
    private SysStaffDao sysStaffDao;

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysStaffDao.queryTotal(map);
    }

    @Override
    public SysStaffEntity queryObject(Long staffId) {
        return sysStaffDao.queryObject(staffId);
    }

    @Override
    public List<SysStaffEntity> queryList(Map<String, Object> map) {
        return sysStaffDao.queryList(map);
    }

    @Override
    public int staffCount(String tel) {
        return sysStaffDao.staffCount(tel);
    }

    @Override
    @Transactional
    public void save(SysStaffEntity staff) {
        staff.setCreateTime(new Date());

        sysStaffDao.save(staff);

        //检查角色是否越权
        checkRole(staff);
    }

    @Override
    @Transactional
    public void update(SysStaffEntity staff) {
        sysStaffDao.update(staff);

        //检查角色是否越权
        checkRole(staff);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] staffId) {
        sysStaffDao.deleteBatch(staffId);
    }

    @Override
    public Page<SysStaffEntity> findPage(SysStaffEntity staff, int pageNum) {
        PageHelper.startPage(pageNum, Constant.pageSize);
        sysStaffDao.queryListByBean(staff);
        return PageHelper.endPage();
    }



    /**
     * 检查角色是否越权
     */
    private void checkRole(SysStaffEntity staff) {
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (staff.getCreateUserId() == Constant.SUPER_ADMIN) {
            return;
        }
    }
}
