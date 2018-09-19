/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.nifa.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.nifa.NifaContractEssenceService;
import com.hyjf.am.vo.trade.nifa.NifaContractEssenceVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaContractEssenceServiceImpl, v0.1 2018/9/6 16:30
 */
@Service
public class NifaContractEssenceServiceImpl extends BaseServiceImpl implements NifaContractEssenceService {

    /**
     * 根据合同编号查询合同要素信息
     *
     * @param contractNo
     * @return
     */
    @Override
    public List<NifaContractEssence> selectNifaContractEssenceByContractNo(String contractNo) {
        NifaContractEssenceExample nifaContractEssenceExample = new NifaContractEssenceExample();
        nifaContractEssenceExample.createCriteria().andContractNoEqualTo(contractNo);
        List<NifaContractEssence> nifaContractEssenceList = this.nifaContractEssenceMapper.selectByExample(nifaContractEssenceExample);
        // 已经生成数据的订单不再重复生成
        if (null != nifaContractEssenceList && nifaContractEssenceList.size() > 0) {
            return nifaContractEssenceList;
        }
        return null;
    }

    /**
     * 根据合同编号获取合同模版约定条款
     *
     * @param templetId
     * @return
     */
    @Override
    public List<NifaContractTemplate> selectNifaContractTemplateByTemplateNid(String templetId) {
        NifaContractTemplateExample nifaContractTemplateExample = new NifaContractTemplateExample();
        NifaContractTemplateExample.Criteria criteria = nifaContractTemplateExample.createCriteria();
        criteria.andTempletNidEqualTo(templetId);
        List<NifaContractTemplate> nifaContractTemplateList = this.nifaContractTemplateMapper.selectByExample(nifaContractTemplateExample);
        if (null != nifaContractTemplateList && nifaContractTemplateList.size() > 0) {
            return nifaContractTemplateList;
        }
        return null;
    }

    @Override
    public List<NifaFieldDefinition> selectNifaFieldDefinition() {
        NifaFieldDefinitionExample nifaFieldDefinitionExample = new NifaFieldDefinitionExample();
        nifaFieldDefinitionExample.setOrderByClause("update_time desc");
        List<NifaFieldDefinition> nifaFieldDefinitionList = this.nifaFieldDefinitionMapper.selectByExample(nifaFieldDefinitionExample);
        if (null != nifaFieldDefinitionList && nifaFieldDefinitionList.size() > 0) {
            return nifaFieldDefinitionList;
        }
        return null;
    }

    /**
     * 插入合同信息要素表
     *
     * @param nifaContractEssence
     * @return
     */
    @Override
    public Integer insertNifaContractEssence(NifaContractEssence nifaContractEssence) {
        return this.nifaContractEssenceMapper.insert(nifaContractEssence);
    }
}
