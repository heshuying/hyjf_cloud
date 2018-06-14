package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.AccountListVO;

/**
 * @author pangchengchao
 * @version AmAccountListClient, v0.1 2018/6/11 17:26
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
public interface AmAccountListClient {
    AccountListVO selectAccountListByOrdId(String ordId, String type);

    int countAccountListByOrdId(String ordId, String type);
}
