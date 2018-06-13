package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.dao.model.auto.AccountRechargeExample;
import com.hyjf.am.vo.trade.AccountRechargeVO;

/**
 * 用户充值Service
 * 
 * @author
 *
 */
public interface RechargeService {

    int selectByOrdId(String ordId);

    int insertSelective(BankRequest bankRequest);

	 AccountRecharge selectByExample(AccountRechargeExample example);

	 int updateByExampleSelective(AccountRecharge accountRecharge, AccountRechargeExample accountRechargeExample);

	 int updateBankRechargeSuccess(Account newAccount);

	 int insertSelective(AccountList accountList);

	 void updateByPrimaryKeySelective(AccountRecharge accountRecharge);

	boolean updateBanks(AccountRechargeVO accountRecharge, String ip);
}
