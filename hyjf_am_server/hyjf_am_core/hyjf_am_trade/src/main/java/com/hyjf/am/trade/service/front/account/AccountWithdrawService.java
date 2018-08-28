package com.hyjf.am.trade.service.front.account;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.WithdrawBeanRequest;
import com.hyjf.am.resquest.trade.BankWithdrawBeanRequest;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;
import com.hyjf.am.trade.dao.model.customize.WithdrawCustomize;

import java.util.List;

/**
 * @author pangchengchao
 * @version AccountWithdrawService, v0.1 2018/6/11 13:47
 */
public interface AccountWithdrawService {
    int insertAccountWithdrawLog(AccountWithdraw accountWithdraw);

    List<AccountWithdraw> findByOrdId(String ordId);

    AccountWithdraw getAccountWithdrawByOrdId(String logOrderId);

    int updatUserBankWithdrawHandler(BankWithdrawBeanRequest bankRequest);

    Integer updateAccountWithdrawLog(AccountWithdraw accountwithdraw);
    
    int updateAccountWithdraw(AccountWithdraw accountWithdraw);

    boolean selectAndUpdateAccountWithdraw(JSONObject pamaMap) throws Exception;

    int getBorrowTender(Integer userId);

    List<AccountRecharge> getTodayRecharge(Integer userId);

    int getWithdrawRecordCount(WithdrawBeanRequest request);

    List<WithdrawCustomize> getWithdrawRecordList(WithdrawBeanRequest request);

}
