/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.NifaFieldDefinitionResponse;
import com.hyjf.am.resquest.admin.NifaFieldDefinitionAddRequest;
import com.hyjf.am.resquest.user.NifaFieldDefinitionRequest;

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
}
