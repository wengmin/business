package com.business.service;

import com.business.entity.CompanyFileEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-14 13:46:23
 */
public interface CompanyFileService {

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
    CompanyFileEntity queryObject(Integer id);

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<CompanyFileEntity> queryList(Map<String, Object> map);

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
     * @param companyFile 实体
     * @return 保存条数
     */
    int save(CompanyFileEntity companyFile);

    /**
     * 根据主键更新实体
     *
     * @param companyFile 实体
     * @return 更新条数
     */
    int update(CompanyFileEntity companyFile);

    /**
     * 根据主键删除
     *
     * @param id
     * @return 删除条数
     */
    int delete(Integer id);

    /**
     * 根据主键批量删除
     *
     * @param ids
     * @return 删除条数
     */
    int deleteBatch(Integer[] ids);
}
