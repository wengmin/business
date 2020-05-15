package com.business.controller;

import com.business.entity.CompanyServiceEntity;
import com.business.service.CompanyServiceService;
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
@RequestMapping("companyservice")
public class CompanyServiceController {
    @Autowired
    private CompanyServiceService companyServiceService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("companyservice:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CompanyServiceEntity> companyServiceList = companyServiceService.queryList(query);
        int total = companyServiceService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(companyServiceList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{serviceId}")
    @RequiresPermissions("companyservice:info")
    public R info(@PathVariable("serviceId") Integer serviceId) {
        CompanyServiceEntity companyService = companyServiceService.queryObject(serviceId);

        return R.ok().put("companyService", companyService);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("companyservice:save")
    public R save(@RequestBody CompanyServiceEntity companyService) {
        companyServiceService.save(companyService);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("companyservice:update")
    public R update(@RequestBody CompanyServiceEntity companyService) {
        companyServiceService.update(companyService);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("companyservice:delete")
    public R delete(@RequestBody Integer[] serviceIds) {
        companyServiceService.deleteBatch(serviceIds);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CompanyServiceEntity> list = companyServiceService.queryList(params);

        return R.ok().put("list", list);
    }
}
