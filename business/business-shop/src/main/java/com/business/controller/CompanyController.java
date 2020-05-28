package com.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.entity.CompanyEntity;
import com.business.service.CompanyService;
import com.business.utils.*;
import com.qiniu.util.StringUtils;
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
        for (CompanyEntity companyEntity : companyInfoList) {
            if (!StringUtils.isNullOrEmpty(companyEntity.getQrcode()))
                companyEntity.setQrcode(ResourceUtil.getConfigByName("Api.RootUrl") + "/upload/" + companyEntity.getQrcode());
        }
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

    /**
     * 创建绑定员工二维码
     */
    @RequestMapping("/createQrCode/{companyId}")
    @RequiresPermissions("companyinfo:update")
    public R createQrCode(@PathVariable("companyId") Integer companyId) {
        if (companyId == 0) {
            return R.error("参数错误");
        }
        CompanyEntity info = companyService.queryObject(companyId);
        if (info == null) {
            return R.error("查询无数据");
        } else {
            if (!StringUtils.isNullOrEmpty(info.getQrcode())) {
                return R.error("二维码已存在");
            }
            if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
                info.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());
            } else {
                info.setCompanyId(companyId);
            }
        }
        if (info.getCompanyId() == 0) {
            return R.error("参数错误.");
        }
        String apiurl = ResourceUtil.getConfigByName("Api.RootUrl") + "/api/wechat/createQrCode";
        Map<String, String> map = new HashMap<>();
        map.put("param", "companyId=" + companyId);
        map.put("path", "pages/company/staff/bind/bind");
        map.put("fileDir", "qrcode/company");
        map.put("fileName", "c_" + companyId);
        String res = HttpUtil.URLGet(apiurl, map, "utf-8");
        JSONObject json = JSON.parseObject(res);
        String qrcode = json.getString("data");
        if (null == json || StringUtils.isNullOrEmpty(qrcode)) {
            return R.error("生成二维码失败");
        }
        info.setQrcode(qrcode);
        companyService.update(info);

        return R.ok();
    }
}
