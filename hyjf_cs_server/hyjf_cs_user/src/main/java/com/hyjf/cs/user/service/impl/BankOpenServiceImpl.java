package com.hyjf.cs.user.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.mq.CouponProducer;
import com.hyjf.cs.user.mq.SmsProducer;
import com.hyjf.cs.user.redis.RedisUtil;
import com.hyjf.cs.user.redis.StringRedisUtil;
import com.hyjf.cs.user.service.BankOpenService;
import com.hyjf.cs.user.service.CouponService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterVO;

/**
 * @author xiasq
 * @version UserServiceImpl, v0.1 2018/4/11 9:34
 */

@Service
public class BankOpenServiceImpl implements BankOpenService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private AmUserClient amUserClient;
	
	@Autowired
	private CouponProducer couponProducer;
	@Autowired
	private SmsProducer smsProducer;

	@Value("${rocketMQ.topic.couponTopic}")
	private String couponTopic;
	@Value("${rocketMQ.topic.smsCodeTopic}")
	private String smsTopic;
	@Value("${rocketMQ.tag.defaultTag}")
	private String defaultTag;
	@Value("${hyjf.activity.regist.tzj.id}")
	private String activityIdTzj;
	@Value("${hyjf.activity.888.id}")
	private String activityId;

	/**
	 * 1. 必要参数检查 2. 注册 3. 注册后处理
	 * 
	 * @param registerVO
	 * @throws ReturnMessageException
	 */
	@Override
	public UserVO register(RegisterVO registerVO, HttpServletRequest request, HttpServletResponse response)
			throws ReturnMessageException {
		// 1. 参数检查
		this.registerCheckParam(registerVO);

		RegisterUserRequest registerUserRequest = new RegisterUserRequest();
		BeanUtils.copyProperties(registerVO, registerUserRequest);
		registerUserRequest.setLoginIp(GetCilentIP.getIpAddr(request));
		// 2.注册
		UserVO userVO = amUserClient.register(registerUserRequest);
		if (userVO == null)
			throw new ReturnMessageException(RegisterError.REGISTER_ERROR);

		// 3.注册后处理
		this.afterRegisterHandle(userVO);

		return userVO;
	}

	@Override
	public boolean existUser(String mobile) {
		UserVO userVO = amUserClient.findUserByMobile(mobile);
		return userVO == null ? false : true;
	}

	/**
	 *
	 * @param loginUserName
	 *            可以是手机号或者用户名
	 * @param loginPassword
	 * @param ip
	 */
	@Override
	public void login(String loginUserName, String loginPassword, String ip) {
		if (checkMaxLength(loginUserName, 16) || checkMaxLength(loginUserName, 32))
			throw new ReturnMessageException(LoginError.USER_LOGIN_ERROR);

		// 获取密码错误次数
		String errCount = stringRedisUtil.get(RedisKey.PASSWORD_ERR_COUNT + loginUserName);
		if (StringUtils.isNotBlank(errCount) && Integer.parseInt(errCount) > 6) {
			throw new ReturnMessageException(LoginError.PWD_ERROR_TOO_MANEY_ERROR);
		}
		this.doLogin(loginUserName, loginPassword, ip);
	}


	
}
