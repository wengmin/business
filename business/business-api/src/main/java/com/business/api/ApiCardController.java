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
import com.business.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private ApiCardInfoService cardInfoService;
    @Autowired
    private ApiCardRecordService recordService;
    @Autowired
    private ApiCardCollectService collectService;

    /**
     * 生成参数
     *
     * @param cardId
     * @param openid
     * @return
     */
    private String getShareParam(Integer cardId, String openid) {
        try {
            if (StringUtils.isNullOrEmpty(openid) || cardId == null || cardId == 0) {
                return "";
            }
            openid = openid.substring(openid.length() - 5, openid.length());
            return cardId + "~" + openid;
        } catch (Exception e) {
            logger.error("checkShareParam.", e);
        }
        return "";
    }

    /**
     * 参数验证
     *
     * @param param
     * @return
     */
    private CardInfoVo checkShareParam(String param) {
        try {
            if (StringUtils.isNullOrEmpty(param)) {
                return null;
            }
            if (param.indexOf("param=") >= 0) {
                param = param.replace("param=", "");
            }
            String[] ps = param.split("\\~");
            if (ps.length != 2) {
                return null;
            }
            if (StringUtils.isNullOrEmpty(ps[0]) || StringUtils.isNullOrEmpty(ps[1])) {
                return null;
            }
            Integer cardId = Integer.parseInt(ps[0]);
            CardInfoVo cardVo = cardInfoService.queryObject(cardId);
            String openid = cardVo.getOpenidXcx();
            openid = StringUtils.isNullOrEmpty(openid) ? "" : openid.substring(openid.length() - 5, openid.length());
            if (openid.equals(ps[1])) {
                return cardVo;
            }
        } catch (Exception e) {
            logger.error("checkShareParam.", e);
        }
        return null;
    }

    /**
     * 根据参数验证获取名片信息
     */
    @ApiOperation(value = "根据参数验证获取名片信息")
    @IgnoreAuth
    @GetMapping("detailByParam")
    public Object detailByParam(@RequestParam("param") String param) {
        try {
            CardInfoVo entity = checkShareParam(param);
            if (entity == null) {
                logger.info("cardUserByParam." + param);
                return toResponsFail("参数错误");
            }

            CompanyVo company = companyService.queryObject(entity.getCompanyId());
            company.setFileList(companyService.queryFileList(entity.getCompanyId()));
            entity.setCompany(company);

            entity.setParam(param);
            entity.setMobile(!StringUtils.isNullOrEmpty(entity.getMobile()) ? Base64.decode(entity.getMobile()) : "");
            entity.setRealname(!StringUtils.isNullOrEmpty(entity.getRealname()) ? Base64.decode(entity.getRealname()) : "");
            entity.setWechat(!StringUtils.isNullOrEmpty(entity.getWechat()) ? Base64.decode(entity.getWechat()) : "");
            entity.setEmail(!StringUtils.isNullOrEmpty(entity.getEmail()) ? Base64.decode(entity.getEmail()) : "");
            return toResponsSuccess(entity);

        } catch (Exception e) {
            logger.error("cardUserByParam.param=>" + param, e);
        }
        return toResponsFail("错误");
    }

    /**
     * 获取名片信息
     */
    @ApiOperation(value = "获取名片信息")
    @GetMapping("detail")
    public Object detail(@RequestParam("cardId") Integer cardId) {
        try {
            CardInfoVo entity = cardInfoService.queryObject(cardId);
            if (entity == null) {
                return toResponsFail("参数错误");
            }

            CompanyVo company = companyService.queryObject(entity.getCompanyId());
            company.setFileList(companyService.queryFileList(entity.getCompanyId()));
            entity.setCompany(company);

            entity.setParam(getShareParam(entity.getCardId(), entity.getOpenidXcx()));
            entity.setMobile(!StringUtils.isNullOrEmpty(entity.getMobile()) ? Base64.decode(entity.getMobile()) : "");
            entity.setRealname(!StringUtils.isNullOrEmpty(entity.getRealname()) ? Base64.decode(entity.getRealname()) : "");
            entity.setWechat(!StringUtils.isNullOrEmpty(entity.getWechat()) ? Base64.decode(entity.getWechat()) : "");
            entity.setEmail(!StringUtils.isNullOrEmpty(entity.getEmail()) ? Base64.decode(entity.getEmail()) : "");
            return toResponsSuccess(entity);
        } catch (Exception e) {
            logger.error("detail.cardId=>" + cardId, e);
        }
        return toResponsFail("错误");
    }

    /**
     * 获取名片信息
     */
    @ApiOperation(value = "获取名片信息")
    @GetMapping("detailByLogin")
    public Object detailByLogin(@LoginUser UserVo loginUser) {
        try {
            CardInfoVo entity = cardInfoService.queryDefault(loginUser.getUserId().intValue());
            if (entity == null) {
                return toResponsFail("参数错误");
            }

            CompanyVo company = companyService.queryObject(entity.getCompanyId());
            company.setFileList(companyService.queryFileList(entity.getCompanyId()));
            entity.setCompany(company);

            entity.setParam(getShareParam(entity.getCardId(), entity.getOpenidXcx()));
            entity.setMobile(!StringUtils.isNullOrEmpty(entity.getMobile()) ? Base64.decode(entity.getMobile()) : "");
            entity.setRealname(!StringUtils.isNullOrEmpty(entity.getRealname()) ? Base64.decode(entity.getRealname()) : "");
            entity.setWechat(!StringUtils.isNullOrEmpty(entity.getWechat()) ? Base64.decode(entity.getWechat()) : "");
            entity.setEmail(!StringUtils.isNullOrEmpty(entity.getEmail()) ? Base64.decode(entity.getEmail()) : "");
            return toResponsSuccess(entity);
        } catch (Exception e) {
            logger.error("detail.cardId=>" + loginUser.getUserId(), e);
        }
        return toResponsFail("错误");
    }

    /**
     * 我的所有名片列表
     */
    @ApiOperation(value = "我的所有名片列表")
    @GetMapping("myCardList")
    public Object myCardList(@LoginUser UserVo loginUser) {
        try {
            List<CardInfoVo> list = cardInfoService.queryByUserId(loginUser.getUserId().intValue());
            for (CardInfoVo entity : list) {
                CompanyVo company = companyService.queryObject(entity.getCompanyId());
                company.setFileList(companyService.queryFileList(entity.getCompanyId()));
                entity.setCompany(company);

                entity.setParam(getShareParam(entity.getCardId(), entity.getOpenidXcx()));
                entity.setMobile(!StringUtils.isNullOrEmpty(entity.getMobile()) ? Base64.decode(entity.getMobile()) : "");
                entity.setRealname(!StringUtils.isNullOrEmpty(entity.getRealname()) ? Base64.decode(entity.getRealname()) : "");
                entity.setWechat(!StringUtils.isNullOrEmpty(entity.getWechat()) ? Base64.decode(entity.getWechat()) : "");
                entity.setEmail(!StringUtils.isNullOrEmpty(entity.getEmail()) ? Base64.decode(entity.getEmail()) : "");
            }
            return toResponsSuccess(list);
        } catch (Exception e) {
            logger.error("myCardList=>" + loginUser.getUserId(), e);
        }
        return toResponsFail("错误");
    }

    /**
     * 编辑用户名片信息
     */
    @ApiOperation(value = "编辑用户名片信息", response = Map.class)
    @PostMapping("save")
    public Object save(@LoginUser UserVo loginUser) {
        JSONObject parameterJson = this.getJsonRequest();
        if (null != parameterJson) {
            String cardId = parameterJson.getString("cardId");
            String realName = parameterJson.getString("realname");
            String mobile = parameterJson.getString("mobile");
            String photo = parameterJson.getString("photo");
            String photoremark = parameterJson.getString("photoRemark");
            String position = parameterJson.getString("position");
            String wechat = parameterJson.getString("wechat");
            String email = parameterJson.getString("email");
            String profile = parameterJson.getString("profile");

            String companyname = parameterJson.getString("companyName");
            String province = parameterJson.getString("province");
            String city = parameterJson.getString("city");
            String county = parameterJson.getString("county");
            String address = parameterJson.getString("address");
            String phone = parameterJson.getString("phone");

            CardInfoVo entity = cardInfoService.queryObject(StringUtils.parseInt(cardId));
            if (entity == null) {
                entity = cardInfoService.queryDefault(loginUser.getUserId().intValue());
            }
            if (entity == null) {
                CompanyVo company = new CompanyVo();
                company.setName(companyname);
                company.setProvince(province != null ? province.trim() : "");
                company.setCity(city != null ? city.trim() : "");
                company.setCounty(county != null ? county.trim() : "");
                company.setAddress(address != null ? address.trim() : "");
                company.setPhone(phone != null ? phone.trim() : "");
                companyService.save(company);

                entity = new CardInfoVo();
                entity.setCompanyId(company.getCompanyId());

                entity.setUserId(loginUser.getUserId().intValue());
                entity.setRealname(realName != null ? Base64.encode(realName.trim()) : "");
                entity.setMobile(mobile != null ? Base64.encode(mobile.trim()) : "");
                entity.setPhoto(photo != null ? photo.trim() : "");
                entity.setPhotoRemark(photoremark != null ? photoremark.trim() : "");
                entity.setPosition(position != null ? position.trim() : "");
                entity.setWechat(wechat != null ? Base64.encode(wechat.trim()) : "");
                entity.setEmail(email != null ? Base64.encode(email.trim()) : "");
                entity.setProfile(profile != null ? profile.trim() : "");
                cardInfoService.save(entity);
            } else {
                CompanyVo company = companyService.queryObject(entity.getCompanyId());
                company.setName(companyname);
                company.setProvince(province != null ? province.trim() : "");
                company.setCity(city != null ? city.trim() : "");
                company.setCounty(county != null ? county.trim() : "");
                company.setAddress(address != null ? address.trim() : "");
                company.setPhone(phone != null ? phone.trim() : "");
                companyService.update(company);

                entity.setRealname(realName != null ? Base64.encode(realName.trim()) : "");
                entity.setMobile(mobile != null ? Base64.encode(mobile.trim()) : "");
                entity.setPhoto(photo != null ? photo.trim() : "");
                entity.setPhotoRemark(photoremark != null ? photoremark.trim() : "");
                entity.setPosition(position != null ? position.trim() : "");
                entity.setWechat(wechat != null ? Base64.encode(wechat.trim()) : "");
                entity.setEmail(email != null ? Base64.encode(email.trim()) : "");
                entity.setProfile(profile != null ? profile.trim() : "");
                cardInfoService.update(entity);
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
    public Object createQrCode(String param) {
        try {
            CardInfoVo entity = checkShareParam(param);
            if (entity == null) {
                return toResponsFail("参数错误");
            }
            String accessToken = tokenService.getAccessToken();
            String imgStr = QRCodeUtils.createQrCodeToUrl(accessToken, "param=" + param, "pages/card/index1/index1", "qrcode/card", entity.getCardId().toString());
            if (!StringUtils.isNullOrEmpty(imgStr)) {
                CardInfoVo uVo = new CardInfoVo();
                uVo.setCardId(entity.getCardId());
                uVo.setQrCode(imgStr);
                cardInfoService.update(uVo);
            }
            return toResponsSuccess(imgStr);
        } catch (Exception e) {
            logger.error("createQrCode.param=>" + param, e);
        }
        return toResponsFail("生成二维码错误");
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
        for (CardRecordVo c : list) {
            c.setTouserName(!StringUtils.isNullOrEmpty(c.getTouserName()) ? Base64.decode(c.getTouserName()) : "");
            c.setParam(getShareParam(c.getCardId(), c.getOpenid()));
        }
        int total = recordService.queryTotal(params);
        PageUtils pageUtil = new PageUtils(list, total, size, page);
        return toResponsSuccess(pageUtil);
    }

    @ApiOperation(value = "浏览名片")
    @PostMapping("saveRecord")
    public Object saveRecord(@LoginUser UserVo loginUser, String param) {
        CardInfoVo entity = checkShareParam(param);
        if (entity == null) {
            return toResponsFail("参数错误");
        }
        if (entity.getUserId() == loginUser.getUserId().intValue()) {
            return toResponsFail("自己的不记录");
        }
        CardRecordVo record = recordService.queryByToUserId(entity.getCardId(), loginUser.getUserId().intValue());
        if (record == null) {
            record = new CardRecordVo();
            record.setTouserId(loginUser.getUserId().intValue());
            record.setUserId(entity.getUserId());
            record.setCardId(entity.getCardId());
            recordService.save(record);
        } else {
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            String nowDay = sf.format(now);
            String day = sf.format(record.getCreateTime());
            if (nowDay.equals(day)) {
                recordService.updateTime(record.getRecordId());
            } else {
                record = new CardRecordVo();
                record.setTouserId(loginUser.getUserId().intValue());
                record.setUserId(entity.getUserId());
                record.setCardId(entity.getCardId());
                recordService.save(record);
            }
        }
        return toResponsSuccess("添加成功");
    }

    @ApiOperation(value = "删除浏览记录")
    @PostMapping("deleteRecord")
    public Object deleteRecord(@RequestParam("id") Integer id) {
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
        for (CardCollectVo c : list) {
            c.setTouserName(!StringUtils.isNullOrEmpty(c.getTouserName()) ? Base64.decode(c.getTouserName()) : "");
            c.setParam(getShareParam(c.getCardId(), c.getOpenid()));
        }
        int total = collectService.queryTotal(params);
        PageUtils pageUtil = new PageUtils(list, total, size, page);
        return toResponsSuccess(pageUtil);
    }

    /**
     * 是否已收藏
     */
    @ApiOperation(value = "是否已收藏")
    @GetMapping("isCollect")
    public Object isCollect(@LoginUser UserVo loginUser, String param) {
        CardInfoVo entity = checkShareParam(param);
        if (entity == null) {
            return toResponsFail("参数错误");
        }
        int hasuser = collectService.queryHasUser(entity.getCardId(), loginUser.getUserId().intValue());
        return toResponsSuccess(hasuser);
    }

    @ApiOperation(value = "收藏名片")
    @PostMapping("saveCollect")
    public Object saveCollect(@LoginUser UserVo loginUser, String param) {
        CardInfoVo entity  = checkShareParam(param);
        if (entity == null) {
            return toResponsFail("参数错误");
        }
        if (entity.getUserId() == loginUser.getUserId().intValue()) {
            return toResponsFail("不能收藏自己的名片");
        }
        int hasuser = collectService.queryHasUser(entity.getCardId(), loginUser.getUserId().intValue());
        if (hasuser > 0) {
            return toResponsFail("已经收藏过该名片");
            //collectService.deleteByUserID(loginUser.getUserId().intValue(), refToUserId);
        } else {
            CardCollectVo record = new CardCollectVo();
            record.setTouserId(loginUser.getUserId().intValue());
            record.setUserId(entity.getUserId());
            record.setCardId(entity.getCardId());
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

    @ApiOperation(value = "我的名片数据")
    @GetMapping("report")
    public Object report(@LoginUser UserVo loginUser) {
        Integer collectCount = cardInfoService.collectCount(loginUser.getUserId().intValue());
        Integer recordCount = cardInfoService.recordCount(loginUser.getUserId().intValue());
        Integer recordTodayCount = cardInfoService.recordTodayCount(loginUser.getUserId().intValue());
        Integer shareCount = cardInfoService.shareCount(loginUser.getUserId().intValue());

        Map<String, Object> resultObj = new HashMap<String, Object>();
        resultObj.put("collectCount", collectCount);
        resultObj.put("recordCount", recordCount);
        resultObj.put("recordTodayCount", recordTodayCount);
        resultObj.put("shareCount", shareCount);
        return toResponsSuccess(resultObj);
    }
}
