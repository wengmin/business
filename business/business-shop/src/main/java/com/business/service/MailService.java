package com.business.service;

import com.business.entity.MailEntity;
import com.business.entity.SysMailFollowEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/16
 * @time 创建时间: 15:13
 */
public interface MailService {

    List<MailEntity> queryList(Map<String, Object> map);

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
    MailEntity queryObject(Long staffId);

    void saveFollows(SysMailFollowEntity follow);

    List<SysMailFollowEntity> followList(Integer mailId);
}
