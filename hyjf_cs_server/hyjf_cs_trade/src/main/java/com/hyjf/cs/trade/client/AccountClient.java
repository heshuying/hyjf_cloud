package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.account.AccountVO;

/**
 * 账户client
 * @author zhangyk
 * @date 2018/6/25 14:24
 */
public interface AccountClient {

    AccountVO getAccountByUserId(Integer userId);
}
