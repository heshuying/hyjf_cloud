package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.WorkFlowMapper;
import com.hyjf.am.config.dao.mapper.customize.WorkFlowConfigMapper;
import com.hyjf.am.config.dao.model.auto.WorkFlow;
import com.hyjf.am.config.dao.model.auto.WorkFlowExample;
import com.hyjf.am.config.service.WorkFlowConfigService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import com.hyjf.am.vo.admin.WorkFlowNodeVO;
import com.hyjf.am.vo.admin.WorkFlowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author xiehuili on 2019/4/12.
 */
@Service
public class WorkFlowConfigServiceImpl implements WorkFlowConfigService {

    @Autowired
    private WorkFlowConfigMapper workFlowConfigMapper;

    /**
     * 查询工作流配置条数
     * @param adminRequest
     * @return
     */
    @Override
    public int countWorkFlowConfigList(WorkFlowConfigRequest adminRequest){
        return workFlowConfigMapper.countWorkFlowConfigList(adminRequest);
    }

    /**
     * 查询工作流配置
     * @param adminRequest
     * @return
     */
    @Override
    public List<WorkFlowVO> selectWorkFlowConfigList(WorkFlowConfigRequest adminRequest, int limitStart, int limitEnd) {
        if(limitStart > -1){
            adminRequest.setLimitStart(limitStart);
            adminRequest.setLimitEnd(limitEnd);
        }
        return workFlowConfigMapper.selectWorkFlowConfigList(adminRequest);
    }
    /**
     * 添加工作流配置
     * @param workFlowVO
     * @return
     */
    @Override
    public int insertWorkFlowConfig(WorkFlowVO workFlowVO){
        //流程节点
        List<WorkFlowNodeVO> flowNodes =workFlowVO.getFlowNodes();
        //保存业务流程表
        int workFlowCount = workFlowConfigMapper.insertWorkFlow(workFlowVO);
        if(workFlowCount > 0){
            //保存业务流程节点表
            int workFlowNodeCount = workFlowConfigMapper.insertWorkFlowNode(flowNodes);
            if(workFlowNodeCount>0){
                return 1;
            }
        }
        return 0;
    }

    /**
     * 查询业务流程详情页面
     * @param id
     * @return
     */
    @Override
    public WorkFlowVO selectWorkFlowConfigInfo(int id){
        //查询业务流程
        WorkFlowVO workFlowVO = workFlowConfigMapper.selectWorkFlowConfigInfo(id);
        if(null == workFlowVO){
            return null;
        }
        if(null != workFlowVO&&null == workFlowVO.getBusinessId()){
            return null;
        }
        //根据业务流程中的业务id查询业务流程节点
        List<WorkFlowNodeVO> flowNodes =  workFlowConfigMapper.selectWorkFlowConfigNode(workFlowVO.getBusinessId());
        if(!CollectionUtils.isEmpty(flowNodes)){
            workFlowVO.setFlowNodes(flowNodes);
            workFlowVO.setFlowNode(flowNodes.size());
        }
        return workFlowVO;
    }
    /**
     * 修改工作流配置
     * @param workFlowVO
     * @return
     */
    @Override
    public int updateWorkFlowConfig(WorkFlowVO workFlowVO){
        //流程节点
        List<WorkFlowNodeVO> flowNodes =workFlowVO.getFlowNodes();
        //修改业务了流程
        int workFlowCount = workFlowConfigMapper.updateWorkFlow(workFlowVO);
        //删除业务流程节点
        int deleteWorkFlowNodeCount = workFlowConfigMapper.deleteWorkFlowNode(flowNodes);
        //添加业务流程节点
        if(workFlowCount >0 && deleteWorkFlowNodeCount>0){
            //添加业务流程节点表
            int workFlowNodeCount = workFlowConfigMapper.insertWorkFlowNode(flowNodes);
            if(workFlowNodeCount>0){
                return 1;
            }
        }
        return 0;
    }
}
