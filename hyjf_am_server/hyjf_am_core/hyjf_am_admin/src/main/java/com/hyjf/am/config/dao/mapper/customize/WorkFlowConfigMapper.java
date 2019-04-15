package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import com.hyjf.am.vo.admin.WorkFlowVO;

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
}
