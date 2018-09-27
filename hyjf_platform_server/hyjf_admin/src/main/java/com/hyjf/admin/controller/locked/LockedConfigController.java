/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.locked;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.am.response.BooleanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.admin.controller.BaseController;
import com.hyjf.am.bean.admin.LockedConfig;
import com.hyjf.am.response.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cui
 * @version LockedConfigController, v0.1 2018/9/27 11:40
 */
@Api(tags = "配置中心-登录失败锁定配置")
@RestController
@RequestMapping("/hyjf-admin/lockedconfig/")
public class LockedConfigController extends BaseController {

	@Autowired
	private AmConfigClient amConfigClient;

	@ApiOperation(value = "前台锁定配置", notes = "前台锁定配置")
	@GetMapping(value = "/frontconfig")
	@ResponseBody
	public Response<LockedConfig.Config> getWebConfig() {

		Response<LockedConfig.Config> response = new Response<>();

		response.setResult(amConfigClient.getFrontLockedCfg());

		return response;

	}

	@ApiOperation(value = "后台锁定配置", notes = "后台锁定配置")
	@GetMapping(value = "/adminconfig")
	@ResponseBody
	public Response<LockedConfig.Config> getAdminConfig() {

		Response<LockedConfig.Config> response = new Response<>();

		response.setResult(amConfigClient.getAdminLockedCfg());

		return response;

	}

	@ApiOperation(value = "保存前台配置", notes = "保存前台配置")
	@PostMapping(value = "/frontconfig")
	@ResponseBody
	public BooleanResponse saveFrontConfig(@RequestBody LockedConfig.Config webConfig) {

		return amConfigClient.saveFrontConfig(webConfig);

	}

	@ApiOperation(value = "保存后台配置", notes = "保存后台配置")
	@PostMapping(value = "/adminconfig")
	@ResponseBody
	public BooleanResponse saveAdminConfig(@RequestBody LockedConfig.Config adminConfig) {

		return amConfigClient.saveAdminConfig(adminConfig);

	}
}
