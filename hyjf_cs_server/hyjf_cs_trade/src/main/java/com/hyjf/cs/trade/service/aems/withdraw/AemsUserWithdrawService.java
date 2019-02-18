package com.hyjf.cs.trade.service.aems.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.trade.bean.AemsUserWithdrawRequestBean;
import com.hyjf.cs.trade.bean.BaseResultBean;
import com.hyjf.cs.trade.bean.assetpush.UserWithdrawRequestBean;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Aems用户提现Service
 * @author Zha Daojian
 * @date 2018/12/12 15:14
 * @param
 * @return
 **/
public interface AemsUserWithdrawService extends BaseTradeService {

	/**
	 * 提现银行异步调用数据处理
	 * @param bean
	 * @param params
	 * @return
	 */
	JSONObject handlerAfterCash(BankCallBean bean, Map<String,String> params);

	/**
	 * 根据userId获取用户信息
	 * @param userId
	 * @return
	 */
	@Override
	UserVO getUserByUserId(Integer userId);

	/**
	 * api提现
	 * @param userWithdrawRequestBean
	 * @param request
	 * @return
	 */
	Map<String,Object> withdraw(AemsUserWithdrawRequestBean userWithdrawRequestBean, HttpServletRequest request);

	/**
	 * 同步
	 * @param request
	 * @param bean
	 * @return
	 */
	Map<String,Object> cashReturn(HttpServletRequest request, BankCallBean bean);

	BankCallResult withdrawBgReturn(HttpServletRequest request, BankCallBean bean);

	BaseResultBean getUserWithdrawRecord(UserWithdrawRequestBean userWithdrawRequestBean);

}
