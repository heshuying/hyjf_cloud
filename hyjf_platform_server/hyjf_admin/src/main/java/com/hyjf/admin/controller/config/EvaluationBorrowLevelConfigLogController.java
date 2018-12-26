/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.EvaluationBorrowLevelConfigLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.EvaluationBorrowLevelConfigLogResponse;
import com.hyjf.am.resquest.admin.EvaluationBorrowLevelConfigLogRequest;
import com.hyjf.am.vo.admin.EvaluationBorrowLevelConfigLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 风险测评配置- 信用等级配置日志Controller
 *
 * @author liuyang
 * @version EvaluationBorrowLevelConfigLogController, v0.1 2018/12/25 15:26
 */

@Api(tags = "配置中心-风险测评配置信用等级配置日志")
@RestController
@RequestMapping("/hyjf-admin/manager/config/evaluationconfig/borrowlevelconfiglog")
public class EvaluationBorrowLevelConfigLogController extends BaseController {

    public static final String PERMISSIONS = "borrowLevelConfigLog";

    @Autowired
    private EvaluationBorrowLevelConfigLogService service;

    @ApiOperation(value = "风险测评信用等级配置操作日志", notes = "风险测评信用等级配置操作日志")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<EvaluationBorrowLevelConfigLogVO>> getRecordList(@RequestBody EvaluationBorrowLevelConfigLogRequest requestBean) {
        EvaluationBorrowLevelConfigLogResponse response = service.getBorrowLevelConfigLogList(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }
}
