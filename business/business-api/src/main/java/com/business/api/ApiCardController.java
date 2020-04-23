package com.business.api;

import com.alibaba.fastjson.JSONObject;
import com.business.annotation.IgnoreAuth;
import com.business.annotation.LoginUser;
import com.business.entity.*;
import com.business.service.*;
import com.business.util.ApiBaseAction;
import com.business.util.QRCodeUtils;
import com.business.utils.Base64;
import com.business.utils.PageUtils;
import com.business.validator.Assert;
import com.qiniu.util.StringUtils;
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
    private ApiCompanyService companyService;
    @Autowired
    private ApiUserService userService;
    @Autowired
    private ApiCardUserService cardUserService;
    @Autowired
    private ApiCardRecordService recordService;
    @Autowired
    private ApiCardCollectService collectService;

    /**
     * 根据openid获取名片信息
     */
    @ApiOperation(value = "根据openid获取名片信息")
    @IgnoreAuth
    @GetMapping("cardUserByOpenId")
    public Object cardUserByOpenId(@RequestParam("openid") String openid) {
        Assert.isBlank(openid, "参数错误!");
        CardUserVo entity = cardUserService.queryByOpenId(openid);
        if (entity == null) {
            return toResponsFail("未注册名片");
        }
        return toResponsSuccess(entity);
    }

    /**
     * 编辑用户名片信息
     */
    @ApiOperation(value = "编辑用户名片信息", response = Map.class)
    @PostMapping("saveCardUser")
    public Object saveCardUser(@LoginUser UserVo loginUser) {
        JSONObject parameterJson = this.getJsonRequest();
        if (null != parameterJson) {
            Integer companyId = 0;
            String companyname = parameterJson.getString("companyName");
            companyname = companyname != null ? companyname.trim() : "";
            CompanyVo company = companyService.queryByName(companyname);
            if (company != null) {
                companyId = company.getCompanyId();
            } else {
                company = new CompanyVo();
                company.setName(companyname);
                companyService.save(company);
                companyId = company.getCompanyId();
            }

            String mobile = parameterJson.getString("mobile");
            String realName = parameterJson.getString("realname");
            String photo = parameterJson.getString("photo");
            String position = parameterJson.getString("position");
            String wechat = parameterJson.getString("wechat");
            String email = parameterJson.getString("email");
            String profile = parameterJson.getString("profile");
            String province = parameterJson.getString("province");
            String city = parameterJson.getString("city");
            String county = parameterJson.getString("county");
            String address = parameterJson.getString("address");
            String telephone = parameterJson.getString("telephone");

            UserVo user = userService.queryObject(loginUser.getUserId());
            mobile = mobile != null ? Base64.encode(mobile.trim()) : "";
            if (!StringUtils.isNullOrEmpty(mobile) && user.getMobile() != mobile) {
                user = new UserVo();
                user.setUserId(loginUser.getUserId());
                user.setMobile(mobile);
                userService.update(user);
            }

            CardUserVo entity = cardUserService.queryByUserId(loginUser.getUserId());
            Boolean isnew = true;
            if (null == entity) {
                entity = new CardUserVo();
            } else {
                isnew = false;
            }
            entity.setUserId(loginUser.getUserId());
            entity.setCompanyId(companyId);
            entity.setRealname(realName != null ? Base64.encode(realName.trim()) : "");
            entity.setPhoto(photo != null ? photo.trim() : "");
            entity.setPosition(position != null ? position.trim() : "");
            entity.setWechat(wechat != null ? Base64.encode(wechat.trim()) : "");
            entity.setEmail(email != null ? Base64.encode(email.trim()) : "");
            entity.setProfile(profile != null ? profile.trim() : "");
            entity.setProvince(province != null ? province.trim() : "");
            entity.setCity(city != null ? city.trim() : "");
            entity.setCounty(county != null ? county.trim() : "");
            entity.setAddress(address != null ? address.trim() : "");
            entity.setTelephone(telephone != null ? telephone.trim() : "");

            if (isnew) {
                cardUserService.save(entity);
            } else {
                cardUserService.update(entity);
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
    public Object createQrCode(String openid) {
        if (StringUtils.isNullOrEmpty(openid)) {
            return toResponsFail("参数错误");
        }
        String accessToken = tokenService.getAccessToken();
        String imgStr = QRCodeUtils.getCardQrCode(accessToken, openid);
        if (!StringUtils.isNullOrEmpty(imgStr)) {
            CardUserVo uVo = new CardUserVo();
            uVo.setUserId(Integer.parseInt(openid));
            uVo.setQrCode(imgStr);
            cardUserService.update(uVo);
        }
        return toResponsSuccess(imgStr);
    }

    /**
     * 获取浏览记录列表
     */
    @ApiOperation(value = "获取浏览记录列表", response = Map.class)
    @GetMapping("listRecord")
    public Object listRecord(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, @LoginUser UserVo loginUser) {
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
    public Object saveRecord(@LoginUser UserVo loginUser, String openid) {
        if (StringUtils.isNullOrEmpty(openid)) {
            return toResponsFail("参数错误");
        }
        UserVo user = userService.queryByOpenId(openid);
        Integer touserid = user.getUserId();
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
    public Object listCollect(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, @LoginUser UserVo loginUser) {
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
    public Object isCollect(@LoginUser UserVo loginUser, String openid) {
        UserVo user = userService.queryByOpenId(openid);
        int hasuser = collectService.queryHasUser(loginUser.getUserId(), user.getUserId());
        return toResponsSuccess(hasuser);
    }

    @ApiOperation(value = "收藏名片")
    @PostMapping("saveCollect")
    public Object saveCollect(@LoginUser UserVo loginUser, String openid) {
        if (StringUtils.isNullOrEmpty(openid)) {
            return toResponsFail("参数错误");
        }
        UserVo user = userService.queryByOpenId(openid);
        Integer touserid = user.getUserId();
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
