package com.hyjf.wbs.client;

import com.hyjf.am.vo.trade.account.AccountVO;

/**
 * @author cui
 * @version AmTradeClient, v0.1 2019/4/17 17:00
 */
public interface AmTradeClient {

    AccountVO getAccount(Integer userId);

}
