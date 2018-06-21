package com.hyjf.am.trade.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BankWithdrawBeanRequest;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.dao.model.auto.Accountwithdraw;

import java.util.List;

/**
 * @author pangchengchao
 * @version AccountWithdrawService, v0.1 2018/6/11 13:47
 */
public interface AccountWithdrawService {
    void insertAccountWithdrawLog(Accountwithdraw accountWithdraw);

    List<Accountwithdraw> findByOrdId(String ordId);

    Accountwithdraw getAccountWithdrawByOrdId(String logOrderId);

    int updatUserBankWithdrawHandler(BankWithdrawBeanRequest bankRequest);

    void updateAccountwithdrawLog(Accountwithdraw accountwithdraw);
    
    int updateAccountwithdraw(Accountwithdraw accountWithdraw);

	void selectAndUpdateAccountWithdraw(JSONObject pamaMap) throws Exception;

    int getBorrowTender(Integer userId);

    List<AccountRecharge> getTodayRecharge(Integer userId);
}
