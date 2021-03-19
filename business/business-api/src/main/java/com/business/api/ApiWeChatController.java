package com.business.api;

import com.alibaba.fastjson.JSON;
import com.business.annotation.IgnoreAuth;
import com.business.entity.UserVo;
import com.business.service.ApiUserService;
import com.business.service.ApiWeChatService;
import com.business.util.ApiBaseAction;
import com.business.util.wechat.EventConstant;
import com.business.util.wechat.MessageEntity;
import com.business.util.wechat.MessageUtil;
import com.business.util.wechat.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/5/26
 * @time 创建时间: 16:30
 */
@Api(tags = "会员验证")
@RestController
@RequestMapping("/api/wechat")
public class ApiWeChatController extends ApiBaseAction {
    @Autowired
    private ApiWeChatService wechatService;
    @Autowired
    private ApiUserService userService;

    @IgnoreAuth
    @ApiOperation(value = "消息验证")
    @GetMapping("/signature")
    public String get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("========WechatController========= ");
        logger.info("-----来自微信的请求123----");

        Enumeration pNames = request.getParameterNames();
        while (pNames.hasMoreElements()) {
            String name = (String) pNames.nextElement();
            String value = request.getParameter(name);
            //查看微信的请求都带了哪些参数
            String log = "name =" + name + "  value =" + value;
            logger.error(log);
        }

        String signature = request.getParameter("signature");/// 微信加密签名
        String timestamp = request.getParameter("timestamp");/// 时间戳
        String nonce = request.getParameter("nonce"); /// 随机数
        String echostr = request.getParameter("echostr"); // 随机字符串
        logger.info(echostr);
        return echostr;
    }

    /**
     * 微信公众号服务器配置接口
     */
    @ApiOperation("微信公众号服务器配置接口")
    @IgnoreAuth
    @RequestMapping(value = "/GzhHandleEvent", produces = {"application/json;charset=UTF-8"})
    public String GzhHandleEvent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("-----微信公众号服务器配置接口：开始----");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");// 随机字符串

        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if (isGet && echostr != null) {
            // 首次请求申请验证,返回echostr
            return echostr;
        }

        String message = "success";
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            try {
                Map<String, String> map = MessageUtil.parseXml(req);
                logger.info("-----微信公众号服务器配置接口Message----" + JSON.toJSONString(map));
                //转换XML
                String fromUserName = map.get("FromUserName");// 消息来源用户标识
                String toUserName = map.get("ToUserName");// 消息目的用户标识
                String msgType = map.get("MsgType");// 消息类型
                String eventKey = map.get("EventKey");// 消息内容
                String eventType = map.get("Event");
                String nickName = "";
                if (EventConstant.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {//如果为事件类型
                    if (EventConstant.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {//处理订阅事件
                        UserVo user = wechatService.getGzhWeChatUser(tokenService.getGzhAccessToken(), fromUserName);
                        if (user != null) {
                            userService.saveEdit(user);
                        }
                        //// 创建图文消息
                        //MessageEntity entity = new MessageEntity();
                        //MessageEntity.NewsMessage newsMessage = entity.new NewsMessage();
                        //newsMessage.setToUserName(fromUserName);
                        //newsMessage.setFromUserName(toUserName);
                        //newsMessage.setCreateTime(new Date().getTime());
                        //newsMessage.setMsgType(EventConstant.RESP_MESSAGE_TYPE_NEWS);
                        //newsMessage.setFuncFlag(0);
                        //List<MessageEntity.Article> articleList = new ArrayList<MessageEntity.Article>();
                        //if ("testNews".equals(eventKey)) {
                        //    MessageEntity.Article article = entity.new Article();
                        //    article.setTitle("我是一条单图文消息");
                        //    article.setDescription("我是描述信息，哈哈哈哈哈哈哈。。。");
                        //    article.setPicUrl("http://www.iteye.com/upload/logo/user/603624/2dc5ec35-073c-35e7-9b88-274d6b39d560.jpg");
                        //    article.setUrl("https://blog.csdn.net/atmknight");
                        //    articleList.add(article);
                        //    //多图文的话,新建多个article 放入articleList 中即可实现
                        //
                        //    // 设置图文消息个数
                        //    newsMessage.setArticleCount(articleList.size());
                        //    // 设置图文消息包含的图文集合
                        //    newsMessage.setArticles(articleList);
                        //    // 将图文消息对象转换成xml字符串
                        //    message = MessageUtil.newsMessageToXml(newsMessage);
                        //}
                        MessageEntity entity = new MessageEntity();
                        MessageEntity.TextMessage text = entity.new TextMessage();
                        text.setToUserName(fromUserName);
                        text.setFromUserName(toUserName);
                        text.setCreateTime(new Date().getTime());
                        text.setMsgType(EventConstant.RESP_MESSAGE_TYPE_TEXT);
                        text.setFuncFlag(0);
                        text.setContent("感谢关注酒网宝！\n" + "酒网宝帮助酒店住客轻松便捷地获取酒店提供的全方位服务，让您的住店之旅更加舒心。");
                        message = MessageUtil.textMessageToXml(text);
                        logger.info(message);
                    } else if (EventConstant.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)) {//处理取消订阅事件
                        UserVo user = new UserVo();
                        user.setOpenidGzh(fromUserName);
                        user.setSubscribe(0);
                        user.setUpdateTime(new Date());
                        userService.updateByOpenIdGzh(user);
                    } else if (EventConstant.EVENT_TYPE_SCAN.equals(eventType)) {//已经关注，然后再次扫描事件
                    } else if (EventConstant.EVENT_TYPE_CLICK.equals(eventType)) {//自定义菜单点击事件
                    }
                } else {
                    //微信消息分为事件推送消息和普通消息,非事件即为普通消息类型
                    String content = "亲，很抱歉无法查询到您需要的信息，请确认输入的查询条件是否有误，谢谢";
                    switch (msgType) {
                        case "text": {//文本消息
                            content = map.get("Content");//用户发的消息内容
                            content = "您发的消息内容是:" + content;
                            break;
                        }
                        case "image": {//图片消息
                            content = "您发的消息内容是图片";
                            break;
                        }
                    }
                    MessageEntity entity = new MessageEntity();
                    MessageEntity.TextMessage text = entity.new TextMessage();
                    text.setContent(content);
                    message = MessageUtil.textMessageToXml(text);
                }
            } catch (Exception e) {
                logger.error("消息处理异常:" + e.getMessage());
            }
        } else {
            logger.info("checkSignature:" + "失败");
        }
        return message;
    }
}
