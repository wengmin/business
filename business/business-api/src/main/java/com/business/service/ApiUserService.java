package com.business.service;

import com.business.dao.ApiUserLevelMapper;
import com.business.dao.ApiUserMapper;
import com.business.entity.SmsLogVo;
import com.business.entity.UserLevelVo;
import com.business.entity.UserVo;
import com.business.utils.RRException;
import com.business.validator.Assert;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class ApiUserService {
    @Autowired
    private ApiUserMapper userDao;
    @Autowired
    private ApiUserLevelMapper userLevelDao;

    public UserVo queryObject(Long userId) {
        return userDao.queryObject(userId);
    }

    public UserVo queryByOpenId(String openId) {
        return userDao.queryByOpenId(openId);
    }

    public List<UserVo> queryList(Map<String, Object> map) {
        return userDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return userDao.queryTotal(map);
    }

    public void save(String mobile, String password) {
        UserVo user = new UserVo();
        user.setMobile(mobile);
        user.setUsername(mobile);
        user.setPassword(DigestUtils.sha256Hex(password));
        user.setRegister_time(new Date());
        userDao.save(user);
    }

    public void save(UserVo userVo) {
        userDao.save(userVo);
    }

    public void update(UserVo user) {
        userDao.update(user);
    }

    public void delete(Long userId) {
        userDao.delete(userId);
    }

    public void deleteBatch(Long[] userIds) {
        userDao.deleteBatch(userIds);
    }

    public UserVo queryByMobile(String mobile) {
        return userDao.queryByMobile(mobile);
    }

    public long login(String mobile, String password) {
        UserVo user = queryByMobile(mobile);
        Assert.isNull(user, "手机号或密码错误");

        //密码错误
        if (!user.getPassword().equals(DigestUtils.sha256Hex(password))) {
            throw new RRException("手机号或密码错误");
        }

        return user.getUserId();
    }

    public SmsLogVo querySmsCodeByUserId(Long user_id) {
        return userDao.querySmsCodeByUserId(user_id);
    }


    public int saveSmsCodeLog(SmsLogVo smsLogVo) {
        return userDao.saveSmsCodeLog(smsLogVo);
    }

    public String getUserLevel(UserVo loginUser) {
        String result = "普通用户";
        UserLevelVo userLevelVo = userLevelDao.queryObject(loginUser.getUser_level_id());
        if (null != userLevelVo) {
            result = userLevelVo.getName();
        }
        return result;
    }
    public void isRealValidate(UserVo userVo){
        UserVo userVo1=new UserVo();
        userVo1.setUserId(userVo.getUserId());
        userVo1.setIdCard(userVo.getIdCard());
        userVo1.setIsReal("2");
        userVo1.setMobile(userVo.getMobile());
        userVo1.setRealName(userVo.getUsername());
        userDao.update(userVo1);
    }
    
    public int updatefx(UserVo vo) {
    	return userDao.updatefx(vo);
    }

    public int updateByOpenId(String openId,String mobile) {
        return userDao.updateByOpenId(openId,mobile);
    }

    public int saveMail(Integer staffId,String mobile) {
        return userDao.saveMail(staffId,mobile);
    }
}
