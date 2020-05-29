package com.business.dao;

import com.business.entity.ServiceRoomEntity;
import com.business.entity.ServiceRoomLogEntity;

import java.util.List;

/**
 * Dao
 *
 * @author xuyang
 * @email 295640759@qq.com
 * @date 2020-05-29 10:24:34
 */
public interface ServiceRoomDao extends BaseDao<ServiceRoomEntity> {

    List<ServiceRoomLogEntity> queryLogList(Integer serviceId);

    void saveLog(ServiceRoomLogEntity logEntity);
}
