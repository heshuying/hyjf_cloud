package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.user.BankWithdrawBeanRequest;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version WithdrawClient, v0.1 2018/6/13 11:18
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
public interface WithdrawClient {
    List<AccountWithdrawVO> selectAccountWithdrawByOrdId(String ordId);

    void insertAccountWithdrawLog(AccountWithdrawVO record);

    AccountWithdrawVO getAccountWithdrawByOrdId(String logOrderId);

    int updateAccountwithdrawLog(AccountWithdrawVO accountwithdraw);

    int updatUserBankWithdrawHandler(BankWithdrawBeanRequest bankWithdrawBeanRequest);
}
