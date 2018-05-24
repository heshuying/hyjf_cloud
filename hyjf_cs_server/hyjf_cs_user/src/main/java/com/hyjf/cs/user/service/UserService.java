package com.hyjf.cs.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.beans.AutoPlusRequestBean;
import com.hyjf.cs.user.beans.BaseMapBean;
import com.hyjf.cs.user.vo.RegisterVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author xiasq
 * @version UserService, v0.1 2018/4/11 9:34
 */
public interface UserService {
	/**
	 * 注册
	 * 
	 * @param registerVO
	 * @param request
	 * @param response
	 * @return
	 * @throws ReturnMessageException
	 */
	UserVO register(RegisterVO registerVO, HttpServletRequest request, HttpServletResponse response)
			throws ReturnMessageException;

	/**
	 * 用户存在检查
	 * 
	 * @param mobile
	 * @return
	 */
	boolean existUser(String mobile);

	/**
	 *
	 * @param loginUserName
	 *            可以是手机号或者用户名
	 * @param loginPassword
	 * @param ip
	 */
	UserVO login(String loginUserName, String loginPassword, String ip);

    ModelAndView userCreditAuthInves(String token, Integer client, String type, String channel, String lastSrvAuthCode,String smsCode);

    Map<String,String> userAuthReturn(String token, BankCallBean bean, String urlType, String isSuccess);

    String userBgreturn(BankCallBean bean);

	Map<String,BaseMapBean> userAuthCreditReturn(String token, BankCallBean bean, String userAutoType, String sign, String isSuccess);

	ModelAndView apiUserAuthInves(String token, AutoPlusRequestBean payRequestBean);
	ModelAndView userAuthCreditReturn(String token, BankCallBean bean, HttpServletRequest request);

	ModelAndView appUserAuthInvesReturn(String token, BankCallBean bean, HttpServletRequest request);

	JSONObject updatePassWd(Integer userId, String oldPW, String newPW);
	ModelAndView userAuthCreditReturn(String token, BankCallBean bean,String userAutoType, HttpServletRequest request);
}
