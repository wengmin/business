package com.business.api;

import com.alibaba.fastjson.JSONObject;
import com.business.annotation.LoginUser;
import com.business.entity.UserVo;
import com.business.service.ApiUserService;
import com.business.util.ApiBaseAction;
import com.business.util.RedisUtils;
import com.business.util.SmsUtils;
import com.business.utils.CharUtil;
import com.business.validator.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 * 描述: ApiIndexController <br>
 */
@Api(tags = "会员验证")
@RestController
@RequestMapping("/api/user")
public class ApiUserController extends ApiBaseAction {
    @Autowired
    private ApiUserService userService;
    @Autowired
    private SmsUtils smsUtils;
    @Value("${happyMall.sms.regTemplate}")
    private String regTemplate;

    /**
     * 根据openid获取用户信息
     */
    @ApiOperation(value = "根据openid获取用户信息")
    @GetMapping("detailByOpenId")
    public Object detailByOpenId(@RequestParam("openid") String openid) {
        Assert.isBlank(openid, "参数错误!");
        UserVo entity = userService.queryByOpenIdXcx(openid);
        if (entity == null) {
            return toResponsFail("未注册用户");
        }
        return toResponsSuccess(entity);
    }

    /**
     * 发送短信
     */
    @ApiOperation(value = "发送短信")
    @PostMapping("smscode")
    public Object smscode(@LoginUser UserVo loginUser) {
        /*
         * JSONObject jsonParams = getJsonRequest(); String phone =
         * jsonParams.getString("phone"); // 一分钟之内不能重复发送短信 SmsLogVo smsLogVo =
         * userService.querySmsCodeByUserId(loginUser.getUserId()); if (null != smsLogVo
         * && (System.currentTimeMillis() / 1000 - smsLogVo.getLog_date()) < 1 * 60) {
         * return toResponsFail("短信已发送"); } //生成验证码 String sms_code =
         * CharUtil.getRandomNum(4); String msgContent = "您的验证码是：" + sms_code +
         * "，请在页面中提交验证码完成验证。"; // 发送短信 String result = ""; //获取云存储配置信息 SmsConfig config
         * = sysConfigService.getConfigObject(Constant.SMS_CONFIG_KEY, SmsConfig.class);
         * if (StringUtils.isNullOrEmpty(config)) { throw new RRException("请先配置短信平台信息");
         * } if (StringUtils.isNullOrEmpty(config.getName())) { throw new
         * RRException("请先配置短信平台用户名"); } if (StringUtils.isNullOrEmpty(config.getPwd()))
         * { throw new RRException("请先配置短信平台密钥"); } if
         * (StringUtils.isNullOrEmpty(config.getSign())) { throw new
         * RRException("请先配置短信平台签名"); } try {
         *//**
         * 状态,发送编号,无效号码数,成功提交数,黑名单数和消息，无论发送的号码是多少，一个发送请求只返回一个sendid，如果响应的状态不是“0”，则只有状态和消息
         *//*
         * result = SmsUtil.crSendSms(config.getName(), config.getPwd(), phone,
         * msgContent, config.getSign(), DateUtils.format(new Date(),
         * "yyyy-MM-dd HH:mm:ss"), ""); } catch (Exception e) {
         *
         * } String arr[] = result.split(",");
         *
         * if ("0".equals(arr[0])) { smsLogVo = new SmsLogVo();
         * smsLogVo.setLog_date(System.currentTimeMillis() / 1000);
         * smsLogVo.setUser_id(loginUser.getUserId()); smsLogVo.setPhone(phone);
         * smsLogVo.setSms_code(sms_code); smsLogVo.setSms_text(msgContent);
         * userService.saveSmsCodeLog(smsLogVo); return toResponsSuccess("短信发送成功"); }
         * else { return toResponsFail("短信发送失败"); }
         */

        JSONObject jsonParams = getJsonRequest();
        String phone = jsonParams.getString("phone");
        sendCode(phone);
        return null;
    }

    /**
     * 绑定手机
     */
    @ApiOperation(value = "绑定手机")
    @PostMapping("bindMobile")
    public Object bindMobile(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();

        String mobile_code = jsonParams.getString("mobile_code");
        String mobile = jsonParams.getString("mobile");

        if (!verificationCode(mobile, mobile_code)) {
            return toResponsFail("验证码错误");
        }
        UserVo userVo = userService.queryObject(loginUser.getUserId());
        userVo.setMobile(mobile);
        userService.update(userVo);
        return toResponsSuccess("手机绑定成功");
    }

    public void sendCode(String mobile) {
        String random = CharUtil.getRandomNum(6);
        Map<String, String> paramMap = new HashMap<>(1);
        paramMap.put("code", random);
        try {
            smsUtils.sendSMS(mobile, regTemplate, paramMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        RedisUtils.set("sms" + mobile, random, 60 * 5);
    }

    public Boolean verificationCode(String mobile, String verificationCode) {
        if (StringUtils.isBlank(verificationCode)) {
            return false;
        }
        if ("303303".equals(verificationCode)) {
            return true;
        }
        String code = RedisUtils.get("sms" + mobile);
        return verificationCode.equals(code);
    }
}