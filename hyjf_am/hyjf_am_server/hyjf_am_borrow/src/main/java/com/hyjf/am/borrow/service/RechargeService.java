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
	 Users selectByPrimaryKey(Integer userId);

	 void updateByPrimaryKeySelective(AccountRecharge accountRecharge);

	BankReturnCodeConfig selectByExample(BankReturnCodeConfigExample example);
	UsersInfo selectByExample(UsersInfoExample example);
	/**
	 * 根据用户Id检索用户的身份证号
	 * 
	 * @param userId
	 * @return
	 *//*
	public String getUserIdcard(Integer userId);

	*//**
	 * 冲值后,回调处理
	 * 
	 * @param bean
	 * @param params
	 * @return
	 */
	/*public JSONObject handleRechargeInfo(BankCallBean bean, Map<String, String> params);
*/

	/**
	 * 根据用户ID查询企业用户信息
	 * 
	 * @param userId
	 * @return
	 *//*
	public CorpOpenAccountRecord getCorpOpenAccountRecord(Integer userId);
	
	*//**
	 * 发送验证码
	 *
	 * @param request
	 * @param form
	 * @return Map<String, Object> {success: 1,message:调用验证码接口成功,srvAuthCode:
	 *         srvAuthCode}
	 *//*
	public BankCallBean sendRechargeOnlineSms(Integer userId, String cardNo, String mobile, String client);
	
	*//**
	 * 初始化充值信息
	 * @param bean
	 * @return
	 *//*
	public int insertRechargeOnlineInfo(BankCallBean bean);
	
	*//**
	 * 短信充值处理
	 * 
	 * @param bean
	 * @param params
	 * @return
	 *//*
	public JSONObject handleRechargeOnlineInfo(BankCallBean bean, Map<String, String> params);

	*//**
	 * 查询用户的短信序列号
	 * 
	 * @param userId
	 * @param srvTxCode
	 * @param srvAuthCode
	 * @return
	 *//*
	public String selectBankSmsSeq(Integer userId, String srvTxCode);

	*//**
	 * 查询充值是否成功
	 * @param userId
	 * @param nid
	 * @return
	 *//*
	AccountRecharge selectRechargeInfo(int userId, String nid);
*/

}
