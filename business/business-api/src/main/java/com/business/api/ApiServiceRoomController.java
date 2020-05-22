package com.business.api;

import com.alibaba.fastjson.JSONObject;
import com.business.annotation.LoginUser;
import com.business.entity.ServiceRoomVo;
import com.business.entity.UserVo;
import com.business.service.ApiServiceRoomService;
import com.business.util.ApiBaseAction;
import com.business.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ApiServiceRoomService serviceRoomService;


    /**
     * 保存
     */
    @ApiOperation(value = "保存服务", response = Map.class)
    @PostMapping("save")
    public Object save(@LoginUser UserVo loginUser) {
        JSONObject parameterJson = this.getJsonRequest();
        String tagList = parameterJson.getString("tagList");
        ServiceRoomVo vo=new ServiceRoomVo();

        serviceRoomService.save(vo);

        return R.ok();
    }

}
