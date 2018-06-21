package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.SynBalanceBeanRequest;

/**
 * @author pangchengchao
 * @version SynBalanceClient, v0.1 2018/6/20 9:40
 */
public interface SynBalanceClient {
    boolean insertAccountDetails(SynBalanceBeanRequest synBalanceBeanRequest);
}
