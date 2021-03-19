package com.business.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.dao.ApiWeChatMapper;
import com.business.entity.*;
import com.business.util.ApiUserUtils;
import com.business.utils.ResourceUtil;
import com.business.utils.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/6/1
 * @time 创建时间: 17:51
 */
@Service
public class ApiWeChatService {
    @Autowired
    private ApiWeChatMapper wechatDao;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 发送服务通知（小程序）
     *
     * @param access_token app的token
     * @param openid       用户openid
     * @param templateId   模板ID
     * @param paras        {与模板字段一一对应}
     * @return
     */
    public String sendSubscribe(String access_token, String openid, String page, String templateId, Map<String, WeChatTemplateDataVo> paras) {
        //
        ////如果access_token为空则从新获取
        //if(StringUtils.isNullOrEmpty(access_token)){
        //    access_token = getAccess_token();
        //}
        RestTemplate restTemplate = new RestTemplate();

        String url = ApiUserUtils.getSendSubscribe(access_token);

        //拼接推送的模版
        WeChatTemplateVo vo = new WeChatTemplateVo();
        vo.setTouser(openid);//用户openid
        vo.setTemplate_id(templateId);//模版id
        vo.setPage(page);//模版id
        vo.setData(paras);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, vo, String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        WeChatMessageVo message = new WeChatMessageVo();
        message.setReceiver(openid);
        message.setSendContent(JSONObject.toJSONString(vo));
        message.setTemplateId(templateId);
        message.setSendTime(new Date());
        message.setCreateTime(new Date());
        if (jsonObject.getString("errcode").equals("0")) {
            message.setStatus(1);
        } else {
            message.setStatus(2);
            message.setErrorMsg(responseEntity.getBody());
        }
        wechatDao.saveMessage(message);
        return responseEntity.getBody();
    }

    public String sendTemplate(String token, String page, String templateId, String openid, TreeMap<String, TreeMap<String, String>> params) {
        String postUrl = String.format(ResourceUtil.getConfigByName("wx.sendTemplate"), token);

        // 将信息封装到实体类中
        WeChatTemplateMessage temp = new WeChatTemplateMessage();
        // 设置模板id
        temp.setTemplate_id(templateId);
        // 设置接受方的openid
        temp.setTouser(openid);
        // 设置点击跳转的路径
        temp.setUrl("http://mp.weixin.qq.com");
        // 主要是这里， 设置小程序的appid和转发的页面
        TreeMap<String, String> miniprograms = new TreeMap<String, String>();
        miniprograms.put("appid", ResourceUtil.getConfigByName("wx.appId"));
        miniprograms.put("pagepath", page);// 注意，这里是支持传参的！！！
        temp.setMiniprogram(miniprograms);
        // 设置消息内容和对应的颜色
        //TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
        //// 设置消息内容，具体的按照你选择的模板消息来
        //params.put("first", WeChatTemplateMessage.item("1", "#173177"));
        //params.put("keyword1", WeChatTemplateMessage.item("2", "#173177"));
        //params.put("keyword2", WeChatTemplateMessage.item("3", "#173177"));
        //params.put("keyword3", WeChatTemplateMessage.item("4", "#173177"));
        //params.put("remark", WeChatTemplateMessage.item("5", "#173177"));
        temp.setData(params);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(postUrl, temp, String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        WeChatMessageVo message = new WeChatMessageVo();
        message.setReceiver(openid);
        message.setSendContent(JSONObject.toJSONString(temp));
        message.setTemplateId(templateId);
        message.setSendTime(new Date());
        message.setCreateTime(new Date());
        if (jsonObject.getString("errcode").equals("0")) {
            message.setStatus(1);
        } else {
            message.setStatus(2);
            message.setErrorMsg(responseEntity.getBody());
        }
        wechatDao.saveMessage(message);
        return responseEntity.getBody();
    }


    /**
     * 解析
     *
     * @param session_key
     * @param encryptedData
     * @param iv
     * @return
     * @throws Exception
     */
    public JSONObject analysis(String session_key, String encryptedData, String iv) throws Exception {
        byte[] dataByte = Base64.decode(encryptedData);

        byte[] keyByte = Base64.decode(session_key);

        byte[] ivByte = Base64.decode(iv);
        try {

            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new Exception("analysis:" + e);
        }
        return null;
    }

    /**
     * 获取用户信息（公众号）
     *
     * @param access_token
     * @param openid
     * @return
     * @throws Exception
     */
    public UserVo getGzhWeChatUser(String access_token, String openid) throws Exception {
        UserVo user = null;
        // 拼接请求地址
        String requestUrl = ApiUserUtils.getGzhUserInfo(access_token, openid);
        //String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        //requestUrl = requestUrl.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
        // 获取用户信息
        String res = restTemplate.getForObject(requestUrl, String.class);
        String json = new String(res.getBytes("ISO-8859-1"), "UTF-8");
        JSONObject jsonObject = JSON.parseObject(json);
        if (null != jsonObject) {
            try {
                user = new UserVo();
                // 用户的标识
                user.setUnionid(jsonObject.getString("unionid"));
                // 用户的标识
                user.setOpenidGzh(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                user.setSubscribe(StringUtils.parseInt(jsonObject.getString("subscribe"), -1));
                // 用户关注时间
                user.setSubscribeTime(new Date(Long.parseLong(jsonObject.getString("subscribe_time")) * 1000));
                // 用户关注渠道
                user.setSubscribeScene(jsonObject.getString("subscribe_scene"));
                // 昵称
                user.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                user.setSex(StringUtils.parseInt(jsonObject.getString("sex")));
                // 用户所在国家
                user.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                user.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                user.setCity(jsonObject.getString("city"));
                // 用户的语言，简体中文为zh_CN
                user.setLanguage(jsonObject.getString("language"));
                // 用户头像
                user.setHeadimgurl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                int errorCode = StringUtils.parseInt(jsonObject.getString("errcode"));
                String errorMsg = jsonObject.getString("errmsg");
                throw new Exception("获取用户信息失败" + e + "user:{" + JSON.toJSONString(jsonObject) + "} errcode:{" + errorCode + "} errmsg:{" + errorMsg + "}");
            }
        } else {
            throw new Exception("获取用户信息失败");
        }
        return user;
    }
}
