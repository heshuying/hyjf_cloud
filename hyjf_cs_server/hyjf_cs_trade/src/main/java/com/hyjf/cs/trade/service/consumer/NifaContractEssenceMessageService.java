/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer;

import com.hyjf.am.vo.admin.NifaContractTemplateVO;
import com.hyjf.am.vo.admin.NifaFieldDefinitionVO;
import com.hyjf.am.vo.config.SiteSettingsVO;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.nifa.NifaContractEssenceVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.common.service.BaseService;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaContractEssenceMessageService, v0.1 2018/9/6 15:15
 */
public interface NifaContractEssenceMessageService extends BaseService {

    /**
     * 通过网站设置获取公司信息
     *
     * @return
     */
    SiteSettingsVO selectSiteSetting();

    /**
     * 获取标的借款详情
     *
     * @param borrowNid
     * @return
     */
    BorrowAndInfoVO selectBorrowByBorrowNid(String borrowNid);

    /**
     * 获取标的借款详情明细
     *
     * @param borrowNid
     * @return
     */
    BorrowInfoVO selectBorrowInfoByBorrowNid(String borrowNid);

    /**
     * 根据放款编号获取该标的的投资信息
     *
     * @param borrowNid
     * @return
     */
    List<BorrowTenderVO> selectTenderListByBorrowNid(String borrowNid);

    /**
     * 根据合同编号查询合同要素信息
     *
     * @param contractNo
     * @return
     */
    List<NifaContractEssenceVO> selectNifaContractEssenceByContractNo(String contractNo);

    /**
     * 获取投资人详情
     *
     * @param userId
     * @return
     */
    UserInfoVO getUsersInfoByUserId(Integer userId);

    /**
     * 获取最新合同模板
     *
     * @return
     */
    FddTempletVO selectFddTemplet();

    /**
     * 根据合同编号获取合同模版约定条款
     *
     * @param templetId
     * @return
     */
    NifaContractTemplateVO selectNifaContractTemplateByTemplateNid(String templetId);

    /**
     * 获取最新互金字段定义
     *
     * @return
     */
    NifaFieldDefinitionVO selectNifaFieldDefinition();

    /**
     * 获取还款计算公式
     *
     * @param borrowStyle
     * @return
     */
    BorrowStyleVO selectBorrowStyleWithBLOBs(String borrowStyle);

    /**
     * 根据借款编号获取借款人公司信息
     *
     * @param borrowNid
     * @return
     */
    BorrowUserVO selectBorrowUsersByBorrowNid(String borrowNid);

    /**
     * 根据借款编号获取借款人信息
     *
     * @param borrowNid
     * @return
     */
    BorrowManinfoVO selectBorrowMainfo(String borrowNid);

    /**
     * 获取用户投资订单还款详情
     *
     * @param nid
     * @return
     */
    List<BorrowRecoverPlanVO> selectBorrowRecoverPlanList(String nid);

    /**
     * 获取用户投资订单还款计划(到期还款)
     *
     * @param nid
     * @return
     */
    BorrowRecoverVO selectBorrowRecover(String nid);

    /**
     * 插入合同信息要素表
     *
     * @param nifaContractEssenceVO
     * @return
     */
    Integer insertNifaContractEssence(NifaContractEssenceVO nifaContractEssenceVO);
}
