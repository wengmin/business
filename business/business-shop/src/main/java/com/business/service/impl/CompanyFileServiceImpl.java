package com.business.service.impl;

import com.business.dao.CompanyFileDao;
import com.business.entity.CompanyFileEntity;
import com.business.service.CompanyFileService;
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
@Service("companyFileService")
public class CompanyFileServiceImpl implements CompanyFileService {
    @Autowired
    private CompanyFileDao companyFileDao;

    @Override
    public CompanyFileEntity queryObject(Integer id) {
        return companyFileDao.queryObject(id);
    }

    @Override
    public List<CompanyFileEntity> queryList(Map<String, Object> map) {
        return companyFileDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return companyFileDao.queryTotal(map);
    }

    @Override
    public int save(CompanyFileEntity companyFile) {
        companyFile.setCreateTime(new Date());
        return companyFileDao.save(companyFile);
    }

    @Override
    public int update(CompanyFileEntity companyFile) {
        return companyFileDao.update(companyFile);
    }

    @Override
    public int delete(Integer id) {
        return companyFileDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return companyFileDao.deleteBatch(ids);
    }
}
