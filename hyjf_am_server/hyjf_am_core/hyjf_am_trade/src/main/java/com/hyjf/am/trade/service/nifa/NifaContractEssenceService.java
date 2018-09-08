/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.nifa;

import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.NifaContractEssence;
import com.hyjf.am.trade.dao.model.auto.NifaContractTemplate;
import com.hyjf.am.trade.dao.model.auto.NifaFieldDefinition;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.nifa.NifaContractEssenceVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaContractEssenceService, v0.1 2018/9/6 16:28
 */
public interface NifaContractEssenceService extends BaseService {

    /**
     * 根据合同编号查询合同要素信息
     *
     * @param contractNo
     * @return
     */
    List<NifaContractEssence> selectNifaContractEssenceByContractNo(String contractNo);

    /**
     * 根据合同编号获取合同模版约定条款
     *
     * @param templetId
     * @return
     */
    List<NifaContractTemplate> selectNifaContractTemplateByTemplateNid(String templetId);

    /**
     * 获取最新互金字段定义
     *
     * @return
     */
    List<NifaFieldDefinition> selectNifaFieldDefinition();

    /**
     * 插入合同信息要素表
     *
     * @param nifaContractEssence
     * @return
     */
    Integer insertNifaContractEssence(NifaContractEssence nifaContractEssence);
}
