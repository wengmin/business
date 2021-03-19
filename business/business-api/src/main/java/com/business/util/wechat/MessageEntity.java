package com.business.util.wechat;

import java.util.List;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/6/23
 * @time 创建时间: 18:12
 */
public class MessageEntity {
    /**
     * 消息基类（公众帐号 -> 用户）
     * @author cdj
     * @date 2018年8月3日 上午8:43:54
     */
    public class BaseMessage {

        /**
         * 接收方帐号（收到的OpenID）
         */
        private String ToUserName;
        /**
         * 开发者微信号
         */
        private String FromUserName;
        /**
         * 消息创建时间 （整型）
         */
        private long CreateTime;

        /**
         * 消息类型
         */
        private String MsgType;

        /**
         * 位0x0001被标志时，星标刚收到的消息
         */
        private int FuncFlag;

        public String getToUserName() {
            return ToUserName;
        }

        public void setToUserName(String toUserName) {
            ToUserName = toUserName;
        }

        public String getFromUserName() {
            return FromUserName;
        }

        public void setFromUserName(String fromUserName) {
            FromUserName = fromUserName;
        }

        public long getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(long createTime) {
            CreateTime = createTime;
        }

        public String getMsgType() {
            return MsgType;
        }

        public void setMsgType(String msgType) {
            MsgType = msgType;
        }

        public int getFuncFlag() {
            return FuncFlag;
        }

        public void setFuncFlag(int funcFlag) {
            FuncFlag = funcFlag;
        }
    }
    /**
     * 文本消息
     * @author 咳嗽停不了
     * @date 2018年8月3日 上午8:44:51
     */
    public class TextMessage extends BaseMessage {
        /**
         * 回复的消息内容
         */
        private String Content;

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }
    }

    /**
     * 多图文消息，
     * 单图文的时候 Articles 只放一个就行了
     * @author cdj
     * @date 2018年8月3日 上午8:45:22
     */
    public class NewsMessage extends BaseMessage {
        /**
         * 图文消息个数，限制为10条以内
         */
        private int ArticleCount;
        /**
         * 多条图文消息信息，默认第一个item为大图
         */
        private List<Article> Articles;

        public int getArticleCount() {
            return ArticleCount;
        }

        public void setArticleCount(int articleCount) {
            ArticleCount = articleCount;
        }

        public List<Article> getArticles() {
            return Articles;
        }

        public void setArticles(List<Article> articles) {
            Articles = articles;
        }
    }


    /**
     * 图文消息
     * @author cdj
     * @date 2018年8月3日 上午8:45:56
     */
    public class Article {
        /**
         * 图文消息名称
         */
        private String Title;

        /**
         * 图文消息描述
         */
        private String Description;

        /**
         * 图片链接，支持JPG、PNG格式，<br>
         * 较好的效果为大图640*320，小图80*80
         */
        private String PicUrl;

        /**
         * 点击图文消息跳转链接
         */
        private String Url;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getDescription() {
            return null == Description ? "" : Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getPicUrl() {
            return null == PicUrl ? "" : PicUrl;
        }

        public void setPicUrl(String picUrl) {
            PicUrl = picUrl;
        }

        public String getUrl() {
            return null == Url ? "" : Url;
        }

        public void setUrl(String url) {
            Url = url;
        }

    }

    /**
     * 音乐消息
     * @author cdj
     * @date 2018年8月3日 上午8:46:35
     */
    public class MusicMessage extends BaseMessage {
        /**
         * 音乐
         */
        private Music Music;

        public Music getMusic() {
            return Music;
        }

        public void setMusic(Music music) {
            Music = music;
        }
    }

    /**
     * 音乐消息
     * @author cdj
     * @date 2018年8月3日 上午8:46:59
     */
    public class Music {
        /**
         * 音乐名称
         */
        private String Title;

        /**
         * 音乐描述
         */
        private String Description;

        /**
         * 音乐链接
         */
        private String MusicUrl;

        /**
         * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
         */
        private String HQMusicUrl;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getMusicUrl() {
            return MusicUrl;
        }

        public void setMusicUrl(String musicUrl) {
            MusicUrl = musicUrl;
        }

        public String getHQMusicUrl() {
            return HQMusicUrl;
        }

        public void setHQMusicUrl(String musicUrl) {
            HQMusicUrl = musicUrl;
        }

    }
}
