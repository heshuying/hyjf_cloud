package com.hyjf.am.borrow.service;

import com.hyjf.am.borrow.dao.model.auto.*;
import com.hyjf.am.user.dao.model.auto.Users;
import com.hyjf.am.user.dao.model.auto.UsersInfo;
import com.hyjf.am.user.dao.model.auto.UsersInfoExample;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * 用户充值Service
 * 
 * @author liuyang
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

	UsersInfo selectByExample(UsersInfoExample example);

}
