package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.NifaConfigService;
import com.hyjf.am.response.admin.NifaContractTemplateResponse;
import com.hyjf.am.response.admin.NifaFieldDefinitionResponse;
import com.hyjf.am.response.trade.FddTempletResponse;
import com.hyjf.am.resquest.admin.NifaContractTemplateAddRequest;
import com.hyjf.am.resquest.admin.NifaFieldDefinitionAddRequest;
import com.hyjf.am.resquest.config.NifaContractTemplateRequest;
import com.hyjf.am.resquest.config.NifaFieldDefinitionRequest;
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

    /**
     * 添加合同模版约定条款表
     * @param request
     * @return
     */
    @Override
    public Boolean insertNifaContractTemplate(NifaContractTemplateAddRequest request){
        return amTradeClient.insertNifaContractTemplate(request);
    }
    /**
     * 查找合同模板id
     * @return
     */
    @Override
    public FddTempletResponse selectFddTempletId(){
        return amTradeClient.selectFddTempletId();
    }
    /**
     * 修改合同模版约定条款表
     * @param request
     * @return
     * @auth nxl
     */
    @Override
    public Boolean updateNifaContractTemplate(NifaContractTemplateAddRequest request){
        return amTradeClient.updateNifaContractTemplate(request);
    }
    /**
     * 根据id查找合同模版约定条款表
     * @param nifaId
     * @auth nxl
     * @return
     */
    @Override
    public NifaContractTemplateResponse selectNifaContractTemplateById(String nifaId){
        return amTradeClient.selectNifaContractTemplateById(nifaId);
    }
    /**
     * 根据id删除合同模版约定条款表
     * @param nifaId
     * @auth nxl
     * @return
     */
    @Override
    public Boolean deleteNifaContractTemplateById(int nifaId){
        return amTradeClient.deleteNifaContractTemplateById(nifaId);
    }
    /**
     * 查找互金字段定义列表
     * @param request
     * @return
     * @auth nxl
     */
    @Override
    public  NifaContractTemplateResponse selectNifaContractTemplateList(NifaContractTemplateRequest request){
        return amTradeClient.selectNifaContractTemplateList(request);
    }
}
