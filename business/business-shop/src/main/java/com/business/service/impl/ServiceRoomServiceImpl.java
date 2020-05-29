package com.business.service.impl;

import com.business.dao.ServiceRoomDao;
import com.business.entity.ServiceRoomEntity;
import com.business.entity.ServiceRoomLogEntity;
import com.business.service.ServiceRoomService;
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
 * @date 2020-05-29 10:24:34
 */
@Service("serviceRoomService")
public class ServiceRoomServiceImpl implements ServiceRoomService {
    @Autowired
    private ServiceRoomDao serviceRoomDao;

    @Override
    public ServiceRoomEntity queryObject(Integer serviceId) {
        return serviceRoomDao.queryObject(serviceId);
    }

    @Override
    public List<ServiceRoomEntity> queryList(Map<String, Object> map) {
        return serviceRoomDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return serviceRoomDao.queryTotal(map);
    }

    @Override
    public int update(ServiceRoomEntity serviceRoom) {
        serviceRoom.setUpdateTime(new Date());
        int res = serviceRoomDao.update(serviceRoom);
        if (res == 0)
            return 0;
        ServiceRoomLogEntity log = new ServiceRoomLogEntity();
        log.setServiceId(serviceRoom.getServiceId());
        log.setStaffId(-1);
        log.setStatus(serviceRoom.getStatus());
        log.setRemark(serviceRoom.getRemark());
        log.setCreateTime(new Date());
        serviceRoomDao.saveLog(log);
        return res;
    }

    @Override
    public List<ServiceRoomLogEntity> queryLogList(Integer serviceId) {
        return serviceRoomDao.queryLogList(serviceId);
    }
}
