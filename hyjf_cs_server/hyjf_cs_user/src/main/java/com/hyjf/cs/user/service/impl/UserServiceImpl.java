package com.hyjf.cs.user.service.impl;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.common.web.WebUtils;
import com.hyjf.common.web.WebViewUser;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.mq.CouponProducer;
import com.hyjf.cs.user.mq.Producer;
import com.hyjf.cs.user.mq.SmsProducer;
import com.hyjf.cs.user.service.CouponService;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.util.TreeDESUtils;
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
	public void sendSmsCode(String validCodeType, String mobile, HttpServletRequest request) throws MQException {

		this.sendSmsCodeCheckParam(validCodeType, mobile, request);

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

		this.afterRegisterHandle(userVO, request, response);

		return userVO;
	}

	@Override
	public boolean existUser(String mobile) {
		UserVO userVO = amUserClient.findUserByMobile(mobile);
		return userVO == null ? false : true;
	}

	@Override
	public WebViewUser getWebViewUserByUserId(Integer userId) {
		WebViewUser result = new WebViewUser();

		UserVO userVO = amUserClient.findUserById(userId);

		result.setUserId(userVO.getUserId());
		result.setUsername(userVO.getUsername());
		if (StringUtils.isNotBlank(userVO.getMobile())) {
			result.setMobile(userVO.getMobile());
		}
		// 文件上传未实现 todo
		// if (StringUtils.isNotBlank(userVO.getIconurl())) {
		// String imghost =
		// UploadFileUtils.getDoPath(PropUtils.getSystem("file.domain.head.url"));
		// imghost = imghost.substring(0, imghost.length() - 1);
		// String fileUploadTempPath =
		// UploadFileUtils.getDoPath(PropUtils.getSystem("file.upload.head.path"));
		// if (StringUtils.isNotEmpty(user.getIconurl())) {
		// result.setIconurl(imghost + fileUploadTempPath + user.getIconurl());
		// }
		// }
		if (StringUtils.isNotBlank(userVO.getEmail())) {
			result.setEmail(userVO.getEmail());
		}
		if (userVO.getOpenAccount() != null) {
			if (userVO.getOpenAccount().intValue() == 1) {
				result.setOpenAccount(true);
			} else {
				result.setOpenAccount(false);
			}
		}
		if (userVO.getBankOpenAccount() != null) {
			if (userVO.getBankOpenAccount() == 1) {
				result.setBankOpenAccount(true);
			} else {
				result.setBankOpenAccount(false);
			}
		}
		result.setRechargeSms(userVO.getRechargeSms());
		result.setWithdrawSms(userVO.getWithdrawSms());
		result.setInvestSms(userVO.getInvestSms());
		result.setRecieveSms(userVO.getRecieveSms());
		result.setIsSetPassword(userVO.getIsSetPassword());
		result.setIsEvaluationFlag(userVO.getIsEvaluationFlag());
		result.setIsSmtp(userVO.getIsSmtp());
		result.setUserType(userVO.getUserType());

		UserInfoVO userInfoVO = amUserClient.findUserInfoById(userId);
		if (userInfoVO != null) {
			result.setSex(userInfoVO.getSex());
			result.setNickname(userInfoVO.getNickname());
			result.setTruename(userInfoVO.getTruename());
			result.setIdcard(userInfoVO.getIdcard());
			result.setBorrowerType(userInfoVO.getBorrowerType());
		}
		result.setRoleId(userInfoVO.getRoleId() + "");

		// 汇付开户， 银行开户。 用户紧急联系人未实现 todo
		// AccountChinapnrExample chinapnrExample = new
		// AccountChinapnrExample();
		// chinapnrExample.createCriteria().andUserIdEqualTo(userId);
		// List<AccountChinapnr> chinapnrList =
		// accountChinapnrMapper.selectByExample(chinapnrExample);
		// if (chinapnrList != null && chinapnrList.size() > 0) {
		// result.setChinapnrUsrid(chinapnrList.get(0).getChinapnrUsrid());
		// result.setChinapnrUsrcustid(chinapnrList.get(0).getChinapnrUsrcustid());
		// }
		//
		// BankOpenAccount bankOpenAccount = this.getBankOpenAccount(userId);
		// if (bankOpenAccount != null &&
		// StringUtils.isNotEmpty(bankOpenAccount.getAccount())) {
		// if (result.isBankOpenAccount()) {
		// result.setBankAccount(bankOpenAccount.getAccount());
		// }
		// }
		// // 用户紧急联系人
		// UsersContractExample usersContractExample = new
		// UsersContractExample();
		// usersContractExample.createCriteria().andUserIdEqualTo(userId);
		// List<UsersContract> UsersContractList =
		// usersContractMapper.selectByExample(usersContractExample);
		// if (UsersContractList != null && UsersContractList.size() > 0) {
		// result.setUsersContract(UsersContractList.get(0));
		// }
		return result;
	}

	private void sendSmsCodeCheckParam(String validCodeType, String mobile, HttpServletRequest request) {

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

		// todo 未实现
		// if (validCodeType.equals(CustomConstants.PARAM_TPL_YZYSJH)) {
		// if (WebUtils.getUser(request) == null ||
		// StringUtils.isBlank(WebUtils.getUser(request).getMobile())) {
		// throw new
		// ReturnMessageException(RegisterError.USER_NOT_EXISTS_ERROR);
		// }
		// if (!WebUtils.getUser(request).getMobile().equals(mobile)) {
		// ret.put(UserRegistDefine.STATUS, UserRegistDefine.STATUS_FALSE);
		// ret.put(UserRegistDefine.ERROR, "获取验证码手机号与注册手机号不一致!");
		// return ret;
		// }
		// }
		// if (validCodeType.equals(CustomConstants.PARAM_TPL_BDYSJH)) {
		// if (WebUtils.getUser(request) == null) {
		// throw new
		// ReturnMessageException(RegisterError.USER_NOT_EXISTS_ERROR);
		// }
		// if (WebUtils.getUser(request).getMobile().equals(mobile)) {
		// ret.put(UserRegistDefine.STATUS, UserRegistDefine.STATUS_FALSE);
		// ret.put(UserRegistDefine.ERROR, "修改手机号与原手机号不能相同!");
		// return ret;
		// }
		// // 不能重复
		// if (existUser(mobile)) {
		// throw new ReturnMessageException(RegisterError.MOBILE_EXISTS_ERROR);
		// }
		// }

		// SmsConfig smsConfig = registService.getSmsConfig();

		// 判断发送间隔时间
		// String intervalTime = RedisUtils.get(mobile + ":" + validCodeType +
		// ":IntervalTime");
		// System.out.println(mobile + ":" + validCodeType +
		// "----------IntervalTime-----------" + intervalTime);
		// if (StringUtils.isNotBlank(intervalTime)) {
		// ret.put(UserRegistDefine.STATUS, UserRegistDefine.STATUS_FALSE);
		// ret.put(UserRegistDefine.ERROR, "请求验证码操作过快");
		// LogUtil.errorLog(UserRegistDefine.THIS_CLASS,
		// UserRegistDefine.REGIST_SEND_CODE_ACTION, "短信验证码发送失败", null);
		// return ret;
		// }
		//
		// String ip = GetCilentIP.getIpAddr(request);
		// String ipCount = RedisUtils.get(ip + ":MaxIpCount");
		// if (StringUtils.isBlank(ipCount) || !Validator.isNumber(ipCount)) {
		// ipCount = "0";
		// RedisUtils.set(ip + ":MaxIpCount", "0");
		// }
		// System.out.println(mobile + "------ip---" + ip +
		// "----------MaxIpCount-----------" + ipCount);
		// if (Integer.valueOf(ipCount) >= smsConfig.getMaxIpCount()) {
		// if (Integer.valueOf(ipCount) == smsConfig.getMaxIpCount()) {
		// try {
		// registService.sendSms(mobile, "IP访问次数超限:" + ip);
		// } catch (Exception e) {
		// LogUtil.errorLog(UserRegistDefine.THIS_CLASS,
		// UserRegistDefine.REGIST_SEND_CODE_ACTION, e);
		// }
		//
		// RedisUtils.set(ip + ":MaxIpCount", (Integer.valueOf(ipCount) + 1) +
		// "", 24 *
		// 60 * 60);
		//
		// }
		// ret.put(UserRegistDefine.STATUS, UserRegistDefine.STATUS_FALSE);
		// ret.put(UserRegistDefine.ERROR, "该设备短信请求次数超限，请明日再试");
		// LogUtil.errorLog(UserRegistDefine.THIS_CLASS,
		// UserRegistDefine.REGIST_SEND_CODE_ACTION, "短信验证码发送失败", null);
		// return ret;
		// }
		//
		// // 判断最大发送数max_phone_count
		// String count = RedisUtils.get(mobile + ":MaxPhoneCount");
		// if (StringUtils.isBlank(count) || !Validator.isNumber(count)) {
		// count = "0";
		// RedisUtils.set(mobile + ":MaxPhoneCount", "0");
		// }
		// System.out.println(mobile + "----------MaxPhoneCount-----------" +
		// count);
		// if (Integer.valueOf(count) >= smsConfig.getMaxPhoneCount()) {
		// if (Integer.valueOf(count) == smsConfig.getMaxPhoneCount()) {
		// try {
		// registService.sendSms(mobile, "手机验证码发送次数超限");
		// } catch (Exception e) {
		// LogUtil.errorLog(UserRegistDefine.THIS_CLASS,
		// UserRegistDefine.REGIST_SEND_CODE_ACTION, e);
		// }
		//
		// RedisUtils.set(mobile + ":MaxPhoneCount", (Integer.valueOf(count) +
		// 1) + "",
		// 24 * 60 * 60);
		// }
		// ret.put(UserRegistDefine.STATUS, UserRegistDefine.STATUS_FALSE);
		// ret.put(UserRegistDefine.ERROR, "该手机号短信请求次数超限，请明日再试");
		// LogUtil.errorLog(UserRegistDefine.THIS_CLASS,
		// UserRegistDefine.REGIST_SEND_CODE_ACTION, "短信验证码发送失败", null);
		// return ret;
		// }

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

	private void afterRegisterHandle(UserVO userVO, HttpServletRequest request, HttpServletResponse response) {
		int userId = userVO.getUserId();

		int timestamp = GetDate.getMyTimeInMillis();

		String userIdStr = TreeDESUtils.getEncrypt(String.valueOf(timestamp), String.valueOf(userId));
		// todo 用户登陆之后缓存
		// ret.put("connection", useridStr);
		// ret.put("timestamp", timestamp);
		// ret.put("userid", userid);
		// ret.put("couponSendCount", 0);
		// ret.put(UserRegistDefine.STATUS, UserRegistDefine.STATUS_TRUE);
		// ret.put(UserRegistDefine.INFO, "注册成功");

		try {
			WebViewUser webUser = this.getWebViewUserByUserId(userId);
			WebUtils.sessionLogin(request, response, webUser);
		} catch (Exception e) {
			logger.error("用户不存在，有可能读写数据库不同步....", e);
		}

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

				// ret.put("couponSendCount", 8);
			}
		}
	}
}
