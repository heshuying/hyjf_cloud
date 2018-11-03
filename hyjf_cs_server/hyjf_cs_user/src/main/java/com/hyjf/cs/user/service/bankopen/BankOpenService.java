package com.hyjf.cs.user.service.bankopen;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.ApiBankOpenRequestBean;
import com.hyjf.cs.user.bean.OpenAccountPageBean;
import com.hyjf.cs.user.bean.OpenAccountPlusRequest;
import com.hyjf.cs.user.bean.OpenAccountPlusResult;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.BankOpenVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;

import java.util.Map;

/**
 * 
 * @author Administrator
 *
 */

public interface BankOpenService extends BaseUserService {

	boolean checkIdNo(String idNo);

	int updateUserAccountLog(int userId, String userName, String mobile, String logOrderId, String clientPc, String name,
			String idno, String cardNo, String srvAuthCode);

	/**
	 * @Description 检查请求参数
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/15 17:28
	 */
	void checkRequestParam(UserVO user, BankOpenVO bankOpenVO);

	/**
	 * @Description 组装跳转江西银行的参数
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/15 17:19
	 */
	Map<String,Object> getOpenAccountMV(OpenAccountPageBean openBean, String sign);

	/**
	 * @Description 开户异步逻辑处理
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/15 17:25
	 */
	BankCallResult openAccountBgReturn(BankCallBean bean);

    Map<String,String> checkApiParam(ApiBankOpenRequestBean requestBean);

		/**
	 * @Description 根据 logOrdId查询失败原因
	 * @Author sunss
	 * @Date 2018/6/21 15:34
	 */
	WebResult<Object> getFiledMess(String logOrdId,int userId);

	/**
	 * 获得担保机构开户调用银行的参数
	 * @param openBean
	 * @return
	 */
	Map<String,Object> getAssureOpenAccountMV(OpenAccountPageBean openBean);

	/**
	 * 获得借款人开户调用银行的参数
	 * @param openBean
	 * @return
	 */
	Map<String,Object> getLoanOpenAccountMV(OpenAccountPageBean openBean);

    OpenAccountPlusResult checkAndUpdateForSendCode(OpenAccountPlusRequest openAccountRequestBean, String ipAddr);

    BankCallBean sendOpenAccountSms(Integer userId, String orderId, String srvTxCode, String mobile, String channel);
}
