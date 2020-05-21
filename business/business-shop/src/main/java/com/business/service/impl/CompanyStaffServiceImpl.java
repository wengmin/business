package com.business.service.impl;

import com.business.dao.CompanyStaffDao;
import com.business.entity.CompanyStaffEntity;
import com.business.service.CardService;
import com.business.service.CompanyStaffService;
import com.business.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-14 13:46:23
 */
@Service("companyStaffService")
public class CompanyStaffServiceImpl implements CompanyStaffService {
    @Autowired
    private CompanyStaffDao companyStaffDao;
    @Autowired
    private CardService cardService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public CompanyStaffEntity queryObject(Integer staffId) {
        return companyStaffDao.queryObject(staffId);
    }

    @Override
    public List<CompanyStaffEntity> queryList(Map<String, Object> map) {
        return companyStaffDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return companyStaffDao.queryTotal(map);
    }

    @Override
    public int save(CompanyStaffEntity companyStaff) {
        companyStaff.setCreateTime(new Date());
        return companyStaffDao.save(companyStaff);
    }

    @Override
    public int update(CompanyStaffEntity companyStaff) {
        companyStaff.setUpdateTime(new Date());
        return companyStaffDao.update(companyStaff);
    }

    @Override
    public int delete(Integer staffId) {
        return companyStaffDao.delete(staffId);
    }

    @Override
    public int deleteBatch(Integer[] staffIds) {
        return companyStaffDao.deleteBatch(staffIds);
    }
}
