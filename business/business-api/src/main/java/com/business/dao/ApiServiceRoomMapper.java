package com.business.dao;

import com.business.entity.ServiceRoomLogVo;
import com.business.entity.ServiceRoomVo;

import java.util.List;

/**
 * Dao
 *
 * @author xuyang
 * @email 295640759@qq.com
 * @date 2020-05-21 17:42:29
 */
public interface ApiServiceRoomMapper extends BaseDao<ServiceRoomVo> {
   List<ServiceRoomLogVo> queryLogList(Integer id);
   int saveLog(ServiceRoomLogVo log);
}
