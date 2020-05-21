package com.business.service;

import com.business.entity.CompanyServiceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-14 13:46:23
 */
public interface CompanyServiceService {

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
    CompanyServiceEntity queryObject(Integer serviceId);

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
    CompanyServiceEntity queryByCompanyService(Integer companyId, String serviceClass);

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<CompanyServiceEntity> queryList(Map<String, Object> map);

    /**
     * 分页统计总数
     *
     * @param map 参数
     * @return 总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存实体
     *
     * @param companyService 实体
     * @return 保存条数
     */
    int save(CompanyServiceEntity companyService);

    /**
     * 查询是否存在
     *
     * @param
     * @return 保存条数
     */
    CompanyServiceEntity hasTag(Integer companyId,  String serviceClass,  String serviceTag);

    void disableByCompanyService(Integer companyId, String serviceClass);

    void deleteByCompanyService(Integer companyId, String serviceClass);

    /*
    根据企业编号和服务类型获取该企业下的项目
     */
    List<String> queryTagList(Integer companyId, String serviceClass);

    /**
     * 根据主键更新实体
     *
     * @param companyService 实体
     * @return 更新条数
     */
    int update(CompanyServiceEntity companyService);

    /**
     * 根据主键删除
     *
     * @param serviceId
     * @return 删除条数
     */
    int delete(Integer serviceId);

    /**
     * 根据主键批量删除
     *
     * @param serviceIds
     * @return 删除条数
     */
    int deleteBatch(Integer[] serviceIds);
}
