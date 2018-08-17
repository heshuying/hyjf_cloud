/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.NifaContractTemplateResponse;
import com.hyjf.am.response.admin.NifaFieldDefinitionResponse;
import com.hyjf.am.response.trade.FddTempletResponse;
import com.hyjf.am.resquest.admin.NifaContractTemplateAddRequest;
import com.hyjf.am.resquest.admin.NifaContractTemplateRequest;
import com.hyjf.am.resquest.admin.NifaFieldDefinitionAddRequest;
import com.hyjf.am.resquest.admin.NifaFieldDefinitionRequest;


/**
 * @author nxl
 * @version NifaConfigService, v0.1 2018/8/15 17:38
 */
public interface NifaConfigService {

    /**
     * 添加字段定义
     * @param nifaFieldDefinitionAddRequest
     * @return
     */
    Boolean insertNifaFieldDefinition(NifaFieldDefinitionAddRequest nifaFieldDefinitionAddRequest);
    /**
     * 查找互金字段定义列表
     * @param request
     * @return
     */
    NifaFieldDefinitionResponse selectFieldDefinitionList(NifaFieldDefinitionRequest request);

    /**
     * 根据id查找互金字段表
     * @param nifaId
     * @return
     */
    NifaFieldDefinitionResponse selectFieldDefinitionById(String nifaId);
    /**
     * 修改字段定义
     * @param nifaFieldDefinitionAddRequest
     * @return
     */
    Boolean updateNifaFieldDefinition(NifaFieldDefinitionAddRequest nifaFieldDefinitionAddRequest);

    /**
     * 添加合同模版约定条款表
     * @param request
     * @return
     */
    Boolean insertNifaContractTemplate(NifaContractTemplateAddRequest request);
    /**
     * 查找合同模板id
     * @return
     */
    FddTempletResponse selectFddTempletId();
    /**
     * 修改合同模版约定条款表
     * @param request
     * @return
     * @auth nxl
     */
    Boolean updateNifaContractTemplate(NifaContractTemplateAddRequest request);
    /**
     * 根据id查找合同模版约定条款表
     * @param nifaId
     * @auth nxl
     * @return
     */
    NifaContractTemplateResponse selectNifaContractTemplateById(String nifaId);
    /**
     * 根据id删除合同模版约定条款表
     * @param nifaId
     * @auth nxl
     * @return
     */
    Boolean deleteNifaContractTemplateById(int nifaId);
    /**
     * 查找互金字段定义列表
     * @param request
     * @return
     * @auth nxl
     */
    NifaContractTemplateResponse selectNifaContractTemplateList(NifaContractTemplateRequest request);
}
