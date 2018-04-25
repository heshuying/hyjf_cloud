package com.hyjf.cs.user.service.impl;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.time.Instant;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MessagePushConstant;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.jwt.JwtHelper;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.MD5Utils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.mq.CouponProducer;
import com.hyjf.cs.user.mq.Producer;
import com.hyjf.cs.user.mq.SmsProducer;
import com.hyjf.cs.user.redis.RedisUtil;
import com.hyjf.cs.user.redis.StringRedisUtil;
import com.hyjf.cs.user.service.CouponService;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterVO;

/**
 * @author xiasq
 * @version UserServiceImpl, v0.1 2018/4/11 9:34
 */

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponProducer couponProducer;
	@Autowired
	private SmsProducer smsProducer;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private StringRedisUtil stringRedisUtil;

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

	/**
	 * 登录处理
	 * 
	 * @param loginUserName
	 * @param loginPassword
	 * @return
	 */
	private void doLogin(String loginUserName, String loginPassword, String ip) {
		UserVO userVO = amUserClient.findUserByUserNameOrMobile(loginUserName);

		if (userVO == null) {
			throw new ReturnMessageException(LoginError.USER_LOGIN_ERROR);
		}

		int userId = userVO.getUserId();
		String codeSalt = userVO.getSalt();
		String passwordDb = userVO.getPassword();
		// 页面传来的密码
		String password = MD5Utils.MD5(loginPassword + codeSalt);

		if (password.equals(passwordDb)) {
			// 是否禁用
			if (userVO.getStatus() == 1)
				throw new ReturnMessageException(LoginError.USER_INVALID_ERROR);

			// 更新登录信息
			amUserClient.updateLoginUser(userId, ip);

			// 1. 登录成功将登陆密码错误次数的key删除
			stringRedisUtil.delete(RedisKey.PASSWORD_ERR_COUNT + loginUserName);

			// 2. 缓存
			String token = generatorToken(userVO.getUserId(), userVO.getUsername());
			userVO.setToken(token);
			redisUtil.set(RedisKey.USER_TOKEN_REDIS + token, userVO);

			// 3. todo 登录时自动同步线下充值记录
		} else {
			// 密码错误，增加错误次数
			stringRedisUtil.incr(RedisKey.PASSWORD_ERR_COUNT + loginUserName);
			throw new ReturnMessageException(LoginError.USER_LOGIN_ERROR);
		}
	}

	/**
	 * 字符串长度检查
	 * 
	 * @param value
	 * @param max
	 * @return
	 */
	private boolean checkMaxLength(String value, int max) {
		if (StringUtils.isEmpty(value))
			return true;
		if (value.length() > max)
			return true;
		return false;
	}

	/**
	 * 注册参数校验
	 * 
	 * @param registerVO
	 */
	private void registerCheckParam(RegisterVO registerVO) {
		if (registerVO == null)
			throw new ReturnMessageException(RegisterError.REGISTER_ERROR);

		String mobile = registerVO.getMobilephone();
		if (Validator.isNull(mobile))
			throw new ReturnMessageException(RegisterError.MOBILE_IS_NOT_NULL_ERROR);

		String smsCode = registerVO.getSmsCode();
		if (Validator.isNull(smsCode))
			throw new ReturnMessageException(RegisterError.SMSCODE_IS_NOT_NULL_ERROR);

		String password = registerVO.getPassword();
		if (Validator.isNull(password))
			throw new ReturnMessageException(RegisterError.PASSWORD_IS_NOT_NULL_ERROR);

		if (!Validator.isMobile(mobile)) {
			throw new ReturnMessageException(RegisterError.MOBILE_IS_NOT_REAL_ERROR);
		} else {
			if (existUser(mobile)) {
				throw new ReturnMessageException(RegisterError.MOBILE_EXISTS_ERROR);
			}
		}
		if (password.length() < 6 || password.length() > 16) {
			throw new ReturnMessageException(RegisterError.PASSWORD_LENGTH_ERROR);
		}

		boolean hasNumber = false;
		for (int i = 0; i < password.length(); i++) {
			if (Validator.isNumber(password.substring(i, i + 1))) {
				hasNumber = true;
				break;
			}
		}
		if (!hasNumber) {
			throw new ReturnMessageException(RegisterError.PASSWORD_NO_NUMBER_ERROR);
		}
		String regEx = "^[a-zA-Z0-9]+$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(password);
		if (!m.matches()) {
			throw new ReturnMessageException(RegisterError.PASSWORD_FORMAT_ERROR);
		}

		String verificationType = CustomConstants.PARAM_TPL_ZHUCE;
		int cnt = amUserClient.checkMobileCode(mobile, smsCode, verificationType, CustomConstants.CLIENT_PC,
				CustomConstants.CKCODE_YIYAN, CustomConstants.CKCODE_USED);
		if (cnt == 0) {
			throw new ReturnMessageException(RegisterError.SMSCODE_INVALID_ERROR);
		}

		String reffer = registerVO.getReffer();
		if (isNotBlank(reffer) && amUserClient.countUserByRecommendName(reffer) <= 0) {
			throw new ReturnMessageException(RegisterError.REFFER_INVALID_ERROR);
		}
	}

	/**
	 * 注册后处理: 1. 单点登录 2. 判断投之家着陆页送券 3. 注册送188红包
	 * 
	 * @param userVO
	 */
	private void afterRegisterHandle(UserVO userVO) {
		int userId = userVO.getUserId();

		// 1. 注册成功之后登录 单点登录
		String token = generatorToken(userId, userVO.getUsername());
		userVO.setToken(token);
		redisUtil.set(RedisKey.USER_TOKEN_REDIS + token, userVO);

		// 2. 投之家用户注册送券活动
		// 活动有效期校验
		if (!couponService.checkActivityIfAvailable(activityIdTzj)) {
			// 投之家用户额外发两张加息券
			if (StringUtils.isNotEmpty(userVO.getReferrerUserName())
					&& userVO.getReferrerUserName().equals("touzhijia")) {
				// 发放两张加息券
				JSONObject json = new JSONObject();
				json.put("userId", userId);
				json.put("couponSource", 2);
				json.put("couponCode", "PJ2958703");
				json.put("sendCount", 2);
				json.put("activityId", Integer.parseInt(activityIdTzj));
				json.put("remark", "投之家用户注册送加息券");
				json.put("sendFlg", 0);
				try {
					couponProducer.messageSend(
							new Producer.MassageContent(couponTopic, defaultTag, "coupon_" + userId, json));
				} catch (MQException e) {
					logger.error("投之家用户注册送券失败....userId is :" + userId, e);
				}
			}
		}

		// 3. 注册送188元新手红包
		if (!couponService.checkActivityIfAvailable(activityId)) {
			try {
				JSONObject params = new JSONObject();
				params.put("mqMsgId", GetCode.getRandomCode(10));
				params.put("userId", String.valueOf(userId));
				params.put("sendFlg", "11");
				couponProducer.messageSend(new Producer.MassageContent(couponTopic, defaultTag, "coupon_" + userId,
						JSON.toJSONBytes(params)));
			} catch (Exception e) {
				logger.error("注册发放888红包失败...", e);
			}

			// 短信通知用户发券成功
			JSONObject params = new JSONObject();
			params.put("mobile", userVO.getMobile());
			params.put("templateCode", MessagePushConstant.SMSSENDFORMOBILE);
			try {
				smsProducer.messageSend(
						new Producer.MassageContent(smsTopic, defaultTag, "sms_" + userVO.getMobile(), params));
			} catch (MQException e) {
				logger.error("短信发送失败...", e);
			}
		}
	}

	/**
	 * jwt生成token
	 * 
	 * @param userId
	 * @param username
	 * @return
	 */
	private String generatorToken(int userId, String username) {
		Map map = ImmutableMap.of("userId", userId, "username", username, "ts", Instant.now().getEpochSecond() + "");
		String token = JwtHelper.genToken(map);
		return token;
	}
}
