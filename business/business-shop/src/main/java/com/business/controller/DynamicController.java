package com.business.controller;

import com.business.entity.DynamicEntity;
import com.business.entity.SysUserEntity;
import com.business.service.DynamicService;
import com.business.utils.PageUtils;
import com.business.utils.Query;
import com.business.utils.R;
import com.business.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/24
 * @time 创建时间: 10:03
 */
@RestController
@RequestMapping("dynamic")
public class DynamicController {
    @Autowired
    private DynamicService dynamicService;

    /**
     * 动态列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("dynamic:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<DynamicEntity> list = dynamicService.queryList(query);
        int total = dynamicService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 动态信息
     */
    @RequestMapping("/info/{dynamicId}")
    @RequiresPermissions("dynamic:info")
    public R info(@PathVariable("dynamicId") Long dynamicId) {
        DynamicEntity dynamic = dynamicService.queryObject(dynamicId);

        return R.ok().put("dynamic", dynamic);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("dynamic:save")
    public R save(@RequestBody DynamicEntity dynamic) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        dynamic.setCreateUserId(user.getUserId());
        dynamicService.save(dynamic);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("dynamic:update")
    public R update(@RequestBody DynamicEntity dynamic) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        dynamic.setCreateUserId(user.getUserId());
        dynamicService.update(dynamic);

        return R.ok();
    }

    /**
     * 删除动态
     */
    @RequestMapping("/delete")
    @RequiresPermissions("dynamic:delete")
    public R delete(@RequestBody Long[] roleIds) {
        dynamicService.deleteBatch(roleIds);
        return R.ok();
    }
}
