package com.hyjf.am.trade.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BankWithdrawBeanRequest;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;

import java.util.List;

/**
 * @author pangchengchao
 * @version AccountWithdrawService, v0.1 2018/6/11 13:47
 */
public interface AccountWithdrawService {
    void insertAccountWithdrawLog(AccountWithdraw accountWithdraw);

    List<AccountWithdraw> findByOrdId(String ordId);

    AccountWithdraw getAccountWithdrawByOrdId(String logOrderId);

    int updatUserBankWithdrawHandler(BankWithdrawBeanRequest bankRequest);

    void updateAccountWithdrawLog(AccountWithdraw accountwithdraw);
    
    int updateAccountWithdraw(AccountWithdraw accountWithdraw);

	void selectAndUpdateAccountWithdraw(JSONObject pamaMap) throws Exception;

    int getBorrowTender(Integer userId);

    List<AccountRecharge> getTodayRecharge(Integer userId);
}
