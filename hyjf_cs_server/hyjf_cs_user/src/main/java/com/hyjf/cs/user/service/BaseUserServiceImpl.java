package com.hyjf.cs.user.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyjf.am.resquest.user.BankSmsLogRequest;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.ApiSignUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.user.bean.AutoPlusRequestBean;
import com.hyjf.cs.user.bean.AutoStateQueryRequest;
import com.hyjf.cs.user.bean.BaseBean;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
public class BaseUserServiceImpl extends BaseServiceImpl implements BaseUserService {

	Logger logger = LoggerFactory.getLogger(BaseUserServiceImpl.class);

	@Autowired
	AmUserClient amUserClient;

	@Autowired
	SystemConfig systemConfig;

	/**
	 * @param token
	 * @Description 根据token查询user
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:34
	 */
	@Override
	public WebViewUserVO getUsersByToken(String token) {
		WebViewUserVO user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUserVO.class);
		return user;
	}

	/**
	 * @param token
	 * @Description 查询用户对象
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:50
	 */
	@Override
	public UserVO getUsers(String token) {
		WebViewUserVO user = getUsersByToken(token);
		if (user == null || user.getUserId() == null) {
			return null;
		}
		return getUsersById(user.getUserId());
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
		}

		return ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
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
	public int updateUserByUserId(UserVO userVO) {
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
		// 调用存管接口发送验证码
		BankCallBean retBean = null;
		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		bean.setTxCode(BankCallMethodConstant.TXCODE_SMSCODE_APPLY);// 交易代码cardBind
		bean.setInstCode(systemConfig.getBankInstcode());// 机构代码
		bean.setBankCode(systemConfig.getBankCode());
		bean.setTxDate(GetOrderIdUtils.getOrderDate());// 交易日期
		bean.setTxTime(GetOrderIdUtils.getOrderTime());// 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
		bean.setChannel(client);// 交易渠道000001手机APP 000002网页
		bean.setReqType("1");
		bean.setCardNo(cardNo);
		bean.setSrvTxCode(srvTxCode);
		bean.setMobile(mobile);// 交易渠道
		bean.setSmsType("1");
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));// 订单号
		bean.setLogUserId(String.valueOf(userId));// 请求用户名

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
			boolean smsFlag = this.updateAfterSendCode(request);
			CheckUtil.check(smsFlag, MsgEnum.ERR_CARD_SAVE);
			return retBean;

		} else {
			// 保存用户开户日志
			BankSmsLogRequest request = new BankSmsLogRequest();
			request.setSrvAuthCode(bean.getSrvAuthCode());
			request.setSrvTxCode(bean.getTxCode());
			request.setUserId(Integer.parseInt(bean.getLogUserId()));
			String srvAuthCode = amUserClient.selectBankSmsLog(request);
			CheckUtil.check(Validator.isNotNull(srvAuthCode), MsgEnum.ERR_CARD_SAVE);
			retBean.setSrvAuthCode(srvAuthCode);
			return retBean;

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
	 */
	/*@Override
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
		}else if((TenderAuthDefine.REQUEST_MAPPING+TenderAuthDefine.TENDER_AUTH).equals(methodName)){
			TenderAuthRequestBean bean = (TenderAuthRequestBean) paramBean;
			sign =  bean.getAccountId() +(bean.getAcqRes()==null?"":bean.getAcqRes()) + (bean.getAgreeWithdraw() == null ?"": bean.getAgreeWithdraw()) + (bean.getAutoBid()==null?"":bean.getAutoBid()) + (bean.getAutoTransfer() ==null ? "":bean.getAutoTransfer()) + bean.getBitMap()
					+ bean.getChannel() + (bean.getDirectConsume() == null ? "" : bean.getDirectConsume()) + bean.getNotifyUrl()
					+ bean.getRetUrl() + bean.getTransactionUrl();
		}else if (PushDefine.PUSH_ACTION.equals(methodName)) {
			// 资产推送--校验接口
			PushRequestBean reflectionBean = (PushRequestBean) paramBean;
			int assetType = reflectionBean.getAssetType();
			Long timestamp = reflectionBean.getTimestamp();
			sign = timestamp + instCode + assetType;
		}else if(BaseDefine.METHOD_SERVER_REGISTER.equals(methodName)){
			// 用户注册
			UserRegisterRequestBean bean = (UserRegisterRequestBean) paramBean;
			sign = bean.getMobile() + bean.getInstCode() + bean.getTimestamp();
			//用户开户
		}else if(BaseDefine.METHOD_SERVER_OPEN_ACCOUNT.equals(methodName)){
			UserOpenAccountRequestBean bean = (UserOpenAccountRequestBean) paramBean;
			sign = bean.getMobile() + bean.getTrueName() + bean.getIdNo() + bean.getCardNo() + bean.getSmsCode() + bean.getOrderId() + bean.getChannel() + bean.getTimestamp();
		}else if(NonCashWithdrawDefine.NON_CASH_WITHDRAW_ACTION.equals(methodName)){
			// 免密提现  验签
			NonCashWithdrawRequestBean bean = (NonCashWithdrawRequestBean) paramBean;
			sign = bean.getAccountId() +bean.getCardNo()+ bean.getAccount()  + bean.getChannel() + bean.getTimestamp() ;
		}else if(BaseDefine.METHOD_REPAY_INFO.equals(methodName)){//获取还款计划
			RepayParamBean bean = (RepayParamBean) paramBean;
			sign = bean.getAccountId() + bean.getProductId() + bean.getRepayType() + bean.getInstCode() + bean.getTimestamp();
		}else if(BaseDefine.METHOD_REPAY_RESULT.equals(methodName)){
			RepayParamBean bean = (RepayParamBean) paramBean;
			sign = bean.getChannel() + bean.getAccountId() + bean.getBorrowNid() + bean.getInstCode() + bean.getTimestamp();
		}else if(BaseDefine.METHOD_SERVER_SET_PASSWORD.equals(methodName)){
			//设置交易密码验签
			ThirdPartyTransPasswordRequestBean bean = (ThirdPartyTransPasswordRequestBean) paramBean;
			sign = bean.getChannel() + bean.getAccountId() + bean.getRetUrl() + bean.getBgRetUrl()+ bean.getTimestamp();
		}else if(BaseDefine.METHOD_SERVER_RESET_PASSWORD.equals(methodName)){
			//设置交易密码验签
			ThirdPartyTransPasswordRequestBean bean = (ThirdPartyTransPasswordRequestBean) paramBean;
			sign = bean.getChannel() + bean.getAccountId() + bean.getRetUrl() + bean.getBgRetUrl()+ bean.getTimestamp();
		}else if(BaseDefine.METHOD_SERVER_SYNBALANCE.equals(methodName)){
			//设用户余额查询
			ThirdPartySynBalanceRequestBean bean = (ThirdPartySynBalanceRequestBean) paramBean;
			sign =  bean.getAccountId() + bean.getTimestamp();
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
		}else if(BaseDefine.METHOD_SERVER_ASSET_STATUS.equals(methodName)){
			AssetStatusRequestBean bean=(AssetStatusRequestBean)paramBean;
			sign = bean.getAssetId() + bean.getTimestamp();
		}else if(BaseDefine.METHOD_SERVER_WITHDRAW.equals(methodName)){
			// 用户提现
			UserWithdrawRequestBean bean = (UserWithdrawRequestBean)paramBean;
			sign = bean.getChannel() + bean.getAccountId() + bean.getAccount() + bean.getCardNo() + bean.getRetUrl() + bean.getBgRetUrl() + bean.getTimestamp();
		}else if(BaseDefine.METHOD_SERVER_SEND_SMS.equals(methodName)){
			UserOpenAccountRequestBean bean = (UserOpenAccountRequestBean) paramBean;
			sign = bean.getChannel() + bean.getMobile() + bean.getTimestamp();
		}else if(BorrowListDefine.BORROW_LIST_ACTION.equals(methodName)){
			BorrowListRequestBean bean = (BorrowListRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getBorrowStatus() + bean.getTimestamp();
		} else if (BaseDefine.METHOD_SERVER_RECHARGE.equals(methodName)) {
			// 短信充值
			UserRechargeRequestBean bean = (UserRechargeRequestBean) paramBean;
			sign = bean.getAccountId() + bean.getCardNo() + bean.getMobile() + bean.getAccount() + bean.getChannel() + bean.getSmsCode() + bean.getTimestamp();
		} else if (BaseDefine.METHOD_SERVER_SEND_RECHARGE_SMS.equals(methodName)) {
			// 短信充值发送验证码
			UserRechargeRequestBean bean = (UserRechargeRequestBean) paramBean;
			sign = bean.getChannel() + bean.getAccountId() + bean.getMobile() + bean.getCardNo() + bean.getTimestamp();
		}else if (BaseDefine.METHOD_SAVE_USER_EVALUATION_RESULTS.equals(methodName)) {
			// 第三方用户测评结果记录
			ThirdPartyEvaluationRequestBean bean = (ThirdPartyEvaluationRequestBean) paramBean;
			sign =  bean.getInstCode() + bean.getMobile() + bean.getPlatform() + bean.getTimestamp();
		} else if(TradeListDefine.TRADELIST_ACTION.equals(methodName)){
			//同步交易明细
			TradeListBean bean = (TradeListBean) paramBean;
			sign = bean.getInstCode() + bean.getTimestamp() + bean.getPhone() + bean.getAccountId();//暂定四个参数
		} else if((AutoTenderDefine.REQUEST_MAPPING+AutoTenderDefine.AUTOTENDER_ACTION).equals(methodName)){
			//自动投资
			AutoTenderRequestBean bean = (AutoTenderRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getAccountId() + bean.getBorrowNid() + bean.getTimestamp();
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
		} else if (BorrowDetailDefine.BORROW_DETAIL_ACTION.equals(methodName)){
			BorrowDetailRequestBean bean = (BorrowDetailRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getBorrowNid() + bean.getTimestamp();
		} else if (OpenAccountPlusDefine.METHOD_SERVER_REGISTER.equals(methodName)){
			OpenAccountPlusRequest bean = (OpenAccountPlusRequest) paramBean;
			sign = bean.getMobile() + bean.getInstCode() + bean.getTimestamp();
		} else if (InvestDefine.INVEST_LIST.equals(methodName)){
			InvestListRequest bean = (InvestListRequest) paramBean;
			sign =  bean.getInstCode() + bean.getStartTime() + bean.getEndTime() + bean.getTimestamp();
		} else if (InvestDefine.REPAY_LIST.equals(methodName)) {
			RepayListRequest bean = (RepayListRequest) paramBean;
			sign =  bean.getInstCode() + bean.getStartTime() + bean.getEndTime() + bean.getTimestamp();
		} else if (BaseDefine.METHOD_BORROW_LIST_ACTION.equals(methodName)) {
			// 第三方还款明细查询
			BorrowRepaymentInfoBean bean = (BorrowRepaymentInfoBean) paramBean;
			sign = bean.getInstCode() + bean.getAssetId() + bean.getTimestamp();
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
		}else if (BaseDefine.METHOD_SERVER_SYNCUSERINFO.equals(methodName)) {
			//查询用户信息
			SyncUserInfoRequest bean = (SyncUserInfoRequest) paramBean;
			sign = bean.getInstCode() + bean.getTimestamp();
			logger.info("sign is :{}", sign);
		}else if (BaseDefine.ORGANIZATION_LIST.equals(methodName)) {
			// 获取集团组织架构信息
			OrganizationStructureBean bean = (OrganizationStructureBean) paramBean;
			sign = bean.getInstCode() +  bean.getTimestamp();
		}else if (BaseDefine.EMPINFO_LIST.equals(methodName)) {
			// 获取员工信息
			OrganizationStructureBean bean = (OrganizationStructureBean) paramBean;
			sign = bean.getInstCode() +  bean.getTimestamp();
		}else if (BaseDefine.INVEST_REPAY_LIST.equals(methodName)) {
			// 获取用户开户信息
			InvestRepayBean bean = (InvestRepayBean) paramBean;
			sign = bean.getInstCode() +  bean.getTimestamp();
		}else if (BaseDefine.METHOD_PAYMENT_AUTH_PAGE.equals(methodName)) {
			// 缴费授权
			PaymentAuthPageRequestBean bean = (PaymentAuthPageRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getAccountId()+bean.getRetUrl()+bean.getNotifyUrl() + bean.getTimestamp();
		}else if (BaseDefine.METHOD_REPAY_AUTH.equals(methodName)) {
			// 还款授权
			RepayAuthRequestBean bean = (RepayAuthRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getAccountId()+bean.getRetUrl()+bean.getNotifyUrl() + bean.getTimestamp();
		}
		else if ((UserDirectRechargeDefine.REQUEST_MAPPING+UserDirectRechargeDefine.RECHARGE_ACTION).equals(methodName)) {
			// 页面充值
			UserDirectRechargeRequestBean bean = (UserDirectRechargeRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getAccountId() + bean.getMobile() + bean.getIdNo() + bean.getCardNo()
					+ bean.getTxAmount() + bean.getName() + bean.getRetUrl() + bean.getBgRetUrl() + bean.getTimestamp();
		}
		else if ((OpenAccountPageDefine.REQUEST_MAPPING+OpenAccountPageDefine.OPEN_ACCOUNT_ACTION).equals(methodName)) {
			// 页面开户
			OpenAccountPageRequestBean bean = (OpenAccountPageRequestBean) paramBean;
			sign = bean.getInstCode() + bean.getMobile() + bean.getIdNo() + bean.getTrueName()
					+ bean.getRetUrl() + bean.getBgRetUrl() + bean.getTimestamp();
		}
		else if ((AutoStateQueryDefine.REQUEST_MAPPING+AutoStateQueryDefine.AUTO_STATE_QUERY_ACTION).equals(methodName)) {
			// 授权状态查询
			AutoStateQueryRequestBean bean = (AutoStateQueryRequestBean) paramBean;
			sign = bean.getInstCode() +bean.getAccountId() + bean.getTimestamp();
		}
		else if ((UserWithdrawDefine.REQUEST_MAPPING+UserWithdrawDefine.GET_USER_WITHDRAW_RECORD_ACTION).equals(methodName)) {
			// 提现记录查询接口
			UserWithdrawRequestBean bean = (UserWithdrawRequestBean) paramBean;
			sign = bean.getInstCode() +bean.getAccountId()+bean.getLimitStart()+bean.getLimitEnd() + bean.getTimestamp();
		}

		return ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
	}*/

}
