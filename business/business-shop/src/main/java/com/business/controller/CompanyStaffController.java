package com.business.controller;

import com.business.entity.CompanyStaffEntity;
import com.business.service.CompanyStaffService;
import com.business.utils.PageUtils;
import com.business.utils.Query;
import com.business.utils.R;
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
@RequestMapping("companystaff")
public class CompanyStaffController {
    @Autowired
    private CompanyStaffService companyStaffService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("companystaff:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CompanyStaffEntity> companyStaffList = companyStaffService.queryList(query);
        int total = companyStaffService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(companyStaffList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{staffId}")
    @RequiresPermissions("companystaff:info")
    public R info(@PathVariable("staffId") Integer staffId) {
        CompanyStaffEntity companyStaff = companyStaffService.queryObject(staffId);

        return R.ok().put("companyStaff", companyStaff);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("companystaff:save")
    public R save(@RequestBody CompanyStaffEntity companyStaff) {
        companyStaffService.save(companyStaff);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("companystaff:update")
    public R update(@RequestBody CompanyStaffEntity companyStaff) {
        companyStaffService.update(companyStaff);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("companystaff:delete")
    public R delete(@RequestBody Integer[] staffIds) {
        companyStaffService.deleteBatch(staffIds);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CompanyStaffEntity> list = companyStaffService.queryList(params);

        return R.ok().put("list", list);
    }
}
