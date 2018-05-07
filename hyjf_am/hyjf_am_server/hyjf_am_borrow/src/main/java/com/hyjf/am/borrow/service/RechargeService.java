package com.hyjf.am.borrow.service;

import com.hyjf.am.borrow.dao.model.auto.Account;
import com.hyjf.am.borrow.dao.model.auto.AccountList;
import com.hyjf.am.borrow.dao.model.auto.AccountRecharge;
import com.hyjf.am.borrow.dao.model.auto.AccountRechargeExample;
import com.hyjf.am.borrow.dao.model.auto.BankCard;
import com.hyjf.am.borrow.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.borrow.dao.model.auto.BankReturnCodeConfigExample;
import com.hyjf.am.borrow.dao.model.auto.BanksConfig;
import com.hyjf.am.borrow.dao.model.auto.CorpOpenAccountRecord;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * 用户充值Service
 * 
 * 
 *
 */
public interface RechargeService {
	/**
	 * 根据用户Id,银行卡号检索用户银行卡信息
	 * @param userId
	 * @param cardNo
	 * @return
	 */
	 BankCard selectBankCardByUserId(Integer userId);

	/**
	 * 获取银行卡配置信息
	 * @param bankId
	 * @return
	 */
	BanksConfig getBanksConfigByBankId(Integer bankId);

	/**
	 * 根据用户ID查询企业用户信息
	 * @param userId
	 * @return
	 */
	 CorpOpenAccountRecord getCorpOpenAccountRecord(Integer userId);

	/**
	 * 插入充值记录
	 *
	 * @param bean
	 * @param params
	 * @return
	 */
	 int insertRechargeInfo(BankCallBean bean);

	AccountRecharge selectByExample(AccountRechargeExample example);

	 int updateByExampleSelective(AccountRecharge accountRecharge,AccountRechargeExample accountRechargeExample);

	 int updateBankRechargeSuccess(Account newAccount);

	 int insertSelective(AccountList accountList);

	 void updateByPrimaryKeySelective(AccountRecharge accountRecharge);

	BankReturnCodeConfig selectByExample(BankReturnCodeConfigExample example);

}
