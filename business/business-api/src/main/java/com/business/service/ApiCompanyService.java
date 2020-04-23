package com.business.service;

import com.business.dao.ApiCompanyMapper;
import com.business.entity.CompanyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/15
 * @time 创建时间: 9:42
 */
@Service
public class ApiCompanyService {
    @Autowired
    private ApiCompanyMapper companyDao;

    public Integer save(CompanyVo companyVo) {
        return companyDao.save(companyVo);
    }

    public CompanyVo queryObject(Integer id) {
        return companyDao.queryObject(id);
    }

    public List<CompanyVo> queryList(Map<String, Object> map) {
        return companyDao.queryList(map);
    }

    public Integer queryTotal(Map<String, Object> map) {
        return companyDao.queryTotal(map);
    }

    public CompanyVo queryByName(String name) {
        return companyDao.queryByName(name);
    }
}
