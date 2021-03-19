package com.business.api;

import com.alibaba.fastjson.JSONObject;
import com.business.annotation.IgnoreAuth;
import com.business.annotation.LoginUser;
import com.business.entity.DocumentsFolderVo;
import com.business.entity.DocumentsPageVo;
import com.business.entity.DocumentsVo;
import com.business.entity.UserVo;
import com.business.service.ApiDocumentsService;
import com.business.util.ApiBaseAction;
import com.business.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2021-03-04 10:53:30
 */
@Api(tags = "文档管理")
@RestController
@RequestMapping("/api/documents")
public class ApiDocumentsController extends ApiBaseAction {
    @Autowired
    private ApiDocumentsService documentsService;

    /**
     * 获取文档列表
     */
    @ApiOperation(value = "获取文档列表", response = Map.class)
    @IgnoreAuth
    @GetMapping("listByUser")
    public Object listByUser(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, Integer userId) {
        Map params = new HashMap();
        params.put("offset", (page - 1) * size);
        params.put("limit", size);
        params.put("userId", userId);
        List<DocumentsVo> list = documentsService.queryList(params);
        int total = documentsService.queryTotal(params);
        PageUtils pageUtil = new PageUtils(list, total, size, page);
        return toResponsSuccess(pageUtil);
    }

    /**
     * 获取文档列表
     */
    @ApiOperation(value = "获取文档列表", response = Map.class)
    @GetMapping("list")
    public Object list(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, @LoginUser UserVo loginUser) {
        Map params = new HashMap();
        params.put("offset", (page - 1) * size);
        params.put("limit", size);
        params.put("userId", loginUser.getUserId());
        List<DocumentsVo> list = documentsService.queryList(params);
        int total = documentsService.queryTotal(params);
        PageUtils pageUtil = new PageUtils(list, total, size, page);
        return toResponsSuccess(pageUtil);
    }

    /**
     * 获取文档详情
     */
    @ApiOperation(value = "获取文档详情")
    @IgnoreAuth
    @GetMapping("detail")
    public Object detail(Integer documentsId) {
        DocumentsVo documents = documentsService.queryObject(documentsId);
        if (documents == null) {
            return toResponsFail("获取文档为空");
        }
        List<DocumentsPageVo> page = documentsService.queryPageList(documentsId);
        documents.setPageList(page);
        return toResponsSuccess(documents);
    }

    /**
     * 获取文件夹列表
     */
    @ApiOperation(value = "获取文件夹列表", response = Map.class)
    @GetMapping("folderDocList")
    public Object folderDocList(@LoginUser UserVo loginUser) {
        Map params = new HashMap();
        params.put("userId", loginUser.getUserId());
        List<DocumentsFolderVo> list = documentsService.queryFolderDocList(params);
        return toResponsSuccess(list);
    }

    /**
     * 获取文件夹列表
     */
    @ApiOperation(value = "获取文件夹列表", response = Map.class)
    @GetMapping("folderList")
    public Object folderList(@LoginUser UserVo loginUser) {
        Map params = new HashMap();
        params.put("userId", loginUser.getUserId());
        List<DocumentsFolderVo> list = documentsService.queryFolderList(params);
        return toResponsSuccess(list);
    }

    /**
     * 新建文件夹
     */
    @ApiOperation(value = "新建文件夹")
    @PostMapping("folderSave")
    public Object folderSave(@LoginUser UserVo loginUser) {
        JSONObject json = super.getJsonRequest();
        if (null != json) {
            DocumentsFolderVo vo = new DocumentsFolderVo();
            vo.setUserId(loginUser.getUserId().intValue());
            vo.setName(json.getString("name"));
            vo.setIsOpen(json.getInteger("isOpen"));
            vo.setPassword(json.getString("password"));
            vo.setSortValue(json.getInteger("sortValue"));
            documentsService.saveFolder(vo);
            return super.toResponsSuccess(vo.getFolderId());
        }
        return super.toResponsFail("新建文件夹失败");
    }

    /**
     * 删除文件夹
     */
    @ApiOperation(value = "删除文件夹")
    @PostMapping("folderDelete")
    public Object folderDelete(@LoginUser UserVo loginUser) {
        JSONObject json = super.getJsonRequest();
        if (null != json) {
            Integer folderId = json.getInteger("folderId");
            documentsService.deleteFolder(loginUser.getUserId(), folderId);
            return super.toResponsSuccess("删除文件夹成功");
        }
        return super.toResponsFail("删除文件夹失败");
    }
}
