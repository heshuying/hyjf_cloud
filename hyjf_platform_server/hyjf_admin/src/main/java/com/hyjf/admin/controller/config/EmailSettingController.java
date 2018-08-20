/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.admin.beans.request.SiteSettingRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.SiteSettingService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.SiteSettingsResponse;
import com.hyjf.am.vo.config.SiteSettingsVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fuqiang
 * @version EmailSettingController, v0.1 2018/7/10 11:40
 */
@Api(tags = "配置中心-邮件设置")
@RestController
@RequestMapping("/hyjf-admin/emailsetting")
public class EmailSettingController extends BaseController {
	@Autowired
	private SiteSettingService siteSettingService;

	@ApiOperation(value = "邮件设置初始化", notes = "邮件设置初始化")
	@GetMapping("/init")
	public AdminResult<SiteSettingsVO> init() {
		SiteSettingsResponse response = siteSettingService.selectSiteSetting();
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(response.getResult());
	}

	@ApiOperation(value = "修改邮件设置", notes = "修改邮件设置")
	@PostMapping("/update")
	public AdminResult update(@RequestBody SiteSettingRequestBean requestBean) {
		SiteSettingsResponse response = siteSettingService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
}
