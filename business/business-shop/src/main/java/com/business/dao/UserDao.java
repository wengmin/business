package com.business.dao;

import com.business.entity.UserEntity;

/**
 * 会员Dao
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-16 15:02:28
 */
public interface UserDao extends BaseDao<UserEntity> {

    void updatePromoter(UserEntity user);
}
