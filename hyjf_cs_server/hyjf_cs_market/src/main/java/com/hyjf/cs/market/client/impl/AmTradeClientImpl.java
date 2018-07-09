package com.hyjf.cs.market.client.impl;

import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.market.client.AmTradeClient;

import java.util.Set;

/**
 * @author xiasq
 * @version AmTradeClientImpl, v0.1 2018/7/6 11:21
 */
@Cilent
public class AmTradeClientImpl implements AmTradeClient {

    /**
     * 查询投之家每日充值人数、投资人数 、投资金额、首投金额、首投人数、复投人数
     * @param tzjUserIds
     * @return
     */
    @Override
    public TzjDayReportVO queryTradeDataOnToday(Set<Integer> tzjUserIds) {
        return null;
    }

    /**
     * 新充人数 新投人数
     * @param registerUserIds
     * @return
     */
    @Override
    public TzjDayReportVO queryTradeNewDataOnToday(Set<Integer> registerUserIds) {
        return null;
    }
}
