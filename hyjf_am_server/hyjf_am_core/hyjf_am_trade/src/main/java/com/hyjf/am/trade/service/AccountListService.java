package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.AccountList;

/**
 * @author jijun
 * @version AccountService, v0.1 2018/06/16
 */
public interface AccountListService {

	int addAccountList(AccountList convertBean);

    AccountList countAccountListByOrdId(String ordId, String type);

    AccountList countAccountListByOrderId(String orderId);

    int insertAccountList(AccountList accountList);
}
