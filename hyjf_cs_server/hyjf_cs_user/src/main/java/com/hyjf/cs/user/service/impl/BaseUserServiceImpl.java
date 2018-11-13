package com.hyjf.cs.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BankSmsLogRequest;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.bean.AccessToken;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.jwt.JwtHelper;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.MD5;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.common.util.ApiSignUtil;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmDataCollectClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.List;

public class BaseUserServiceImpl extends BaseServiceImpl implements BaseUserService {

	protected Logger logger = LoggerFactory.getLogger(BaseUserServiceImpl.class);

	@Autowired
	public AmUserClient amUserClient;
	@Autowired
	public AmTradeClient amTradeClient;
	@Autowired
	public SystemConfig systemConfig;

	@Autowired
	public AmConfigClient amConfigClient;

	@Autowired
	AmDataCollectClient amDataCollectClient;

	@Override
	public boolean existUser(String mobile) {
		UserVO userVO = amUserClient.findUserByMobile(mobile);
		return userVO == null ? false : true;
	}

	/**
	 * @param mobile
	 * @Description 根据手机号查询user
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:36
	 */
	@Override
	public UserVO getUsersByMobile(String mobile) {
		UserVO userVO = amUserClient.findUserByMobile(mobile);
		return userVO;
	}

	@Override
	public HjhUserAuthVO getHjhUserAuth(Integer userId){
		HjhUserAuthVO userAuth = amUserClient.getHjhUserAuthByUserId(userId);
		return userAuth;
	}

	/**
	 * @param userId
	 * @Description 根据userid查询用户
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:37
	 */
	@Override
	public UserVO getUsersById(Integer userId) {
		UserVO userVO = amUserClient.findUserById(userId);
		return userVO;
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
		} else if (BaseDefine.METHOD_BORROW_AUTH_STATE.equals(methodName)) {
			// 授权状态查询
			AutoStateQueryRequest bean = (AutoStateQueryRequest) paramBean;
			sign = bean.getInstCode() +bean.getAccountId() + bean.getTimestamp();
		}else if(BaseDefine.ORGANIZATION_LIST.endsWith(methodName)){
			// 集团组织机构查询
			OrganizationStructureRequestBean bean = (OrganizationStructureRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getTimestamp();
		}else if(BaseDefine.METHOD_BORROW_AUTH_SEND_SMS.endsWith(methodName)){
			// 自动投资 债转  短信验证码
			AutoPlusRequestBean bean = (AutoPlusRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getAccountId() + bean.getMobile() + bean.getSendType() + bean.getTimestamp();
		}else if (BaseDefine.METHOD_SAVE_USER_EVALUATION_RESULTS.equals(methodName)) {
			// 第三方用户测评结果记录
			ThirdPartyEvaluationRequestBean bean = (ThirdPartyEvaluationRequestBean) paramBean;
			sign =  bean.getInstCode() + bean.getMobile() + bean.getPlatform() + bean.getTimestamp();
		} else if (BaseDefine.METHOD_SERVER_BIND_CARD_PAGE.equals(methodName)) {
			// 页面绑卡
			BindCardPageRequestBean bean = (BindCardPageRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getAccountId() + bean.getRetUrl() + bean.getForgotPwdUrl() + bean.getNotifyUrl() + bean.getTimestamp();
		}else if(BaseDefine.METHOD_SERVER_REGISTER.equals(methodName)){
			// 用户注册
			UserRegisterRequestBean bean = (UserRegisterRequestBean) paramBean;
			sign = bean.getMobile() + bean.getInstCode() + bean.getTimestamp();
			//用户开户
		}else if (BaseDefine.METHOD_SERVER_SYNCUSERINFO.equals(methodName)) {
			//查询用户信息
			SyncUserInfoRequestBean bean = (SyncUserInfoRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getTimestamp();
			logger.info("sign is :{}", sign);
		}else if(BaseDefine.METHOD_SERVER_WITHDRAW.equals(methodName)){
			// 用户提现
			UserWithdrawRequestBean bean = (UserWithdrawRequestBean)paramBean;
			sign = bean.getChannel() + bean.getAccountId() + bean.getAccount() + bean.getCardNo() + bean.getRetUrl() + bean.getBgRetUrl() + bean.getTimestamp();
		}else if(BaseDefine.METHOD_SERVER_SET_PASSWORD.equals(methodName)){
			//设置交易密码验签
			ThirdPartyTransPasswordRequestBean bean = (ThirdPartyTransPasswordRequestBean) paramBean;
			sign = bean.getChannel() + bean.getAccountId() + bean.getRetUrl() + bean.getBgRetUrl()+ bean.getTimestamp();
		}else if(BaseDefine.METHOD_SERVER_RESET_PASSWORD.equals(methodName)){
			//设置交易密码验签
			ThirdPartyTransPasswordRequestBean bean = (ThirdPartyTransPasswordRequestBean) paramBean;
			sign = bean.getChannel() + bean.getAccountId() + bean.getRetUrl() + bean.getBgRetUrl()+ bean.getTimestamp();
		}else if (BaseDefine.METHOD_SERVER_UNBIND_CARD_PAGE.equals(methodName)) {
			// 解卡(页面调用)合规
			UnbindCardPageRequestBean bean = (UnbindCardPageRequestBean) paramBean;
			sign = bean.getInstCode()+ bean.getAccountId() + bean.getMobile() + bean.getCardNo()+bean.getTimestamp();
		}

		return ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
	}

	/**
	 * 检查短信验证码searchStatus:查询的短信状态,updateStatus:查询结束后的短信状态
	 * @param mobile
	 * @param verificationCode
	 * @param verificationType
	 * @param platform
	 * @param searchStatus
	 * @param updateStatus
	 * @return
	 */
	@Override
	public int updateCheckMobileCode(String mobile, String verificationCode, String verificationType, String platform,
									 Integer searchStatus, Integer updateStatus,boolean isUpdate) {
		int cnt = amUserClient.checkMobileCode( mobile,  verificationCode,  verificationType,  platform, searchStatus,  updateStatus,isUpdate);
		return cnt;
	}

	/**
	 * 获取用户在银行的开户信息
	 * @param userId
	 * @return
	 */
	@Override
	public BankOpenAccountVO getBankOpenAccount(Integer userId) {
		BankOpenAccountVO bankAccount = this.amUserClient.selectById(userId);
		return bankAccount;
	}

	/**
	 * 根据accountId获取开户信息
	 * @param accountId
	 * @return
	 */
	@Override
	public BankOpenAccountVO getBankOpenAccountByAccount(String accountId) {
		BankOpenAccountVO bankAccount = this.amUserClient.selectBankOpenAccountByAccountId(accountId);
		return bankAccount;
	}

	/**
	 * 校验用户是否已开户
	 * @param userId
	 * @return
	 */
	@Override
	public boolean checkIsOpen(Integer userId) {
		BankOpenAccountVO bankAccount = this.amUserClient.selectById(userId);

		if(bankAccount == null) {
			return false;
		}

		return true;
	}

	/**
	 * 更新用户信息
	 * @param userVO
	 * @return
	 */
	@Override
	public Integer updateUserByUserId(UserVO userVO) {
		return amUserClient.updateUserById(userVO);
	}

	/**
	 * 请求验证码接口
	 * @param userId
	 * @param cardNo
	 * @param mobile
	 * @return
	 */
	@Override
	public BankCallBean callSendCode(Integer userId, String mobile, String srvTxCode ,String client, String cardNo) {
		BankCallBean bean = new BankCallBean();
		// 交易代码cardBind
		bean.setTxCode(BankCallMethodConstant.TXCODE_SMSCODE_APPLY);
		// 交易渠道000001手机APP 000002网页
		bean.setChannel(client);
		bean.setCardNo(cardNo);
		if(null!=cardNo){
			// 绑卡接口参照生产环境类型改为2
			bean.setReqType("2");
		}
		bean.setSrvTxCode(srvTxCode);
		// 交易渠道
		bean.setMobile(mobile);
		bean.setSmsType("1");
		// 订单号
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
		// 请求用户名
		bean.setLogUserId(String.valueOf(userId));
		// 调用存管接口发送验证码
		BankCallBean retBean = null;
		try {
			retBean  = BankCallUtils.callApiBg(bean);
		} catch (Exception e) {
			logger.info("请求银行接口失败", e);
			return null;
		}
		if (Validator.isNull(retBean)) {
			return null;
		}
		// 短信发送返回结果码
		String retCode = retBean.getRetCode();
		if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode) && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
			return null;
		}
		if (Validator.isNull(retBean.getSrvAuthCode()) && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
			return null;
		}
		// 业务授权码
		if (Validator.isNotNull(retBean.getSrvAuthCode())) {
			BankSmsLogRequest request = new BankSmsLogRequest();
			request.setSrvAuthCode(retBean.getSrvAuthCode());
			request.setSrvTxCode(retBean.getTxCode());
			request.setUserId(Integer.parseInt(retBean.getLogUserId()));
			request.setSmsSeq(retBean.getSmsSeq());
			boolean smsFlag = this.updateAfterSendCode(request);
			CheckUtil.check(smsFlag, MsgEnum.ERR_CARD_SAVE);
			return retBean;

		} else {
			// 保存用户验证码日志
			BankSmsLogRequest request = new BankSmsLogRequest();
			request.setSrvAuthCode(bean.getSrvAuthCode());
			request.setSrvTxCode(bean.getTxCode());
			request.setUserId(Integer.parseInt(bean.getLogUserId()));
			String srvAuthCode = amUserClient.selectBankSmsLog(request);
			if (Validator.isNull(srvAuthCode)) {
				return null;
			} else {
				retBean.setSrvAuthCode(srvAuthCode);
				return retBean;
			}

		}
	}

	/**
	 * 更新绑卡短信验证码
	 * @param
	 * @return
	 */
	@Override
	public boolean updateAfterSendCode(BankSmsLogRequest request) {
		return amUserClient.updateBankSmsLog(request);
	}
	/**
	 * 验证外部请求签名
	 *
	 * @param paramBean
	 * @return
	 *//*
	@Override
	public boolean verifyRequestSign(BaseBean paramBean, String methodName) {

		String sign = StringUtils.EMPTY;

		// 机构编号必须参数
		String instCode = paramBean.getInstCode();
		if (StringUtils.isEmpty(instCode)) {
			return false;
		}

		if(BaseDefine.METHOD_SERVER_CASHWITHDRAWAL.equals(methodName)){
			CashAuthRequestBean bean = (CashAuthRequestBean) paramBean;
			sign =  bean.getAccountId() +(bean.getAcqRes()==null?"":bean.getAcqRes()) + (bean.getAgreeWithdraw() == null ?"": bean.getAgreeWithdraw()) + (bean.getAutoBid()==null?"":bean.getAutoBid()) + (bean.getAutoTransfer() ==null ? "":bean.getAutoTransfer()) + bean.getBitMap()
					+ bean.getChannel() + (bean.getDirectConsume() == null ? "" : bean.getDirectConsume()) + bean.getNotifyUrl()
					+ bean.getRetUrl() + bean.getTransactionUrl();
		}else if(BaseDefine.METHOD_SERVER_REGISTER.equals(methodName)){
			// 用户注册
			UserRegisterRequestBean bean = (UserRegisterRequestBean) paramBean;
			sign = bean.getMobile() + bean.getInstCode() + bean.getTimestamp();
			//用户开户
		}else if(BaseDefine.METHOD_SERVER_SET_PASSWORD.equals(methodName)){
			//设置交易密码验签
			ThirdPartyTransPasswordRequestBean bean = (ThirdPartyTransPasswordRequestBean) paramBean;
			sign = bean.getChannel() + bean.getAccountId() + bean.getRetUrl() + bean.getBgRetUrl()+ bean.getTimestamp();
		}else if(BaseDefine.METHOD_SERVER_RESET_PASSWORD.equals(methodName)){
			//设置交易密码验签
			ThirdPartyTransPasswordRequestBean bean = (ThirdPartyTransPasswordRequestBean) paramBean;
			sign = bean.getChannel() + bean.getAccountId() + bean.getRetUrl() + bean.getBgRetUrl()+ bean.getTimestamp();
		}else if(BaseDefine.METHOD_SERVER_BIND_CARD_SEND_PLUS_CODE.equals(methodName)){
			//绑定银行卡发送短信验证码
			ThirdPartyBankCardRequestBean bean = (ThirdPartyBankCardRequestBean) paramBean;
			sign = bean.getInstCode()+ bean.getAccountId() + bean.getMobile() + bean.getCardNo()+bean.getTimestamp();
		}else if(BaseDefine.METHOD_SERVER_BIND_CARD.equals(methodName)){
			//绑定银行卡
			ThirdPartyBankCardRequestBean bean = (ThirdPartyBankCardRequestBean) paramBean;
			sign =  bean.getInstCode() + bean.getAccountId() + bean.getLastSrvAuthCode() + bean.getCode()+bean.getMobile()+ bean.getCardNo()+ bean.getTimestamp();
		}else if(BaseDefine.METHOD_SERVER_DELETE_CARD.equals(methodName)){
			//设用户余额查询
			ThirdPartyBankCardRequestBean bean = (ThirdPartyBankCardRequestBean) paramBean;
			sign =  bean.getChannel() + bean.getAccountId() + bean.getTimestamp();
		}else if(BaseDefine.METHOD_SERVER_WITHDRAW.equals(methodName)){
			// 用户提现
			UserWithdrawRequestBean bean = (UserWithdrawRequestBean)paramBean;
			sign = bean.getChannel() + bean.getAccountId() + bean.getAccount() + bean.getCardNo() + bean.getRetUrl() + bean.getBgRetUrl() + bean.getTimestamp();
		}else if (BaseDefine.METHOD_SAVE_USER_EVALUATION_RESULTS.equals(methodName)) {
			// 第三方用户测评结果记录
			ThirdPartyEvaluationRequestBean bean = (ThirdPartyEvaluationRequestBean) paramBean;
			sign =  bean.getInstCode() + bean.getMobile() + bean.getPlatform() + bean.getTimestamp();
		} else if (BaseDefine.METHOD_SERVER_TRUSTEE_PAY.equals(methodName)) {
			// 借款人受托支付申请
			TrusteePayRequestBean bean = (TrusteePayRequestBean) paramBean;
			sign = bean.getChannel() + bean.getAccountId() + bean.getProductId() + bean.getIdType() + bean.getIdNo()
					+ bean.getReceiptAccountId() + bean.getForgotPwdUrl() + bean.getRetUrl() + bean.getNotifyUrl()
					+ bean.getTimestamp();
		} else if (BaseDefine.METHOD_SERVER_TRUSTEE_PAY_QUERY.equals(methodName)) {
			// 借款人受托支付申请查询
			TrusteePayRequestBean bean = (TrusteePayRequestBean) paramBean;
			sign = bean.getChannel() + bean.getAccountId() + bean.getProductId() + bean.getTimestamp();
		}else if (BaseDefine.METHOD_BORROW_AUTH_SEND_SMS.equals(methodName)) {
			// 自动投资 债转  短信验证码
			AutoPlusRequestBean bean = (AutoPlusRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getAccountId() + bean.getMobile() + bean.getSendType() + bean.getTimestamp();
		}else if (BaseDefine.METHOD_BORROW_AUTH_INVES.equals(methodName)) {
			// 自动投资 增强
			AutoPlusRequestBean bean = (AutoPlusRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getAccountId() + bean.getSmsCode() + bean.getTimestamp();
		}else if (BaseDefine.METHOD_BORROW_AUTH_CREDIT.equals(methodName)) {
			// 自动 债转  授权增强
			AutoPlusRequestBean bean = (AutoPlusRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getAccountId() + bean.getSmsCode() + bean.getTimestamp();
		}else if (BaseDefine.METHOD_PAYMENT_AUTH_PAGE.equals(methodName)) {
			// 缴费授权
			PaymentAuthPageRequestBean bean = (PaymentAuthPageRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getAccountId()+bean.getRetUrl()+bean.getNotifyUrl() + bean.getTimestamp();
		}else if (BaseDefine.METHOD_REPAY_AUTH.equals(methodName)) {
			// 还款授权
			RepayAuthRequestBean bean = (RepayAuthRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getAccountId()+bean.getRetUrl()+bean.getNotifyUrl() + bean.getTimestamp();
		}

		return ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
	}*/

	/**
	 * 根据用户ID查询企业用户信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public CorpOpenAccountRecordVO getCorpOpenAccountRecord(Integer userId) {
		return amUserClient.getCorpOpenAccountRecord(userId);
	}

	@Override
	public UserInfoVO getUserInfo(int userId) {
		UserInfoVO userInfo = amUserClient.findUserInfoById(userId);
		return userInfo;
	}


	/**
	 * @Description 根据token查询user
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:34
	 */
	@Override
	public WebViewUserVO getUserFromCache(int userId) {
		WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY+userId, WebViewUserVO.class);
		return user;
	}

	@Override
	public WebViewUserVO setToken(WebViewUserVO webViewUserVO){
		String token = generatorToken(webViewUserVO.getUserId(), webViewUserVO.getUsername());
		webViewUserVO.setToken(token);
		RedisUtils.setObjEx(RedisConstants.USERID_KEY + webViewUserVO.getUserId(), webViewUserVO, 7 * 24 * 60 * 60);
		return webViewUserVO;
	}

	@Override
	public WebViewUserVO updateUserToCache(WebViewUserVO webViewUserVO){
		RedisUtils.setObjEx(RedisConstants.USERID_KEY + webViewUserVO.getUserId(), webViewUserVO, 7 * 24 * 60 * 60);
		return webViewUserVO;
	}


	/**
	 * jwt生成token
	 *
	 * @param userId
	 * @param username
	 * @return
	 */
	protected String generatorToken(int userId, String username) {
		AccessToken accessToken = new AccessToken(userId, username, Instant.now().getEpochSecond());
		String token = JwtHelper.generatorToken(accessToken);
		// 1.设置页面30分钟超时 2.jwt无法删除已知非法token,redis可以做到
		RedisUtils.setObjEx(RedisConstants.USER_TOEKN_KEY + token, userId, 30*60);
		return token;
	}

	@Override
	public WebViewUserVO getWebViewUserByUserId(Integer userId) {
		UserVO user = this.getUsersById(userId);
		WebViewUserVO result = new WebViewUserVO();
		result.setUserId(user.getUserId());
		result.setUsername(user.getUsername());
		if (StringUtils.isNotBlank(user.getMobile())) {
			result.setMobile(user.getMobile());
		}
		if (StringUtils.isNotBlank(user.getIconUrl())) {
			String imghost = UploadFileUtils.getDoPath(systemConfig.getFileDomainUrl());
			imghost = imghost.substring(0, imghost.length() - 1);
			String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getFileUpload());
			if(StringUtils.isNotEmpty(user.getIconUrl())){
				result.setIconUrl(imghost + fileUploadTempPath + user.getIconUrl());
			}
		}
		if (StringUtils.isNotBlank(user.getEmail())) {
			result.setEmail(user.getEmail());
		}
		if (user.getOpenAccount() != null) {
			if (user.getOpenAccount().intValue() == 1) {
				result.setOpenAccount(true);
			} else {
				result.setOpenAccount(false);
			}
		}
		if (user.getBankOpenAccount() != null) {
			if (user.getBankOpenAccount() == 1) {
				result.setBankOpenAccount(true);
			} else {
				result.setBankOpenAccount(false);
			}
		}
		result.setRechargeSms(user.getRechargeSms());
		result.setWithdrawSms(user.getWithdrawSms());
		result.setInvestSms(user.getInvestSms());
		result.setRecieveSms(user.getRecieveSms());
		result.setIsSetPassword(user.getIsSetPassword());
		try {
			if(user.getIsEvaluationFlag()==1 && null != user.getEvaluationExpiredTime()){
				//测评到期日
				Long lCreate = user.getEvaluationExpiredTime().getTime();
				//当前日期
				Long lNow = System.currentTimeMillis();
				if (lCreate <= lNow) {
					//已过期需要重新评测
					result.setIsEvaluationFlag(2);
					result.setEvaluationExpiredTime(user.getEvaluationExpiredTime());
				} else {
					//未到一年有效期
					result.setIsEvaluationFlag(1);
					result.setEvaluationExpiredTime(user.getEvaluationExpiredTime());
				}
			}else{
				result.setIsEvaluationFlag(0);
			}
			// 用户是否完成风险测评标识 end
		} catch (Exception e) {
			result.setIsEvaluationFlag(0);
		}
		result.setIsSmtp(user.getIsSmtp());
		result.setUserType(user.getUserType());
		UserInfoVO userInfoVO = this.getUserInfo(userId);
		if (userInfoVO != null && userInfoVO.getSex() != null) {
			result.setSex(userInfoVO.getSex());
			if (StringUtils.isNotBlank(userInfoVO.getNickname())) {
				result.setNickname(userInfoVO.getNickname());
			}
			if (StringUtils.isNotBlank(userInfoVO.getTruename())) {
				result.setTruename(userInfoVO.getTruename());
			}
			if (StringUtils.isNotBlank(userInfoVO.getIdcard())) {
				result.setIdcard(userInfoVO.getIdcard());
			}
			result.setBorrowerType(userInfoVO.getBorrowerType());
			result.setRoleId(userInfoVO.getRoleId() + "");
		}
		AccountChinapnrVO chinapnr = amUserClient.getAccountChinapnr(userId);
		if (chinapnr != null) {
			result.setChinapnrUsrid(chinapnr.getChinapnrUsrid());
			result.setChinapnrUsrcustid(chinapnr.getChinapnrUsrcustid());
		}
		BankOpenAccountVO bankOpenAccount = this.getBankOpenAccount(userId);
		if (bankOpenAccount != null && StringUtils.isNotEmpty(bankOpenAccount.getAccount())) {
			if (result.isBankOpenAccount()) {
				result.setBankAccount(bankOpenAccount.getAccount());
			}
		}
		// 用户紧急联系人
		UsersContactVO usersContactVO = amUserClient.selectUserContact(userId);
		if (usersContactVO != null) {
			result.setUsersContact(usersContactVO);
		}
		return result;
	}

	@Override
	public List<BankCardVO> getBankOpenAccountById(UserVO userVO) {
		return  amUserClient.getBankOpenAccountById(userVO.getUserId());
	}

	@Override
	public String getBankReturnErrorMsg(String retCode){
		BankReturnCodeConfigVO returnCodeConfigVO = amConfigClient.getBankReturnCodeConfig(retCode);
		if(returnCodeConfigVO != null){
			return returnCodeConfigVO.getErrorMsg();
		}
		return "请联系客服";
	}


	/**
	 * 获取前端的地址
	 * @param sysConfig
	 * @param platform
	 * @return
	 */
	public String getFrontHost(SystemConfig sysConfig, String platform) {

		Integer client = Integer.parseInt(platform);
		if (ClientConstants.WEB_CLIENT == client) {
			return sysConfig.getFrontHost();
		}
		if (ClientConstants.APP_CLIENT_IOS == client || ClientConstants.APP_CLIENT == client) {
			return sysConfig.getAppFrontHost();
		}
		if (ClientConstants.WECHAT_CLIENT == client) {
			return sysConfig.getWeiFrontHost();
		}
		return null;
	}

	/**
	 * 特殊字符编码
	 * @param str
	 * @return
	 */
	@Override
	public String strEncode(String str) {
		try {
			str = URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	@Autowired
	private RestTemplate restTemplate;
	// 同步余额接口
	private static final String  SYNBALANCE= "/hyjf-api/server/synbalance/synbalance.do";
	@Override
	public JSONObject synBalance(String account, String instcode, String webHost, String aopAccesskey) {
		SynBalanceRequestBean balanceRequestBean=new SynBalanceRequestBean();
		balanceRequestBean.setAccountId(account);
		balanceRequestBean.setInstCode(instcode);
		Long timestamp=System.currentTimeMillis()/ 1000;
		balanceRequestBean.setTimestamp(timestamp);

		// 优惠券投资url
		String requestUrl = webHost + SYNBALANCE;
		String sign = StringUtils.lowerCase(MD5.toMD5Code(aopAccesskey + account + instcode + timestamp + aopAccesskey));
		balanceRequestBean.setChkValue(sign);
		logger.info("同步余额调用:" + requestUrl);
		String result = restTemplate.postForEntity(requestUrl,balanceRequestBean,String.class).getBody();
		JSONObject status = JSONObject.parseObject(result);
		return status;
	}

	@Override
	public AccountVO getAccountByUserId(Integer userId) {
		return amTradeClient.getAccount(userId);
	}

	/**
	 * 获取银行错误返回码
	 * @param retCode
	 * @return
	 */
	@Override
	public String getBankRetMsg(String retCode) {
		return amConfigClient.getBankRetMsg(retCode);
	}

	@Override
	public String getFailedMess(String logOrdId) {
		//根据ordid获取retcode
		String retCode = amDataCollectClient.getRetCode(logOrdId);
		if (retCode==null){
			return "未知错误";
		}
		if(retCode.equals("00000000")){
			return "00000000";
		}
		//根据retCode获取retMsg
		String retMsg = this.getBankRetMsg(retCode);
		return retMsg;

	}
	// 组装url
	@Override
	public String packageStr(HttpServletRequest request) {
		StringBuffer sbUrl = new StringBuffer();
		// 版本号
		String version = request.getParameter("version");
		// 网络状态
		String netStatus = request.getParameter("netStatus");
		// 平台
		String platform = request.getParameter("platform");
		// token
		String token = request.getParameter("token");
		// 唯一标识
		String sign = request.getParameter("sign");
		// 随机字符串
		String randomString = request.getParameter("randomString");
		// Order
		String order = request.getParameter("order");
		sbUrl.append("?").append("version").append("=").append(version);
		sbUrl.append("&").append("netStatus").append("=").append(netStatus);
		sbUrl.append("&").append("platform").append("=").append(platform);
		sbUrl.append("&").append("randomString").append("=").append(randomString);
		sbUrl.append("&").append("sign").append("=").append(sign);
		sbUrl.append("&").append("token").append("=").append(strEncode(token));
		sbUrl.append("&").append("order").append("=").append(strEncode(order));
		return sbUrl.toString();
	}

	@Override
	public String packageStrForm(HttpServletRequest request) {
		StringBuffer sbUrl = new StringBuffer();
		// 版本号
		String version = request.getParameter("version");
		// 网络状态
		String netStatus = request.getParameter("netStatus");
		// 平台
		String platform = request.getParameter("platform");
		// token
		String token = request.getParameter("token");
		// 唯一标识
		String sign = request.getParameter("sign");
		// 随机字符串
		String randomString = request.getParameter("randomString");
		// Order
		String order = request.getParameter("order");
		sbUrl.append("&").append("version").append("=").append(version);
		sbUrl.append("&").append("netStatus").append("=").append(netStatus);
		sbUrl.append("&").append("platform").append("=").append(platform);
		sbUrl.append("&").append("randomString").append("=").append(randomString);
		sbUrl.append("&").append("sign").append("=").append(sign);
		sbUrl.append("&").append("token").append("=").append(strEncode(token));
		sbUrl.append("&").append("order").append("=").append(strEncode(order));
		return sbUrl.toString();
	}

	@Override
	public ModelAndView getErrorMV(AutoPlusRequestBean payRequestBean, ModelAndView modelAndView, String status) {
		AutoPlusRetBean repwdResult = new AutoPlusRetBean();
		BaseResultBean resultBean = new BaseResultBean();
		resultBean.setStatusForResponse(status);
		repwdResult.setCallBackAction(payRequestBean.getRetUrl());
		repwdResult.set("chkValue", resultBean.getChkValue());
		repwdResult.set("status", resultBean.getStatus());
		repwdResult.setAcqRes(payRequestBean.getAcqRes());
		modelAndView.addObject("callBackForm", repwdResult);
		return modelAndView;
	}
}
