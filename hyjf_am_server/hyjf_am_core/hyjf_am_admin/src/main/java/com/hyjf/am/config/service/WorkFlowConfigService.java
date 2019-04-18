package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.WorkFlow;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.WorkFlowUserResponse;
import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import com.hyjf.am.vo.admin.WorkFlowUserVO;
import com.hyjf.am.vo.admin.WorkFlowVO;

import java.util.List;

/**
 * @author xiehuili on 2019/4/12.
 */
public interface WorkFlowConfigService {

    /**
     * 查询工作流配置条数
     * @param adminRequest
     * @return
     */
    int countWorkFlowConfigList(WorkFlowConfigRequest adminRequest);

    /**
     * 查询工作流配置
     * @param adminRequest
     * @return
     */
    List<WorkFlowVO> selectWorkFlowConfigList(WorkFlowConfigRequest adminRequest, int limitStart, int limitEnd);
    /**
     * 添加工作流配置
     * @param workFlowVO
     * @return
     */
    int insertWorkFlowConfig(WorkFlowVO workFlowVO);

    /**
     * 查询业务流程详情页面
     * @param id
     * @return
     */
    WorkFlowVO selectWorkFlowConfigInfo(int id);
    /**
     * 修改工作流配置
     * @param workFlowVO
     * @return
     */
    int updateWorkFlowConfig(WorkFlowVO workFlowVO);
    /**
     *  删除工作流配置业务流程
     * @param id
     * @return
     */
    Integer deleteWorkFlowConfigById(int id);

    /**
     * 查询所有业务流程
     * @return
     */
    List<WorkFlowVO> findWorkFlowAll();

    /**
     * 查询所有业务流程节点的用户
     * @return
     */
    List<WorkFlowUserVO> findWorkFlowNodeUserEmailAll();

    int updateFlowStatus(WorkFlow workFlow);
    /**
     *  查询邮件预警通知人
     * @param userName
     * @return
     */
    List<WorkFlowUserVO> selectUser(String userName);
}
