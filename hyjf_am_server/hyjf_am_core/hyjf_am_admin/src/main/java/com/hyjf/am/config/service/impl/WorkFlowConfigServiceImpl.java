package com.hyjf.am.config.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.mapper.auto.WorkFlowMapper;
import com.hyjf.am.config.dao.mapper.auto.WorkFlowNodeMapper;
import com.hyjf.am.config.dao.mapper.customize.WorkFlowConfigMapper;
import com.hyjf.am.config.dao.model.auto.WorkFlow;
import com.hyjf.am.config.dao.model.auto.WorkFlowNode;
import com.hyjf.am.config.dao.model.auto.WorkFlowNodeExample;
import com.hyjf.am.config.service.WorkFlowConfigService;
import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import com.hyjf.am.vo.admin.WorkFlowNodeVO;
import com.hyjf.am.vo.admin.WorkFlowUserVO;
import com.hyjf.am.vo.admin.WorkFlowVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiehuili on 2019/4/12.
 */
@Service
public class WorkFlowConfigServiceImpl implements WorkFlowConfigService {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WorkFlowConfigMapper workFlowConfigMapper;
    @Autowired
    private WorkFlowMapper workFlowMapper;
    @Autowired
    private WorkFlowNodeMapper workFlowNodeMapper;

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
        workFlowConfigMapper.insertWorkFlow(workFlowVO);
        logger.debug("工作流添加业务流程配置,插入数据的业务流程id:" + workFlowVO.getId()+"工作流节点，请求参数："+ JSONObject.toJSONString(flowNodes));
        //保存业务流程节点表
        return workFlowConfigMapper.insertWorkFlowNode(flowNodes,workFlowVO.getId());
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
        //根据业务流程中的业务id查询业务流程节点
        List<WorkFlowNodeVO> flowNodes =  workFlowConfigMapper.selectWorkFlowConfigNode(id);
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
        if(!CollectionUtils.isEmpty(flowNodes)){
            //判断流程节点是异常 true正常
            boolean flag= workFlowStatus(flowNodes);
            if(flag){
                workFlowVO.setAuditFlag(1);
            }else{
                //异常，不允许修改
                return -1;
            }
        }
        //修改业务了流程
        workFlowConfigMapper.updateWorkFlow(workFlowVO);
        //删除业务流程节点
        deleteWorkFolwNode(workFlowVO.getId());
        //添加业务流程节点表
         int count = workFlowConfigMapper.insertWorkFlowNode(flowNodes,workFlowVO.getId());
         if(count <=0){
             return 0;
         }
         return 1;
    }
    /**
     *  删除工作流配置业务流程
     * @param id
     * @return
     */
    @Override
    public Integer deleteWorkFlowConfigById(int id){
        //查询业务流程
        WorkFlow workFlow = workFlowMapper.selectByPrimaryKey(id);
        if(null == workFlow){
            return null;
        }
        if(1 == workFlow.getCheckStatus()){
            //当业务流程需要审核时，不能进行删除
            return 2;
        }
        //删除业务流程
        workFlowMapper.deleteByPrimaryKey(id);
        //删除业务流程节点
        deleteWorkFolwNode(workFlow.getId());
        return 1;
    }
    /**
     *  查询邮件预警通知人
     * @param userName
     * @return
     */
    @Override
    public List<WorkFlowUserVO> selectUser(String userName){
        return workFlowConfigMapper.selectUser(userName);
    }
    public int deleteWorkFolwNode(int workflowId){
        WorkFlowNodeExample example = new WorkFlowNodeExample();
        example.createCriteria().andWorkflowIdEqualTo(workflowId);
        return workFlowNodeMapper.deleteByExample(example);
    }


    /**
     * 判断业务流程是否异常
     * @param flowNodes
     * @return
     */
    public boolean workFlowStatus(List<WorkFlowNodeVO> flowNodes){
        int size =flowNodes.size();
        //角色节点的大小
        int roleSize = 0;
        //用户节点大小
        int userSize = 0;
        //获取业务流程中角色的节点
        List<WorkFlowNodeVO> roleFlow = new ArrayList<>();
        //获取业务流程中用户的节点
        List<WorkFlowNodeVO> userFlow = new ArrayList<>();
        for(int i=0;i<flowNodes.size();i++){
            if(null != flowNodes.get(i).getRole() ){
                if(flowNodes.get(i).getRole().intValue()==1){
                    roleFlow.add(flowNodes.get(i));
                }else{
                    userFlow.add(flowNodes.get(i));
                }
            }
        }
        if(!CollectionUtils.isEmpty(userFlow)){
            logger.debug("工作流节点，用户请求参数userFlow："+JSONObject.toJSONString(userFlow));
            //查询业务流程节点对应的所有用户
            List<WorkFlowUserVO> workFlowUserVO = workFlowConfigMapper.selectWorkFlowUser(userFlow);
            //判断业务流程是否异常
            if(!CollectionUtils.isEmpty(workFlowUserVO)){
                userSize = workFlowUserVO.size();
            }
        }
        if(!CollectionUtils.isEmpty(roleFlow)){
            logger.debug("工作流节点，角色请求参数roleFlow："+JSONObject.toJSONString(roleFlow));
            //查询业务流程中每个角色下的一个可用用户（只要存在一个用户就可以）
            List<WorkFlowUserVO> workFlowUserRoleVO = workFlowConfigMapper.selectWorkFlowUserRole(roleFlow);
            if(!CollectionUtils.isEmpty(workFlowUserRoleVO)){
                roleSize = workFlowUserRoleVO.size();
            }
        }
        if(size == roleSize+userSize){
            //用户和角色下的用户都存在
            return true;
        }
        return false;
    }
}
