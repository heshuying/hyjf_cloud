/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.BorrowRepay;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.NifaContractStatus;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version LateAndCreditService, v0.1 2018/9/5 11:50
 */
public interface LateAndCreditService extends BaseService {

    /**
     * 检索逾期的还款标的
     *
     * @return
     */
    List<BorrowRepay> selectOverdueBorrowList();

    /**
     * 获取互金表未更新为其他方式还款的完全债转的标的
     *
     * @return
     */
    List<BorrowRecover> selectBorrowRecoverCredit();

    /**
     * 获取用户投资信息
     *
     * @param borrowNid
     * @return
     * @author PC-LIUSHOUYI
     */
    List<BorrowTender> selectBorrowTenderListByBorrowNid(String borrowNid);

    /**
     * 获取还款信息详情
     *
     * @param borrowNid
     * @return
     * @author PC-LIUSHOUYI
     */
    BorrowRepay selectBorrowRepay(String borrowNid);

    NifaContractStatus selectNifaContractStatusByNid(String nid);

    /**
     * 插入合同信息
     *
     * @param thisMessName
     * @param com_social_credit_code
     * @param borrowNid
     * @param contractStatus
     * @param borrowTender
     * @param borrowRepay
     * @return
     */
    boolean insertNifaContractStatusRecord(String thisMessName, String com_social_credit_code, String borrowNid, Integer contractStatus, BorrowTender borrowTender, BorrowRepay borrowRepay);

    /**
     * 互金合同状态更新
     *
     * @param thisMessName
     * @param contractStatus
     * @param borrowRepay
     * @param nifaContractStatusOld
     * @return
     */
    boolean updateNifaContractStatusRecord(String thisMessName, Integer contractStatus, BorrowRepay borrowRepay, NifaContractStatus nifaContractStatusOld);

    /**
     * 根据nid获取投资信息
     *
     * @param nid
     * @return
     */
    BorrowTender selectBorrowTenderByNid(String nid);
}
