package com.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.entity.CompanyPostEntity;
import com.business.service.CompanyPostService;
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
 * @date 2020-05-18 17:09:25
 */
@RestController
@RequestMapping("companypost")
public class CompanyPostController {
    @Autowired
    private CompanyPostService companyPostService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("companypost:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            query.put("companyId", ShiroUtils.getUserEntity().getCompanyId());
        }
        List<CompanyPostEntity> companyPostList = companyPostService.queryList(query);
        int total = companyPostService.queryTotal(query);
        for (CompanyPostEntity companyPostEntity : companyPostList) {
            if (!StringUtils.isNullOrEmpty(companyPostEntity.getQrCode()))
                companyPostEntity.setQrCode(ResourceUtil.getConfigByName("Api.RootUrl") + "/upload/" + companyPostEntity.getQrCode());
        }
        PageUtils pageUtil = new PageUtils(companyPostList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{postId}")
    @RequiresPermissions("companypost:info")
    public R info(@PathVariable("postId") Integer postId) {
        CompanyPostEntity companyPost = companyPostService.queryObject(postId);

        return R.ok().put("companyPost", companyPost);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("companypost:save")
    public R save(@RequestBody CompanyPostEntity companyPost) {
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            companyPost.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());
        }
        companyPostService.save(companyPost);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("companypost:update")
    public R update(@RequestBody CompanyPostEntity companyPost) {
        if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId()) {
            companyPost.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());
        }
        companyPostService.update(companyPost);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("companypost:delete")
    public R delete(@RequestBody Integer[] postIds) {
        companyPostService.deleteBatch(postIds);

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
        List<CompanyPostEntity> list = companyPostService.queryList(params);

        return R.ok().put("list", list);
    }

    /**
     * 创建绑定员工二维码
     */
    @RequestMapping("/createQrCode/{postId}")
    @RequiresPermissions("companypost:update")
    public R createQrCode(@PathVariable("postId") Integer postId) {
        if (postId == 0) {
            return R.error("参数错误");
        }
        CompanyPostEntity info = companyPostService.queryObject(postId);
        if (info == null) {
            return R.error("查询无数据");
        } else {
            if (!StringUtils.isNullOrEmpty(info.getQrCode())) {
                return R.error("二维码已存在");
            }
            if (Constant.SUPER_ADMIN != ShiroUtils.getUserEntity().getUserId() && info.getCompanyId() != ShiroUtils.getUserEntity().getCompanyId()) {
                return R.error("非法数据");
            }
        }
        String apiurl = ResourceUtil.getConfigByName("Api.RootUrl") + "/api/wechat/createQrCode";
        Map<String, String> map = new HashMap<>();
        map.put("param", "postId=" + postId);
        map.put("path", "pages/company/post/post");
        map.put("fileDir", "qrcode/companypost");
        map.put("fileName", "p_" + info.getCompanyId() + "_" + postId);
        String res = HttpUtil.URLGet(apiurl, map, "utf-8");
        JSONObject json = JSON.parseObject(res);
        String qrcode = json.getString("data");
        if (null == json || StringUtils.isNullOrEmpty(qrcode)) {
            return R.error("生成二维码失败");
        }
        CompanyPostEntity companyPost = new CompanyPostEntity();
        companyPost.setPostId(postId);
        companyPost.setQrCode(qrcode);
        companyPostService.update(companyPost);
        return R.ok();
    }
}
