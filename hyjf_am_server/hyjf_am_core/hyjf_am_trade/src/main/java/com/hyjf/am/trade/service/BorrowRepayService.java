/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.BorrowRepay;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayService, v0.1 2018/6/23 11:38
 */
public interface BorrowRepayService {

    /**
     * 检索正在还款中的标的
     * @return
     */
    List<BorrowRepay> selectBorrowRepayList(String borrowNid,Integer repaySmsReminder);

    /**
     * 更新标的还款记录（借款人）总表
     * @param borrowRepay
     * @return
     */
    Integer updateBorrowRepay(BorrowRepay borrowRepay);
}
