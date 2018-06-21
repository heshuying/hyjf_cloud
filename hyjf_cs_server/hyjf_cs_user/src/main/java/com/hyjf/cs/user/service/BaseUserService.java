package com.hyjf.cs.user.service;

import com.hyjf.am.resquest.user.BankSmsLogRequest;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.user.bean.BaseBean;
import com.hyjf.pay.lib.bank.bean.BankCallBean;


public interface BaseUserService extends BaseService{

	/**
	 * @Description 根据token查询user
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:34
	 */
	WebViewUserVO getUsersByToken(String token);

	/**
	 * @Description 查询用户对象
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:50
	 */
	UserVO getUsers(String token);

	/**
	 * @Description 根据手机号查询user
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:36
	 */
	UserVO getUsersByMobile(String mobile);

	/**
	 * @Description 根据userid查询用户
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:37
	 */
	UserVO getUsersById(Integer userId);

	boolean verifyRequestSign(BaseBean paramBean, String methodName);

    BankOpenAccountVO getBankOpenAccount(Integer userId);

	boolean checkIsOpen(Integer userId);

	int updateUserByUserId(UserVO userVO);

	BankCallBean callSendCode(Integer userId, String mobile, String txCode, String client, String cardNo);

	boolean updateAfterSendCode(BankSmsLogRequest request);
}
