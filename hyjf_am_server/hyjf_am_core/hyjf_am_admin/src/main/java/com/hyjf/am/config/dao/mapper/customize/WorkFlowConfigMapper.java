package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import com.hyjf.am.vo.admin.WorkFlowNodeVO;
import com.hyjf.am.vo.admin.WorkFlowUserVO;
import com.hyjf.am.vo.admin.WorkFlowVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiehuili on 2019/4/12.
 */
public interface WorkFlowConfigMapper {
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
    List<WorkFlowVO> selectWorkFlowConfigList(WorkFlowConfigRequest adminRequest);

    /**
     * 保存业务流程表
     * @param workFlowVO
     * @return
     */
    int insertWorkFlow(WorkFlowVO workFlowVO);
    /**
     * 修改业务流程表
     * @param workFlowVO
     * @return
     */
    int updateWorkFlow(WorkFlowVO workFlowVO);

    /**
     * 保存业务流程节点表
     * @param flowNodes
     * @return
     */
    int insertWorkFlowNode(@Param("flowNodes")List<WorkFlowNodeVO> flowNodes,@Param("workFlowId")Integer workFlowId);
    /**
     * 查询业务流程详情页面
     * @param id
     * @return
     */
    WorkFlowVO selectWorkFlowConfigInfo(@Param("id")int id);

    /**
     * 根据业务流程中的业务id查询业务流程节点
     * @param businessId
     * @return
     */
    List<WorkFlowNodeVO> selectWorkFlowConfigNode(@Param("businessId")int businessId);
    /**
     *  查询邮件预警通知人
     * @param truename
     * @return
     */
    List<WorkFlowUserVO> selectUser(@Param("truename")String truename);

    /**
     * 查询业务流程节点对应的所有用户
     * @param flowNodes
     * @return
     */
    List<WorkFlowUserVO> selectWorkFlowUser(@Param("flowNodes")List<WorkFlowNodeVO> flowNodes);
    /**
     * 查询每个角色下的一条可用用户
     * @param flowNodes
     * @return
     */
    List<WorkFlowUserVO> selectWorkFlowUserRole(@Param("flowNodes")List<WorkFlowNodeVO> flowNodes);
}
