package com.business.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.business.annotation.LoginUser;
import com.business.entity.CardInfoVo;
import com.business.entity.CompanyFileVo;
import com.business.entity.CompanyVo;
import com.business.entity.UserVo;
import com.business.service.ApiCardInfoService;
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
    private ApiCardInfoService cardUserService;

    @ApiOperation(value = "获取用户企业信息")
    @GetMapping("detail")
    public Object detail(@LoginUser UserVo loginUser) {
        CardInfoVo entity = cardUserService.queryObject(0);
        if (entity == null) {
            return toResponsFail("未注册名片");
        }
        CompanyVo company = companyService.queryObject(entity.getCompanyId());
        company.setFileList(companyService.queryFileList(entity.getCompanyId()));
        return toResponsSuccess(company);
    }

    @ApiOperation(value = "获取用户企业信息")
    @GetMapping("detailById")
    public Object detailById(Integer companyId) {
        if (companyId == null || companyId == 0) {
            return toResponsFail("参数错误");
        }
        CompanyVo company = companyService.queryObject(companyId);
        company.setFileList(companyService.queryFileList(companyId));
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
            CardInfoVo entity = cardUserService.queryObject(0);
            if (entity == null) {
                return toResponsFail("请先注册名片");
            }
            String logo = parameterJson.getString("logo");
            String name = parameterJson.getString("name");
            String simpleName = parameterJson.getString("simpleName");
            String introduction = parameterJson.getString("introduction");
            String fileurl = parameterJson.getString("fileList");
            CompanyVo company = companyService.queryObject(entity.getCompanyId());
            int companyId = 0;
            int result = 0;
            if (company != null) {
                companyId = company.getCompanyId();
                company.setLogo(logo != null ? logo.trim() : "");
                company.setName(name != null ? name.trim() : "");
                company.setSimpleName(simpleName != null ? simpleName.trim() : "");
                company.setIntroduction(introduction != null ? introduction.trim() : "");
                result = companyService.update(company);
            } else {
                company = new CompanyVo();
                company.setLogo(logo != null ? logo.trim() : "");
                company.setName(name != null ? name.trim() : "");
                company.setSimpleName(simpleName != null ? simpleName.trim() : "");
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
}
