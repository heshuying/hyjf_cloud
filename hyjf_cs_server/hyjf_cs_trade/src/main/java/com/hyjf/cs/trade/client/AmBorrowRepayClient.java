/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version AmBorrowRepayClient, v0.1 2018/6/22 18:43
 */
public interface AmBorrowRepayClient {

    /**
     * 检索borrowRecover数据
     *
     * @param borrowNid
     * @param repaySmsReminder
     * @return
     */
    List<BorrowRepayVO> selectBorrowRepayList(String borrowNid, Integer repaySmsReminder);

    /**
     * 更新borrowRepay
     *
     * @param borrowRepayVO
     * @return
     */
    Integer updateBorrowRepay(BorrowRepayVO borrowRepayVO);


    /**
     * 根据borrowNid 获取还款信息
     * @author zhangyk
     * @date 2018/6/30 14:01
     */
    List<BorrowRepayVO> getBorrowRepayList(String borrowNid);

    BorrowRepayVO getBorrowRepay(String borrowNid);
}
