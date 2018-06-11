package com.hyjf.cs.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.config.SmsConfigVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.constants.RedisKey;
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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

	/**
	 * 1. 验证码发送前校验 2. 生成验证码 3. 保存验证码 4. 发送短信
	 *
	 * @param validCodeType
	 * @param mobile
	 * @param token
	 * @param ip
	 * @throws MQException
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
		amUserClient.saveSmsCode(mobile, checkCode, validCodeType, CommonConstant.CKCODE_NEW, CommonConstant.CLIENT_PC);

		SmsMessage smsMessage = new SmsMessage(null, param, mobile, null, MessageConstant.SMSSENDFORMOBILE, null,
				validCodeType, CustomConstants.CHANNEL_TYPE_NORMAL);

		// 发送
		smsProducer.messageSend(new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(smsMessage)));
	}

	/**
	 * 参数检查
	 * 
	 * @param validCodeType
	 * @param mobile
	 * @param token
	 * @param ip
	 */
	private void sendSmsCodeCheckParam(String validCodeType, String mobile, String token, String ip) {

		List<String> codeTypes = Arrays.asList(CommonConstant.PARAM_TPL_ZHUCE, CommonConstant.PARAM_TPL_ZHAOHUIMIMA,
				CommonConstant.PARAM_TPL_YZYSJH, CommonConstant.PARAM_TPL_BDYSJH);
		if (Validator.isNull(validCodeType) || !codeTypes.contains(validCodeType)) {
			throw new ReturnMessageException(RegisterError.CODETYPE_INVALID_ERROR);
		}
		if (Validator.isNull(mobile) || !Validator.isMobile(mobile)) {
			throw new ReturnMessageException(RegisterError.MOBILE_FORMAT_ERROR);
		}

		if (validCodeType.equals(CommonConstant.PARAM_TPL_ZHUCE)) {
			// 注册时要判断不能重复
			if (userService.existUser(mobile)) {
				throw new ReturnMessageException(RegisterError.MOBILE_EXISTS_ERROR);
			}
		}

		if (StringUtils.isNotEmpty(token)) {
			WebViewUser webViewUser = (WebViewUser) redisUtil.get(RedisKey.USER_TOKEN_REDIS+token);
			if (webViewUser != null) {
				// 验证原手机号校验
				if (validCodeType.equals(CommonConstant.PARAM_TPL_YZYSJH)) {
					if (StringUtils.isBlank(webViewUser.getMobile())) {
						throw new ReturnMessageException(RegisterError.USER_NOT_EXISTS_ERROR);
					}
					if (!webViewUser.getMobile().equals(mobile)) {
						throw new ReturnMessageException(RegisterError.MOBILE_NEED_SAME_ERROR);
					}
				}

				// 绑定新手机号校验
				if (validCodeType.equals(CommonConstant.PARAM_TPL_BDYSJH)) {
					if (webViewUser.equals(mobile)) {
						throw new ReturnMessageException(RegisterError.MOBILE_MODIFY_ERROR);
					}
					if (userService.existUser(mobile)) {
						throw new ReturnMessageException(RegisterError.MOBILE_EXISTS_ERROR);
					}
				}
			} else {
                throw new ReturnMessageException(RegisterError.USER_NOT_EXISTS_ERROR);
            }
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
		if (smsConfig == null) {
            throw new ReturnMessageException(RegisterError.FIND_SMSCONFIG_ERROR);
        }

		if (Integer.valueOf(ipCount) >= smsConfig.getMaxIpCount()) {
			if (Integer.valueOf(ipCount).equals(smsConfig.getMaxIpCount())) {
				try {
					// 发送短信通知
					Map<String, String> replaceStrs = new HashMap<String, String>();
					replaceStrs.put("var_phonenu", mobile);
					replaceStrs.put("val_reason", "IP访问次数超限");
					SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
							MessageConstant.SMSSENDFORMANAGER, null, CustomConstants.PARAM_TPL_DUANXINCHAOXIAN,
							CustomConstants.CHANNEL_TYPE_NORMAL);
					try {
						smsProducer.messageSend(
								new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(smsMessage)));
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
			if (Integer.valueOf(count).equals(smsConfig.getMaxPhoneCount())) {
				try {
					// 发送短信通知
					Map<String, String> replaceStrs = new HashMap<String, String>();
					replaceStrs.put("var_phonenu", mobile);
					replaceStrs.put("val_reason", "手机验证码发送次数超限");
					SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
							MessageConstant.SMSSENDFORMANAGER, null, CustomConstants.PARAM_TPL_DUANXINCHAOXIAN,
							CustomConstants.CHANNEL_TYPE_NORMAL);
					try {
						smsProducer.messageSend(
								new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(smsMessage)));
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

		// 发送checkCode最大时间间隔，默认60秒
		stringRedisUtil.setEx(mobile + ":" + validCodeType + ":IntervalTime", mobile,
				smsConfig.getMaxIntervalTime() == null ? 60 : smsConfig.getMaxIntervalTime(), TimeUnit.SECONDS);
	}

}
