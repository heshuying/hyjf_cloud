package com.hyjf.cs.market.client;

import com.hyjf.am.vo.datacollect.TzjDayReportVO;

import java.util.Set; /**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/7/6 11:04
 */
public interface AmTradeClient {

    /**
     * 查询投之家每日充值人数、投资人数 、投资金额、首投金额、首投人数、复投人数
     * @param tzjUserIds
     * @return
     */
    TzjDayReportVO queryTradeDataOnToday(Set<Integer> tzjUserIds);

    /**
     * 新充人数 新投人数
     * @param registerUserIds
     * @return
     */
    TzjDayReportVO queryTradeNewDataOnToday(Set<Integer> registerUserIds);
}
