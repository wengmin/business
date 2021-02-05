package com.business.api;

import com.business.annotation.IgnoreAuth;
import com.business.oss.OSSFactory;
import com.business.util.ApiBaseAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Api(tags = "上传")
@RestController
@RequestMapping("/api/upload")
public class ApiUploadController extends ApiBaseAction {

    //@Autowired
    //private SysOssService sysOssService;

    /**
     * 上传图片
     */
    @ApiOperation(value = "上传图片")
    @IgnoreAuth
    @PostMapping("image")
    public Object image(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return toResponsFail("上传文件不能为空");
        }
        try {
            //上传文件
            String url = OSSFactory.build().upload(file);
            ////保存文件信息
            //SysOssEntity ossEntity = new SysOssEntity();
            //ossEntity.setUrl(url);
            //ossEntity.setCreateDate(new Date());
            //sysOssService.save(ossEntity);

            return toResponsSuccess(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toResponsFail("上传文件不能为空");
    }

    /**
     * 上传图片
     */
    @ApiOperation(value = "上传图片")
    @IgnoreAuth
    @PostMapping("file")
    public Object file(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return toResponsFail("上传文件不能为空");
        }
        try {
            String fileName = file.getOriginalFilename();// 获取文件名
            String suffix = fileName.substring(fileName.lastIndexOf(".")); // 获取文件的后缀名
            String dic = "";
            if (suffix.equals(".doc") || suffix.equals(".docx")) {
                dic = "upload/file/word";
            }
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + dic;//存储路径
            //String returnUrl = "https://emiaoweb.com/business/upload/cover/";//存储路径
            File filePath = new File("");
            String path = filePath.getCanonicalPath() + "/" + dic;
            fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + suffix;//新的文件名

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File folder = new File(path + "/" + fileAdd);
            if (!folder.exists() && !folder.isDirectory()) {//如果文件夹不存在则创建
                folder.mkdirs();
            }
            File targetFile = new File(folder, fileName);//将图片存入文件夹
            file.transferTo(targetFile);//将上传的文件写到服务器上指定的文件。

            //FileToImageUtil.docToImg(path + "/" + fileAdd + "/" + fileName, path + "/" + fileAdd+".pdf");

            return toResponsSuccess("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toResponsFail("上传文件不能为空");
    }
}