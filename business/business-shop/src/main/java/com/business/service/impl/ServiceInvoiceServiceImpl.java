package com.business.service.impl;

import com.business.dao.ServiceInvoiceDao;
import com.business.entity.ServiceInvoiceEntity;
import com.business.service.ServiceInvoiceService;
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
@Service("serviceInvoiceService")
public class ServiceInvoiceServiceImpl implements ServiceInvoiceService {
    @Autowired
    private ServiceInvoiceDao serviceInvoiceDao;

    @Override
    public ServiceInvoiceEntity queryObject(Integer invoiceId) {
        return serviceInvoiceDao.queryObject(invoiceId);
    }

    @Override
    public List<ServiceInvoiceEntity> queryList(Map<String, Object> map) {
        return serviceInvoiceDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return serviceInvoiceDao.queryTotal(map);
    }

    @Override
    public int update(ServiceInvoiceEntity serviceInvoice) {
        serviceInvoice.setUpdateTime(new Date());
        return serviceInvoiceDao.update(serviceInvoice);
    }
}
