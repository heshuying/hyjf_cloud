package com.hyjf.cs.trade.service.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.UserDirectRechargeBean;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.Map;

/**
 * 用户充值Service
 * 
 * @author
 *
 */
public interface RechargeService extends BaseTradeService {
	/**
	 * 根据用户Id,银行卡号检索用户银行卡信息
	 * @param userId
	 * @param
	 * @return
	 */
	BankCardVO selectBankCardByUserId(Integer userId);

	AccountVO getAccount(Integer userId);

	UserVO getUsers(Integer userId);
    String getBankRetMsg(String retCode);

	/**
	 * 插入充值记录
	 * 
	 * @param bean
	 * @param
	 * @return
	 */
	 int insertRechargeInfo(BankCallBean bean);

	/**
	 * 充值后,回调处理
	 * 
	 * @param bean
	 * @param params
	 * @return
	 */
	 JSONObject handleRechargeInfo(BankCallBean bean, Map<String, String> params);

	 UserInfoVO getUsersInfoByUserId(Integer userId);

	/**
	 *
	 * 获取页面充值接口
	 * @author sunss
	 * @param
	 * @param
	 * @return
	 */
	 BankCallBean insertGetMV(UserDirectRechargeBean rechargeBean) throws Exception;


	BankCallBean rechargeService(String token, String ipAddr, String mobile, String money) throws Exception;
	/**
	 * @Description 获取充值页面数据
	 * @Author pangchengchao
	 * @Version v0.1
	 * @Date
	 */
    WebResult<Object> toRecharge(WebViewUserVO user);
	/**
	 * @Description web端查询充值失败原因
	 * @Author pangchengchao
	 * @Version v0.1
	 * @Date
	 */
	WebResult<Object> seachUserBankRechargeErrorMessgae(String logOrdId);
}
