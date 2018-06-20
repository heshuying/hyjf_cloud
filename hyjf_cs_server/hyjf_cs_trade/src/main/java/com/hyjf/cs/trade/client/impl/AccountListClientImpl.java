package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.cs.trade.client.AccountListClient;
import org.springframework.stereotype.Service;

/**
 * @author xiasq
 * @version AccountListClientImpl, v0.1 2018/6/20 16:33
 */
@Service
public class AccountListClientImpl implements AccountListClient {
    @Override
    public AccountListVO selectAccountListByOrdId(String ordId, String type) {
        return null;
    }

    @Override
    public int countAccountListByOrdId(String ordId, String type) {
        return 0;
    }
}
