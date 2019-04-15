package com.hyjf.admin.controller.workflow;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.workflow.WorkFlowConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.WorkFlowConfigResponse;
import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiehuili on 2019/4/12.
 */
@Api(tags = "工作流---业务流程配置")
@RestController
@RequestMapping("/hyjf-admin/workflow/bussinessflow")
public class WorkFlowConfigController  extends BaseController {


    //权限名称
    private static final String PERMISSIONS = "bussinessflow";

    @Autowired
    private WorkFlowConfigService workFlowConfigService;

    @ApiOperation(value = "查询业务流程", notes = "查询业务流程")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult selectWorkFlowConfigList(HttpServletRequest request, @RequestBody WorkFlowConfigRequest adminRequest) {
        logger.info("工作流查询查询业务流程配置..." + JSONObject.toJSON(adminRequest));
        WorkFlowConfigResponse response = workFlowConfigService.selectWorkFlowConfigList(adminRequest);
        logger.debug("工作流查询查询业务流程配置..." + JSONObject.toJSON(response));
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<WorkFlowConfigResponse>(response) ;
    }
}
