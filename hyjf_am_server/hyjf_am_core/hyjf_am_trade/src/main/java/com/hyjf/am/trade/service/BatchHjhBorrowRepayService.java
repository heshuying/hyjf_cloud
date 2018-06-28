/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.vo.trade.CalculateInvestInterestVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BatchHjhBorrowRepayService, v0.1 2018/6/27 9:21
 */
public interface BatchHjhBorrowRepayService{

    /**
     * 根据订单号获取投资表状态为0初始的数据
     *
     * @param accedeOrderId
     * @return
     */
    List<BorrowTender> selectBorrowTenderListByAccedeOrderId(String accedeOrderId);

    /**
     * 根据订单号查询汇计划加入明细
     *
     * @param accedeOrderId
     * @return
     */
    List<HjhAccede> selectHjhAccedeListByOrderId(String accedeOrderId);

    /**
     * 更新待收收益
     *
     * @param hjhAccedeVO
     * @return
     */
    Integer updateHjhBorrowRepayInterest(HjhAccedeVO hjhAccedeVO);

    /**
     * 根据计划编号获取计划详情
     * @param planNid
     * @return
     */
    List<HjhPlan> selectHjhPlanListByPlanNid(String planNid);

    /**
     * 获得更新后的计划订单信息
     * @param id
     * @return
     */
    List<HjhAccede> selectHjhAccedeListById(Integer id);

    /**
     * 计划退出更新计划加入明细
     * @param hjhAccedeVO
     * @return
     */
    Integer updateHjhAccedeByPrimaryKey(HjhAccedeVO hjhAccedeVO);

    /**
     * 根据订单号查询放款信息
     * @param accedeOrderId
     * @return
     */
    List<BorrowRecover> selectBorrowRecoverListByAccedeOrderId(String accedeOrderId);

    /**
     * 根据订单号查询还款信息
     * @param accedeOrderId
     * @return
     */
    List<HjhRepay> selectHjhRepayListByAccedeOrderId(String accedeOrderId);

    /**
     * 汇计划还款信息表插入
     * @param hjhRepayVO
     * @return
     */
    Integer insertHjhBorrowRepay(HjhRepayVO hjhRepayVO);

    /**
     * 进入锁定期后 修改账户总资产 待收收益
     * @param accountVO
     * @return
     */
    Integer updateBankTotalForLockPlan(AccountVO accountVO);

    /**
     * 汇计划退出还款信息更新
     * @param hjhRepayVO
     * @return
     */
    Integer updateHjhRepayByPrimaryKey(HjhRepayVO hjhRepayVO);

    /**
     * 根据ID获取汇计划还款信息
     * @param id
     * @return
     */
    HjhRepay selectHjhRepayListById(Integer id);

    /**
     * 获取为用户赚取金额数据
     * @return
     */
    List<CalculateInvestInterest> selectCalculateInvestInterest();

    /**
     * 更新为用户赚取金额数据
     * @param calculateInvestInterestVO
     * @return
     */
    Integer updateCalculateInvestByPrimaryKey(CalculateInvestInterestVO calculateInvestInterestVO);

}
