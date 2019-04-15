package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.customize.WorkFlowConfigMapper;
import com.hyjf.am.config.service.WorkFlowConfigService;
import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import com.hyjf.am.vo.admin.WorkFlowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
