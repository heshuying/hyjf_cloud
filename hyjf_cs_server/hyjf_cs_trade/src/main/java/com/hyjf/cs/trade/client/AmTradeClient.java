package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.RtbIncreaseRepayRequest;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;

import java.math.BigDecimal;

/**
 * @author xiasq
 * @version AmTradeClient, v0.1 2018/6/19 15:44
 */
public interface AmTradeClient {
    /**
     * 根据借款编号,还款期数,还款方式取得融通宝待还款金额
     * @param borrowNid
     * @param borrowStyle
     * @param periodNow
     * @return
     */
    BigDecimal selectRtbRepayAmount(String borrowNid, String borrowStyle, Integer periodNow);

    /**
     * 融通宝还款加息
     * @param borrowApicronVO
     * @param account
     * @param companyAccount
     */
    void rtbIncreaseReapy(BorrowApicronVO borrowApicronVO, String account, String companyAccount);
}
