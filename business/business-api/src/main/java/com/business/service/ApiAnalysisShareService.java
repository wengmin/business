package com.business.service;

import com.business.dao.ApiAnalysisShareMapper;
import com.business.entity.AnalysisShareVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/26
 * @time 创建时间: 16:05
 */
@Service
public class ApiAnalysisShareService {
    @Autowired
    private ApiAnalysisShareMapper dao;

    public Integer save(AnalysisShareVo vo) {
        return dao.save(vo);
    }
}
