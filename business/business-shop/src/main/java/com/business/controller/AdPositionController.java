package com.business.controller;

import com.business.entity.AdPositionEntity;
import com.business.service.AdPositionService;
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
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-19 12:02:42
 */
@RestController
@RequestMapping("adposition")
public class AdPositionController {
    @Autowired
    private AdPositionService adPositionService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("adposition:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<AdPositionEntity> adPositionList = adPositionService.queryList(query);
        int total = adPositionService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(adPositionList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("adposition:info")
    public R info(@PathVariable("id") Integer id) {
        AdPositionEntity adPosition = adPositionService.queryObject(id);

        return R.ok().put("adPosition", adPosition);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("adposition:save")
    public R save(@RequestBody AdPositionEntity adPosition) {
        adPositionService.save(adPosition);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("adposition:update")
    public R update(@RequestBody AdPositionEntity adPosition) {
        adPositionService.update(adPosition);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("adposition:delete")
    public R delete(@RequestBody Integer[] ids) {
        adPositionService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<AdPositionEntity> list = adPositionService.queryList(params);

        return R.ok().put("list", list);
    }
}
