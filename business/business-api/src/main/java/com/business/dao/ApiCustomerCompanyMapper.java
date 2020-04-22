package com.business.dao;

import com.business.entity.CustomerCompanyVo;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/15
 * @time 创建时间: 9:36
 */
public interface ApiCustomerCompanyMapper extends BaseDao<CustomerCompanyVo> {
    CustomerCompanyVo queryByName(String name);
}
