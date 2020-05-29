package com.business.service;

import com.business.dao.ApiServiceInvoiceMapper;
import com.business.entity.ServiceInvoiceVo;
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
 * @date 2020-05-28 18:16:20
 */
@Service
public class ApiServiceInvoiceService {
    @Autowired
    private ApiServiceInvoiceMapper serviceInvoiceMapper;

    public ServiceInvoiceVo queryObject(Integer invoiceId) {
        return serviceInvoiceMapper.queryObject(invoiceId);
    }

    public List<ServiceInvoiceVo> queryList(Map<String, Object> map) {
        return serviceInvoiceMapper.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return serviceInvoiceMapper.queryTotal(map);
    }

    public int save(ServiceInvoiceVo serviceInvoice) {
        serviceInvoice.setCreateTime(new Date());
        return serviceInvoiceMapper.save(serviceInvoice);
    }

    public int update(ServiceInvoiceVo serviceInvoice) {
        serviceInvoice.setUpdateTime(new Date());
        return serviceInvoiceMapper.update(serviceInvoice);
    }
}
