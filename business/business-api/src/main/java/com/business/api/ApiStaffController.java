package com.business.api;

import com.business.annotation.IgnoreAuth;
import com.business.entity.ConfigDataVo;
import com.business.entity.StaffVo;
import com.business.service.ApiConfigDataService;
import com.business.service.ApiStaffService;
import com.business.util.ApiBaseAction;
import com.business.utils.StringUtils;
import com.business.validator.Assert;
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
 * @date 创建日期：2020/2/27
 * @time 创建时间: 13:49
 */
@Api(tags = "员工接口")
@RestController
@RequestMapping("/api/staff")
public class ApiStaffController extends ApiBaseAction {
    @Autowired
    private ApiStaffService staffService;
    @Autowired
    private ApiConfigDataService cdService;

    /**
     * 获取用户的收货地址
     */
    @ApiOperation(value = "获取用户的收货地址接口", response = Map.class)
    @IgnoreAuth
    @GetMapping("companyInfo")
    public Object companyInfo() {
        Map params = new HashMap();
        List<ConfigDataVo> cdlist = cdService.queryList(params);
        return toResponsSuccess(cdlist);
    }

    /**
     * 获取用户的收货地址
     */
    @ApiOperation(value = "获取用户的收货地址接口", response = Map.class)
    @IgnoreAuth
    @GetMapping("detail")
    public Object detail(Integer id) {
        StaffVo entity = staffService.queryObject(id);
        //判断越权行为
        //if (!entity.getUserId().equals(loginUser.getUserId())) {
        //    return toResponsObject(403, "您无权查看", "");
        //}
        return toResponsSuccess(entity);
    }

    /**
     * 是否绑定员工
     */
    @ApiOperation(value = "是否绑定员工", response = Map.class)
    @IgnoreAuth
    @GetMapping("isBind")
    public Object isBind(@RequestParam("openid") String openid) {
        Assert.isBlank(openid, "参数错误!");
        StaffVo entity = staffService.queryByOpenId(openid);
        if (entity == null) {
            return toResponsFail("未绑定名片");
        }
        return toResponsSuccess(entity);
    }

    /**
     * 是否绑定员工
     */
    @ApiOperation(value = "我的同事", response = Map.class)
    @IgnoreAuth
    @GetMapping("colleague")
    public Object colleague(Integer id) {
        List<StaffVo> entity = staffService.colleague(id);
        return toResponsSuccess(entity);
    }

    /**
     * 绑定
     */
    @ApiOperation(value = "绑定")
    @IgnoreAuth
    @PostMapping("bingOpenIdByMobile")
    public Object bingOpenIdByMobile(@RequestParam("mobile") String mobile, @RequestParam("code") String code, @RequestParam("openid") String openid) {
        //public Object bingOpenIdByMobile( @RequestBody TT tt) {
        //String mobile = tt.getMobile();
        //String code = tt.getCode();
        //String openid = tt.getOpenid();

        Assert.isBlank(mobile, "手机号码不能为空!");
        Assert.isBlank(code, "手机验证码不能为空!");
        Assert.isBlank(openid, "参数错误!");

        StaffVo staffVo = staffService.queryByMobile(mobile);
        //StaffVo staffVo = staffService.queryByOpenId(openid);
        //if (null == staffVo) {
        //    return toResponsFail("名片不存在，请绑定");
        //}
        if (!StringUtils.isNullOrEmpty(staffVo.getWeixin_openid())) {
            return toResponsFail("该手机号已绑定过");
        }
        int s = staffService.bingOpenIdByMobile(openid, mobile);
        if (s == 0) {
            return toResponsFail("绑定失败");
        }
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //resultObj.put("openid", openid);
        staffVo.setMobile(mobile);
        resultObj.put("staffVo", staffVo);
        return toResponsSuccess(resultObj);
    }
}
