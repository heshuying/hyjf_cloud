package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.account.AccountListVO;

/**
 * @author pangchengchao
 * @version AccountListClient, v0.1 2018/6/11 17:26
 */
public interface AccountListClient {
    AccountListVO selectAccountListByOrdId(String ordId, String type);

    int countAccountListByOrdId(String ordId, String type);

    Integer insertAccountListSelective(AccountListVO accountListVO);
}
