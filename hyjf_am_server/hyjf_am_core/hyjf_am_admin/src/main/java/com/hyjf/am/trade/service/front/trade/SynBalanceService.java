package com.hyjf.am.trade.service.front.trade;

import com.hyjf.am.resquest.trade.SynBalanceBeanRequest;

/**
 * @author pangchengchao
 * @version SynBalanceService, v0.1 2018/6/20 9:58
 */
public interface SynBalanceService {
    boolean insertAccountDetails(SynBalanceBeanRequest synBalanceBeanRequest);
}
