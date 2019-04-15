package com.hyjf.admin.service.workflow;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.WorkFlowConfigResponse;
import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import com.hyjf.am.vo.admin.WorkFlowVO;

/**
 * @author xiehuili on 2019/4/12.
 */
public interface WorkFlowConfigService {

    /**
     * 查询工作流配置
     * @param adminRequest
     * @return
     */
    WorkFlowConfigResponse selectWorkFlowConfigList(WorkFlowConfigRequest adminRequest);

    /**
     * 添加工作流配置
     * @param workFlowVO
     * @return
     */
    BooleanResponse insertWorkFlowConfig(WorkFlowVO workFlowVO);
}
