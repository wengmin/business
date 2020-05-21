package com.business.controller;

import com.business.entity.CompanyRoomEntity;
import com.business.service.CompanyRoomService;
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
@RequestMapping("companyroom")
public class CompanyRoomController {
    @Autowired
    private CompanyRoomService companyRoomService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("companyroom:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            query.put("companyId", ShiroUtils.getUserEntity().getCompanyId());
        }
        List<CompanyRoomEntity> companyRoomList = companyRoomService.queryList(query);
        int total = companyRoomService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(companyRoomList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{roomId}")
    @RequiresPermissions("companyroom:info")
    public R info(@PathVariable("roomId") Integer roomId) {
        CompanyRoomEntity companyRoom = companyRoomService.queryObject(roomId);

        return R.ok().put("companyRoom", companyRoom);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("companyroom:save")
    public R save(@RequestBody CompanyRoomEntity companyRoom) {
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            companyRoom.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());
        }
        companyRoomService.save(companyRoom);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("companyroom:update")
    public R update(@RequestBody CompanyRoomEntity companyRoom) {
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            companyRoom.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());
        }
        companyRoomService.update(companyRoom);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("companyroom:delete")
    public R delete(@RequestBody Integer[] roomIds) {
        companyRoomService.deleteBatch(roomIds);

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
        List<CompanyRoomEntity> list = companyRoomService.queryList(params);

        return R.ok().put("list", list);
    }
}
