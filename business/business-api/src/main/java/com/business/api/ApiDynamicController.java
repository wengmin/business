package com.business.api;

import com.business.annotation.IgnoreAuth;
import com.business.entity.DynamicVo;
import com.business.service.ApiDynamicService;
import com.business.util.ApiBaseAction;
import com.business.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/3/23
 * @time 创建时间: 10:06
 */
@Api(tags = "动态")
@RestController
@RequestMapping("/api/dynamic")
public class ApiDynamicController extends ApiBaseAction {
    @Autowired
    private ApiDynamicService dynamicService;

    /**
     * 获取动态列表
     */
    @ApiOperation(value = "获取动态列表", response = Map.class)
    @IgnoreAuth
    @GetMapping("list")
    public Object list(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map params = new HashMap();
        params.put("offset", (page-1)*size);
        params.put("limit", size);
        //param.put("user_id", loginUser.getUserId());
        List<DynamicVo> list = dynamicService.queryList(params);
        int total = dynamicService.queryTotal(params);
        PageUtils pageUtil = new PageUtils(list, total, size, page);
        return toResponsSuccess(pageUtil);
    }

    /**
     * 获取动态的详情
     */
    @ApiOperation(value = "获取动态的详情", response = Map.class)
    @IgnoreAuth
    @GetMapping("detail")
    public Object detail(Integer id) {
        DynamicVo entity = dynamicService.queryObject(id);
        ////判断越权行为
        //if (!entity.getUserId().equals(loginUser.getUserId())) {
        //    return toResponsObject(403, "您无权查看", "");
        //}
        return toResponsSuccess(entity);
    }
}
