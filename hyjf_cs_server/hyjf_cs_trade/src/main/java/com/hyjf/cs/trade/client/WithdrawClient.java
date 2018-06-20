package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.BankWithdrawBeanRequest;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version WithdrawClient, v0.1 2018/6/13 11:18
 */
public interface WithdrawClient {
    List<AccountWithdrawVO> selectAccountWithdrawByOrdId(String ordId);

    void insertAccountWithdrawLog(AccountWithdrawVO record);

    AccountWithdrawVO getAccountWithdrawByOrdId(String logOrderId);

    int updateAccountwithdrawLog(AccountWithdrawVO accountwithdraw);

    int updatUserBankWithdrawHandler(BankWithdrawBeanRequest bankWithdrawBeanRequest);

    int getBorrowTender(Integer userId);

    List<AccountRechargeVO> getTodayRecharge(Integer userId);
}
