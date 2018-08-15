/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.admin.beans.request.PushMoneyRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PushMoneyService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.vo.trade.PushMoneyVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 提成设置
 * 
 * @author fuqiang
 * @version PushMoneyController, v0.1 2018/7/10 10:56
 */
@Api(tags = "提成配置")
@RestController
@RequestMapping("/hyjf-admin/pushmoney")
public class PushMoneyController extends BaseController {

	@Autowired
	private PushMoneyService pushMoneyService;

	@ApiOperation(value = "获取提成配置列表", notes = "获取提成配置列表")
	@GetMapping("/init")
	public AdminResult<ListResult<PushMoneyVO>> getRecordList() {
		PushMoneyResponse response = pushMoneyService.getRecordList();
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "添加提成配置", notes = "添加提成配置")
	@PostMapping("/insert")
	public AdminResult add(@RequestBody PushMoneyRequestBean requestBean) {
		PushMoneyResponse response = pushMoneyService.insertPushMoney(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "修改提成配置", notes = "修改提成配置")
	@PostMapping("/update")
	public AdminResult update(@RequestBody PushMoneyRequestBean requestBean) {
		PushMoneyResponse response = pushMoneyService.updatePushMoney(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}
}
