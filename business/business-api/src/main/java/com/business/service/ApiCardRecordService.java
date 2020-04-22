package com.business.service;

import com.business.dao.ApiCardRecordMapper;
import com.business.entity.CardRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/16
 * @time 创建时间: 17:18
 */
@Service
public class ApiCardRecordService {
    @Autowired
    private ApiCardRecordMapper recordDao;

    public Integer save(CardRecordVo recordVo) {
        return recordDao.save(recordVo);
    }

    public List<CardRecordVo> queryList(Map<String, Object> map) {
        return recordDao.queryList(map);
    }

    public Integer queryTotal(Map<String, Object> map) {
        return recordDao.queryTotal(map);
    }

    public void delete(Integer id) {
        recordDao.delete(id);
    }

    public void deleteBatch(Integer[] ids) {
        recordDao.deleteBatch(ids);
    }

    public CardRecordVo queryByToUserId(Integer userId, Integer touserId){
        return recordDao.queryByToUserId(userId, touserId);
    }

    public void updateTime(Integer id) {
        recordDao.updateTime(id);
    }
}
