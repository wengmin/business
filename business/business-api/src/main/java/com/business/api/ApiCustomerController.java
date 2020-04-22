package com.business.api;

import com.alibaba.fastjson.JSONObject;
import com.business.annotation.IgnoreAuth;
import com.business.annotation.LoginUser;
import com.business.entity.CustomerCompanyVo;
import com.business.entity.CustomerUserVo;
import com.business.service.ApiCustomerCompanyService;
import com.business.service.ApiCustomerUserService;
import com.business.util.ApiBaseAction;
import com.business.util.QRCodeUtils;
import com.business.validator.Assert;
import com.qiniu.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/15
 * @time 创建时间: 10:29
 */
@Api(tags = "客户管理")
@RestController
@RequestMapping("/api/customer")
public class ApiCustomerController extends ApiBaseAction {
    @Autowired
    private ApiCustomerUserService userService;
    @Autowired
    private ApiCustomerCompanyService companyService;

    /**
     * 用户详情
     */
    @ApiOperation(value = "用户详情")
    @IgnoreAuth
    @GetMapping("userDetailById")
    public Object userDetailById(Integer id) {
        CustomerUserVo entity = userService.queryObject(id);
        return toResponsSuccess(entity);
    }

    /**
     * 是否注册过
     */
    @ApiOperation(value = "是否注册过")
    @IgnoreAuth
    @GetMapping("userDetailByOpenid")
    public Object userDetailByOpenid(@RequestParam("openid") String openid) {
        Assert.isBlank(openid, "参数错误!");
        CustomerUserVo entity = userService.queryByOpenId(openid);
        if (entity == null) {
            return toResponsFail("未注册名片");
        }
        return toResponsSuccess(entity);
    }

    /**
     * 编辑用户信息
     */
    @ApiOperation(value = "编辑用户信息", response = Map.class)
    @PostMapping("saveUser")
    public Object saveUser(@LoginUser CustomerUserVo loginUser) {
        JSONObject parameterJson = this.getJsonRequest();
        CustomerUserVo entity = new CustomerUserVo();
        if (null != parameterJson) {
            String companyname = parameterJson.getString("companyName");
            companyname = companyname != null ? companyname.trim() : "";
            CustomerCompanyVo company = companyService.queryByName(companyname);
            if (company != null) {
                entity.setCompanyId(company.getCompanyId());
            } else {
                company = new CustomerCompanyVo();
                company.setName(companyname);
                companyService.save(company);
                entity.setCompanyId(company.getCompanyId());
            }

            entity.setUserId(loginUser.getUserId());
            entity.setRealname(parameterJson.getString("realname").trim());
            entity.setPosition(parameterJson.getString("position").trim());
            entity.setMobile(parameterJson.getString("mobile").trim());
            entity.setEmail(parameterJson.getString("email"));
            entity.setWechat(parameterJson.getString("wechat"));
            entity.setProfile(parameterJson.getString("profile"));
            entity.setPhoto(parameterJson.getString("photo"));
            entity.setProvince(parameterJson.getString("province"));
            entity.setCity(parameterJson.getString("city"));
            entity.setCounty(parameterJson.getString("county"));
            entity.setAddress(parameterJson.getString("address"));
            entity.setTelephone(parameterJson.getString("telephone"));
            entity.setWeixin_openid(parameterJson.getString("weixin_openid"));

            CustomerUserVo has = userService.queryByOpenId(entity.getWeixin_openid());
            if (has.getUserId() == null || has.getUserId() == 0) {
                userService.save(entity);
            } else {
                userService.updateByOpenId(entity);
            }
            return toResponsSuccess(entity);
        }
        return toResponsFail("参数错误");
    }

    /**
     * 生成二维码
     */
    @ApiOperation(value = "生成二维码")
    @IgnoreAuth
    @GetMapping("createQrCode")
    public Object createQrCode(String userId) {
        if (StringUtils.isNullOrEmpty(userId)) {
            return toResponsFail("参数错误");
        }
        String accessToken = tokenService.getAccessToken();
        String imgStr = QRCodeUtils.getCardQrCode(accessToken, userId);
        if (!StringUtils.isNullOrEmpty(imgStr)) {
            CustomerUserVo uVo = new CustomerUserVo();
            uVo.setUserId(Integer.parseInt(userId));
            uVo.setQrCode(imgStr);
            userService.update(uVo);
        }
        return toResponsSuccess(imgStr);
    }
}
