package com.business.dao;

import com.business.entity.SysStaffEntity;

import java.util.List;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/2
 * @time 创建时间: 17:41
 */
public interface SysStaffDao extends BaseDao<SysStaffEntity> {

    /**
     * 根据实体类查询
     * @param userWindowDto
     * @return
     */
    List<SysStaffEntity> queryListByBean(SysStaffEntity staff);

    int  staffCount(String tel);
}
