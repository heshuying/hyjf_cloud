package com.hyjf.cs.borrow.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.borrow.dao.model.auto.Account;
import com.hyjf.am.borrow.dao.model.auto.BankCard;
import com.hyjf.am.borrow.dao.model.auto.BanksConfig;
import com.hyjf.am.borrow.dao.model.auto.CorpOpenAccountRecord;
import com.hyjf.am.user.dao.model.auto.Users;
import com.hyjf.am.user.dao.model.auto.UsersInfo;
import com.hyjf.cs.borrow.bean.UserDirectRechargeBean;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 用户充值Service
 * 
 * @author
 *
 */
public interface RechargeService extends BaseService{
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

	Account getAccount(Integer userId);

	Users getUsers(Integer userId);
    String getBankRetMsg(String retCode);

	/**
	 * 根据用户Id检索用户的身份证号
	 * 
	 * @param userId
	 * @return
	 *//*
	public String getUserIdcard(Integer userId);

	*//**
	 * 插入充值记录
	 * 
	 * @param bean
	 * @param params
	 * @return
	 */
	 int insertRechargeInfo(BankCallBean bean);

	/**
	 * 冲值后,回调处理
	 * 
	 * @param bean
	 * @param params
	 * @return
	 */
	 JSONObject handleRechargeInfo(BankCallBean bean, Map<String, String> params);

	 UsersInfo getUsersInfoByUserId(Integer userId);

	/**
	 *
	 * 获取页面充值接口
	 * @author sunss
	 * @param userId
	 * @param TxCode
	 * @return
	 */
	 ModelAndView insertGetMV(UserDirectRechargeBean rechargeBean) throws Exception;


}
