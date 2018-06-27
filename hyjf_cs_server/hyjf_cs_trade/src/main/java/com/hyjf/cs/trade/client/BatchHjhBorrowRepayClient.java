/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.CalculateInvestInterestVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BatchHjhBorrowRepayClient, v0.1 2018/6/25 17:40
 */
public interface BatchHjhBorrowRepayClient {

    /**
     *
     * @param accedeOrderId
     * @return
     */
    List<BorrowTenderVO> selectBorrowTenderListByAccedeOrderId(String accedeOrderId);

    /**
     *
     * @param accedeOrderId
     * @return
     */
    List<HjhAccedeVO> selectHjhAccedeListByOrderId(String accedeOrderId);

    /**
     *
     * @param Id
     * @return
     */
    List<HjhAccedeVO> selectHjhAccedeListById(Integer Id);
    /**
     *
     * @param planNid
     * @return
     */
    List<HjhPlanVO> selectHjhPlanListByPlanNid(String planNid);

    /**
     *
     * @return
     */
    Integer updateHjhBorrowRepayInterest(HjhAccedeVO hjhAccedeVO);

    /**
     *
     * @param hjhAccedeVO
     * @return
     */
    Integer updateHjhAccedeByPrimaryKey(HjhAccedeVO hjhAccedeVO);

    /**
     *
     * @param accedeOrderId
     * @return
     */
    List<BorrowRecoverVO> selectBorrowRecoverListByAccedeOrderId(String accedeOrderId);

    /**
     *
     * @param accedeOrderId
     * @return
     */
    List<HjhRepayVO> selectHjhRepayListByAccedeOrderId(String accedeOrderId);

    /**
     *
     * @param Id
     * @return
     */
    HjhRepayVO selectHjhRepayListById(Integer Id);

    /**
     *
     * @param hjhRepayVO
     * @return
     */
    Integer insertHjhBorrowRepay(HjhRepayVO hjhRepayVO);

    /**
     *
     * @param accountVO
     * @return
     */
    Integer updateBankTotalForLockPlan(AccountVO accountVO);

    /**
     *
     * @param hjhRepayVO
     * @return
     */
    Integer updateHjhRepayByPrimaryKey(HjhRepayVO hjhRepayVO);

    /**
     *
     * @param hjhPlanVO
     * @return
     */
    Integer updateHjhPlanByPrimaryKey(HjhPlanVO hjhPlanVO);

    /**
     *
     * @return
     */
    List<CalculateInvestInterestVO> selectCalculateInvestInterest();

    /**
     *
     * @param calculateInvestInterestVO
     * @return
     */
    Integer updateCalculateInvestByPrimaryKey(CalculateInvestInterestVO calculateInvestInterestVO);

}

