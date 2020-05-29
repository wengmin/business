package com.business.controller;

import com.business.entity.ServiceInvoiceEntity;
import com.business.service.ServiceInvoiceService;
import com.business.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-29 10:24:34
 */
@RestController
@RequestMapping("serviceinvoice")
public class ServiceInvoiceController {
    @Autowired
    private ServiceInvoiceService serviceInvoiceService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("serviceinvoice:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            query.put("companyId", ShiroUtils.getUserEntity().getCompanyId());
        }
        List<ServiceInvoiceEntity> serviceInvoiceList = serviceInvoiceService.queryList(query);
        int total = serviceInvoiceService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(serviceInvoiceList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{invoiceId}")
    @RequiresPermissions("serviceinvoice:info")
    public R info(@PathVariable("invoiceId") Integer invoiceId) {
        ServiceInvoiceEntity serviceInvoice = serviceInvoiceService.queryObject(invoiceId);
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            if (serviceInvoice.getCompanyId() != ShiroUtils.getUserEntity().getCompanyId()) {
                return R.error("非法操作");
            }
        }
        return R.ok().put("serviceInvoice", serviceInvoice);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("serviceinvoice:update")
    public R update(@RequestBody ServiceInvoiceEntity serviceInvoice) {
        ServiceInvoiceEntity info = serviceInvoiceService.queryObject(serviceInvoice.getInvoiceId());
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            if (info.getCompanyId() != ShiroUtils.getUserEntity().getCompanyId()) {
                return R.error("非法操作");
            }
        }
        serviceInvoice.setStaffId(-1);
        serviceInvoiceService.update(serviceInvoice);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            params.put("companyId", ShiroUtils.getUserEntity().getCompanyId());
        }
        List<ServiceInvoiceEntity> list = serviceInvoiceService.queryList(params);

        return R.ok().put("list", list);
    }
}
