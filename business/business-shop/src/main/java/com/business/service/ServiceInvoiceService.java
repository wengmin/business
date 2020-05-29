package com.business.service;

import com.business.entity.ServiceInvoiceEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-29 10:24:34
 */
public interface ServiceInvoiceService {

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
    ServiceInvoiceEntity queryObject(Integer invoiceId);

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<ServiceInvoiceEntity> queryList(Map<String, Object> map);

    /**
     * 分页统计总数
     *
     * @param map 参数
     * @return 总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 根据主键更新实体
     *
     * @param serviceInvoice 实体
     * @return 更新条数
     */
    int update(ServiceInvoiceEntity serviceInvoice);
}
