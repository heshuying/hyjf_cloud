package com.hyjf.admin.service.workflow;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.AdminRoleResponse;
import com.hyjf.am.response.admin.WorkFlowConfigResponse;
import com.hyjf.am.response.admin.WorkFlowUserResponse;
import com.hyjf.am.response.config.AdminUserResponse;
import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import com.hyjf.am.resquest.config.AdminRequest;
import com.hyjf.am.vo.admin.WorkFlowUserVO;
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

    /**
     * 查询业务流程详情页面
     * @param id
     * @return
     */
    WorkFlowConfigResponse selectWorkFlowConfigInfo(int id);

    /**
     * 校验业务id是否存在
     * @param businessId
     * @return
     */
    BooleanResponse selectWorkFlowConfigByBussinessId(int businessId);
    /**
     * 修改工作流配置
     * @param workFlowVO
     * @return
     */
    BooleanResponse updateWorkFlowConfig(WorkFlowVO workFlowVO);
    /**
     *  删除工作流配置业务流程
     * @param id
     * @return
     */
    BooleanResponse deleteWorkFlowConfigById(int id);
    /**
     *  查询邮件预警通知人
     * @param userName
     * @return
     */
    WorkFlowUserResponse selectUser(WorkFlowUserVO workFlowUserVO);

    void disableAdminUser(Integer[] adminUserId);

    /**
     * 工作流查询所有用户角色
     * @return
     */
    AdminRoleResponse selectWorkFlowRoleList();

}
