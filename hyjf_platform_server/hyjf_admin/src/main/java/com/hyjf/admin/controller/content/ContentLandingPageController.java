/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.hyjf.admin.beans.request.ContentLandingPageRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentLandingPageService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.LandingPageResponse;
import com.hyjf.am.vo.config.LandingPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanyy
 * @version ContentLandingPageController, v0.1 2018/7/16 10:35
 */
@Api(value = "着路页管理", description = "着路页管理")
@RestController
@RequestMapping("/hyjf-admin/content/contentlandingpage")
public class ContentLandingPageController extends BaseController {
	@Autowired
	private ContentLandingPageService contentLandingPageService;

	@ApiOperation(value = "着路页管理列表查询", notes = "着路页管理列表查询")
	@RequestMapping("/searchaction")
	public AdminResult<ListResult<LandingPageVo>> searchAction(@RequestBody ContentLandingPageRequestBean requestBean) {
		LandingPageResponse response = contentLandingPageService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "添加着路页管理", notes = "添加着路页管理")
	@RequestMapping("/insert")
	public AdminResult add(@RequestBody ContentLandingPageRequestBean requestBean) {
		LandingPageResponse response = contentLandingPageService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "修改着路页管理", notes = "修改着路页管理")
	@RequestMapping("/update")
	public AdminResult update(@RequestBody ContentLandingPageRequestBean requestBean) {
		LandingPageResponse response = contentLandingPageService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "删除着路页管理", notes = "删除着路页管理")
	@RequestMapping("/delete/{id}")
	public AdminResult delete(@PathVariable Integer id) {
		LandingPageResponse response = contentLandingPageService.deleteById(id);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
}
