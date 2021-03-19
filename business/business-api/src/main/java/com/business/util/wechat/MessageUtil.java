package com.business.util.wechat;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 微信事件消息处理
 *
 * @author wm
 * @date 2020年6月15日 上午9:26:27
 */
public class MessageUtil {

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 读取输入流
        SAXReader reader = new SAXReader();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点

        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }

    /**
     * 文本消息对象转换成xml
     *
     * @param textMessage 文本消息对象
     * @return xml
     */
    public static String textMessageToXml(MessageEntity.TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * 音乐消息对象转换成xml
     *
     * @param musicMessage 音乐消息对象
     * @return xml
     */
    public static String musicMessageToXml(MessageEntity.MusicMessage musicMessage) {
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    /**
     * 图文消息对象转换成xml
     *
     * @param newsMessage 图文消息对象
     * @return xml
     */
    public static String newsMessageToXml(MessageEntity.NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        MessageEntity e = new MessageEntity();
        xstream.alias("item", (e.new Article()).getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     *
     * 定义xstream让value自动加上CDATA标签
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                boolean cdata = false;
                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {

                    if (!name.equals("xml")) {
                        char[] arr = name.toCharArray();
                        if (arr[0] >= 'a' && arr[0] <= 'z') {
                            // arr[0] -= 'a' - 'A';
                            arr[0] = (char) ((int) arr[0] - 32);
                        }
                        name = new String(arr);
                    }

                    super.startNode(name, clazz);

                }

                @Override
                public void setValue(String text) {

                    if (text != null && !"".equals(text)) {
                        Pattern patternInt = Pattern
                                .compile("[0-9]*(\\.?)[0-9]*");
                        Pattern patternFloat = Pattern.compile("[0-9]+");
                        if (patternInt.matcher(text).matches()
                                || patternFloat.matcher(text).matches()) {
                            cdata = false;
                        } else {
                            cdata = true;
                        }
                    }
                    super.setValue(text);
                }

                protected void writeText(QuickWriter writer, String text) {

                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }

    });
}
