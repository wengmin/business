package com.business.service.impl;

import com.business.dao.CompanyRoomDao;
import com.business.entity.CompanyRoomEntity;
import com.business.service.CompanyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-14 13:46:23
 */
@Service("companyRoomService")
public class CompanyRoomServiceImpl implements CompanyRoomService {
    @Autowired
    private CompanyRoomDao companyRoomDao;

    @Override
    public CompanyRoomEntity queryObject(Integer roomId) {
        return companyRoomDao.queryObject(roomId);
    }

    @Override
    public List<CompanyRoomEntity> queryList(Map<String, Object> map) {
        return companyRoomDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return companyRoomDao.queryTotal(map);
    }

    @Override
    public int save(CompanyRoomEntity companyRoom) {
        companyRoom.setCreateTime(new Date());
        return companyRoomDao.save(companyRoom);
    }

    @Override
    public int update(CompanyRoomEntity companyRoom) {
        companyRoom.setUpdateTime(new Date());
        return companyRoomDao.update(companyRoom);
    }

    @Override
    public int delete(Integer roomId) {
        return companyRoomDao.delete(roomId);
    }

    @Override
    public int deleteBatch(Integer[] roomIds) {
        return companyRoomDao.deleteBatch(roomIds);
    }
}
