package com.business.dao;

import com.business.entity.CompanyVo;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/15
 * @time 创建时间: 9:36
 */
public interface ApiCompanyMapper extends BaseDao<CompanyVo> {
    CompanyVo queryByName(String name);
}
