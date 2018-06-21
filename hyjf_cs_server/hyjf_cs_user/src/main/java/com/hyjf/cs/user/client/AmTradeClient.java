/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

/**
 * @author zhangqingqing
 * @version AmTradeClient, v0.1 2018/6/20 12:46
 */
public interface AmTradeClient {

    HjhInstConfigVO selectInstConfigByInstCode(String instCode);

    AccountVO getAccount(Integer userId);
}
