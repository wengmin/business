package com.business.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.business.annotation.IgnoreAuth;
import com.business.annotation.LoginUser;
import com.business.entity.*;
import com.business.service.ApiCardUserService;
import com.business.service.ApiCompanyService;
import com.business.util.ApiBaseAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/15
 * @time 创建时间: 10:29
 */
@Api(tags = "企业管理")
@RestController
@RequestMapping("/api/company")
public class ApiCompanyController extends ApiBaseAction {
    @Autowired
    private ApiCompanyService companyService;
    @Autowired
    private ApiCardUserService cardUserService;

    @ApiOperation(value = "获取用户企业信息")
    @GetMapping("detail")
    public Object detail(@LoginUser UserVo loginUser) {
        CardUserVo entity = cardUserService.queryByUserId(loginUser.getUserId());
        if (entity == null) {
            return toResponsFail("未注册名片");
        }
        CompanyVo company = companyService.queryObject(entity.getCompanyId());
        company.setFileList(companyService.queryFileList(entity.getCompanyId()));
        return toResponsSuccess(company);
    }

    /**
     * 编辑企业信息
     */
    @ApiOperation(value = "编辑企业信息", response = Map.class)
    @PostMapping("save")
    public Object save(@LoginUser UserVo loginUser) {
        try {
            JSONObject parameterJson = this.getJsonRequest();
            CardUserVo entity = cardUserService.queryByUserId(loginUser.getUserId());
            if (entity == null) {
                return toResponsFail("请先注册名片");
            }
            String logo = parameterJson.getString("logo");
            String name = parameterJson.getString("name");
            String introduction = parameterJson.getString("introduction");
            String fileurl = parameterJson.getString("fileList");
            CompanyVo company = companyService.queryObject(entity.getCompanyId());
            int companyId = 0;
            int result = 0;
            if (company != null) {
                companyId = company.getCompanyId();
                company.setLogo(logo != null ? logo.trim() : "");
                company.setName(name != null ? name.trim() : "");
                company.setIntroduction(introduction != null ? introduction.trim() : "");
                result = companyService.update(company);
            } else {
                company = new CompanyVo();
                company.setLogo(logo != null ? logo.trim() : "");
                company.setName(name != null ? name.trim() : "");
                company.setIntroduction(introduction != null ? introduction.trim() : "");
                result = companyService.save(company);
                companyId = result;
            }
            if (result > 0) {
                List<CompanyFileVo> fileList = (List<CompanyFileVo>) JSONArray.parseArray(fileurl, CompanyFileVo.class);
                for (CompanyFileVo f : fileList) {
                    f.setCompanyId(companyId);
                }
                companyService.deleteFileByCompany(company.getCompanyId());
                companyService.saveFile(fileList);
                return toResponsSuccess("编辑成功");
            }
        } catch (Exception e) {
            logger.error("companySave.", e);
            return toResponsFail("程序出错");
        }
        return toResponsFail("参数错误");
    }

    @ApiOperation(value = "删除附件")
    @PostMapping("deleteFile")
    public Object deleteFile(@RequestParam("id") Integer id) {
        if (id == 0 || id == null) {
            return toResponsFail("参数错误");
        }
        companyService.deleteFile(id);
        return toResponsSuccess("删除成功");
    }


    @IgnoreAuth
    @ApiOperation(value = "获取岗位")
    @GetMapping("postDetail")
    public Object postDetail(Integer postId) {
        CompanyPostVo entity = companyService.queryPost(postId);
        return toResponsSuccess(entity);
    }

    @IgnoreAuth
    @ApiOperation(value = "获取企业下的岗位")
    @GetMapping("postList")
    public Object postList(@RequestParam("companyId") Integer companyId, @RequestParam(value = "postId", defaultValue = "0") Integer postId) {
        List<CompanyPostVo> entity = companyService.queryPostList(companyId, postId);
        return toResponsSuccess(entity);
    }


    @IgnoreAuth
    @ApiOperation(value = "获取房间信息")
    @GetMapping("roomDetail")
    public Object roomDetail(Integer roomId) {
        CompanyRoomVo entity = companyService.queryRoom(roomId);
        return toResponsSuccess(entity);
    }


    @IgnoreAuth
    @ApiOperation(value = "获取企业下的服务")
    @GetMapping("serviceList")
    public Object serviceList(Integer companyId, String serviceClass) {
        List<CompanyServiceVo> entity = companyService.queryServiceList(companyId, serviceClass);
        return toResponsSuccess(entity);
    }


    @IgnoreAuth
    @ApiOperation(value = "获取企业下的服务")
    @GetMapping("serviceGroup")
    public Object serviceGroup(Integer companyId) {
        List<CompanyServiceVo> entity = companyService.queryServiceGroup(companyId);
        return toResponsSuccess(entity);
    }
}
