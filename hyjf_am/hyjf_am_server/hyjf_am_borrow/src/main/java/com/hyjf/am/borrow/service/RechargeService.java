package com.hyjf.am.borrow.service;

import com.hyjf.am.borrow.dao.model.auto.*;
import com.hyjf.am.vo.borrow.AccountListVO;
import com.hyjf.am.vo.borrow.AccountRechargeVO;
import com.hyjf.am.vo.borrow.AccountVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * 用户充值Service
 * 
 * @author
 *
 */
public interface RechargeService {

    int selectByOrdId(String ordId);

    int insertSelective(BankCallBean bean, BankCard bankCard);

	 AccountRecharge selectByExample(AccountRechargeExample example);

	 int updateByExampleSelective(AccountRechargeVO accountRecharge,AccountRechargeExample accountRechargeExample);

	 int updateBankRechargeSuccess(AccountVO newAccount);

	 int insertSelective(AccountListVO accountList);

	 void updateByPrimaryKeySelective(AccountRechargeVO accountRecharge);

	boolean updateBanks(AccountRechargeVO accountRecharge, BankCallBean bean, String ip);
}
