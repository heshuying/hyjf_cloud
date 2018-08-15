/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.hyjf.am.response.config.LinkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.admin.beans.request.ContentPartnerRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentPartnerService;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.LinkVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 合作伙伴type为2
 * 
 * @author fuqiang
 * @version ContentPartnerController, v0.1 2018/7/12 9:52
 */
@Api(tags = "公司管理-合作伙伴")
@RestController
@RequestMapping("/hyjf-admin/content/contentpartner")
public class ContentPartnerController extends BaseController {
	@Autowired
	private ContentPartnerService contentPartnerService;

	@ApiOperation(value = "公司管理-合作伙伴列表查询", notes = "公司管理-合作伙伴列表查询")
	@PostMapping("/searchaction")
	public AdminResult<ListResult<LinkVO>> searchAction(@RequestBody ContentPartnerRequestBean requestBean) {
		LinkResponse response = contentPartnerService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "添加公司管理-合作伙伴", notes = "添加公司管理-合作伙伴")
	@PostMapping("/insert")
	public AdminResult add(@RequestBody ContentPartnerRequestBean requestBean) {
		LinkResponse response = contentPartnerService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "修改公司管理-合作伙伴", notes = "修改公司管理-合作伙伴")
	@PostMapping("/update")
	public AdminResult update(@RequestBody ContentPartnerRequestBean requestBean) {
		LinkResponse response = contentPartnerService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "修改公司管理-合作伙伴", notes = "修改公司管理-合作伙伴")
	@PostMapping("/updatestatus")
	public AdminResult updatestatus(@RequestBody ContentPartnerRequestBean requestBean) {
		LinkResponse response = contentPartnerService.updateStatus(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "删除公司管理-合作伙伴", notes = "删除公司管理-合作伙伴")
	@GetMapping("/delete/{id}")
	public AdminResult delete(@PathVariable Integer id) {
		LinkResponse response = contentPartnerService.deleteById(id);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
}
