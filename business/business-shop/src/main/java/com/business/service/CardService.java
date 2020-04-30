package com.business.service;

import com.business.entity.CardEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-04-28 15:10:37
 */
public interface CardService {

    /**
     * 根据主键查询实体
     *
     * @param cardId 主键
     * @return 实体
     */
    CardEntity queryObject(Integer cardId);

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<CardEntity> queryList(Map<String, Object> map);

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
     * @param cardUser 实体
     * @return 保存条数
     */
    int save(CardEntity cardUser);

    /**
     * 根据主键更新实体
     *
     * @param cardUser 实体
     * @return 更新条数
     */
    int update(CardEntity cardUser);

    /**
     * 根据主键删除
     *
     * @param cardId
     * @return 删除条数
     */
    int delete(Integer cardId);

    /**
     * 根据主键批量删除
     *
     * @param cardIds
     * @return 删除条数
     */
    int deleteBatch(Integer[] cardIds);
}
