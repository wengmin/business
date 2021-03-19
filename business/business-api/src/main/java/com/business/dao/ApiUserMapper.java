package com.business.dao;

import com.business.entity.UserVo;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/14
 * @time 创建时间: 18:10
 */
public interface ApiUserMapper extends BaseDao<UserVo> {
    UserVo queryByUnionId(String id);
    UserVo queryByOpenIdGzh(String id);
    UserVo queryByOpenIdXcx(String id);
    UserVo queryByMobile(String id);

    int updateByOpenIdXcx(UserVo user);

    int updateByOpenIdGzh(UserVo user);

    int updateByUnionId(UserVo user);
}
