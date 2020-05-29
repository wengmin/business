package com.business.controller;

import com.business.entity.ServiceRoomEntity;
import com.business.entity.ServiceRoomLogEntity;
import com.business.service.ServiceRoomService;
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
@RequestMapping("serviceroom")
public class ServiceRoomController {
    @Autowired
    private ServiceRoomService serviceRoomService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("serviceroom:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            query.put("companyId", ShiroUtils.getUserEntity().getCompanyId());
        }
        List<ServiceRoomEntity> serviceRoomList = serviceRoomService.queryList(query);
        int total = serviceRoomService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(serviceRoomList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{serviceId}")
    @RequiresPermissions("serviceroom:info")
    public R info(@PathVariable("serviceId") Integer serviceId) {
        ServiceRoomEntity serviceRoom = serviceRoomService.queryObject(serviceId);
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            if (serviceRoom.getCompanyId() != ShiroUtils.getUserEntity().getCompanyId()) {
                return R.error("非法操作");
            }
        }
        return R.ok().put("serviceRoom", serviceRoom);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("serviceroom:update")
    public R update(@RequestBody ServiceRoomEntity serviceRoom) {
        ServiceRoomEntity info = serviceRoomService.queryObject(serviceRoom.getServiceId());
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            if (info.getCompanyId() != ShiroUtils.getUserEntity().getCompanyId()) {
                return R.error("非法操作");
            }
        }
        serviceRoomService.update(serviceRoom);

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
        List<ServiceRoomEntity> list = serviceRoomService.queryList(params);

        return R.ok().put("list", list);
    }


    /**
     * 查看日志列表
     */
    @RequestMapping("/listLog")
    @RequiresPermissions("serviceroom:list")
    public R listLog(@RequestParam Integer serviceId) {

        List<ServiceRoomLogEntity> list = serviceRoomService.queryLogList(serviceId);

        return R.ok().put("list", list);
    }
}
