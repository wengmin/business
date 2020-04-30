package com.business.util;

import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class QRCodeUtils {

    // 判断文件夹是否存在
    public static void dirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdirs();
        }

    }

    // 判断文件夹是否存在
    public static Boolean fileExists(File file) {
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * 获取小程序商品分享二维码
     *
     */
    public static String createQrCodeToBytes(String accessToken, Long staffId, Integer id, String page) {
        try {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", "id=" + id + "&staffId=" + staffId);
            paramJson.put("page", page);
            paramJson.put("width", 280);//最小280
            paramJson.put("is_hyaline", false);
            paramJson.put("auto_color", true);
            /**
             * line_color生效 paramJson.put("auto_color", false); JSONObject lineColor = new
             * JSONObject(); lineColor.put("r", 0); lineColor.put("g", 0);
             * lineColor.put("b", 0); paramJson.put("line_color", lineColor);
             */

            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();

            byte[] bytes = input2byte(httpURLConnection.getInputStream());
            BASE64Encoder base64Encoder = new BASE64Encoder();
            return base64Encoder.encode(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }


    /*
     * 获取小程序商品分享二维码
     *
     */
    public static String createQrCodeToUrl(String accessToken, String param, String path, String imageName) throws Exception {
        try {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", param);
            paramJson.put("page", path);
            paramJson.put("width", 430);//最小280
            paramJson.put("is_hyaline", false);
            paramJson.put("auto_color", true);

            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            // 开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());

            String urlPath = System.getProperty("catalina.home") + "/webapps/business/upload/qrcode";
            String imgname = "/" + imageName + ".png";
            String codeUrl = urlPath + imgname;
            File file = new File(urlPath);
            dirExists(file);

            OutputStream os = new FileOutputStream(new File(codeUrl));
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
            bis.close();
            return "qrcode" + imgname;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}