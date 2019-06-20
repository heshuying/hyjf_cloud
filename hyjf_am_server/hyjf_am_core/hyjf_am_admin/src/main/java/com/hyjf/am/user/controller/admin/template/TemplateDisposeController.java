package com.hyjf.am.user.controller.admin.template;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.TemplateDisposeResponse;
import com.hyjf.am.response.market.UtmRegResponse;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.user.dao.model.auto.TemplateDispose;
import com.hyjf.am.user.dao.model.auto.UtmReg;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthListCustomize;
import com.hyjf.am.user.service.admin.promotion.UtmRegService;
import com.hyjf.am.vo.user.AdminUserAuthListVO;
import com.hyjf.am.vo.user.UtmRegVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;

@RestController
@RequestMapping("/am-user/templateDispose")
public class TemplateDisposeController {

	@Autowired
	private TemplateDisposeService templateDisposeService;
	

	/**
	 * 查询授权明细
 	 */
	@PostMapping("/templateDisposeList")
	public TemplateDisposeResponse templateDisposeList(@RequestBody @Valid TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.templateDisposeList(templateDisposeRequest);
		return templateDisposeResponse;

	}
	/**
	 * 查询授权明细
 	 */
	@PostMapping("/updateTemplateDispose")
	public TemplateDisposeResponse templateDisposeList(@RequestBody @Valid TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.updateTemplateDispose(templateDisposeRequest);
		return templateDisposeResponse;

	}
	/**
	 * 查询授权明细
 	 */
	@PostMapping("/deleteTemplateDispose")
	public TemplateDisposeResponse templateDisposeList(@RequestBody @Valid TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.deleteTemplateDispose(templateDisposeRequest);
		return templateDisposeResponse;

	}



}
