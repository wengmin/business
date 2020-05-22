package com.business.service;

import com.business.dao.ApiServiceRoomMapper;
import com.business.entity.ServiceRoomVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-21 17:42:29
 */
@Service
public class ApiServiceRoomService {
    @Autowired
    private ApiServiceRoomMapper serviceRoomMapper;

    
    public ServiceRoomVo queryObject(Integer serviceId) {
        return serviceRoomMapper.queryObject(serviceId);
    }

    public List<ServiceRoomVo> queryList(Map<String, Object> map) {
        return serviceRoomMapper.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return serviceRoomMapper.queryTotal(map);
    }

    public int save(ServiceRoomVo serviceRoom) {
        return serviceRoomMapper.save(serviceRoom);
    }

    public int update(ServiceRoomVo serviceRoom) {
        return serviceRoomMapper.update(serviceRoom);
    }

    public int delete(Integer serviceId) {
        return serviceRoomMapper.delete(serviceId);
    }

    public int deleteBatch(Integer[] serviceIds) {
        return serviceRoomMapper.deleteBatch(serviceIds);
    }
}
