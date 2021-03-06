package com.business.controller;

import com.business.entity.CompanyFileEntity;
import com.business.service.CompanyFileService;
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
 * @date 2020-05-14 13:46:23
 */
@RestController
@RequestMapping("companyfile")
public class CompanyFileController {
    @Autowired
    private CompanyFileService companyFileService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("companyfile:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            query.put("companyId", ShiroUtils.getUserEntity().getCompanyId());
        }
        List<CompanyFileEntity> companyFileList = companyFileService.queryList(query);
        int total = companyFileService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(companyFileList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("companyfile:info")
    public R info(@PathVariable("id") Integer id) {
        CompanyFileEntity companyFile = companyFileService.queryObject(id);
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            if (companyFile.getCompanyId() != ShiroUtils.getUserEntity().getCompanyId()) {
                return R.error("非法操作");
            }
        }
        return R.ok().put("companyFile", companyFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("companyfile:save")
    public R save(@RequestBody CompanyFileEntity companyFile) {
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            companyFile.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());
        }
        companyFileService.save(companyFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("companyfile:update")
    public R update(@RequestBody CompanyFileEntity companyFile) {
        CompanyFileEntity info = companyFileService.queryObject(companyFile.getId());
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            if (info.getCompanyId() != ShiroUtils.getUserEntity().getCompanyId()) {
                return R.error("非法操作");
            }
        }
        companyFileService.update(companyFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("companyfile:delete")
    public R delete(@RequestBody Integer[] ids) {
        companyFileService.deleteBatch(ids);

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
        List<CompanyFileEntity> list = companyFileService.queryList(params);

        return R.ok().put("list", list);
    }
}
