package com.business.controller;

import com.business.annotation.SysLog;
import com.business.entity.SysStaffEntity;
import com.business.service.SysStaffService;
import com.business.utils.Constant;
import com.business.utils.PageUtils;
import com.business.utils.Query;
import com.business.utils.R;
import com.business.validator.ValidatorUtils;
import com.business.validator.group.AddGroup;
import com.business.validator.group.UpdateGroup;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/2
 * @time 创建时间: 17:33
 */
@RestController
@RequestMapping("/sys/staff")
public class SysStaffController extends AbstractController {

    @Autowired
    private SysStaffService sysStaffService;

    /**
     * 所有员工列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:staff:list")
    public R list(@RequestParam Map<String, Object> params) {
        //只有超级管理员，才能查看所有管理员列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }

        //查询列表数据
        Query query = new Query(params);
        List<SysStaffEntity> staffList = sysStaffService.queryList(query);
        int total = sysStaffService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(staffList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 员工信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:staff:info")
    public R info(@PathVariable("userId") Long userId) {
        SysStaffEntity staff = sysStaffService.queryObject(userId);

        return R.ok().put("staff", staff);
    }

    /**
     * 保存员工
     */
    @SysLog("保存员工")
    @RequestMapping("/save")
    @RequiresPermissions("sys:staff:save")
    public R save(@RequestBody SysStaffEntity staff) {
        ValidatorUtils.validateEntity(staff, AddGroup.class);
        int count = sysStaffService.staffCount(staff.getMobile());
        if (count > 0) {
            return R.error("手机号已存在");
        }
        staff.setCreateUserId(getUserId());
        sysStaffService.save(staff);

        return R.ok();
    }

    /**
     * 修改员工
     */
    @SysLog("修改员工")
    @RequestMapping("/update")
    @RequiresPermissions("sys:staff:update")
    public R update(@RequestBody SysStaffEntity staff) {
        ValidatorUtils.validateEntity(staff, UpdateGroup.class);

        staff.setCreateUserId(getUserId());
        sysStaffService.update(staff);

        return R.ok();
    }

    /**
     * 删除员工
     */
    @SysLog("删除员工")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:staff:delete")
    public R delete(@RequestBody Long[] staffIds) {
        if (ArrayUtils.contains(staffIds, 1L)) {
            return R.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(staffIds, getUserId())) {
            return R.error("当前员工不能删除");
        }

        sysStaffService.deleteBatch(staffIds);

        return R.ok();
    }
}
