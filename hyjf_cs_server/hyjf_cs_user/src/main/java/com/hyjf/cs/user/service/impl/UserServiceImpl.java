package com.hyjf.cs.user.service.impl;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
import com.hyjf.am.vo.config.SmsConfigVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.jwt.JwtHelper;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmUserClient;
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
	private AmConfigClient amConfigClient;
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
	 * 1. 验证码发送前校验 2. 生成验证码 3. 保存验证码 4. 发送短信
	 *
	 * @param validCodeType
	 * @param mobile
	 * @param request
	 */
	@Override
	public void sendSmsCode(String validCodeType, String mobile, String token, String ip) throws MQException {

		this.sendSmsCodeCheckParam(validCodeType, mobile, token, ip);

		// 生成验证码
		String checkCode = GetCode.getRandomSMSCode(6);
		logger.info("手机号: {}, 短信验证码: {}", mobile, checkCode);
		Map<String, String> param = new HashMap<String, String>();
		param.put("val_code", checkCode);

		// 保存短信验证码
		amUserClient.saveSmsCode(mobile, checkCode, validCodeType, CustomConstants.CKCODE_NEW,
				CustomConstants.CLIENT_PC);

		JSONObject params = new JSONObject();
		params.put("checkCode", checkCode);
		params.put("validCodeType", validCodeType);
		params.put("mobile", mobile);

		// 发送
		smsProducer.messageSend(new Producer.MassageContent(smsTopic, defaultTag, JSON.toJSONBytes(params)));
	}

	/**
	 * 1. 必要参数检查 2. 注册 3. 注册后处理
	 * 
	 * @param registerVO
	 * @throws ReturnMessageException
	 */
	@Override
	public UserVO register(RegisterVO registerVO, HttpServletRequest request, HttpServletResponse response)
			throws ReturnMessageException {

		this.registerCheckParam(registerVO);

		RegisterUserRequest registerUserRequest = new RegisterUserRequest();
		BeanUtils.copyProperties(registerVO, registerUserRequest);
		registerUserRequest.setLoginIp(GetCilentIP.getIpAddr(request));
		UserVO userVO = amUserClient.register(registerUserRequest);
		if (userVO == null)
			throw new ReturnMessageException(RegisterError.REGISTER_ERROR);

		this.afterRegisterHandle(userVO);

		return userVO;
	}

	@Override
	public boolean existUser(String mobile) {
		UserVO userVO = amUserClient.findUserByMobile(mobile);
		return userVO == null ? false : true;
	}

	private void sendSmsCodeCheckParam(String validCodeType, String mobile, String token, String ip) {

		List<String> codeTypes = Arrays.asList(CustomConstants.PARAM_TPL_ZHUCE, CustomConstants.PARAM_TPL_ZHAOHUIMIMA,
				CustomConstants.PARAM_TPL_YZYSJH, CustomConstants.PARAM_TPL_BDYSJH);
		if (Validator.isNull(validCodeType) || !codeTypes.contains(validCodeType)) {
			throw new ReturnMessageException(RegisterError.CODETYPE_INVALID_ERROR);
		}
		if (Validator.isNull(mobile) || !Validator.isMobile(mobile)) {
			throw new ReturnMessageException(RegisterError.MOBILE_FORMAT_ERROR);
		}

		if (validCodeType.equals(CustomConstants.PARAM_TPL_ZHUCE)) {
			// 注册时要判断不能重复
			if (existUser(mobile)) {
				throw new ReturnMessageException(RegisterError.MOBILE_EXISTS_ERROR);
			}
		}

		if (StringUtils.isNotEmpty(token)) {
			UserVO userVO = (UserVO) redisUtil.get(token);
			if (userVO != null) {
				// 验证原手机号校验
				if (validCodeType.equals(CustomConstants.PARAM_TPL_YZYSJH)) {
					if (StringUtils.isBlank(userVO.getMobile())) {
						throw new ReturnMessageException(RegisterError.USER_NOT_EXISTS_ERROR);
					}
					if (!userVO.getMobile().equals(mobile)) {
						throw new ReturnMessageException(RegisterError.MOBILE_NEED_SAME_ERROR);
					}
				}

				// 绑定新手机号校验
				if (validCodeType.equals(CustomConstants.PARAM_TPL_BDYSJH)) {
					if (userVO.equals(mobile)) {
						throw new ReturnMessageException(RegisterError.MOBILE_MODIFY_ERROR);
					}
					if (existUser(mobile)) {
						throw new ReturnMessageException(RegisterError.MOBILE_EXISTS_ERROR);
					}
				}
			} else
				throw new ReturnMessageException(RegisterError.USER_NOT_EXISTS_ERROR);
		}

		// 判断发送间隔时间
		String intervalTime = stringRedisUtil.get(mobile + ":" + validCodeType + ":IntervalTime");
		logger.info(mobile + ":" + validCodeType + "----------IntervalTime-----------" + intervalTime);
		if (StringUtils.isNotBlank(intervalTime)) {
			throw new ReturnMessageException(RegisterError.SEND_SMSCODE_TOO_FAST_ERROR);
		}

		String ipCount = stringRedisUtil.get(ip + ":MaxIpCount");
		if (StringUtils.isBlank(ipCount) || !Validator.isNumber(ipCount)) {
			ipCount = "0";
			stringRedisUtil.set(ip + ":MaxIpCount", "0");
		}
		logger.info(mobile + "------ip---" + ip + "----------MaxIpCount-----------" + ipCount);

		// SmsConfigVO smsConfig = RedisUtils.get("smsConfig"); todo 这里从redis取
		SmsConfigVO smsConfig = null;

		if (Integer.valueOf(ipCount) >= smsConfig.getMaxIpCount()) {
			if (Integer.valueOf(ipCount) == smsConfig.getMaxIpCount()) {
				try {
					// registService.sendSms(mobile, "IP访问次数超限:" + ip); TODO
					// 发送短信通知
				} catch (Exception e) {
					throw new ReturnMessageException(RegisterError.IP_VISIT_TOO_MANNY_ERROR);
				}
				stringRedisUtil.setEx(ip + ":MaxIpCount", (Integer.valueOf(ipCount) + 1) + "", 24 * 60 * 60,
						TimeUnit.SECONDS);
			}
			throw new ReturnMessageException(RegisterError.SEND_SMSCODE_TOO_MANNY_ERROR);
		}

		// 判断最大发送数max_phone_count
		String count = stringRedisUtil.get(mobile + ":MaxPhoneCount");
		if (StringUtils.isBlank(count) || !Validator.isNumber(count)) {
			count = "0";
			stringRedisUtil.set(mobile + ":MaxPhoneCount", "0");
		}
		logger.info(mobile + "----------MaxPhoneCount-----------" + count);
		if (Integer.valueOf(count) >= smsConfig.getMaxPhoneCount()) {
			if (Integer.valueOf(count) == smsConfig.getMaxPhoneCount()) {
				try {
					// registService.sendSms(mobile, "手机验证码发送次数超限"); TODO 发送短信通知
				} catch (Exception e) {
					throw new ReturnMessageException(RegisterError.SEND_SMSCODE_TOO_MANNY_ERROR);
				}
				stringRedisUtil.setEx(mobile + ":MaxPhoneCount", (Integer.valueOf(count) + 1) + "", 24 * 60 * 60,
						TimeUnit.SECONDS);
			}
			throw new ReturnMessageException(RegisterError.SEND_SMSCODE_TOO_MANNY_ERROR);
		}

	}

	private void registerCheckParam(RegisterVO registerVO) {
		if (registerVO == null)
			throw new ReturnMessageException(RegisterError.REGISTER_ERROR);

		String mobile = registerVO.getMobilephone();
		if (Validator.isNull(mobile))
			throw new ReturnMessageException(RegisterError.MOBILE_IS_NOT_NULL_ERROR);

		String smscode = registerVO.getSmscode();
		if (Validator.isNull(smscode))
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
		int cnt = amUserClient.checkMobileCode(mobile, smscode, verificationType, CustomConstants.CLIENT_PC,
				CustomConstants.CKCODE_YIYAN, CustomConstants.CKCODE_USED);
		if (cnt == 0) {
			throw new ReturnMessageException(RegisterError.SMSCODE_INVALID_ERROR);
		}

		String reffer = registerVO.getReffer();
		if (isNotBlank(reffer) && amUserClient.countUserByRecommendName(reffer) <= 0) {
			throw new ReturnMessageException(RegisterError.REFFER_INVALID_ERROR);
		}
	}

	private void afterRegisterHandle(UserVO userVO) {
		int userId = userVO.getUserId();

		// 注册成功之后登录 单点登录
		Map map = ImmutableMap.of("userId", userId, "username", userVO.getUsername(), "ts",
				Instant.now().getEpochSecond() + "");
		String token = JwtHelper.genToken(map);
		userVO.setToken(token);
		redisUtil.set(RedisKey.USER_TOKEN_REDIS + token, userVO);

		// 投之家用户注册送券活动
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
					couponProducer.messageSend(new Producer.MassageContent(couponTopic, defaultTag, "coupon_" + userId,
							JSON.toJSONBytes(json)));
				} catch (MQException e) {
					logger.error("注册送券失败....userId is :" + userId, e);
				}
			}
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
				try {
					smsProducer.messageSend(new Producer.MassageContent(smsTopic, defaultTag,
							"sms_" + userVO.getMobile(), JSON.toJSONBytes(params)));
				} catch (MQException e) {
					logger.error("短信发送失败...", e);
				}
			}
		}
	}
}
