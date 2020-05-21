package com.business.service.impl;

import com.business.dao.CompanyPostDao;
import com.business.entity.CompanyPostEntity;
import com.business.service.CompanyPostService;
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
 * @date 2020-05-18 17:09:25
 */
@Service("companyPostService")
public class CompanyPostServiceImpl implements CompanyPostService {
    @Autowired
    private CompanyPostDao companyPostDao;

    @Override
    public CompanyPostEntity queryObject(Integer postId) {
        return companyPostDao.queryObject(postId);
    }

    @Override
    public List<CompanyPostEntity> queryList(Map<String, Object> map) {
        return companyPostDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return companyPostDao.queryTotal(map);
    }

    @Override
    public int save(CompanyPostEntity companyPost) {
        companyPost.setCreateTime(new Date());
        return companyPostDao.save(companyPost);
    }

    @Override
    public int update(CompanyPostEntity companyPost) {
        companyPost.setUpdateTime(new Date());
        return companyPostDao.update(companyPost);
    }

    @Override
    public int delete(Integer postId) {
        return companyPostDao.delete(postId);
    }

    @Override
    public int deleteBatch(Integer[] postIds) {
        return companyPostDao.deleteBatch(postIds);
    }

}
