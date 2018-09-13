/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.impl;

import com.hyjf.am.vo.admin.NifaContractTemplateVO;
import com.hyjf.am.vo.admin.NifaFieldDefinitionVO;
import com.hyjf.am.vo.config.SiteSettingsVO;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.nifa.NifaContractEssenceVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.service.consumer.NifaContractEssenceMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaContractEssenceMessageServiceImpl, v0.1 2018/9/6 15:16
 */
@Service
public class NifaContractEssenceMessageServiceImpl extends BaseServiceImpl implements NifaContractEssenceMessageService {

    @Autowired
    AmConfigClient amConfigClient;
    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    AmUserClient amUserClient;

    /**
     * 通过网站设置获取公司信息
     *
     * @return
     */
    @Override
    public SiteSettingsVO selectSiteSetting() {
        return amConfigClient.selectSiteSetting();
    }

    /**
     * 获取标的借款详情
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowAndInfoVO selectBorrowByBorrowNid(String borrowNid) {
        return amTradeClient.getBorrowByNid(borrowNid);
    }

    /**
     * 获取标的借款详情明细
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowInfoVO selectBorrowInfoByBorrowNid(String borrowNid) {
        return amTradeClient.getBorrowInfoByNid(borrowNid);
    }

    /**
     * 根据放款编号获取该标的的投资信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowTenderVO> selectTenderListByBorrowNid(String borrowNid) {
        return amTradeClient.getBorrowTenderListByBorrowNid(borrowNid);
    }

    /**
     * 根据合同编号查询合同要素信息
     *
     * @param contractNo
     * @return
     */
    @Override
    public List<NifaContractEssenceVO> selectNifaContractEssenceByContractNo(String contractNo) {
        return amTradeClient.selectNifaContractEssenceByContractNo(contractNo);
    }

    /**
     * 获取投资人详情
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfoVO getUsersInfoByUserId(Integer userId) {
        return amUserClient.findUsersInfoById(userId);
    }

    /**
     * 获取最新合同模板
     *
     * @return
     */
    @Override
    public FddTempletVO selectFddTemplet() {
        List<FddTempletVO> voList = amTradeClient.getFddTempletList(FddGenerateContractConstant.PROTOCOL_TYPE_TENDER);
        if (null != voList && voList.size() > 0) {
            return voList.get(0);
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
    public NifaContractTemplateVO selectNifaContractTemplateByTemplateNid(String templetId) {
        List<NifaContractTemplateVO> voList = amTradeClient.selectNifaContractTemplateByTemplateNid(templetId);
        if (null != voList && voList.size() > 0) {
            return voList.get(0);
        }
        return null;
    }

    /**
     * 获取最新互金字段定义
     *
     * @return
     */
    @Override
    public NifaFieldDefinitionVO selectNifaFieldDefinition() {
        List<NifaFieldDefinitionVO> voList = amTradeClient.selectNifaFieldDefinition();
        if (null != voList && voList.size() > 0) {
            return voList.get(0);
        }
        return null;
    }

    /**
     * 获取还款计算公式
     *
     * @param borrowStyle
     * @return
     */
    @Override
    public BorrowStyleVO selectBorrowStyleWithBLOBs(String borrowStyle) {
        List<BorrowStyleVO> voList = amTradeClient.selectBorrowStyleWithBLOBs(borrowStyle);
        if (null != voList && voList.size() > 0) {
            return voList.get(0);
        }
        return null;
    }

    /**
     * 根据借款编号获取借款人公司信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowUserVO selectBorrowUsersByBorrowNid(String borrowNid) {
        return amTradeClient.getBorrowUser(borrowNid);
    }

    /**
     * 根据借款编号获取借款人信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowManinfoVO selectBorrowMainfo(String borrowNid) {
        return amTradeClient.getBorrowManinfo(borrowNid);
    }

    /**
     * 获取用户投资订单还款详情
     *
     * @param nid
     * @return
     */
    @Override
    public List<BorrowRecoverPlanVO> selectBorrowRecoverPlanList(String nid) {
        return amTradeClient.selectBorrowRecoverPlanList(nid);
    }

    /**
     * 获取用户投资订单还款计划(到期还款)
     *
     * @param nid
     * @return
     */
    @Override
    public BorrowRecoverVO selectBorrowRecover(String nid) {
        return amTradeClient.selectBorrowRecoverByNid(nid);
    }

    /**
     * 插入合同信息要素表
     *
     * @param nifaContractEssenceVO
     * @return
     */
    @Override
    public Integer insertNifaContractEssence(NifaContractEssenceVO nifaContractEssenceVO) {
        return amTradeClient.insertNifaContractEssence(nifaContractEssenceVO);
    }
}
