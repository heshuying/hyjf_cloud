/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.locked;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.am.bean.admin.LockedConfig;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.locked.LockedConfigResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cui
 * @version LockedConfigController, v0.1 2018/9/27 11:40
 */
@Api(tags = "配置中心-登录失败锁定配置")
@RestController
@RequestMapping("/hyjf-admin/lockedconfig/")
public class LockedConfigController extends BaseController {

	@Autowired
	private AmAdminClient amAdminClient;

	@ApiOperation(value = "前台锁定配置", notes = "前台锁定配置")
	@GetMapping(value = "/frontconfig")
	@ResponseBody
	public LockedConfigResponse getWebConfig() {

		LockedConfigResponse response = new LockedConfigResponse();

		response.setData(amAdminClient.getFrontLockedCfg());

		return response;

	}

	@ApiOperation(value = "后台锁定配置", notes = "后台锁定配置")
	@GetMapping(value = "/adminconfig")
	@ResponseBody
	public LockedConfigResponse getAdminConfig() {

		LockedConfigResponse response = new LockedConfigResponse();

		response.setData(amAdminClient.getAdminLockedCfg());

		return response;

	}

	@ApiOperation(value = "保存前台配置", notes = "保存前台配置")
	@PostMapping(value = "/frontconfig")
	@ResponseBody
	public BaseResult<Boolean> saveFrontConfig(@RequestBody LockedConfig.Config webConfig) {

		BooleanResponse response= amAdminClient.saveFrontConfig(webConfig);

		return response.getResultBoolean()?new BaseResult<>(true):new BaseResult<>(BaseResult.ERROR,response.getMessage());

	}

	@ApiOperation(value = "保存后台配置", notes = "保存后台配置")
	@PostMapping(value = "/adminconfig")
	@ResponseBody
	public BaseResult<Boolean> saveAdminConfig(@RequestBody LockedConfig.Config adminConfig) {

		BooleanResponse response= amAdminClient.saveAdminConfig(adminConfig);

		return response.getResultBoolean()?new BaseResult<>(true):new BaseResult<>(BaseResult.ERROR,response.getMessage());

	}
}
