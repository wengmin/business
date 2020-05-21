package com.business.dao;

import com.business.entity.CompanyServiceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/5/14
 * @time 创建时间: 13:56
 */
public interface CompanyServiceDao extends BaseDao<CompanyServiceEntity> {
    CompanyServiceEntity queryByCompanyService(@Param("companyId") Integer companyId, @Param("serviceClass") String serviceClass);

    CompanyServiceEntity hasTag(@Param("companyId") Integer companyId, @Param("serviceClass") String serviceClass, @Param("serviceTag") String serviceTag);

    void disableByCompanyService(@Param("companyId") Integer companyId, @Param("serviceClass") String serviceClass);

    void deleteByCompanyService(@Param("companyId") Integer companyId, @Param("serviceClass") String serviceClass);

    /*
    根据企业编号和服务类型获取该企业下的项目
     */
    List<String> queryTagList(@Param("companyId") Integer companyId, @Param("serviceClass") String serviceClass);
}
