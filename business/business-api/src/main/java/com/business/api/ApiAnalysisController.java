package com.business.api;

import com.business.annotation.LoginUser;
import com.business.entity.AnalysisShareVo;
import com.business.entity.UserVo;
import com.business.service.ApiAnalysisShareService;
import com.business.util.ApiBaseAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/26
 * @time 创建时间: 16:13
 */
@Api(tags = "分析管理")
@RestController
@RequestMapping("/api/analysis")
public class ApiAnalysisController extends ApiBaseAction {
    @Autowired
    private ApiAnalysisShareService shareService;

    @ApiOperation(value = "记录用户分享")
    @PostMapping("saveShare")
    public Object saveShare(@LoginUser UserVo loginUser) {
        AnalysisShareVo vo = new AnalysisShareVo();
        vo.setUserId(loginUser.getUserId());
        shareService.save(vo);
        return toResponsSuccess("记录用户分享成功");
    }
}
