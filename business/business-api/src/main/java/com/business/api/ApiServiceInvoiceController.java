package com.business.api;

import com.alibaba.fastjson.JSONObject;
import com.business.annotation.LoginUser;
import com.business.entity.CompanyRoomVo;
import com.business.entity.CompanyStaffVo;
import com.business.entity.ServiceInvoiceVo;
import com.business.entity.UserVo;
import com.business.service.ApiCompanyService;
import com.business.service.ApiServiceInvoiceService;
import com.business.util.ApiBaseAction;
import com.business.utils.PageUtils;
import com.business.utils.StringUtils;
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
 * @date 2020-05-28 18:16:20
 */
@Api(tags = "开票服务")
@RestController
@RequestMapping("/api/serviceinvoice")
public class ApiServiceInvoiceController extends ApiBaseAction {
    @Autowired
    private ApiCompanyService companyService;
    @Autowired
    private ApiServiceInvoiceService serviceInvoiceService;

    /**
     * 保存
     */
    @ApiOperation(value = "保存服务", response = Map.class)
    @PostMapping("save")
    public Object save(@LoginUser UserVo loginUser) {
        try {
            JSONObject parameterJson = this.getJsonRequest();
            int roomId = StringUtils.parseInt(parameterJson.getString("roomId"));
            if (roomId == 0) {
                return toResponsFail("参数错误");
            }
            CompanyRoomVo company = companyService.queryRoom(roomId);
            if (company == null || StringUtils.parseInt(company.getCompanyId()) == 0) {
                return toResponsFail("参数错误.");
            }

            int titletype = StringUtils.parseInt(parameterJson.getString("titletype"));
            String content = parameterJson.getString("content");
            String titlename = parameterJson.getString("titlename");
            String taxno = parameterJson.getString("taxno");
            String registaddress = parameterJson.getString("registaddress");
            String registphone = parameterJson.getString("registphone");
            String bank = parameterJson.getString("bank");
            String bankaccount = parameterJson.getString("bankaccount");
            String remark = parameterJson.getString("remark");

            ServiceInvoiceVo vo = new ServiceInvoiceVo();
            vo.setCompanyId(company.getCompanyId());
            vo.setRoomId(roomId);
            vo.setUserId(loginUser.getUserId());
            vo.setTitletype(titletype);
            vo.setContent(content);
            vo.setTitlename(titlename);
            vo.setTaxno(taxno);
            vo.setRegistaddress(registaddress);
            vo.setRegistphone(registphone);
            vo.setBank(bank);
            vo.setBankaccount(bankaccount);
            vo.setStatus(0);
            vo.setRemark(remark);

            int res = serviceInvoiceService.save(vo);
            if (res == 0) {
                return toResponsSuccess("添加失败");
            }
            return toResponsSuccess("添加成功");
        } catch (Exception e) {
            logger.error("serviceinvoiceSave.", e);
            return toResponsFail("程序出错");
        }
    }

    /**
     * 查看列表
     */
    @ApiOperation(value = "查看列表", response = Map.class)
    @GetMapping("list")
    public Object list(@LoginUser UserVo loginUser, Integer companyId, Integer status, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.parseInt(companyId) == 0) {
            return toResponsFail("参数错误");
        }
        CompanyStaffVo staff = companyService.queryStaffByUserIdCompanyId(companyId, loginUser.getUserId());
        if (staff == null) {
            return toResponsFail("您未绑定该企业");
        }
        Map params = new HashMap();
        params.put("offset", (page - 1) * size);
        params.put("limit", size);
        params.put("companyId", companyId);
        params.put("status", status);
        List<ServiceInvoiceVo> list = serviceInvoiceService.queryList(params);
        int total = serviceInvoiceService.queryTotal(params);
        PageUtils pageUtil = new PageUtils(list, total, size, page);
        return toResponsSuccess(pageUtil);
    }

    /**
     * 查看信息
     */
    @ApiOperation(value = "查看信息")
    @GetMapping("detail")
    public Object detail(@LoginUser UserVo loginUser, Integer companyId, Integer invoiceId) {
        if (StringUtils.parseInt(companyId) == 0 || StringUtils.parseInt(invoiceId) == 0) {
            return toResponsFail("参数错误");
        }
        CompanyStaffVo staff = companyService.queryStaffByUserIdCompanyId(companyId, loginUser.getUserId());
        if (staff == null) {
            return toResponsFail("您未绑定该企业");
        }
        ServiceInvoiceVo entity = serviceInvoiceService.queryObject(invoiceId);
        return toResponsSuccess(entity);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "处理服务")
    @PostMapping("update")
    public Object update(@LoginUser UserVo loginUser, @RequestParam("companyId") Integer companyId, @RequestParam("invoiceId") Integer invoiceId, @RequestParam("status") Integer status, @RequestParam("reply") String reply) {
        try {
            if (StringUtils.parseInt(companyId) == 0 || StringUtils.parseInt(invoiceId) == 0 || StringUtils.parseInt(status) == 0) {
                return toResponsFail("参数错误");
            }
            CompanyStaffVo staff = companyService.queryStaffByUserIdCompanyId(companyId, loginUser.getUserId());
            if (staff == null) {
                return toResponsFail("您未绑定该企业");
            }
            ServiceInvoiceVo vo = new ServiceInvoiceVo();
            vo.setInvoiceId(invoiceId);
            vo.setStaffId(staff.getStaffId());
            vo.setStatus(status);
            vo.setReply(reply);
            serviceInvoiceService.update(vo);
            return toResponsSuccess("处理成功");
        } catch (Exception e) {
            logger.error("serviceinvoiceUpdate.", e);
            return toResponsFail("程序出错");
        }
    }
}
