package com.business.service;

import com.business.dao.ApiConfigDataMapper;
import com.business.entity.ConfigDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/6
 * @time 创建时间: 10:41
 */
@Service
public class ApiConfigDataService {
    @Autowired
    private ApiConfigDataMapper cdDao;
    public List<ConfigDataVo> queryList(Map<String, Object> map) {
        return cdDao.queryList(map);
    }
}
