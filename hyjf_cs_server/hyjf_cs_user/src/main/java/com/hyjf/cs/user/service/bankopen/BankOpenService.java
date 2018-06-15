package com.hyjf.cs.user.service.bankopen;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.bean.ApiBankOpenRequestBean;
import com.hyjf.cs.user.bean.OpenAccountPageBean;
import com.hyjf.cs.user.result.AppResult;
import com.hyjf.cs.user.service.BaseService;
import com.hyjf.cs.user.vo.BankOpenVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 
 * @author Administrator
 *
 */

public interface BankOpenService extends BaseService {

	boolean existUser(String mobile);

	boolean checkIdNo(String idNo);

	int updateUserAccountLog(int userId, String userName, String mobile, String logOrderId, String clientPc, String name,
			String idno, String cardNo);

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
	ModelAndView getOpenAccountMV(OpenAccountPageBean openBean);

	/**
	 * @Description 开户异步逻辑处理
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/15 17:25
	 */
	BankCallResult openAccountBgReturn(BankCallBean bean);

    Map<String,String> checkApiParam(ApiBankOpenRequestBean requestBean);
}
