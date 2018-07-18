/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.extensioncenter;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.service.AdminUtmReadPermissionsService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tanyy
 * @version AdminUtmReadPermissionsController, v0.1 2018/7/18 16:03
 */
@Api(value = "渠道帐号管理")
@RestController
@RequestMapping("/am-config/extensioncenter/adminutmreadpermissions")
public class AdminUtmReadPermissionsController extends BaseConfigController {
	private Logger logger = LoggerFactory.getLogger(AdminUtmReadPermissionsController.class);
	@Resource
	private AdminUtmReadPermissionsService adminUtmReadPermissionsService;
	/**
	 * 根据条件查询渠道帐号管理
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/searchaction")
	public AdminUtmReadPermissionsResponse searchAction(@RequestBody AdminUtmReadPermissionsRequest request) {
		logger.info("渠道帐号管理查询开始......");
		AdminUtmReadPermissionsResponse response = adminUtmReadPermissionsService.searchAction(request);
		return response;
	}

	/**
	 * 添加渠道帐号管理
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/insert")
	public AdminUtmReadPermissionsResponse insertAction(@RequestBody AdminUtmReadPermissionsRequest request) {
		AdminUtmReadPermissionsResponse response = new AdminUtmReadPermissionsResponse();
		adminUtmReadPermissionsService.insertAction(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 修改渠道帐号管理
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	public AdminUtmReadPermissionsResponse updateAction(@RequestBody AdminUtmReadPermissionsRequest request) {
		AdminUtmReadPermissionsResponse response = new AdminUtmReadPermissionsResponse();
		adminUtmReadPermissionsService.updateAction(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}


	/**
	 * 删除渠道帐号管理
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public AdminUtmReadPermissionsResponse delete(@PathVariable Integer id) {
		AdminUtmReadPermissionsResponse response = new AdminUtmReadPermissionsResponse();
		adminUtmReadPermissionsService.deleteById(id);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

}
