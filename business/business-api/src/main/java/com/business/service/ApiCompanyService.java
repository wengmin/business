package com.business.service;

import com.business.dao.ApiCompanyMapper;
import com.business.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Integer update(CompanyVo companyVo) {
        return companyDao.update(companyVo);
    }

    public CompanyVo queryObject(Integer id) {
        return companyDao.queryObject(id);
    }


    public List<CompanyFileVo> queryFileList(Integer companyId) {
        return companyDao.queryFileList(companyId);
    }

    public Integer saveFile(List<CompanyFileVo> fileList) {
        return companyDao.saveFile(fileList);
    }

    public Integer deleteFile(Integer id) {
        return companyDao.deleteFile(id);
    }

    public Integer deleteFileByCompany(Integer companyId) {
        return companyDao.deleteFileByCompany(companyId);
    }


    public CompanyPostVo queryPost(Integer postId) {
        return companyDao.queryPost(postId);
    }

    public List<CompanyPostVo> queryPostList(Integer companyId, Integer postId) {
        return companyDao.queryPostList(companyId, postId);
    }


    public CompanyRoomVo queryRoom(Integer roomId) {
        return companyDao.queryRoom(roomId);
    }


    public List<CompanyServiceVo> queryServiceList(Integer companyId, String serviceClass) {
        return companyDao.queryServiceList(companyId, serviceClass);
    }
}
