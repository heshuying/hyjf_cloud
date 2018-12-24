/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.EvaluationMoneyLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.EvaluationMoneyLogResponse;
import com.hyjf.am.resquest.admin.EvaluationMoneyLogRequest;
import com.hyjf.am.vo.trade.EvaluationMoneyLogConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 风险测评限额配置日志
 * @author Zha Daojian
 * @date 2018/12/20 17:24
 * @param
 * @return
 **/
@Api(tags = "配置中心-风险测评限额配置日志")
@RestController
@RequestMapping("/hyjf-admin/evaluationchecklog")
public class EvaluationMoneyLogController extends BaseController {

	@Autowired
	private EvaluationMoneyLogService service;

	public static final String PERMISSIONS = "evaluationchecklog";

	@ApiOperation(value = "风险测评限额配置日志", notes = "风险测评限额配置日志")
	@PostMapping("/init")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<EvaluationMoneyLogConfigVO>> getRecordList(@RequestBody EvaluationMoneyLogRequest requestBean) {
		EvaluationMoneyLogResponse response = service.getEvaluationMoneyLogList(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

}
