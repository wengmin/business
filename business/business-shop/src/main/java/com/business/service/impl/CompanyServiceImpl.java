package com.business.service.impl;

import com.business.dao.CompanyDao;
import com.business.entity.CompanyEntity;
import com.business.service.CompanyService;
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
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;

    @Override
    public CompanyEntity queryObject(Integer companyId) {
        return companyDao.queryObject(companyId);
    }

    @Override
    public List<CompanyEntity> queryList(Map<String, Object> map) {
        return companyDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return companyDao.queryTotal(map);
    }

    @Override
    public int save(CompanyEntity companyInfo) {
        companyInfo.setCreateTime(new Date());
        return companyDao.save(companyInfo);
    }

    @Override
    public int update(CompanyEntity companyInfo) {
        companyInfo.setUpdateTime(new Date());
        return companyDao.update(companyInfo);
    }

    @Override
    public int delete(Integer companyId) {
        return companyDao.delete(companyId);
    }

    @Override
    public int deleteBatch(Integer[] companyIds) {
        return companyDao.deleteBatch(companyIds);
    }
}
