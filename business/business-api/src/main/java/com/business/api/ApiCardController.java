package com.business.api;

import com.business.annotation.LoginUser;
import com.business.entity.CardCollectVo;
import com.business.entity.CardRecordVo;
import com.business.entity.CustomerUserVo;
import com.business.service.ApiCardCollectService;
import com.business.service.ApiCardRecordService;
import com.business.util.ApiBaseAction;
import com.business.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/16
 * @time 创建时间: 17:22
 */
@Api(tags = "名片管理")
@RestController
@RequestMapping("/api/card")
public class ApiCardController extends ApiBaseAction {
    @Autowired
    private ApiCardRecordService recordService;
    @Autowired
    private ApiCardCollectService collectService;

    /**
     * 获取浏览记录列表
     */
    @ApiOperation(value = "获取浏览记录列表", response = Map.class)
    @GetMapping("listRecord")
    public Object listRecord(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, @LoginUser CustomerUserVo loginUser) {
        Map params = new HashMap();
        params.put("offset", (page - 1) * size);
        params.put("limit", size);
        params.put("userId", loginUser.getUserId());
        List<CardRecordVo> list = recordService.queryList(params);
        int total = recordService.queryTotal(params);
        PageUtils pageUtil = new PageUtils(list, total, size, page);
        return toResponsSuccess(pageUtil);
    }

    @ApiOperation(value = "浏览名片")
    @PostMapping("saveRecord")
    public Object saveRecord(@LoginUser CustomerUserVo loginUser, Integer touserid) {
        if (touserid == 0 || touserid == null) {
            return toResponsFail("参数错误");
        }
        if (touserid == loginUser.getUserId()) {
            return toResponsFail("自己的不记录");
        }
        CardRecordVo record = recordService.queryByToUserId(loginUser.getUserId(), touserid);
        if (record == null) {
            record = new CardRecordVo();
            record.setTouserId(touserid);
            record.setUserId(loginUser.getUserId());
            recordService.save(record);
        } else {
            recordService.updateTime(record.getRecordId());
        }
        return toResponsSuccess("添加成功");
    }

    @ApiOperation(value = "删除浏览记录")
    @PostMapping("deleteRecord")
    public Object saveRecord(@RequestParam("id") Integer id) {
        if (id == 0 || id == null) {
            return toResponsFail("参数错误");
        }
        recordService.delete(id);
        return toResponsSuccess("删除成功");
    }


    /**
     * 获取收藏记录列表
     */
    @ApiOperation(value = "获取收藏记录列表", response = Map.class)
    @GetMapping("listCollect")
    public Object listCollect(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, @LoginUser CustomerUserVo loginUser) {
        Map params = new HashMap();
        params.put("offset", (page - 1) * size);
        params.put("limit", size);
        params.put("userId", loginUser.getUserId());
        System.out.println(loginUser.getUserId());
        List<CardCollectVo> list = collectService.queryList(params);
        int total = collectService.queryTotal(params);
        PageUtils pageUtil = new PageUtils(list, total, size, page);
        return toResponsSuccess(pageUtil);
    }

    /**
     * 是否已收藏
     */
    @ApiOperation(value = "是否已收藏")
    @GetMapping("isCollect")
    public Object isCollect(@LoginUser CustomerUserVo loginUser, Integer touserid) {
        int hasuser = collectService.queryHasUser(loginUser.getUserId(), touserid);
        return toResponsSuccess(hasuser);
    }

    @ApiOperation(value = "收藏名片")
    @PostMapping("saveCollect")
    public Object saveCollect(@LoginUser CustomerUserVo loginUser, Integer touserid) {
        if (touserid == 0 || touserid == null) {
            return toResponsFail("参数错误");
        }
        CardCollectVo record = new CardCollectVo();
        if (touserid == loginUser.getUserId()) {
            return toResponsFail("不能收藏自己的名片");
        }
        int hasuser = collectService.queryHasUser(loginUser.getUserId(), touserid);
        if (hasuser > 0) {
            //return toResponsFail("已经收藏过该名片");
            collectService.deleteByUserID(loginUser.getUserId(), touserid);
        } else {
            record.setTouserId(touserid);
            record.setUserId(loginUser.getUserId());
            collectService.save(record);
        }
        return toResponsSuccess("添加成功");
    }

    @ApiOperation(value = "删除收藏记录")
    @PostMapping("deleteCollect")
    public Object deleteCollect(@RequestParam("id") Integer id) {
        if (id == 0 || id == null) {
            return toResponsFail("参数错误");
        }
        collectService.delete(id);
        return toResponsSuccess("删除成功");
    }
}
