package com.business.dao;

import java.util.List;

import com.business.entity.OrderGoodsVo;

/**
 * 
 * 
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-11 09:16:46
 */
public interface ApiOrderGoodsMapper extends BaseDao<OrderGoodsVo> {
	List<OrderGoodsVo> queryInvalidOrder();
}
