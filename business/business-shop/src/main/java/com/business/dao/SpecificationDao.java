package com.business.dao;

import java.util.List;

import com.business.entity.SpecificationEntity;

/**
 * 规格表
 * 
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-13 10:41:10
 */
public interface SpecificationDao extends BaseDao<SpecificationEntity> {
	List<SpecificationEntity> queryListByGoodsId(String goodsId);
	
}
