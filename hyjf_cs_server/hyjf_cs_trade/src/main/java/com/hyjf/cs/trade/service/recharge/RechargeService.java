package com.hyjf.cs.trade.service.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.UserDirectRechargeBean;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;
import java.util.Map;

/**
 * 用户充值Service
 * 
 * @author
 *
 */
public interface RechargeService extends BaseTradeService {


	AccountVO getAccount(Integer userId);


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

	 @Override
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


	/**
	 * 充值接口
	 * @param userId
	 * @param ipAddr
	 * @param mobile
	 * @param money
	 * @return
	 * @throws Exception
	 */
	BankCallBean rechargeService(UserDirectRechargeBean directRechargeBean,int userId, String ipAddr, String mobile, String money) throws Exception;
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

	/**
	 * 获取快捷充值银行限额
	 * @return
	 */
    List<BanksConfigVO> getRechargeQuotaLimit();
}
