package com.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.entity.CompanyRoomEntity;
import com.business.service.CompanyRoomService;
import com.business.utils.*;
import com.qiniu.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        for (CompanyRoomEntity companyRoomEntity : companyRoomList) {
            if (!StringUtils.isNullOrEmpty(companyRoomEntity.getQrcode()))
                companyRoomEntity.setQrcode(ResourceUtil.getConfigByName("Api.RootUrl") + "/upload/" + companyRoomEntity.getQrcode());
        }
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

    /**
     * 创建绑定员工二维码
     */
    @RequestMapping("/createQrCode/{roomId}")
    @RequiresPermissions("companyroom:update")
    public R createQrCode(@PathVariable("roomId") Integer roomId) {
        if (roomId == 0) {
            return R.error("参数错误");
        }
        CompanyRoomEntity info = companyRoomService.queryObject(roomId);
        if (info == null) {
            return R.error("查询无数据");
        } else {
            if (!StringUtils.isNullOrEmpty(info.getQrcode())) {
                return R.error("二维码已存在");
            }
            if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId() && info.getCompanyId() != ShiroUtils.getUserEntity().getCompanyId()) {
                return R.error("非法数据");
            }
        }
        String apiurl = ResourceUtil.getConfigByName("Api.RootUrl") + "/api/wechat/createQrCode";
        Map<String, String> map = new HashMap<>();
        map.put("param", "roomId=" + roomId);
        map.put("path", "pages/service/index/index");
        map.put("fileDir", "qrcode/companyroom");
        map.put("fileName", "r_" + info.getCompanyId() + "_" + roomId);
        String res = HttpUtil.URLGet(apiurl, map, "utf-8");
        JSONObject json = JSON.parseObject(res);
        String qrcode = json.getString("data");
        if (null == json || StringUtils.isNullOrEmpty(qrcode)) {
            return R.error("生成二维码失败");
        }
        CompanyRoomEntity companyRoom = new CompanyRoomEntity();
        companyRoom.setRoomId(roomId);
        companyRoom.setQrcode(qrcode);
        companyRoomService.update(companyRoom);
        return R.ok();
    }
}
