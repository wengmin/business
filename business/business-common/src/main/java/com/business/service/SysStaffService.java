package com.business.service;

import com.business.entity.SysStaffEntity;
import com.business.page.Page;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/3
 * @time 创建时间: 14:05
 */
public interface SysStaffService {

    List<SysStaffEntity> queryList(Map<String, Object> map);

    /**
     * 查询总数
     */
    int queryTotal(Map<String, Object> map);
    /**
     * 根据员工ID，查询员工
     *
     * @param staffId
     * @return
     */
    SysStaffEntity queryObject(Long staffId);

    /**
     * 保存员工
     */
    void save(SysStaffEntity staff);

    /**
     * 修改员工
     */
    void update(SysStaffEntity user);
    
    /**
     * 删除员工
     */
    void deleteBatch(Long[] userIds);

    int  staffCount(String tel);
    /**
     * 根据条件分页查询
     * @param userEntity
     * @param pageNum
     * @return
     */
    Page<SysStaffEntity> findPage(SysStaffEntity userEntity, int pageNum);
}
