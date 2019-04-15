package com.hyjf.admin.service.workflow.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.workflow.WorkFlowConfigService;
import com.hyjf.am.response.admin.WorkFlowConfigResponse;
import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiehuili on 2019/4/12.
 */
@Service
public class WorkFlowConfigServiceImpl implements WorkFlowConfigService {

    @Autowired
    private AmAdminClient adminClient;

    /**
     * 查询工作流配置
     * @param adminRequest
     * @return
     */
    @Override
    public WorkFlowConfigResponse selectWorkFlowConfigList(WorkFlowConfigRequest adminRequest) {
        return adminClient.selectWorkFlowConfigList(adminRequest);
    }
}
