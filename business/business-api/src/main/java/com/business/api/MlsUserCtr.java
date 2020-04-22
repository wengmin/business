
package com.business.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.annotation.APPLoginUser;
import com.business.entity.MlsUserEntity2;
import com.business.entity.UserVo;
import com.business.service.ApiUserService;
import com.business.service.MlsUserSer;
import com.business.util.ApiBaseAction;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 分销用户<br>
 */
@Api(tags = "分销用户")
@Controller
@RequestMapping("/api/mlsuser")
public class MlsUserCtr extends ApiBaseAction {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MlsUserSer mlsUserSer;
	@Autowired
	private ApiUserService userService;

	@RequestMapping("/getCurrentUser")
	@ResponseBody
	@ApiOperation(value = "取当前登录用户信息", httpMethod = "POST", tags = "个人中心")
	public Object getCurrentUser(@APPLoginUser MlsUserEntity2 mlsuser) {
		mlsuser = mlsUserSer.getEntityMapper().getById(mlsuser.getMlsUserId());
		return toResponsSuccess(mlsuser);
	}
	
	
	@RequestMapping("/setFid")
	@ResponseBody
	@ApiOperation(value = "用户设置Fid", httpMethod = "POST", tags = "个人中心")
	public Object setFid(String openId, Long fid) {
		UserVo user = userService.queryByOpenId(openId);
		MlsUserEntity2 mlsuser = mlsUserSer.getEntityMapper().findByUserId(user.getUserId());
		if(mlsuser != null && mlsuser.getFid() == null) {
			mlsuser.setFid(fid);
		}
		return toResponsSuccess(mlsuser);
	}

}
