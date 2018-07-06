package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.hjh.HjhDebtCreditRepayVO;

import java.util.List;

/**
 * @author hesy
 * @version HjhDebtCreditRepayClient, v0.1 2018/7/4 19:09
 */
public interface HjhDebtCreditRepayClient {
    List<HjhDebtCreditRepayVO> selectHjhDebtCreditRepay(String borrowNid, String tenderOrderId, int periodNow, int status);
}
