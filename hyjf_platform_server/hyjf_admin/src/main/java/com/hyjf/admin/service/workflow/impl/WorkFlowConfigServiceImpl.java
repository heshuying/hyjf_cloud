package com.hyjf.admin.service.workflow.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.workflow.WorkFlowConfigService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.WorkFlowConfigResponse;
import com.hyjf.am.response.admin.WorkFlowUserResponse;
import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import com.hyjf.am.vo.admin.WorkFlowVO;
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
    /**
     * 添加工作流配置
     * @param workFlowVO
     * @return
     */
    @Override
    public BooleanResponse insertWorkFlowConfig(WorkFlowVO workFlowVO){
        return adminClient.insertWorkFlowConfig(workFlowVO);
    }
    /**
     * 查询业务流程详情页面
     * @param id
     * @return
     */
    @Override
    public WorkFlowConfigResponse selectWorkFlowConfigInfo(int id){
        return adminClient.selectWorkFlowConfigInfo(id);
    }
    /**
     * 校验业务id是否存在
     * @param businessId
     * @return
     */
    @Override
    public WorkFlowConfigResponse selectWorkFlowConfigByBussinessId(int businessId){
        return adminClient.selectWorkFlowConfigByBussinessId(businessId);
    }
    /**
     * 修改工作流配置
     * @param workFlowVO
     * @return
     */
    @Override
    public BooleanResponse updateWorkFlowConfig(WorkFlowVO workFlowVO){
        return adminClient.updateWorkFlowConfig(workFlowVO);
    }
    /**
     *  删除工作流配置业务流程
     * @param id
     * @return
     */
    @Override
    public BooleanResponse deleteWorkFlowConfigById(int id){
        return adminClient.deleteWorkFlowConfigById(id);
    }
    /**
     *  查询邮件预警通知人
     * @param userName
     * @return
     */
    @Override
    public WorkFlowUserResponse selectUser(String userName){
        return adminClient.selectUser(userName);
    }
}
