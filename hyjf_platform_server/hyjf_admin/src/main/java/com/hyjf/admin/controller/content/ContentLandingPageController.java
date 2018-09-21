/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.ContentLandingPageRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentLandingPageService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.LandingPageResponse;
import com.hyjf.am.vo.config.LandingPageVo;
import com.hyjf.common.file.UploadFileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author tanyy
 * @version ContentLandingPageController, v0.1 2018/7/16 10:35
 */
@Api(value = "着路页管理",tags = "内容中心-着路页管理")
@RestController
@RequestMapping("/hyjf-admin/content/contentlandingpage")
public class ContentLandingPageController extends BaseController {
	@Autowired
	private ContentLandingPageService contentLandingPageService;

	@Value("${file.domain.url}")
	private String FILEDOMAILURL;

	@ApiOperation(value = "着路页管理列表查询", notes = "着路页管理列表查询")
	@PostMapping("/searchaction")
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
	@PostMapping("/insert")
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



	@ApiOperation(value = "着路页修改详情", notes = "着路页修改详情")
	@PostMapping("/info")
	public AdminResult infoAction(@RequestBody ContentLandingPageRequestBean requestBean) {
		AdminResult result = new AdminResult();
		JSONObject jsonObject = new JSONObject();
		if (requestBean.getId()!=null) {
			LandingPageResponse record = this.contentLandingPageService.getRecord(requestBean.getId());
			if(record!=null) {
				BeanUtils.copyProperties(record.getResult(), requestBean);
				jsonObject.put("landingPageForm",requestBean);
			}

			String fileDomainUrl = UploadFileUtils.getDoPath(FILEDOMAILURL);
			jsonObject.put("fileDomainUrl", fileDomainUrl);
		}
		result.setData(jsonObject);
		return result;
	}
	@ApiOperation(value = "修改着路页管理", notes = "修改着路页管理")
	@PostMapping("/update")
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
	@GetMapping("/delete/{id}")
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
