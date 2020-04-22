package com.business.controller;

import com.business.annotation.SysLog;
import com.business.entity.MailEntity;
import com.business.entity.SysMailFollowEntity;
import com.business.service.MailService;
import com.business.utils.Constant;
import com.business.utils.PageUtils;
import com.business.utils.Query;
import com.business.utils.R;
import com.business.validator.ValidatorUtils;
import com.business.validator.group.AddGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/16
 * @time 创建时间: 10:30
 */
@RestController
@RequestMapping("mail")
public class MailController extends AbstractController {
    @Autowired
    private MailService sysMailService;

    /**
     * 所有通讯录列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("mail:list")
    public R list(@RequestParam Map<String, Object> params) {
        //只有超级管理员，才能查看所有管理员列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("staffId", getUserId());
        }

        //查询列表数据
        Query query = new Query(params);
        List<MailEntity> staffList = sysMailService.queryList(query);
        int total = sysMailService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(staffList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 跟进列表
     */
    @RequestMapping("/followList/{rowId}")
    @RequiresPermissions("mail:followList")
    public R followList(@PathVariable("rowId") Integer rowId) {
        //查询列表数据
        List<SysMailFollowEntity> list = sysMailService.followList(rowId);

        return R.ok().put("list", list);
    }

    /**
     * 保存跟进记录
     */
    @SysLog("保存跟进记录")
    @RequestMapping("/saveFollow")
    @RequiresPermissions("mail:saveFollow")
    public R saveFollow(@RequestBody SysMailFollowEntity follow) {
        ValidatorUtils.validateEntity(follow, AddGroup.class);

        sysMailService.saveFollows(follow);

        return R.ok();
    }
}
