package com.business.dao;

import com.business.entity.UserVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/14
 * @time 创建时间: 18:10
 */
public interface ApiUserMapper extends BaseDao<UserVo> {
    UserVo queryByOpenId(@Param("openId") String openId);

    UserVo queryByMobile(@Param("mobile") String mobile);

    int updateByOpenId(UserVo user);
}
