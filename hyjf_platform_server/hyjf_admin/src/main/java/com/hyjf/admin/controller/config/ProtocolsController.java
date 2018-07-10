/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.beans.request.ProtocolsRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.impl.ProtocolsService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;
import com.hyjf.am.vo.trade.FddTempletCustomizeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fuqiang
 * @version ProtocolsController, v0.1 2018/7/10 16:03
 */
@Api(value = "协议管理")
@RestController
@RequestMapping("/hyjf-admin/protocols")
public class ProtocolsController extends BaseController {
	@Autowired
	private ProtocolsService protocolsService;

	@ApiOperation(value = "协议管理", notes = "展示协议管理列表")
	public AdminResult<ListResult<FddTempletCustomizeVO>> selectFddTempletList(
			@RequestBody ProtocolsRequestBean request) {
		FddTempletCustomizeResponse response = protocolsService.selectFddTempletList(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "协议管理", notes = "添加协议管理")
	@RequestMapping("/insert")
	public AdminResult insert(@RequestBody ProtocolsRequestBean requestBean) {
		FddTempletCustomizeResponse response = protocolsService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "协议管理", notes = "修改协议管理")
	@RequestMapping("/update")
	public AdminResult update(@RequestBody ProtocolsRequestBean requestBean) {
		FddTempletCustomizeResponse response = protocolsService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

}
