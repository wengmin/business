package com.business.controller;

import com.business.entity.CompanyEntity;
import com.business.service.CompanyService;
import com.business.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/5/13
 * @time 创建时间: 18:10
 */
@RestController
@RequestMapping("company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("companyinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            query.put("companyId", ShiroUtils.getUserEntity().getCompanyId());
        }
        List<CompanyEntity> companyInfoList = companyService.queryList(query);
        int total = companyService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(companyInfoList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{companyId}")
    @RequiresPermissions("companyinfo:info")
    public R info(@PathVariable("companyId") Integer companyId) {
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            companyId = ShiroUtils.getUserEntity().getCompanyId();
        }
        CompanyEntity companyInfo = companyService.queryObject(companyId);

        return R.ok().put("companyInfo", companyInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("companyinfo:save")
    public R save(@RequestBody CompanyEntity companyInfo) {
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            companyInfo.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());
        }
        companyService.save(companyInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("companyinfo:update")
    public R update(@RequestBody CompanyEntity companyInfo) {
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            companyInfo.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());
        }
        companyService.update(companyInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("companyinfo:delete")
    public R delete(@RequestBody Integer[] companyIds) {
        companyService.deleteBatch(companyIds);

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
        List<CompanyEntity> list = companyService.queryList(params);

        return R.ok().put("list", list);
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/getAll")
    public R getAll() {
        Map<String, Object> params = new HashMap<>();
        int companyId = 0;
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            companyId = ShiroUtils.getUserEntity().getCompanyId();
            params.put("companyId", ShiroUtils.getUserEntity().getCompanyId());
        }

        List<CompanyEntity> list = companyService.queryList(params);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("cid", companyId);
        return R.ok(map);
    }
}
