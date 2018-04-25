package com.hyjf.cs.user.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.SmsConfigVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MessagePushConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.mq.Producer;
import com.hyjf.cs.user.mq.SmsProducer;
import com.hyjf.cs.user.redis.RedisUtil;
import com.hyjf.cs.user.redis.StringRedisUtil;
import com.hyjf.cs.user.service.SmsCodeService;
import com.hyjf.cs.user.service.UserService;

/**
 * @author xiasq
 * @version SmsCodeServiceImpl, v0.1 2018/4/25 9:02
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {
	private static final Logger logger = LoggerFactory.getLogger(SmsCodeServiceImpl.class);

	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private StringRedisUtil stringRedisUtil;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private SmsProducer smsProducer;
	@Autowired
	private AmConfigClient amConfigClient;
	@Autowired
	private UserService userService;

	@Value("${rocketMQ.topic.couponTopic}")
	private String couponTopic;
	@Value("${rocketMQ.topic.smsCodeTopic}")
	private String smsTopic;
	@Value("${rocketMQ.tag.defaultTag}")
	private String defaultTag;

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
     * 参数检查
     * @param validCodeType
     * @param mobile
     * @param token
     * @param ip
     */
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
			if (userService.existUser(mobile)) {
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
					if (userService.existUser(mobile)) {
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

		SmsConfigVO smsConfig = amConfigClient.findSmsConfig();
		if (smsConfig == null)
			throw new ReturnMessageException(RegisterError.FIND_SMSCONFIG_ERROR);

		if (Integer.valueOf(ipCount) >= smsConfig.getMaxIpCount()) {
			if (Integer.valueOf(ipCount) == smsConfig.getMaxIpCount()) {
				try {
					// 发送短信通知
					JSONObject params = new JSONObject();
					params.put("var_phonenu", mobile);
					params.put("val_reason", "IP访问次数超限");
					params.put("templateCode", MessagePushConstant.SMSSENDFORMANAGER);
					try {
						smsProducer.messageSend(
								new Producer.MassageContent(smsTopic, defaultTag, JSON.toJSONBytes(params)));
					} catch (MQException e) {
						logger.error("短信发送失败...", e);
					}
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
					// 发送短信通知
					JSONObject params = new JSONObject();
					params.put("var_phonenu", mobile);
					params.put("val_reason", "手机验证码发送次数超限");
					params.put("templateCode", MessagePushConstant.SMSSENDFORMANAGER);
					try {
						smsProducer.messageSend(
								new Producer.MassageContent(smsTopic, defaultTag, JSON.toJSONBytes(params)));
					} catch (MQException e) {
						logger.error("短信发送失败...", e);
					}
				} catch (Exception e) {
					throw new ReturnMessageException(RegisterError.SEND_SMSCODE_TOO_MANNY_ERROR);
				}
				stringRedisUtil.setEx(mobile + ":MaxPhoneCount", (Integer.valueOf(count) + 1) + "", 24 * 60 * 60,
						TimeUnit.SECONDS);
			}
			throw new ReturnMessageException(RegisterError.SEND_SMSCODE_TOO_MANNY_ERROR);
		}

	}

}
