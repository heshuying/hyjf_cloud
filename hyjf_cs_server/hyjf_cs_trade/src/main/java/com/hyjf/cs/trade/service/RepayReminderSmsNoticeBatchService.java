/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;

import java.util.List;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version RepayReminderSmsNoticeBatchService, v0.1 2018/6/22 10:56
 */
public interface RepayReminderSmsNoticeBatchService extends BaseTradeService {

    /**
     * 检索正在还款中的标的
     * @return
     */
    List<BorrowVO> selectBorrowList();

    /**
     * 根据标的编号检索还款计划
     * @param borrowNid
     * @param repaySmsReminder
     * @return
     */
    List<BorrowRepayPlanVO> selectBorrowRepayPlan(String borrowNid , Integer repaySmsReminder);

    /**
     * 发送短信
     *
     * @param msgList
     * @param temp
     */
    void sendSms(List<Map<String, String>> msgList, String temp);

    /**
     * 获取BorrowRepay数据
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
     * @param i
     * @return
     */
    Integer updateBorrowRepay(BorrowRepayVO borrowRepayVO, int i);

    /**
     * 短信发送后更新borrowRecoverPlan
     *
     * @param borrowRepayPlanVO
     * @return
     */
    Integer updateBorrowRepayPlan(BorrowRepayPlanVO borrowRepayPlanVO, Integer repaySmsReminder);
}
