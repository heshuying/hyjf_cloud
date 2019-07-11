/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 资金服务: BaseService
 * @author liuyang
 * @version BaseService, v0.1 2018/6/27 9:32
 */
public interface BaseService {
    /**
     * 根据标的编号检索标的借款信息
     * @param borrowNid
     * @return
     */
    Borrow getBorrow(String borrowNid);

    /**
     * 根据标的编号检索标的借款详情
     * @param borrowNid
     * @return
     */
    BorrowInfo getBorrowInfoByNid(String borrowNid);

    /**
     * 根据标的编号检索标的借款信息(主库)
     * @param borrowNid
     * @return
     */
    Borrow doGetBorrow(String borrowNid);

    /**
     * 根据标的编号检索标的借款详情(主库)
     * @param borrowNid
     * @return
     */
    BorrowInfo doGetBorrowInfoByNid(String borrowNid);

    BorrowAndInfoVO getBorrowAndInfoByNid(String borrowNid);

    /**
     * 获取用户的账户信息
     * @param userId
     * @return
     */
    Account getAccount(Integer userId);

    /**
     * 取得本库冗余的用户信息
     * @param userId
     * @return
     */
    RUser getRUser(Integer userId);

    /**
     * 取得本库冗余的用户信息
     * @param userName
     * @return
     */
    RUser getRUser(String userName);

    /**
     * 取得本库冗余的推荐人信息
     * @param userId
     * @return
     */
    RUser getRefUser(Integer userId);

    /**
     * 汇计划全部流程用更新用户的账户表
     * @param hjhProcessFlg
     * @param userId
     * @param amount
     * @param interest
     * @return
     */
    public Boolean updateAccountForHjh(String hjhProcessFlg, Integer userId, BigDecimal amount, BigDecimal interest) ;

    /**
     * 汇计划重算更新汇计划加入明细表
     * @param hjhProcessFlg
     * @param id
     * @param amount
     * @param interest
     * @return
     */
    public Boolean updateHjhAccedeForHjh(String hjhProcessFlg, Integer id, BigDecimal amount, BigDecimal interest, BigDecimal serviceFee) ;

    BorrowRepay getBorrowRepay(String borrowNid);

    List<BorrowRecover> getBorrowRecover(String borrowNid);

    BorrowRecover getBorrowRecoverByNid(String nid);

    List<BorrowRecoverPlan> getBorrowRecoverPlan(String borrowNid, int period);

    BorrowRepayPlan getRepayPlan(String borrowNid, int period);

    CreditTender getCreditTender(String assignNid);

    HjhDebtCreditTender getHjhDebtCreditTender(String assignNid);

    /**
     * 获取系统配置
     * @param configCd
     * @return
     */
    String getBorrowConfig(String configCd);

    /**
     * 根据电子账号查询用户在江西银行的可用余额
     * @param accountId
     * @return
     */
    BigDecimal getBankBalance(Integer userId, String accountId);

    /**
     * 根据项目编号查询资产信息
     *
     * @param borrowNid
     * @return
     */
    HjhPlanAsset selectHjhPlanAssetByBorrowNid(String borrowNid);

    /**
     * 根据借款机构编号和借款类型获取该机构流程配置
     *
     * @param instCode
     * @param assetType
     * @return
     */
    HjhAssetBorrowtype selectAssetBorrowType(String instCode, int assetType);

    /**
     *  判断是否属于线下充值类型.
     * @param tranType
     * @return
     * @Author : huanghui
     */
    boolean getIsRechargeTransType(String tranType);

}
