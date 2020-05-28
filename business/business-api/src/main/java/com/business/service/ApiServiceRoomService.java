package com.business.service;

import com.business.dao.ApiServiceRoomMapper;
import com.business.entity.ServiceRoomLogVo;
import com.business.entity.ServiceRoomVo;
import com.business.utils.StringUtils;
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
        serviceRoom.setCreateTime(new Date());
        int serviceId = serviceRoomMapper.save(serviceRoom);
        if (StringUtils.parseInt(serviceId) == 0) {
            return 0;
        }
        ServiceRoomLogVo log = new ServiceRoomLogVo();
        log.setServiceId(serviceId);
        log.setStatus(serviceRoom.getStatus());
        log.setRemark(serviceRoom.getRemark());
        log.setCreateTime(new Date());
        serviceRoomMapper.saveLog(log);
        return serviceId;
    }

    public int update(ServiceRoomVo serviceRoom, int staffId, String remark) {
        serviceRoom.setUpdateTime(new Date());
        int count = serviceRoomMapper.update(serviceRoom);
        if (count == 0) {
            return 0;
        }
        ServiceRoomLogVo log = new ServiceRoomLogVo();
        log.setServiceId(serviceRoom.getServiceId());
        log.setStaffId(staffId);
        log.setStatus(serviceRoom.getStatus());
        log.setRemark(remark);
        log.setCreateTime(new Date());
        serviceRoomMapper.saveLog(log);
        return count;
    }

    public List<ServiceRoomLogVo> queryLogList(Integer id) {
        return serviceRoomMapper.queryLogList(id);
    }
}
