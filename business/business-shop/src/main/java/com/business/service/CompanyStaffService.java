package com.business.service;

import com.business.entity.CompanyStaffEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-14 13:46:23
 */
public interface CompanyStaffService {

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
    CompanyStaffEntity queryObject(Integer staffId);

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<CompanyStaffEntity> queryList(Map<String, Object> map);

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
     * @param companyStaff 实体
     * @return 保存条数
     */
    int save(CompanyStaffEntity companyStaff);

    /**
     * 根据主键更新实体
     *
     * @param companyStaff 实体
     * @return 更新条数
     */
    int update(CompanyStaffEntity companyStaff);

    /**
     * 根据主键删除
     *
     * @param staffId
     * @return 删除条数
     */
    int delete(Integer staffId);

    /**
     * 根据主键批量删除
     *
     * @param staffIds
     * @return 删除条数
     */
    int deleteBatch(Integer[] staffIds);
}
