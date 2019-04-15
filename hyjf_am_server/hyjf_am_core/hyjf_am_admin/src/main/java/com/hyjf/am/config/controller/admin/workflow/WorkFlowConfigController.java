package com.hyjf.am.config.controller.admin.workflow;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.service.WorkFlowConfigService;
import com.hyjf.am.response.admin.WorkFlowConfigResponse;
import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import com.hyjf.am.vo.admin.WorkFlowVO;
import com.hyjf.common.paginator.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiehuili on 2019/4/12.
 */
@RestController
@RequestMapping("/am-admin/workflow/bussinessflow")
public class WorkFlowConfigController extends BaseConfigController {

    @Autowired
    private WorkFlowConfigService workFlowConfigService;
    @PostMapping("/init")
    public WorkFlowConfigResponse selectWorkFlowConfigList(@RequestBody WorkFlowConfigRequest adminRequest) {
        logger.info("工作流查询查询业务流程配置..." + JSONObject.toJSON(adminRequest));
        WorkFlowConfigResponse response = new WorkFlowConfigResponse();
        int count = workFlowConfigService.countWorkFlowConfigList(adminRequest);
        if(count>0){
            Paginator paginator = new Paginator(adminRequest.getCurrPage(), count, adminRequest.getPageSize());
            List<WorkFlowVO> workFlowVOS =workFlowConfigService.selectWorkFlowConfigList(adminRequest, paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(workFlowVOS)){
                response.setResultList(workFlowVOS);
            }
            response.setCount(count);
        }
        logger.debug("工作流查询查询业务流程配置..." + JSONObject.toJSON(response));
        return response;
    }
}
