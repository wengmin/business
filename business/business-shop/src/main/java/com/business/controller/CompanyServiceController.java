package com.business.controller;

import com.business.entity.CompanyServiceEntity;
import com.business.entity.SysMacroEntity;
import com.business.service.CompanyServiceService;
import com.business.service.SysMacroService;
import com.business.utils.*;
import com.business.validator.ValidatorUtils;
import com.business.validator.group.AddGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-14 13:46:23
 */
@RestController
@RequestMapping("companyservice")
public class CompanyServiceController {
    @Autowired
    private CompanyServiceService companyServiceService;
    @Autowired
    private SysMacroService sysMacroService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("companyservice:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            query.put("companyId", ShiroUtils.getUserEntity().getCompanyId());
        }
        List<CompanyServiceEntity> companyServiceList = companyServiceService.queryList(query);
        int total = companyServiceService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(companyServiceList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info")
    @RequiresPermissions("companyservice:info")
    public R info(Integer companyId, String serviceClass) {
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            companyId = ShiroUtils.getUserEntity().getCompanyId();
        }
        CompanyServiceEntity companyService = companyServiceService.queryByCompanyService(companyId, serviceClass);
        return R.ok().put("companyService", companyService);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("companyservice:save")
    public R save(@RequestBody CompanyServiceEntity companyService) {
        ValidatorUtils.validateEntity(companyService, AddGroup.class);
        int companyId = 0;
        if (Constant.SUPER_ADMIN == ShiroUtils.getUserEntity().getUserId()) {
            companyId = companyService.getCompanyId();
        } else {
            companyId = ShiroUtils.getUserEntity().getCompanyId();
        }
        if (companyId == 0 || companyService.getListServiceTag() == null) {
            return R.error("参数不满足要求");
        }
        companyServiceService.disableByCompanyService(companyId, companyService.getServiceClass());
        for (String s : companyService.getListServiceTag()) {
            CompanyServiceEntity hastag = companyServiceService.hasTag(companyId, companyService.getServiceClass(), s);
            if (hastag == null) {
                CompanyServiceEntity entity = new CompanyServiceEntity();
                entity.setCompanyId(companyId);
                entity.setServiceClass(companyService.getServiceClass());
                entity.setServiceTag(s);
                companyServiceService.save(entity);
            } else {
                CompanyServiceEntity entity = new CompanyServiceEntity();
                entity.setServiceId(hastag.getServiceId());
                entity.setStatus(0);
                companyServiceService.update(entity);
            }
        }

        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/saveTag")
    @RequiresPermissions("companyservice:save")
    public R saveTag(Integer companyId, String serviceClass, String serviceTag) {
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            companyId = ShiroUtils.getUserEntity().getCompanyId();
        }
        if (companyId == 0 || StringUtils.isNullOrEmpty(serviceClass) || StringUtils.isNullOrEmpty(serviceTag)) {
            return R.error("参数不满足要求");
        }
        CompanyServiceEntity hastag = companyServiceService.hasTag(companyId, serviceClass, serviceTag);
        if (hastag == null) {
            CompanyServiceEntity entity = new CompanyServiceEntity();
            entity.setCompanyId(companyId);
            entity.setServiceClass(serviceClass);
            entity.setServiceTag(serviceTag);
            companyServiceService.save(entity);
            return R.ok();
        } else {
            CompanyServiceEntity entity = new CompanyServiceEntity();
            entity.setServiceId(hastag.getServiceId());
            entity.setStatus(0);
            companyServiceService.update(entity);
            return R.ok();
        }
    }

    /**
     * 根据value查询数据字典
     *
     * @param companyId value
     * @return R
     */
    @RequestMapping("/getServiceTag")
    public R getServiceTag(Integer companyId, String serviceClass) {
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            companyId = ShiroUtils.getUserEntity().getCompanyId();
        }
        List<String> list = new ArrayList<String>();
        List<SysMacroEntity> mlist = sysMacroService.queryMacrosByValue(serviceClass);
        for (SysMacroEntity sysMacroEntity : mlist) {
            list.add(sysMacroEntity.getName());
        }
        List<String> alist = companyServiceService.queryTagList(companyId, serviceClass);
        list.removeAll(alist);
        list.addAll(alist);
        return R.ok().put("list", list);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("companyservice:delete")
    public R delete(Integer companyId, String serviceClass) {
        companyServiceService.deleteByCompanyService(companyId, serviceClass);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CompanyServiceEntity> list = companyServiceService.queryList(params);

        return R.ok().put("list", list);
    }
}
