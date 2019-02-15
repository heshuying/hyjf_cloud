/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan;
import com.hyjf.am.trade.dao.model.auto.BorrowRepay;
import com.hyjf.am.trade.service.BaseService;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jijun
 * @date 20180630
 */
public interface BorrowRecoverService extends BaseService {


    BorrowRecover selectBorrowRecoverByTenderNid(String tenderAgreementID);

    BorrowRecover selectBorrowRecoverById(Integer id);

    List<BorrowRecover> selectByBorrowNid(String borrowNid);

    int updateBorrowRecover(BorrowRecover borrowRecover);

    /**
     * 根据tenderNid 和bidNid 查询
     * @param tenderNid
     * @param bidNid
     * @return
     */
    BorrowRecover getBorrowRecoverByTenderNidBidNid(String tenderNid, String bidNid);

    /**
     * 根据tenderNid查询
     * @param tenderNid
     * @return
     */
    BorrowRecover getBorrowRecoverByTenderNid(String tenderNid);

    /**
     * 获取borrow_recover_plan更新每次还款时间
     * @param bidNid
     * @param creditTenderNid
     * @param periodNow
     * @return
     */
    BorrowRecoverPlan getPlanByBidTidPeriod(String bidNid, String creditTenderNid, Integer periodNow);
    /**
     * 根据用户id和borrowNid查询
     * @param userId,borrowNid
     * @return
     */
    BorrowRepay getRepayPlanByUserIdAndBorrowNid(Integer userId, String borrowNid);

    /**
     * 根据borrowNid，tenderNid，accedeOrderId查找放款记录
     *
     * @param tenderNid
     * @param borrowNid
     * @return
     */
    BorrowRecover getRecoverDateByTenderNid(String tenderNid, String borrowNid,String accedeOrderId);

    /**
     * 服务费=放款服务费+还款服务费
     * @param borrowNid
     * @return
     */
    BigDecimal selectServiceCostSum(String borrowNid);

}
