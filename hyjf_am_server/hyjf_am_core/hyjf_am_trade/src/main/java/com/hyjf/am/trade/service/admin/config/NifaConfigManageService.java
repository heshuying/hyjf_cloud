/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.config;

import com.hyjf.am.trade.dao.model.auto.NifaContractTemplate;
import com.hyjf.am.trade.dao.model.auto.NifaFieldDefinition;
import com.hyjf.am.trade.dao.model.customize.FddTempletCustomize;

import java.util.List;

/**
 * @author nxl
 * @version NifaConfigManageService, v0.1 2018/8/15 14:40
 */
public interface NifaConfigManageService {

    /**
     * 添加胡进字段定义
     * @param nifaFieldDefinition
     * @return
     */
    int insertNifaFieldDefinition(NifaFieldDefinition nifaFieldDefinition);

    /**
     * 显示字段定义列表
     * @param limtStart
     * @param limtEnd
     * @return
     */
    List<NifaFieldDefinition> selectNifaFieldDefinition(int limtStart, int limtEnd);

    /**
     * 统计字段定义总数
     * @return
     */
    int countNifaFieldDefinition();
    /**
     * 根据id查找互金字段表
     * @param nifaId
     * @return
     */
    NifaFieldDefinition selectFieldDefinitionById(String nifaId);
    /**
     * 修改互金字段定义
     * @param nifaFieldDefinition
     * @return
     */
    int updateNifaFieldDefinition(NifaFieldDefinition nifaFieldDefinition);

    /**
     * 添加合同模版约定条款表
     * @param nifaContractTemplate
     * @return
     */
    int insertNifaContractTemplate(NifaContractTemplate nifaContractTemplate);

    /**
     * 查找发大大合同id
     * @return
     */
    List<FddTempletCustomize> selectTempletId();

    /**
     * 根据id查找合同模版约定条款表
     * @return
     */
    NifaContractTemplate selelctNifaContractTemplateById(String nifaId);
    /**
     * 修改同模版约定条款表
     * @param nifaContractTemplate
     * @return
     */
    int updateNifaContractTemplate(NifaContractTemplate nifaContractTemplate);
    /**
     * 修改同模版约定条款表
     * @param nifaId
     * @return
     */
    int deleteNifaContractTemplate(int nifaId);
    /**
     * 统计模版约定条款总数
     * @return
     */
    int countNifaContractTemplate();
    /**
     * 显示合同模版约定条款表列表
     * @param limtStart
     * @param limtEnd
     * @return
     */
    List<NifaContractTemplate> selectNifaContractTemplateList(int limtStart, int limtEnd);
}
