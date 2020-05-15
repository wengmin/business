package com.business.service.impl;

import com.business.dao.CompanyServiceDao;
import com.business.entity.CompanyServiceEntity;
import com.business.service.CompanyServiceService;
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
@Service("companyServiceService")
public class CompanyServiceServiceImpl implements CompanyServiceService {
    @Autowired
    private CompanyServiceDao companyServiceDao;

    @Override
    public CompanyServiceEntity queryObject(Integer serviceId) {
        return companyServiceDao.queryObject(serviceId);
    }

    @Override
    public List<CompanyServiceEntity> queryList(Map<String, Object> map) {
        return companyServiceDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return companyServiceDao.queryTotal(map);
    }

    @Override
    public int save(CompanyServiceEntity companyService) {
        companyService.setCreateTime(new Date());
        return companyServiceDao.save(companyService);
    }

    @Override
    public int update(CompanyServiceEntity companyService) {
        companyService.setUpdateTime(new Date());
        return companyServiceDao.update(companyService);
    }

    @Override
    public int delete(Integer serviceId) {
        return companyServiceDao.delete(serviceId);
    }

    @Override
    public int deleteBatch(Integer[] serviceIds) {
        return companyServiceDao.deleteBatch(serviceIds);
    }
}
