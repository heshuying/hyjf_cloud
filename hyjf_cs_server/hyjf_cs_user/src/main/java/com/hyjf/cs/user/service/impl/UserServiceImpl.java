package com.hyjf.cs.user.service.impl;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.HjhUserAuthLogVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.ActivityService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
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
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.jwt.JwtHelper;
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
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterVO;
import org.springframework.web.servlet.ModelAndView;

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
		if (userVO == null) {
			throw new ReturnMessageException(RegisterError.REGISTER_ERROR);
		}

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
	public UserVO login(String loginUserName, String loginPassword, String ip) {
		if (checkMaxLength(loginUserName, 16) || checkMaxLength(loginUserName, 32)) {
			throw new ReturnMessageException(LoginError.USER_LOGIN_ERROR);
		}

		// 获取密码错误次数
		String errCount = stringRedisUtil.get(RedisKey.PASSWORD_ERR_COUNT + loginUserName);
		if (StringUtils.isNotBlank(errCount) && Integer.parseInt(errCount) > 6) {
			throw new ReturnMessageException(LoginError.PWD_ERROR_TOO_MANEY_ERROR);
		}
		return this.doLogin(loginUserName, loginPassword, ip);
	}

	@Override
	public ModelAndView userAuthInves(String token,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
		String smsCode = request.getParameter("smsCode");
		WebViewUser user = (WebViewUser) redisUtil.get(token);
		if (user == null) {
			modelAndView = new ModelAndView("/bank/user/autoplus/auto-tender-fail");
			modelAndView.addObject("message", "登录失效，请重新登陆");
			return modelAndView;
		}
		// 检查数据是否完整
		if (Validator.isNull(lastSrvAuthCode) || Validator.isNull(smsCode)) {
			modelAndView = new ModelAndView("/bank/user/autoplus/auto-tender-fail");
			modelAndView.addObject("message", "参数错误，请重试");
			return modelAndView;
		}
		UserVO users= amUserClient.findUserById(user.getUserId());
		if (users.getBankOpenAccount()==0) {
			modelAndView = new ModelAndView("/bank/user/autoplus/auto-tender-fail");
			modelAndView.addObject("message", "用户未开户！");
			return modelAndView;
		}
		// 判断用户是否设置过交易密码
		if (users.getIsSetPassword() == 0) {
			modelAndView = new ModelAndView("/bank/user/autoplus/auto-tender-fail");
			modelAndView.addObject("message", "用户未设置交易密码");
			return modelAndView;
		}
		// 判断是否授权过  todo
		HjhUserAuthVO hjhUserAuth=amUserClient.getHjhUserAuthByUserId(user.getUserId());
		if(hjhUserAuth!=null&&hjhUserAuth.getAutoCreditStatus().intValue()==1){
			modelAndView = new ModelAndView("/bank/user/autoplus/auto-tender-fail");
			modelAndView.addObject("message", "用户已授权,无需重复授权");
			return modelAndView;
		}
		// 组装发往江西银行参数
		BankCallBean bean = getCommonBankCallBean(user,2,lastSrvAuthCode,smsCode);
		// 插入日志
		Integer nowTime=GetDate.getNowTime10();
		HjhUserAuthLogVO hjhUserAuthLog=new HjhUserAuthLogVO();
		hjhUserAuthLog.setUserId(users.getUserId());
		hjhUserAuthLog.setUserName(users.getUsername());
		hjhUserAuthLog.setOrderId(bean.getLogOrderId());
		hjhUserAuthLog.setOrderStatus(2);
		if(BankCallConstant.QUERY_TYPE_2!=null){
			hjhUserAuthLog.setAuthType(4);
		}else{
			hjhUserAuthLog.setAuthType(Integer.valueOf(BankCallConstant.QUERY_TYPE_2));
		}
		hjhUserAuthLog.setOperateEsb(0);
		hjhUserAuthLog.setCreateUser(user.getUserId());
		hjhUserAuthLog.setCreateTime(nowTime);
		hjhUserAuthLog.setUpdateTime(nowTime);
		hjhUserAuthLog.setUpdateUser(user.getUserId());
		hjhUserAuthLog.setDelFlg(0);
		this.amUserClient.insertUserAuthLog(hjhUserAuthLog);
		// 跳转到汇付天下画面
		try {
			modelAndView = BankCallUtils.callApi(bean);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView = new ModelAndView("/bank/user/autoplus/auto-tender-fail");
			modelAndView.addObject("message", "调用银行接口失败！");
		}

		return modelAndView;
	}

	/**
	 * 组装发往江西银行参数
	 * @param users
	 * @param type
	 * @param lastSrvAuthCode
	 * @param smsCode
	 * @return
	 */
	private BankCallBean getCommonBankCallBean(WebViewUser users,int type,String lastSrvAuthCode,String smsCode) {
		String remark = "";
		String txcode = "";
		// 同步调用路径
		String retUrl = systemConfig.getWebHost()+"/bank/user/autoplus";
		// 异步调用路
		String bgRetUrl = systemConfig.getWebHost()+"/bank/user/autoplus";
		// 构造函数已经设置
		// 版本号  交易代码  机构代码  银行代码  交易日期  交易时间  交易流水号   交易渠道
		BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10,txcode,users.getUserId(),"000002");

		if(type==1){
			remark = "投资人自动投标签约增强";
			retUrl += "/userAuthInvesReturn" + ".do";
			bgRetUrl+= "/userAuthInvesBgreturn" + ".do";
			bean.setTxCode(BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS);
			bean.setDeadline(GetDate.date2Str(GetDate.countDate(1,5),new SimpleDateFormat("yyyyMMdd")));
			bean.setTxAmount("1000000");
			bean.setTotAmount("1000000000");
		} else if(type==2){
			// 2.4.8.投资人自动债权转让签约增强
			remark = "投资人自动债权转让签约增强";
			retUrl += "/credituserAuthInvesReturn" + ".do";
			bgRetUrl+="/credituserAuthInvesBgreturn" + ".do";
			bean.setTxCode(BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS);
		}
		bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_PLUS);
		bean.setOrderId(bean.getLogOrderId());
		// 电子账号
		bean.setAccountId(users.getBankAccount());
		//忘记密码通知地址
		bean.setForgotPwdUrl(CustomConstants.FORGET_PASSWORD_URL);
		bean.setRetUrl(retUrl);
		bean.setNotifyUrl(bgRetUrl);
		bean.setLastSrvAuthCode(lastSrvAuthCode);
		bean.setSmsCode(smsCode);
		bean.setLogRemark(remark);
		return bean;
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
		if (Validator.isNull(mobile)) {
			throw new ReturnMessageException(RegisterError.MOBILE_IS_NOT_NULL_ERROR);
		}

		String smsCode = registerVO.getSmsCode();
		if (Validator.isNull(smsCode)) {
			throw new ReturnMessageException(RegisterError.SMSCODE_IS_NOT_NULL_ERROR);
		}

		String password = registerVO.getPassword();
		if (Validator.isNull(password)) {
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
		int cnt = amUserClient.checkMobileCode(mobile, smsCode, verificationType, CommonConstant.CLIENT_PC,
				CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED);
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
		WebViewUser webViewUser = new WebViewUser();
		BeanUtils.copyProperties(userVO,webViewUser);
		webViewUser.setToken(token);
		redisUtil.setEx(RedisKey.USER_TOKEN_REDIS + token, webViewUser, 7 * 24 * 60 * 60, TimeUnit.SECONDS);

		// 2. 投之家用户注册送券活动
		// 活动有效期校验
		if (!activityService.checkActivityIfAvailable(activityIdTzj)) {
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
					couponProducer.messageSend(new Producer.MassageContent(MQConstant.TZJ_REGISTER_INTEREST_TOPIC, JSON.toJSONBytes(json)));
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
				couponProducer
						.messageSend(new Producer.MassageContent(MQConstant.REGISTER_COUPON_TOPIC, JSON.toJSONBytes(params)));
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
}
