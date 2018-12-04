/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.request.SiteSettingRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.SiteSettingService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.SiteSettingsResponse;
import com.hyjf.am.vo.config.SiteSettingsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fuqiang
 * @version SiteSettingController, v0.1 2018/7/10 11:21
 */
@Api(tags = "配置中心-网站设置")
@RestController
@RequestMapping("/hyjf-admin/sitesetting")
public class SiteSettingController extends BaseController {
	@Autowired
	private SiteSettingService siteSettingService;

	/** 权限关键字 */
	public static final String PERMISSIONS = "sitesetting";

	@ApiOperation(value = "网站设置初始化", notes = "网站设置初始化")
	@GetMapping("/init")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
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

	@ApiOperation(value = "修改网站设置", notes = "修改网站设置")
	@PostMapping("/update")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
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
