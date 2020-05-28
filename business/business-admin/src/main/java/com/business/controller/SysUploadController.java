package com.business.controller;

import com.business.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/10
 * @time 创建时间: 11:31
 */
@RestController
@RequestMapping("sys/upload")
public class SysUploadController {

    @RequestMapping("/cover")
    public R uploadCover(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        //SysUploadEntity result = new SysUploadEntity();
        Map<String, Object> map = new HashMap<String, Object>();
        File targetFile = null;
        String url = "";//返回存储路径
        int code = 1;
        System.out.println(file);
        String fileName = file.getOriginalFilename();//获取文件名加后缀
        if (fileName != null && fileName != "") {
            //String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/upload/cover/";//存储路径
            String returnUrl = "https://emiaoweb.com/business/upload/cover/";//存储路径
            String path = request.getSession().getServletContext().getRealPath("upload/cover"); //文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileF;//新的文件名

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 = new File(path + "/" + fileAdd);
            //如果文件夹不存在则创建
            if (!file1.exists() && !file1.isDirectory()) {
                file1.mkdirs();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
                url = returnUrl + fileAdd + "/" + fileName;
                //result.setCode(code);
                //result.setMessage("图片上传成功");
                //map.put("url", url);
                //result.setResult(map);
            } catch (Exception e) {
                e.printStackTrace();
                //result.setMessage("系统异常，图片上传失败");
            }
        }
        R r = new R();
        r.put("url", url);
        r.put("link", url);
        return r;
    }
}
