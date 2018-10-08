/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.extensioncenter;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.service.AdminUtmReadPermissionsService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.trade.service.admin.extensioncenter.ChannelStatisticsDetailService;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tanyy
 * @version AdminUtmReadPermissionsController, v0.1 2018/7/18 16:03
 */
@Api(value = "渠道帐号管理")
@RestController
@RequestMapping("/am-admin/extensioncenter/adminutmreadpermissions")
public class AdminUtmReadPermissionsController extends BaseConfigController {
	private Logger logger = LoggerFactory.getLogger(AdminUtmReadPermissionsController.class);
	@Resource
	private AdminUtmReadPermissionsService adminUtmReadPermissionsService;

	@Resource
	private ChannelStatisticsDetailService channelStatisticsDetailService;
	/**
	 * 根据条件查询渠道帐号管理
	 *
	 * @param request
	 * @return
	 */

	@ApiOperation(value = "渠道帐号管理列表", notes = "渠道帐号管理列表")
	@PostMapping("/searchaction")
	public AdminUtmReadPermissionsResponse searchAction(@RequestBody AdminUtmReadPermissionsRequest request) {
		logger.info("渠道帐号管理查询开始......");
		AdminUtmReadPermissionsResponse response = adminUtmReadPermissionsService.searchAction(request);
		return response;
	}

	@ApiOperation(value = "检查编号唯一性", notes = "检查编号唯一性")
	@GetMapping ("/checkaction/{userName}")
	public IntegerResponse checkAction(@PathVariable String userName) {
		logger.info("检查编号唯一性......");
		IntegerResponse response = new IntegerResponse();
		int flag = adminUtmReadPermissionsService.isExistsAdminUser(userName);
		response.setResultInt(flag);
		return response;
	}

	@ApiOperation(value = "渠道权限列表转换接口", notes = "渠道权限列表转换接口")
	@GetMapping("/preparedatas")
	public UtmPlatResponse prepareDatas() {
		logger.info("渠道权限列表转换接口......");
		UtmPlatResponse response = channelStatisticsDetailService.getUtmPlatList();
		return response;
	}
	/**
	 * 添加渠道帐号管理
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "渠道帐号管理插入", notes = "渠道帐号管理插入")
	@PostMapping("/insert")
	public AdminUtmReadPermissionsResponse insertAction(@RequestBody AdminUtmReadPermissionsRequest request) {
		AdminUtmReadPermissionsResponse response = new AdminUtmReadPermissionsResponse();
		adminUtmReadPermissionsService.insertAction(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}


	/**
	 * 新增修改详情
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "新增修改详情", notes = "新增修改详情")
	@PostMapping("/getrecord")
	public AdminUtmReadPermissionsResponse getRecord(@RequestBody AdminUtmReadPermissionsRequest request) {
		AdminUtmReadPermissionsResponse response =adminUtmReadPermissionsService.getAdminUtmReadPermissions(request);
		return response;
	}
	/**
	 * 修改渠道帐号管理
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "渠道帐号管理更新", notes = "渠道帐号管理更新")
	@PostMapping("/update")
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
	@ApiOperation(value = "渠道帐号管理删除", notes = "渠道帐号管理删除")
	@RequestMapping("/delete/{id}")
	public AdminUtmReadPermissionsResponse delete(@PathVariable Integer id) {
		AdminUtmReadPermissionsResponse response = new AdminUtmReadPermissionsResponse();
		adminUtmReadPermissionsService.deleteById(id);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  根据用户Id查询渠道账号管理
	 * @Date 16:57 2018/7/24
	 * @Param userId
	 * @return
	 */
	@ApiOperation(value = "根据用户Id查询渠道账号管理", notes = "根据用户Id查询渠道账号管理")
	@RequestMapping("/getadminutmreadpermissions/{userId}")
	public AdminUtmReadPermissionsResponse selectAdminUtmReadPermissions(@PathVariable Integer userId) {
		AdminUtmReadPermissionsResponse response = new AdminUtmReadPermissionsResponse();
		AdminUtmReadPermissionsVO adminUtmReadPermissionsVO = adminUtmReadPermissionsService.selectAdminUtmReadPermissions(userId);
		response.setRtn(AdminResponse.SUCCESS);
		response.setResult(adminUtmReadPermissionsVO);
		return response;
	}

}
