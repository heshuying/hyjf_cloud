/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.WhereaboutsPageService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.am.vo.config.WhereaboutsPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tanyy
 * @version WhereaboutsPageController, v0.1 2018/7/20 10:35
 */
@Api(value = "移动端着陆页管理")
@RestController
@RequestMapping("/hyjf-admin/content/whereaboutspage")
public class WhereaboutsPageController extends BaseController {
	@Autowired
	private WhereaboutsPageService whereaboutsPageService;

	@ApiOperation(value = "移动端着陆页管理", notes = "移动端着陆页管理列表查询")
	@RequestMapping("/searchaction")
	public AdminResult<ListResult<WhereaboutsPageVo>> searchAction(@RequestBody WhereaboutsPageRequestBean requestBean) {
		WhereaboutsPageResponse response = whereaboutsPageService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "移动端着陆页管理", notes = "添加移动端着陆页管理")
	@RequestMapping("/insert")
	public AdminResult add(@RequestBody WhereaboutsPageRequestBean requestBean) {
		WhereaboutsPageResponse response = whereaboutsPageService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "移动端着陆页管理", notes = "修改移动端着陆页管理")
	@RequestMapping("/update")
	public AdminResult update(@RequestBody WhereaboutsPageRequestBean requestBean) {
		WhereaboutsPageResponse response = whereaboutsPageService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
	@ApiOperation(value = "移动端着陆页管理", notes = "修改移动端着陆页管理状态")
	@RequestMapping("/updatestatus")
	public AdminResult updateStatus(@RequestBody WhereaboutsPageRequestBean requestBean) {
		WhereaboutsPageResponse response = whereaboutsPageService.updateStatus(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
	@ApiOperation(value = "移动端着陆页管理", notes = "删除着路页管理")
	@RequestMapping("/delete/{id}")
	public AdminResult delete(@PathVariable Integer id) {
		WhereaboutsPageResponse response = whereaboutsPageService.deleteById(id);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
}
