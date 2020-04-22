package com.business.dao;

import com.business.entity.MailEntity;
import com.business.entity.SysMailFollowEntity;

import java.util.List;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/16
 * @time 创建时间: 10:31
 */
public interface MailDao extends BaseDao<MailEntity> {

    void saveFollow(SysMailFollowEntity follow);

    void updateStatus(MailEntity mail);

    List<SysMailFollowEntity> followList(Integer mailId);
}
