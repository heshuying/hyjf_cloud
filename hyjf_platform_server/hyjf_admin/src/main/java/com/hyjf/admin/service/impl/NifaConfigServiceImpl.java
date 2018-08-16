package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.NifaConfigService;
import com.hyjf.am.response.admin.NifaFieldDefinitionResponse;
import com.hyjf.am.resquest.admin.NifaFieldDefinitionAddRequest;
import com.hyjf.am.resquest.user.NifaFieldDefinitionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author nxl
 * @version NifaConfigServiceImpl, v0.1 2018/8/15 17:38
 */
@Service
public class NifaConfigServiceImpl extends BaseServiceImpl implements NifaConfigService {
    @Autowired
    private AmTradeClient amTradeClient;
    /**
     * 添加字段定义
     *
     * @param nifaFieldDefinitionAddRequest
     * @return
     */
    @Override
    public Boolean insertNifaFieldDefinition(NifaFieldDefinitionAddRequest nifaFieldDefinitionAddRequest) {
        return amTradeClient.insertNifaFieldDefinition(nifaFieldDefinitionAddRequest);
    }
    /**
     * 查找互金字段定义列表
     * @param request
     * @return
     */
    @Override
    public NifaFieldDefinitionResponse selectFieldDefinitionList(NifaFieldDefinitionRequest request){
        return amTradeClient.selectFieldDefinitionList(request);
    }
    /**
     * 根据id查找互金字段表
     * @param nifaId
     * @return
     */
    @Override
    public NifaFieldDefinitionResponse selectFieldDefinitionById(String nifaId){
        return amTradeClient.selectFieldDefinitionById(nifaId);
    }
    /**
     * 修改字段定义
     * @param nifaFieldDefinitionAddRequest
     * @return
     */
    @Override
    public Boolean updateNifaFieldDefinition(NifaFieldDefinitionAddRequest nifaFieldDefinitionAddRequest){
        return  amTradeClient.updateNifaFieldDefinition(nifaFieldDefinitionAddRequest);
    }
}
