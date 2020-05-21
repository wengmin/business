package com.business.controller;

import com.business.entity.CompanyPostEntity;
import com.business.service.CompanyPostService;
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
}
