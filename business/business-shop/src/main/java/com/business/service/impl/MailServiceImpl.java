package com.business.service.impl;

import com.business.dao.MailDao;
import com.business.entity.MailEntity;
import com.business.entity.SysMailFollowEntity;
import com.business.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/16
 * @time 创建时间: 15:13
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    private MailDao sysMailDao;

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysMailDao.queryTotal(map);
    }

    @Override
    public MailEntity queryObject(Long staffId) {
        return sysMailDao.queryObject(staffId);
    }

    @Override
    public List<MailEntity> queryList(Map<String, Object> map) {
        return sysMailDao.queryList(map);
    }

    @Override
    public void saveFollows(SysMailFollowEntity follow) {
        sysMailDao.saveFollow(follow);
        MailEntity mail = new MailEntity();
        mail.setMailId(follow.getMailId());
        mail.setStatus(follow.getStatus());
        sysMailDao.updateStatus(mail);
    }

    @Override
    public List<SysMailFollowEntity> followList(Integer mailId) {
        return sysMailDao.followList(mailId);
    }
}
