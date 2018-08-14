/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.extensioncenter;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminUtmReadPermissionsService;
import com.hyjf.admin.service.ChannelStatisticsDetailService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author tanyy
 * @version AdminUtmReadPermissionsController, v0.1 2018/7/18 16:03
 */
@Api(value = "渠道帐号管理",tags ="渠道帐号管理")
@RestController
@RequestMapping("/hyjf-admin/adminutmreadpermissions")
public class AdminUtmReadPermissionsController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(AdminUtmReadPermissionsController.class);
	@Resource
	private AdminUtmReadPermissionsService adminUtmReadPermissionsService;

	@ApiOperation(value = "渠道帐号管理", notes = "渠道帐号管理列表")
	@PostMapping("/searchaction")
	public AdminResult searchAction(@RequestBody AdminUtmReadPermissionsRequest request) {
		logger.info("渠道帐号管理查询开始......");
		AdminUtmReadPermissionsResponse response = adminUtmReadPermissionsService.searchAction(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));

	}
	@ApiOperation(value = "渠道帐号管理", notes = "添加渠道帐号管理")
	@RequestMapping("/insert")
	public AdminResult add(@RequestBody AdminUtmReadPermissionsRequest requestBean) {
		AdminUtmReadPermissionsResponse response = adminUtmReadPermissionsService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "渠道帐号管理", notes = "修改渠道帐号管理")
	@RequestMapping("/update")
	public AdminResult update(@RequestBody AdminUtmReadPermissionsRequest requestBean) {
		AdminUtmReadPermissionsResponse response = adminUtmReadPermissionsService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "渠道帐号管理", notes = "删除渠道帐号管理")
	@RequestMapping("/delete/{id}")
	public AdminResult delete(@PathVariable Integer id) {
		AdminUtmReadPermissionsResponse response = adminUtmReadPermissionsService.deleteById(id);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

}
