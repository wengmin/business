package com.business.api;

import com.business.annotation.IgnoreAuth;
import com.business.entity.SysMacroEntity;
import com.business.service.SysMacroService;
import com.business.util.ApiBaseAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/5/9
 * @time 创建时间: 13:41
 */
@Api(tags = "反馈")
@RestController
@RequestMapping("/api/macro")
public class ApiMacroController  extends ApiBaseAction {
    @Autowired
    private SysMacroService sysMacroService;

    /**
     * 根据value查询数据字典
     *
     * @param value value
     * @return R
     */
    @ApiOperation(value = "根据value查询数据字典")
    @IgnoreAuth
    @GetMapping("queryMacrosByValue")
    public Object queryMacrosByValue(String value) {

        List<SysMacroEntity> list = sysMacroService.queryMacrosByValue(value);

        return toResponsSuccess(list);
    }
}
