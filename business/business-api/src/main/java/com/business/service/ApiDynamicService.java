package com.business.service;

import com.business.dao.ApiDynamicMapper;
import com.business.entity.DynamicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/23
 * @time 创建时间: 10:06
 */
@Service
public class ApiDynamicService {
    @Autowired
    private ApiDynamicMapper dynamicDao;


    public DynamicVo queryObject(Integer id) {
        return dynamicDao.queryObject(id);
    }


    public List<DynamicVo> queryList(Map<String, Object> map) {
        return dynamicDao.queryList(map);
    }


    public int queryTotal(Map<String, Object> map) {
        return dynamicDao.queryTotal(map);
    }
}
