package com.hyjf.cs.user.client;

import com.hyjf.am.vo.trade.account.AccountVO;

/**
 * 账户client
 * @author jijun
 * @date 20180703
 */
public interface AmAccountClient {

    AccountVO getAccountByUserId(Integer userId);


}
