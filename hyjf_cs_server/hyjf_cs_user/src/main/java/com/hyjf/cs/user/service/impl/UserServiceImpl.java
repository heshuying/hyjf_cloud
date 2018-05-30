package com.hyjf.cs.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.jwt.JwtHelper;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.beans.*;
import com.hyjf.cs.user.client.AmBankOpenClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.AuthorizedError;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.mq.CouponProducer;
import com.hyjf.cs.user.mq.Producer;
import com.hyjf.cs.user.mq.SmsProducer;
import com.hyjf.cs.user.redis.RedisUtil;
import com.hyjf.cs.user.redis.StringRedisUtil;
import com.hyjf.cs.user.service.ActivityService;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.ClientConstant;
import com.hyjf.cs.user.util.ErrorCodeConstant;
import com.hyjf.cs.user.util.ResultEnum;
import com.hyjf.cs.user.vo.RegisterVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author xiasq
 * @version UserServiceImpl, v0.1 2018/4/11 9:34
 */

@Service
public class UserServiceImpl implements UserService  {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private AmUserClient amUserClient;

	@Autowired
	private AmBankOpenClient amBankOpenClient;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private CouponProducer couponProducer;

	@Autowired
	private SmsProducer smsProducer;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private StringRedisUtil stringRedisUtil;

	@Autowired
	SystemConfig systemConfig;
	@Value("${hyjf.activity.regist.tzj.id}")
	private String activityIdTzj;
	@Value("${hyjf.activity.888.id}")
	private String activityId;

    /**
     * 1. 必要参数检查 2. 注册 3. 注册后处理
     * @param registerVO
     * @param request
     * @param response
     * @return
     * @throws ReturnMessageException
     */
	@Override
	public UserVO register(RegisterVO registerVO, String ip)
			throws ReturnMessageException {
		// 1. 参数检查
		this.registerCheckParam(registerVO);
		RegisterUserRequest registerUserRequest = new RegisterUserRequest();
		BeanUtils.copyProperties(registerVO, registerUserRequest);
		registerUserRequest.setLoginIp(ip);
		registerUserRequest.setInstCode(1);
		// 2.注册
		UserVO userVO = amUserClient.register(registerUserRequest);
		if (userVO == null) {
			throw new ReturnMessageException(RegisterError.REGISTER_ERROR);
		}

		// 3.注册后处理
		this.afterRegisterHandle(userVO);

		return userVO;
	}

	/**
	 * api注册
	 * @param registerVO
	 * @param ipAddr
	 * @return
	 */
	@Override
	public UserVO apiRegister(@Valid RegisterVO registerVO, String ipAddr) {
		// 1. 参数检查
		this.registerCheckParam(registerVO);
		RegisterUserRequest registerUserRequest = new RegisterUserRequest();
		BeanUtils.copyProperties(registerVO, registerUserRequest);
		registerUserRequest.setLoginIp(ipAddr);
		// 根据机构编号检索机构信息
		HjhInstConfigVO instConfig = this.amUserClient.selectInstConfigByInstCode(registerVO.getInstCode());
		// 机构编号
		if (instConfig == null) {
			throw new ReturnMessageException(RegisterError.INSTCODE_ERROR);

		}
		// TODO: 2018/5/28 验签
		registerUserRequest.setInstCode(instConfig.getInstType());
		// 2.注册
		UserVO userVO = amUserClient.register(registerUserRequest);
		if (userVO == null) {
			throw new ReturnMessageException(RegisterError.REGISTER_ERROR);
		}
		return userVO;
	}

	/**
	 * 账户设置信息查询
	 * @param token
	 * @return
	 */
	@Override
	public String safeInit(String token) {
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("url","user/safe/account-setting-index");
		WebViewUser webViewUser = (WebViewUser) redisUtil.get(token);
		resultMap.put("webViewUser", webViewUser);
		if (webViewUser.getTruename() != null && webViewUser.getTruename().length() >= 1) {
			resultMap.put("truename", webViewUser.getTruename().substring(0, 1) + "**");
		}
		if (webViewUser.getIdcard() != null && webViewUser.getIdcard().length() >= 15) {
			resultMap.put("idcard", webViewUser.getIdcard().substring(0, 3) + "***********" + webViewUser.getIdcard().substring(webViewUser.getIdcard().length() - 4));
		}
		if (webViewUser.getMobile() != null && webViewUser.getMobile().length() == 11) {
			resultMap.put("mobile", webViewUser.getMobile().substring(0, 3) + "****" + webViewUser.getMobile().substring(webViewUser.getMobile().length() - 4));
		}
		if (webViewUser.getEmail() != null && webViewUser.getEmail().length() >= 2) {
			String emails[] = webViewUser.getEmail().split("@");
			resultMap.put("email", AsteriskProcessUtil.getAsteriskedValue(emails[0], 2, emails[0].length() -2) + "@" + emails[1]);
		}
		UserVO user = amUserClient.findUserById(webViewUser.getUserId());
		// 用户角色
		UserInfoVO userInfo = this.amUserClient.findUsersInfoById(webViewUser.getUserId());
		resultMap.put("roleId", userInfo.getRoleId());
		// 是否设置交易密码
		resultMap.put("isSetPassword", user.getIsSetPassword());
		// 紧急联系人类型
		// TODO: 2018/5/29 紧急联系人
		/*List<ParamName> paramList = safeService.getParamNameList("USER_RELATION");
		JSONArray result = new JSONArray();
		for (int i = 0; i < paramList.size(); i++) {
			JSONObject json = new JSONObject();
			json.put("name", paramList.get(i).getName());
			json.put("value", paramList.get(i).getNameCd());
			result.add(json);
		}
		resultMap.put("userRelation", result);*/
		BankOpenAccountVO bankOpenAccount =amBankOpenClient.selectById(webViewUser.getUserId());
		AccountChinapnrVO chinapnr = amUserClient.getAccountChinapnr(webViewUser.getUserId());
		resultMap.put("bankOpenAccount", bankOpenAccount);
		resultMap.put("chinapnr", chinapnr);

		UserEvalationResultVO userEvalationResult = amBankOpenClient.selectUserEvalationResultByUserId(webViewUser.getUserId());
		if (userEvalationResult != null && userEvalationResult.getId() != 0) {
			//获取评测时间加一年的毫秒数18.2.2评测 19.2.2
			Long lCreate = GetDate.countDate(userEvalationResult.getCreateTime(),1,1).getTime();
			//获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
			Long lNow = GetDate.countDate(new Date(), 5,1).getTime();
			if (lCreate <= lNow) {
				//已过期需要重新评测 2是已过期
				resultMap.put("ifEvaluation", 2);
			} else {
				// ifEvaluation是否已经调查表示 1是已调查，0是未调查
				resultMap.put("ifEvaluation", 1);
				// userEvalationResult 测评结果
				resultMap.put("userEvalationResult",userEvalationResult);
			}
		} else {
			resultMap.put("ifEvaluation", 0);
		}
		HjhUserAuthVO hjhUserAuth=amUserClient.getHjhUserAuthByUserId(webViewUser.getUserId());
		resultMap.put("hjhUserAuth", getUserAuthState(hjhUserAuth));
		// 获得是否授权
		// 获取用户上传头像
		String imghost = UploadFileUtils.getDoPath(systemConfig.getHeadUrl());
		imghost = imghost.substring(0, imghost.length() - 1);
		// 实际物理路径前缀2
		String fileUploadTempPath = UploadFileUtils.getDoPath(PropUtils.getSystem("file.upload.head.path"));
		if(org.apache.commons.lang3.StringUtils.isNotEmpty( user.getIconurl())){
			resultMap.put("iconUrl", imghost + fileUploadTempPath + user.getIconurl());
		}
		resultMap.put("inviteLink", systemConfig.getWebHost()+"/user/regist/init?from="+webViewUser.getUserId());
		 return JSONObject.toJSONString(resultMap, true);
	}

	/**
	 * 获得用户授权状态信息
	 * 自动投标状态          缴费授权状态      还款授权状态
	 * @param auth
	 * @return
	 */
	public HjhUserAuthVO getUserAuthState(HjhUserAuthVO auth) {
		// 缴费授权
		int paymentAuth = valdateAuthState(auth.getAutoPaymentStatus(),auth.getAutoPaymentEndTime());
		auth.setAutoPaymentStatus(paymentAuth);
		// 还款授权
		int repayAuth = valdateAuthState(auth.getAutoRepayStatus(),auth.getAutoRepayEndTime());
		auth.setAutoRepayStatus(repayAuth);
		// 自动投资授权
		int invesAuth = valdateAuthState(auth.getAutoInvesStatus(),auth.getAutoBidEndTime());
		auth.setAutoInvesStatus(invesAuth);
		return auth;
	}

	/**
	 * 检查是否授权  0未授权  1已授权
	 * @param status
	 * @param endTime
	 * @return
	 */
	private int valdateAuthState(Integer status, String endTime) {
		String nowTime = GetDate.date2Str(new Date(),GetDate.yyyyMMdd);
		if(endTime==null || status==null){
			return 0;
		}
		if (status - 0 == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
			return 0;
		}
		return 1;
	}

	@Override
	public boolean existUser(String mobile) {
		UserVO userVO = amUserClient.findUserByMobile(mobile);
		return userVO == null ? false : true;
	}

	/**
	 *登录
	 * @param loginUserName
	 *           手机号
	 * @param loginPassword
	 * @param ip
	 */
	@Override
	public UserVO login(String loginUserName, String loginPassword, String ip) {
		if (checkMaxLength(loginUserName, 16) || checkMaxLength(loginPassword, 32)) {
			throw new ReturnMessageException(LoginError.USER_LOGIN_ERROR);
		}

		// 获取密码错误次数
		String errCount = stringRedisUtil.get(RedisKey.PASSWORD_ERR_COUNT + loginUserName);
		if (StringUtils.isNotBlank(errCount) && Integer.parseInt(errCount) > 6) {
			throw new ReturnMessageException(LoginError.PWD_ERROR_TOO_MANEY_ERROR);
		}
		return this.doLogin(loginUserName, loginPassword, ip);
	}

	/**
	 * 登录处理
	 *
	 * @param loginUserName
	 * @param loginPassword
	 * @return
	 */
	private UserVO doLogin(String loginUserName, String loginPassword, String ip) {
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
			if (userVO.getStatus() == 1) {
				throw new ReturnMessageException(LoginError.USER_INVALID_ERROR);
			}

			// 更新登录信息
			amUserClient.updateLoginUser(userId, ip);

			// 1. 登录成功将登陆密码错误次数的key删除
			stringRedisUtil.delete(RedisKey.PASSWORD_ERR_COUNT + loginUserName);

			// 2. 缓存
			String token = generatorToken(userVO.getUserId(), userVO.getUsername());
			WebViewUser webViewUser = new WebViewUser();
			BeanUtils.copyProperties(userVO,webViewUser);
			webViewUser.setToken(token);
			redisUtil.setEx(RedisKey.USER_TOKEN_REDIS + token, webViewUser, 7 * 24 * 60 * 60, TimeUnit.SECONDS);

			// 3. todo 登录时自动同步线下充值记录

			return userVO;
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
		if (StringUtils.isEmpty(value)) {
			return true;
		}
		if (value.length() > max) {
			return true;
		}
		return false;
	}

	/**
	 * 注册参数校验
	 *
	 * @param registerVO
	 */
	private void registerCheckParam(RegisterVO registerVO) {
		if (registerVO == null) {
			throw new ReturnMessageException(RegisterError.REGISTER_ERROR);
		}

		String mobile = registerVO.getMobilephone();
		if (StringUtils.isEmpty(mobile)) {
			throw new ReturnMessageException(RegisterError.MOBILE_IS_NOT_NULL_ERROR);
		}

		String smsCode = registerVO.getSmsCode();
		if (StringUtils.isEmpty(smsCode)) {
			throw new ReturnMessageException(RegisterError.SMSCODE_IS_NOT_NULL_ERROR);
		}

		String password = registerVO.getPassword();
		if (StringUtils.isEmpty(password)) {
			throw new ReturnMessageException(RegisterError.PASSWORD_IS_NOT_NULL_ERROR);
		}

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

		String verificationType = CommonConstant.PARAM_TPL_ZHUCE;
		/*int cnt = amUserClient.checkMobileCode(mobile, smsCode, verificationType, CommonConstant.CLIENT_PC,
				CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED);
		if (cnt == 0) {
			throw new ReturnMessageException(RegisterError.SMSCODE_INVALID_ERROR);
		}*/
		String reffer = registerVO.getReffer();
		if (isNotBlank(reffer) && amUserClient.countUserByRecommendName(reffer) <= 0) {
			throw new ReturnMessageException(RegisterError.REFFER_INVALID_ERROR);
		}
	}

	/**
	 * 注册后处理: 1. 登录 2. 判断投之家着陆页送券 3. 注册送188红包
	 *
	 * @param userVO
	 */
	private void afterRegisterHandle(UserVO userVO) {
		int userId = userVO.getUserId();

		// 1. 注册成功之后登录
		String token = generatorToken(userId, userVO.getUsername());
		WebViewUser webViewUser = new WebViewUser();
		BeanUtils.copyProperties(userVO,webViewUser);
		webViewUser.setToken(token);
		redisUtil.setEx(RedisKey.USER_TOKEN_REDIS + token, webViewUser, 7 * 24 * 60 * 60, TimeUnit.SECONDS);

		// 2. 投之家用户注册送券活动
		// 活动有效期校验
		if (!activityService.checkActivityIfAvailable(activityIdTzj)) {
			// 投之家用户额外发两张加息券
			if ("touzhijia".equals(userVO.getReferrerUserName())) {
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
					couponProducer.messageSend(new Producer.MassageContent(MQConstant.REGISTER_COUPON_TOPIC,
							MQConstant.TZJ_REGISTER_INTEREST_TAG, JSON.toJSONBytes(json)));
				} catch (MQException e) {
					logger.error("投之家用户注册送券失败....userId is :" + userId, e);
				}
			}
		}

		// 3. 注册送188元新手红包
		if (!activityService.checkActivityIfAvailable(activityId)) {
			try {
				JSONObject params = new JSONObject();
				params.put("mqMsgId", GetCode.getRandomCode(10));
				params.put("userId", String.valueOf(userId));
				params.put("sendFlg", "11");
				couponProducer.messageSend(new Producer.MassageContent(MQConstant.REGISTER_COUPON_TOPIC,
						MQConstant.REGISTER_COUPON_TAG, JSON.toJSONBytes(params)));
			} catch (Exception e) {
				logger.error("注册发放888红包失败...", e);
			}

			// 短信通知用户发券成功
			SmsMessage smsMessage = new SmsMessage(userId, null, userVO.getMobile(), null,
					MessageConstant.SMSSENDFORMOBILE, null, CustomConstants.PARAM_TPL_TZJ_188HB,
					CustomConstants.CHANNEL_TYPE_NORMAL);
			try {
				smsProducer.messageSend(
						new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(smsMessage)));
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
		Map map = ImmutableMap.of("userId", String.valueOf(userId), "username", username, "ts",
				String.valueOf(Instant.now().getEpochSecond()));
		String token = JwtHelper.genToken(map);
		return token;
	}

	/**
	 * 自动投资、债转授权
	 * @param token
	 * @param client 0web 1wechat 2app
	 * @param type 1表示投资 2表示债转
	 * @param request
	 * @return
	 */
	@Override
	public BankCallBean userCreditAuthInves(String token, Integer client, String type, String channel, String lastSrvAuthCode,String smsCode) {
		WebViewUser user = (WebViewUser) redisUtil.get(token);
		//检查用户信息
		UserVO users = this.checkUserMessage(user,lastSrvAuthCode,smsCode);
		// 判断是否授权过
		// TODO: 2018/5/24 判断授权方法有不同处理 
		HjhUserAuthVO hjhUserAuth=amUserClient.getHjhUserAuthByUserId(user.getUserId());
		if(hjhUserAuth!=null&&hjhUserAuth.getAutoCreditStatus().intValue()==1){
			throw new ReturnMessageException(AuthorizedError.CANNOT_REPEAT_ERROR);
		}
		// 组装发往江西银行参数
		BankCallBean bean = getCommonBankCallBean(user,type,client,channel,lastSrvAuthCode,smsCode);
		// 插入日志
		this.insertUserAuthLog(users, bean,client,type);
		return bean;
	}


	/**
	 * 组装发往江西银行参数
	 * @param users
	 * @param type
	 * @param lastSrvAuthCode
	 * @param smsCode
	 * @return
	 */
	private BankCallBean getCommonBankCallBean(WebViewUser users,String type,Integer client, String channel,String lastSrvAuthCode,String smsCode) {
		String remark = "";
		String txcode = "";
		String retUrl = systemConfig.getWebHost()+ClientConstant.CLIENT_HEADER_MAP.get(client)+"/user";
		String bgRetUrl = systemConfig.getWebHost()+ClientConstant.CLIENT_HEADER_MAP.get(client)+"/user";
		BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10,txcode,users.getUserId(),channel);
		if(BankCallConstant.QUERY_TYPE_1.equals(type)){
			remark = "投资人自动投标签约增强";
			retUrl += "/userAuthInvesReturn";
			bgRetUrl+= "/userAuthInvesBgreturn";
			bean.setTxCode(BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS);
			bean.setDeadline(GetDate.date2Str(GetDate.countDate(1,5),new SimpleDateFormat("yyyyMMdd")));
			bean.setTxAmount("1000000");
			bean.setTotAmount("1000000000");
		} else if(BankCallConstant.QUERY_TYPE_2.equals(type)){
			remark = "投资人自动债权转让签约增强";
			retUrl += "/credituserAuthInvesReturn";
			bgRetUrl+="/credituserAuthInvesBgreturn";
			bean.setTxCode(BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS);
		}
		//1wechat 2app
		if(client == 1 || client == 2){
			bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_PLUS);
			bean.setOrderId(bean.getLogOrderId());
			bean.setAccountId(users.getBankAccount());
			bean.setForgotPwdUrl(CustomConstants.FORGET_PASSWORD_URL);
			bean.setRetUrl(retUrl);
			bean.setNotifyUrl(bgRetUrl);
			bean.setLastSrvAuthCode(lastSrvAuthCode);
			bean.setSmsCode(smsCode);
			bean.setLogRemark(remark);
		}else {
			String orderId = GetOrderIdUtils.getOrderId2(users.getUserId());
			// 取得用户在江西银行的客户号
			BankOpenAccountVO bankOpenAccount =amBankOpenClient.selectById(users.getUserId());
			bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_PLUS);
			bean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE));
			bean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE));
			bean.setTxDate(GetOrderIdUtils.getTxDate());
			bean.setTxTime(GetOrderIdUtils.getTxTime());
			bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
			bean.setChannel(channel);
			bean.setAccountId(bankOpenAccount.getAccount());
			bean.setOrderId(orderId);
			bean.setForgotPwdUrl(CustomConstants.FORGET_PASSWORD_URL);
			bean.setRetUrl(retUrl);
			bean.setNotifyUrl(bgRetUrl);
			bean.setLastSrvAuthCode(lastSrvAuthCode);
			bean.setSmsCode(smsCode);
			// 操作者ID
			bean.setLogUserId(String.valueOf(users.getUserId()));
			bean.setLogOrderId(orderId);
			bean.setLogRemark(remark);
			bean.setLogClient(client);
		}
		return bean;
	}

	/**
	 * 组装发往江西银行参数
	 * @param accountId
	 * @param userid
	 * @param type
	 * @param channel
	 * @param lastSrvAuthCode
	 * @param smsCode
	 * @return
	 */
	private BankCallBean getCommonBankCallBean(String accountId, Integer userid, String type, String channel, String lastSrvAuthCode, String smsCode) {
		String remark = "";
		String txcode = "";
		// 构造函数已经设置
		// 版本号  交易代码  机构代码  银行代码  交易日期  交易时间  交易流水号   交易渠道
		BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10,txcode,userid,channel);
		if(BankCallConstant.QUERY_TYPE_1.equals(type)){
			remark = "投资人自动投标签约增强";
			bean.setTxCode(BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS);
			bean.setDeadline(GetDate.date2Str(GetDate.countDate(1,5),new SimpleDateFormat("yyyyMMdd")));
			bean.setTxAmount("1000000");
			bean.setTotAmount("1000000000");
		} else if(BankCallConstant.QUERY_TYPE_2.equals(type)){
			remark = "投资人自动债权转让签约增强";
			bean.setTxCode(BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS);
		}
		bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_PLUS);
		bean.setOrderId(bean.getLogOrderId());
		bean.setAccountId(accountId);
		bean.setForgotPwdUrl(CustomConstants.FORGET_PASSWORD_URL);
		bean.setLastSrvAuthCode(lastSrvAuthCode);
		bean.setSmsCode(smsCode);
		bean.setLogRemark(remark);

		return bean;
	}

	/**
	 * 检查用户信息
	 * @param user
	 * @param lastSrvAuthCode
	 * @param smsCode
	 */
	public UserVO checkUserMessage(WebViewUser user,String lastSrvAuthCode,String smsCode){
		if (user == null) {
			throw new ReturnMessageException(AuthorizedError.USER_LOGIN_ERROR);
		}
		// 检查数据是否完整
		if (Validator.isNull(lastSrvAuthCode) || Validator.isNull(smsCode)) {
			throw new ReturnMessageException(AuthorizedError.PARAM_ERROR);
		}
		UserVO users= amUserClient.findUserById(user.getUserId());
		if (users.getBankOpenAccount()==0) {
			throw new ReturnMessageException(AuthorizedError.NOT_REGIST_ERROR);
		}
		// 判断用户是否设置过交易密码
		if (users.getIsSetPassword() == 0) {
			throw new ReturnMessageException(AuthorizedError.NOT_SET_PWD_ERROR);
		}
		return users;
	}

	/**
	 *插入日志
	 * @param user
	 * @param bean
	 * @param client
	 * @param authType
	 */
	public void insertUserAuthLog(UserVO user, BankCallBean bean, Integer client, String authType) {
		Date nowTime=GetDate.getNowTime();
		HjhUserAuthLogVO hjhUserAuthLog=new HjhUserAuthLogVO();
		hjhUserAuthLog.setUserId(user.getUserId());
		hjhUserAuthLog.setUserName(user.getUsername());
		hjhUserAuthLog.setOrderId(bean.getLogOrderId());
		hjhUserAuthLog.setOrderStatus(2);
		if(authType!=null&&authType.equals(BankCallConstant.QUERY_TYPE_2)){
			hjhUserAuthLog.setAuthType(4);
		}else{
			hjhUserAuthLog.setAuthType(Integer.valueOf(authType));
		}
		hjhUserAuthLog.setOperateEsb(client);
		hjhUserAuthLog.setCreateUser(user.getUserId());
		hjhUserAuthLog.setCreateTime(nowTime);
		hjhUserAuthLog.setUpdateTime(nowTime);
		hjhUserAuthLog.setUpdateUser(user.getUserId());
		hjhUserAuthLog.setDelFlg(0);
		amUserClient.insertUserAuthLog(hjhUserAuthLog);
	}

	/**
	 * 检查是否已经授权过了
	 * @param hjhUserAuth
	 * @param txcode
	 * @return
	 */
	public boolean checkIsAuth(HjhUserAuthVO hjhUserAuth , String txcode) {
		String endTime = "";
		int status = 0;
		String nowTime = GetDate.date2Str(new Date(),GetDate.yyyyMMdd);
		// 缴费授权
		if(hjhUserAuth==null){
			return false;
		}
		if (BankCallConstant.TXCODE_PAYMENT_AUTH_PAGE.equals(txcode)) {
			endTime = hjhUserAuth.getAutoPaymentEndTime();
			status = hjhUserAuth.getAutoPaymentStatus();
		}else if(BankCallConstant.TXCODE_REPAY_AUTH_PAGE.equals(txcode)){
			endTime = hjhUserAuth.getAutoRepayEndTime();
			status = hjhUserAuth.getAutoRepayStatus();
		}else if(BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS.equals(txcode)){
			endTime = hjhUserAuth.getAutoBidEndTime();
			status = hjhUserAuth.getAutoInvesStatus();
		}
		// 0是未授权
		if (status - 0 == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
			return false;
		}
		return true;
	}

	/**
	 * web自动投资授权同步回调
	 * @param token
	 * @param bean
	 * @param request
	 * @return
	 */
	@Override
	public Map<String,String> userAuthReturn(String token, BankCallBean bean,String urlType,String isSuccess) {
		Map<String,String> resultMap = new HashMap<>();
		resultMap.put("status", "success");
		bean.convert();
		WebViewUser user = (WebViewUser) redisUtil.get(token);
		if (user == null) {
			throw new ReturnMessageException(AuthorizedError.USER_LOGIN_ERROR);
		}
		if (isSuccess == null || !"1".equals(isSuccess)) {
			resultMap.put("status", "fail");
		}
		logger.info("自动投资授权同步回调调用查询接口查询状态结束  结果为:" + (bean == null ? "空" : bean.getRetCode()));
		HjhUserAuthVO hjhUserAuth=amUserClient.getHjhUserAuthByUserId(user.getUserId());
		if(hjhUserAuth.getAutoCreditStatus()==0) {
			resultMap.put("typeURL", urlType);
		}else {
			resultMap.put("typeURL", "0");
		}
		return resultMap;
	}

	/**
	 * 异步回调接口
	 * @param bean
	 * @return
	 */
	@Override
	public String userBgreturn(BankCallBean bean) {
		BankCallResult result = new BankCallResult();
		logger.info("[用户授权异步回调开始]");
		bean.convert();
		Integer userId = Integer.parseInt(bean.getLogUserId());
		// 查询用户开户状态
		UserVO user= amUserClient.findUserById(userId);
		// 成功
		if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
			try {
				// 更新签约状态和日志表
				BankRequest bankRequest = new BankRequest();
				BeanUtils.copyProperties(bean,bankRequest);
				amUserClient.updateUserAuthInves(bankRequest);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("userAuthInvesBgreturn",e);
			}
		}
		logger.info("[用户授权完成后,回调结束]");
		result.setStatus(true);
		return JSONObject.toJSONString(result, true);
	}

	/**
	 * app wechat授权自动债转、投资同步回调
	 * @param token
	 * @param bean
	 * @param userAutoType 1债转 0投资
	 * @param request
	 * @return
	 */
	@Override
	public Map<String,BaseMapBean> userAuthCreditReturn(String token, BankCallBean bean,String userAutoType, String sign,String isSuccess) {
		bean.convert();
		// 用户ID
		Integer userId = Integer.parseInt(bean.getLogUserId());
		HjhUserAuthVO hjhUserAuth=amUserClient.getHjhUserAuthByUserId(userId);
		if (isSuccess == null || !"1".equals(isSuccess)|| hjhUserAuth == null||hjhUserAuth.getAutoCreditStatus()!=1) {
			if (ClientConstant.INVES_AUTO_TYPE.equals(userAutoType)){
				return getErrorMap(ResultEnum.USER_ERROR_204,sign,userAutoType, hjhUserAuth);
			}else {
				return getErrorMap(ResultEnum.USER_ERROR_205,sign,userAutoType, hjhUserAuth);
			}
		}else {
			return getSuccessMap(sign, userAutoType, hjhUserAuth);
		}
	}

	@Override
	public BankCallBean apiUserAuth(String type, String smsSeq, AutoPlusRequestBean payRequestBean) {
		BankOpenAccountVO bankOpenAccount = this.amBankOpenClient.selectByAccountId(payRequestBean.getAccountId());
		UserVO user= amUserClient.findUserById(bankOpenAccount.getUserId());
		Integer userId = user.getUserId();
		// 同步调用路径
		String retUrl = systemConfig.getWebHost()
				+ "/server/autoPlus/userAuthInvesReturn?acqRes="
				+ payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getRetUrl().replace("#", "*-*-*");
		// 异步调用路
		String bgRetUrl =systemConfig.getWebHost()
				+ "/server/autoPlus/userAuthInvesBgreturn?acqRes="
				+ payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getNotifyUrl().replace("#", "*-*-*");
		// 组装发往江西银行参数
		BankCallBean bean = getCommonBankCallBean(payRequestBean.getAccountId(),userId,type,payRequestBean.getChannel(),smsSeq,payRequestBean.getSmsCode());
		bean.setRetUrl(retUrl);
		bean.setNotifyUrl(bgRetUrl);
		// 插入日志
		this.insertUserAuthLog(user, bean,1, BankCallConstant.QUERY_TYPE_1);
		logger.info("自动投资授权申请end");
		return bean;
	}



	/**
	 * 组装跳转错误页面MV
	 * @param param
	 * @param sign
	 * @param type
	 * @param hjhUserAuth
	 * @return
	 */
	private Map<String,BaseMapBean> getErrorMap(ResultEnum param,String sign, String type, HjhUserAuthVO hjhUserAuth) {
		Map<String,BaseMapBean> result = new HashMap<>();
		BaseMapBean baseMapBean = new BaseMapBean();
		baseMapBean.set(CustomConstants.APP_STATUS, param.getStatus());
		baseMapBean.set(CustomConstants.APP_STATUS_DESC, param.getStatusDesc());
		baseMapBean.set("autoInvesStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoInvesStatus()==null?"0":hjhUserAuth.getAutoInvesStatus()+ "");
		baseMapBean.set("autoCreditStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoCreditStatus()==null?"0":hjhUserAuth.getAutoCreditStatus() + "");
		baseMapBean.set("userAutoType", type);
		baseMapBean.set(CustomConstants.APP_SIGN, sign);
		baseMapBean.setCallBackAction(systemConfig.getWebHost()+"/user/setting/authorization/result/failed");
		result.put("callBackForm", baseMapBean);
		return result;
	}

	/**
	 * 组装跳转成功页面MV
	 * @param sign
	 * @param type
	 * @param autoInvesStatus
	 * @param autoCreditStatus
	 * @return
	 */
	private Map<String,BaseMapBean> getSuccessMap(String sign, String type, HjhUserAuthVO hjhUserAuth) {
		Map<String,BaseMapBean> result = new HashMap<>();
		BaseMapBean baseMapBean = new BaseMapBean();
		baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.SUCCESS.getStatus());
		baseMapBean.set(CustomConstants.APP_STATUS_DESC, ResultEnum.SUCCESS.getStatusDesc());
		baseMapBean.set(CustomConstants.APP_SIGN, sign);
		baseMapBean.set("autoInvesStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoInvesStatus()==null?"0":hjhUserAuth.getAutoInvesStatus()+ "");
		baseMapBean.set("autoCreditStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoCreditStatus()==null?"0":hjhUserAuth.getAutoCreditStatus() + "");
		baseMapBean.set("userAutoType", type);
		baseMapBean.setCallBackAction(systemConfig.getWebHost()+"/user/setting/authorization/result/success");
		result.put("callBackForm", baseMapBean);
		return result;
	}

	/**
	 * 验证外部请求签名
	 *
	 * @param paramBean
	 * @return
	 */
	@Override
	public boolean verifyRequestSign(BaseBean paramBean, String methodName) {

		String sign = StringUtils.EMPTY;

		// 机构编号必须参数
		String instCode = paramBean.getInstCode();
		if (StringUtils.isEmpty(instCode)) {
			return false;
		}

		if (BaseDefine.METHOD_BORROW_AUTH_INVES.equals(methodName)) {
			// 自动投资 增强
			AutoPlusRequestBean bean = (AutoPlusRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getAccountId() + bean.getSmsCode() + bean.getTimestamp();
		}

		return ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
	}

	/**
	 * 修改登陆密码
	 * @param userId
	 * @param oldPW
	 * @param newPW
	 * @return
	 */
	@Override
	public JSONObject updatePassWd(Integer userId, String oldPW, String newPW){
		logger.info("UserService.updatePassWd run...userId is :{}, oldPW is :{}, newPW is :{}",userId,oldPW,newPW);
		return amUserClient.updatePassWd(userId, oldPW, newPW);
	}

	@Override
	public Map<String,String> checkParam(AutoPlusRequestBean payRequestBean){
		Map<String,String> resultMap = new HashMap<>();
		// 检查参数是否为空
		if(payRequestBean.checkParmIsNull()){
			payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000001, "请求参数异常"));
			logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
			return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000001);
		}
		// 验签
		if (!this.verifyRequestSign(payRequestBean, "/server/autoPlus/userAuthInves")) {
			payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000002, "验签失败"));
			logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
			return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000002);
		}
		// 根据电子账户号查询用户ID
		BankOpenAccountVO bankOpenAccount = this.amBankOpenClient.selectByAccountId(payRequestBean.getAccountId());
		if(bankOpenAccount == null){
			logger.info("-------------------没有根据电子银行卡找到用户"+payRequestBean.getAccountId()+"！--------------------");
			Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000004,"没有根据电子银行卡找到用户");
			payRequestBean.doNotify(params);
			return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000004);
		}
		// 检查用户是否存在
		UserVO user= amUserClient.findUserById(bankOpenAccount.getUserId());
		if (user == null) {
			logger.info("-------------------用户不存在汇盈金服账户！"+payRequestBean.getAccountId()+"！--------------------");
			Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000007,"用户不存在汇盈金服账户！");
			payRequestBean.doNotify(params);
			return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000007);
		}
		if (user.getBankOpenAccount().intValue() != 1) {
			logger.info("-------------------用户未开户！"+payRequestBean.getAccountId()+"！--------------------");
			Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000006,"用户未开户！");
			payRequestBean.doNotify(params);
			return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000006);
		}
		// 检查是否设置交易密码
		Integer passwordFlag = user.getIsSetPassword();
		if (passwordFlag != 1) {
			logger.info("-------------------未设置交易密码！"+payRequestBean.getAccountId()+"！--------------------status"+user.getIsSetPassword());
			Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_TP000002,"未设置交易密码！");
			payRequestBean.doNotify(params);
			return getErrorMV(payRequestBean,  ErrorCodeConstant.STATUS_TP000002);
		}
		// TODO: 2018/5/24 xiashuqing 根据订单号查询授权码
		//this.autoPlusService.selectBankSmsSeq(userId, BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS);
		String smsSeq = null;
		if (StringUtils.isBlank(smsSeq)) {
			logger.info("-------------------授权码为空！"+payRequestBean.getAccountId()+"！--------------------status"+user.getIsSetPassword());
			Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000008,"未查询到短信授权码！");
			payRequestBean.doNotify(params);
			return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000008);
		}
		logger.info("-------------------授权码为！"+smsSeq+"电子账户号"+payRequestBean.getAccountId()+"！--------------------status"+user.getIsSetPassword());
		// 查询是否已经授权过
		HjhUserAuthVO hjhUserAuth=amUserClient.getHjhUserAuthByUserId(user.getUserId());
		if(hjhUserAuth!=null&&hjhUserAuth.getAutoInvesStatus()==1){
			logger.info("-------------------已经授权过！"+payRequestBean.getAccountId());
			Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000009,"已授权,请勿重复授权！");
			payRequestBean.doNotify(params);
			return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000009);
		}else {
			resultMap.put("isSuccess","true");
			resultMap.put("smsSeq",smsSeq);
			return null;
		}
	}

	@Override
	public Map<String,String> getErrorMV(AutoPlusRequestBean payRequestBean, String status) {
		Map<String,String> result = new HashMap<>();
		BaseResultBean resultBean = new BaseResultBean();
		resultBean.setStatusForResponse(status);
		result.put("callBackAction",payRequestBean.getRetUrl());
		result.put("chkValue", resultBean.getChkValue());
		result.put("status", resultBean.getStatus());
		result.put("acqRes",payRequestBean.getAcqRes());
		result.put("isSuccess","false");
		return result;
	}

	/**
	 * 获取用户对象
	 * @param userId
	 * @return
	 */
	@Override
	public UserVO queryUserByUserId(Integer userId) {
		UserVO user = amUserClient.findUserById(userId);
		return user;
	}

	/**
	 * 更新用户信息
	 * @param userVO
	 * @return
	 */
	@Override
	public int updateUserByUserId(UserVO userVO) {
		return amUserClient.updateUserById(userVO);
	}


}