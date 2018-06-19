package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.cs.trade.client.AmAccountListClient;
import org.springframework.stereotype.Service;

/**
 * @author pangchengchao
 * @version AmAccountListClientImpl, v0.1 2018/6/19 10:56
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
@Service
public class AmAccountListClientImpl implements AmAccountListClient {
    @Override
    public AccountListVO selectAccountListByOrdId(String ordId, String type) {
        return null;
    }

    @Override
    public int countAccountListByOrdId(String ordId, String type) {
        return 0;
    }
}
