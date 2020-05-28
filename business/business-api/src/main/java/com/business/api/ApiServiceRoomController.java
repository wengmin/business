package com.business.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.business.annotation.LoginUser;
import com.business.entity.*;
import com.business.service.ApiCompanyService;
import com.business.service.ApiServiceRoomService;
import com.business.util.ApiBaseAction;
import com.business.utils.PageUtils;
import com.business.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-05-21 17:42:29
 */
@Api(tags = "企业管理")
@RestController
@RequestMapping("/api/serviceroom")
public class ApiServiceRoomController extends ApiBaseAction {
    @Autowired
    private ApiCompanyService companyService;
    @Autowired
    private ApiServiceRoomService serviceRoomService;

    /**
     * 保存
     */
    @ApiOperation(value = "保存服务", response = Map.class)
    @PostMapping("save")
    public Object save(@LoginUser UserVo loginUser) {
        try {
            JSONObject parameterJson = this.getJsonRequest();
            //int companyId = StringUtils.parseInt(parameterJson.getString("companyId"));
            int roomId = StringUtils.parseInt(parameterJson.getString("roomId"));
            if (roomId == 0) {
                return toResponsFail("参数错误");
            }
            String serviceClass = parameterJson.getString("serviceClass");
            String tagList = parameterJson.getString("tagList");
            String remark = parameterJson.getString("remark");
            String appointmentTime = parameterJson.getString("appointmentTime");
            CompanyRoomVo company = companyService.queryRoom(roomId);
            if (company == null || company.getCompanyId() == 0) {
                return toResponsFail("参数错误");
            }
            int companyId = company.getCompanyId();
            ServiceRoomVo vo = new ServiceRoomVo();
            vo.setCompanyId(companyId);
            vo.setRoomId(roomId);
            vo.setServiceClass(serviceClass);
            if (!StringUtils.isNullOrEmpty(tagList)) {
                List<String> l = (List<String>) JSONArray.parseArray(tagList, String.class);
                vo.setServiceTag(String.join("、", l));
            }
            vo.setRemark(remark);
            if (!StringUtils.isNullOrEmpty(appointmentTime)) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                vo.setAppointmentTime(formatter.parse(appointmentTime));
            }
            vo.setStatus(0);
            vo.setUserId(loginUser.getUserId());
            int res = serviceRoomService.save(vo);
            if (res == 0) {
                return toResponsSuccess("添加服务失败");
            }
            return toResponsSuccess("添加服务成功");
        } catch (Exception e) {
            logger.error("companySave.", e);
            return toResponsFail("程序出错");
        }
    }

    /**
     * 获取客户服务列表
     */
    @ApiOperation(value = "获取客户服务列表", response = Map.class)
    @GetMapping("list")
    public Object list(@LoginUser UserVo loginUser, Integer companyId, String serviceClass, Integer status, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
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
        params.put("serviceClass", serviceClass);
        params.put("status", status);
        params.put("userId", loginUser.getUserId());
        List<ServiceRoomVo> list = serviceRoomService.queryList(params);
        int total = serviceRoomService.queryTotal(params);
        PageUtils pageUtil = new PageUtils(list, total, size, page);
        return toResponsSuccess(pageUtil);
    }

    @ApiOperation(value = "获取客户服务信息")
    @GetMapping("detail")
    public Object detail(@LoginUser UserVo loginUser, Integer companyId, Integer serviceId) {
        if (StringUtils.parseInt(companyId) == 0 || StringUtils.parseInt(serviceId) == 0) {
            return toResponsFail("参数错误");
        }
        CompanyStaffVo staff = companyService.queryStaffByUserIdCompanyId(companyId, loginUser.getUserId());
        if (staff == null) {
            return toResponsFail("您未绑定该企业");
        }
        ServiceRoomVo entity = serviceRoomService.queryObject(serviceId);
        return toResponsSuccess(entity);
    }

    @ApiOperation(value = "处理服务")
    @PostMapping("update")
    public Object update(@LoginUser UserVo loginUser, @RequestParam("companyId") Integer companyId, @RequestParam("serviceId") Integer serviceId, @RequestParam("status") Integer status, @RequestParam("remark") String remark) {
        try {
            if (StringUtils.parseInt(companyId) == 0 || StringUtils.parseInt(serviceId) == 0 || StringUtils.parseInt(status) == 0) {
                return toResponsFail("参数错误");
            }
            CompanyStaffVo staff = companyService.queryStaffByUserIdCompanyId(companyId, loginUser.getUserId());
            if (staff == null) {
                return toResponsFail("您未绑定该企业");
            }
            ServiceRoomVo vo = new ServiceRoomVo();
            vo.setServiceId(serviceId);
            vo.setStatus(status);
            serviceRoomService.update(vo, staff.getStaffId(), remark);
            return toResponsSuccess("处理成功");
        } catch (Exception e) {
            logger.error("serviceroomUpdate.", e);
            return toResponsFail("程序出错");
        }
    }

    /**
     * 获取客户服务列表
     */
    @ApiOperation(value = "获取客户服务记录列表")
    @GetMapping("logList")
    public Object logList(@LoginUser UserVo loginUser, @RequestParam("companyId") Integer companyId, Integer serviceId) {
        if (StringUtils.parseInt(companyId) == 0 || StringUtils.parseInt(serviceId) == 0) {
            return toResponsFail("参数错误");
        }
        CompanyStaffVo staff = companyService.queryStaffByUserIdCompanyId(companyId, loginUser.getUserId());
        if (staff == null) {
            return toResponsFail("您未绑定该企业");
        }
        List<ServiceRoomLogVo> list = serviceRoomService.queryLogList(serviceId);
        return toResponsSuccess(list);
    }
}
