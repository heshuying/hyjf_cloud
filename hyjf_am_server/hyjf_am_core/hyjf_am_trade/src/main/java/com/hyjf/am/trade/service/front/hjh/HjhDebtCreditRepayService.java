package com.hyjf.am.trade.service.front.hjh;

import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditRepay;

import java.util.List;

/**
 * @author hesy
 * @version HjhDebtCreditRepayService, v0.1 2018/7/4 18:53
 */
public interface HjhDebtCreditRepayService {
    List<HjhDebtCreditRepay> selectHjhDebtCreditRepay(String borrowNid, String tenderOrderId, int periodNow, int status);
}
