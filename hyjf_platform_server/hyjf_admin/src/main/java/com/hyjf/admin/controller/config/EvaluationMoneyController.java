/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.EvaluationMoneyService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.EvaluationMoneyResponse;
import com.hyjf.am.resquest.admin.EvaluationMoneyRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.admin.EvaluationMoneyConfigVO;
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
@Api(tags = "配置中心-风险测评限额配置")
@RestController
@RequestMapping("/hyjf-admin/evaluationmoney")
public class EvaluationMoneyController extends BaseController {

	@Autowired
	private EvaluationMoneyService service;

	public static final String PERMISSIONS = "evaluationmoney";

	@ApiOperation(value = "风险测评限额配置", notes = "风险测评限额配置")
	@PostMapping("/init")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<EvaluationMoneyConfigVO>> getRecordList(@RequestBody EvaluationMoneyRequest requestBean) {
		EvaluationMoneyResponse response = service.getEvaluationMoneyList(requestBean);
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
	public AdminResult info(@RequestBody EvaluationMoneyRequest requestBean) {
		logger.info(EvaluationMoneyController.class.toString(), "infoAction");
		EvaluationMoneyResponse response = new EvaluationMoneyResponse();
		// modify by libin sonar start 拿到前面
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		// modify by libin sonar end
		if (requestBean.getId() != null) {
			EvaluationMoneyConfigVO evaluationMoneyConfigVO = this.service.getEvaluationMoneyById(requestBean.getId());
			response.setForm(evaluationMoneyConfigVO);
		}

		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		logger.info(EvaluationMoneyController.class.toString(), "infoAction");
		return new AdminResult<>(response);
	}

	@ApiOperation(value = "修改风险测评限额配置", notes = "修改风险测评限额配置")
	@PostMapping("/updateAction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult updateAction(HttpServletRequest request, @RequestBody EvaluationMoneyRequest requestBean) {
		AdminSystemVO loginUser=getUser(request);
		requestBean.setUpdateUser(loginUser.getUsername());
		EvaluationMoneyResponse response = service.updateEvaluationMoney(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(response);
	}

}
