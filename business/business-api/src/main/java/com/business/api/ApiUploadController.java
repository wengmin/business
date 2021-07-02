package com.business.api;

import com.business.annotation.IgnoreAuth;
import com.business.annotation.LoginUser;
import com.business.entity.DocumentsPageVo;
import com.business.entity.DocumentsVo;
import com.business.entity.UserVo;
import com.business.oss.OSSFactory;
import com.business.service.ApiDocumentsService;
import com.business.util.ApiBaseAction;
import com.business.util.FileToImageUtil;
import com.business.utils.ResourceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Api(tags = "上传")
@RestController
@RequestMapping("/api/upload")
public class ApiUploadController extends ApiBaseAction {

    @Autowired
    private ApiDocumentsService documentsService;

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
     * 上传文档
     */
    @ApiOperation(value = "上传文档")
    @PostMapping("documents")
    public Object documents(@RequestParam("file") MultipartFile file, Integer folderId, String docName, String profiles, Integer isOpen, String password, @LoginUser UserVo loginUser) {
        if (file.isEmpty()) {
            return toResponsFail("文件不能为空");
        }
        try {
            long len = file.getSize();//上传文件的大小, 单位为字节.
            //准备接收换算后文件大小的容器
            double fileSize = (double) len / 1048576;
            //if ("B".equals(unit.toUpperCase())) {
            //    fileSize = (double) len;
            //} else if ("K".equals(unit.toUpperCase())) {
            //    fileSize = (double) len / 1024;
            //} else if ("M".equals(unit.toUpperCase())) {
            //    fileSize = (double) len / 1048576;
            //} else if ("G".equals(unit.toUpperCase())) {
            //    fileSize = (double) len / 1073741824;
            //}
            //如果上传文件大于限定的容量
            if (fileSize > 5) {
                logger.info("file=>docName:" + docName + ";fileSize:" + fileSize);
                return toResponsFail("文件不能超过5M");
            }
            String fileName = file.getOriginalFilename();// 获取文件名
            String suffix = fileName.substring(fileName.lastIndexOf(".")); // 获取文件的后缀名
            String dic = "";
            if (suffix.equals(".doc") || suffix.equals(".docx")) {
                dic = "upload/file/word";
            } else if (suffix.equals(".xls") || suffix.equals(".xlsx")) {
                dic = "upload/file/excel";
            } else if (suffix.equals(".ppt") || suffix.equals(".pptx")) {
                dic = "upload/file/ppt";
            } else if (suffix.equals(".pdf")) {
                dic = "upload/file/pdf";
            } else {
                return toResponsFail("文件类型错误，只能选择word、excel、ppt、pdf");
            }

            String path = request.getSession().getServletContext().getRealPath(dic);
            fileName = new Date().getTime() + "_" + new Random().nextInt(1000);//新的文件名

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dicName = sdf.format(new Date());
            //获取文件夹路径
            String dicFullName = path + File.separator + dicName;
            File folder = new File(dicFullName);
            if (!folder.exists() && !folder.isDirectory()) {//如果文件夹不存在则创建
                folder.mkdirs();
            }
            File targetFile = new File(folder, fileName + suffix);//将文件存入文件夹
            file.transferTo(targetFile);//将上传的文件写到服务器上指定的文件。

            String inPath = dicFullName + File.separator + fileName + suffix;

            List<String> pageList = FileToImageUtil.turnMain(suffix, fileName, inPath, dicFullName);
            String url = ResourceUtil.getConfigByName("Api.RootUrl") + "/" + dic + "/" + dicName;
            if (pageList.size() > 0) {
                DocumentsVo documentsVo = new DocumentsVo();
                documentsVo.setFolderId(folderId);
                documentsVo.setName(docName);
                documentsVo.setProfiles(profiles);
                documentsVo.setCover(url + "/" + pageList.get(0));
                documentsVo.setIsOpen(isOpen);
                documentsVo.setPassword(password);
                documentsVo.setUserId(loginUser.getUserId().intValue());
                documentsService.save(documentsVo);
                for (int i = 0; i < pageList.size(); i++) {
                    DocumentsPageVo page = new DocumentsPageVo();
                    page.setDocumentsId(documentsVo.getId());
                    page.setUrl(url + "/" + pageList.get(i));
                    page.setSortValue(i);
                    documentsService.savePage(page);
                }
            }
            return toResponsSuccess("");
        } catch (Exception e) {
            logger.error("file=>上传文件:" + e.getMessage());
            e.printStackTrace();
        }
        return toResponsFail("上传文件错误");
    }
}