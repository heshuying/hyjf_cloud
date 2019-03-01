/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.EvaluationCheckService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.EvaluationCheckResponse;
import com.hyjf.am.resquest.admin.EvaluationCheckRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.admin.EvaluationCheckConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 风险测评开关配置
 * @author Zha Daojian
 * @date 2018/12/20 17:24
 * @param
 * @return
 **/
@Api(tags = "配置中心-风险测评开关配置")
@RestController
@RequestMapping("/hyjf-admin/evaluationcheck")
public class EvaluationCheckController extends BaseController {

	@Autowired
	private EvaluationCheckService service;

	public static final String PERMISSIONS = "evaluationcheck";

	@ApiOperation(value = "风险测评开关配置列表页", notes = "风险测评开关配置列表页")
	@PostMapping("/init")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<EvaluationCheckConfigVO>> getRecordList(@RequestBody EvaluationCheckRequest requestBean) {
		EvaluationCheckResponse response = service.getEvaluationCheckList(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@SuppressWarnings("unused")
	@ApiOperation(value = "修改画面迁移", notes = "修改画面迁移")
	@PostMapping("/infoAction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult info(@RequestBody EvaluationCheckRequest requestBean) {
		logger.info(EvaluationCheckController.class.toString(), "infoAction");
		EvaluationCheckResponse response = new EvaluationCheckResponse();
		// modify by libin sonar start 拿到前面来
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		// modify by libin sonar end
		if (requestBean.getId() != null) {
			EvaluationCheckConfigVO evaluationCheckConfigVO = this.service.getEvaluationCheckById(requestBean.getId());
			response.setForm(evaluationCheckConfigVO);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		logger.info(EvaluationCheckController.class.toString(), "infoAction");
		return new AdminResult<>(response);
	}

	@ApiOperation(value = "修改风险测评开关配置", notes = "修改风险测评开关配置")
	@PostMapping("/updateAction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult updateAction(HttpServletRequest request, @RequestBody EvaluationCheckRequest requestBean) {
		AdminSystemVO loginUser=getUser(request);
		requestBean.setUpdateUser(loginUser.getUsername());
		EvaluationCheckResponse response = service.updateEvaluationCheck(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(response);
	}

}
