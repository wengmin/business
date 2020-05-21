package com.business.service;

import com.business.entity.CompanyPostEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-18 17:09:25
 */
public interface CompanyPostService {

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
    CompanyPostEntity queryObject(Integer postId);

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<CompanyPostEntity> queryList(Map<String, Object> map);

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
     * @param companyPost 实体
     * @return 保存条数
     */
    int save(CompanyPostEntity companyPost);

    /**
     * 根据主键更新实体
     *
     * @param companyPost 实体
     * @return 更新条数
     */
    int update(CompanyPostEntity companyPost);

    /**
     * 根据主键删除
     *
     * @param postId
     * @return 删除条数
     */
    int delete(Integer postId);

    /**
     * 根据主键批量删除
     *
     * @param postIds
     * @return 删除条数
     */
    int deleteBatch(Integer[] postIds);
}
