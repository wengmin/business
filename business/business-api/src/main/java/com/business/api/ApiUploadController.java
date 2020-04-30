package com.business.api;

import com.business.annotation.IgnoreAuth;
import com.business.entity.SysOssEntity;
import com.business.oss.OSSFactory;
import com.business.service.SysOssService;
import com.business.util.ApiBaseAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Api(tags = "上传")
@RestController
@RequestMapping("/api/upload")
public class ApiUploadController extends ApiBaseAction {

    @Autowired
    private SysOssService sysOssService;

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
            //保存文件信息
            SysOssEntity ossEntity = new SysOssEntity();
            ossEntity.setUrl(url);
            ossEntity.setCreateDate(new Date());
            sysOssService.save(ossEntity);

            return toResponsSuccess(url);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return toResponsFail("上传文件不能为空");
    }

}