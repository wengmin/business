package com.business.dao;

import com.business.entity.StaffVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/2/27
 * @time 创建时间: 13:56
 */
public interface ApiStaffMapper  extends BaseDao<StaffVo>{
    StaffVo queryByOpenId(@Param("openId") String openId);

    StaffVo queryByMobile(@Param("mobile") String mobile);

    List<StaffVo> colleague(Integer id);

    int bingOpenIdByMobile(@Param("openId") String openId,@Param("mobile") String mobile);
}
