package com.hyjf.cs.user.service.auth.impl;
import com.hyjf.am.resquest.user.UserAuthRequest;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.AuthBean;
import com.hyjf.cs.user.service.auth.AuthService;

import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Service
public class AuthServiceImpl extends BaseUserServiceImpl implements AuthService {

	@Override
	public HjhUserAuthVO getHjhUserAuthByUserId(Integer userId) {
		HjhUserAuthVO hjhUserAuth = getHjhUserAuthByUserId(userId);
		return hjhUserAuth;
	}

	@Override
	public void updateUserAuth(Integer userId, BankCallBean retBean, String authType) {
		HjhUserAuthVO hjhUserAuth = this.getHjhUserAuthByUserId(userId);
		UserAuthRequest request=new UserAuthRequest();
		Date nowTime = GetDate.getNowTime();
		String orderId = retBean.getOrderId();
		if (StringUtils.isNotBlank(orderId)) {
			HjhUserAuthLogVO hjhUserAuthLog = amUserClient.selectByExample(orderId);
			// 更新用户签约授权日志表
			if (hjhUserAuthLog != null) {
				hjhUserAuthLog.setUpdateTime(nowTime);
				hjhUserAuthLog.setUpdateUserId(userId);
				hjhUserAuthLog.setOrderStatus(1);
				hjhUserAuthLog.setAuthCreateTime(GetDate.getNowTime10());
				hjhUserAuthLog.setRemark("授权成功");
				request.setHjhUserAuthLogVO(hjhUserAuthLog);
			}
		}

		if (retBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(retBean.get(BankCallConstant.PARAM_RETCODE))) {
			// 更新user表状态为授权成功
			UserVO user=this.getUsersById(userId);
			if(retBean.getPaymentAuth()!=null){
				user.setPaymentAuthStatus(Integer.parseInt(retBean.getPaymentAuth()));
				request.setUser(user);
			}


			//更新用户签约授权状态信息表
			if(hjhUserAuth==null){
				hjhUserAuth=new HjhUserAuthVO();
				hjhUserAuth.setUserId(user.getUserId());
				// 设置状态
				setAuthType(hjhUserAuth,retBean,authType);

				hjhUserAuth.setUserName(user.getUsername());
				hjhUserAuth.setCreateUserId(user.getUserId());
				hjhUserAuth.setCreateTime(nowTime);
				hjhUserAuth.setUpdateTime(nowTime);
				hjhUserAuth.setUpdateUserId(userId);
				hjhUserAuth.setDelFlag(0);
				request.setHjhUserAuth(hjhUserAuth);
			}else{
				HjhUserAuthVO updateHjhUserAuth=new HjhUserAuthVO();
				updateHjhUserAuth.setUserId(user.getUserId());
				// 设置状态
				setAuthType(updateHjhUserAuth,retBean,authType);
				updateHjhUserAuth.setId(hjhUserAuth.getId());
				updateHjhUserAuth.setUpdateTime(nowTime);
				updateHjhUserAuth.setUpdateUserId(userId);
				request.setHjhUserAuth(hjhUserAuth);
			}
		}
		amUserClient.updateUserAuth(request);
	}

	// 设置状态
	private void setAuthType(HjhUserAuthVO hjhUserAuth, BankCallBean bean, String authType) {
		// 授权类型
		String txcode = bean.getTxCode();
		HjhUserAuthConfigVO config=this.getAuthConfigFromCache(this.KEY_AUTO_CREDIT_AUTH);
		if(BankCallConstant.TXCODE_TERMS_AUTH_QUERY.equals(txcode)){
			//自动投标功能开通标志
			String autoBidStatus = bean.getAutoBid();
			//自动债转功能开通标志
			String autoTransfer = bean.getAutoTransfer();
			//缴费授权
			String paymentAuth = bean.getPaymentAuth();
			//还款授权
			String repayAuth = bean.getRepayAuth();
			// 用户ID
			Integer userId = hjhUserAuth.getUserId();
			// 根据用户ID 查询用户信息
			UserVO users = this.getUsersById(userId);

			switch (authType) {
				case AuthBean.AUTH_TYPE_AUTO_BID:
					if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)){
						hjhUserAuth.setAutoOrderId(bean.getOrderId());
						hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
						hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
						hjhUserAuth.setAutoInvesStatus(Integer.parseInt(autoBidStatus));
						hjhUserAuth.setAutoBidEndTime(bean.getAutoBidDeadline());
						hjhUserAuth.setInvesMaxAmt(bean.getAutoBidMaxAmt());
						// add by liuyang 神策数据统计修改 20180927 start
						/*if ("10000000".equals(users.getInstCode())) {
							try {
								SensorsDataBean sensorsDataBean = new SensorsDataBean();
								sensorsDataBean.setUserId(userId);
								// 汇计划授权结果
								sensorsDataBean.setEventCode("plan_auth_result");
								sensorsDataBean.setOrderId(bean.getOrderId());
								// 授权类型
								sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_AUTO_BID);
								this.sendSensorsDataMQ(sensorsDataBean);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}*/
						// add by liuyang 神策数据统计修改 20180927 end

					}
					break;
				case AuthBean.AUTH_TYPE_AUTO_CREDIT:
					if(StringUtils.isNotBlank(autoTransfer)&&"1".equals(autoTransfer)){
						hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
						hjhUserAuth.setAutoCreditStatus(Integer.parseInt(autoTransfer));
						hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
						hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
						hjhUserAuth.setCreditMaxAmt(config.getPersonalMaxAmount()+"");
						hjhUserAuth.setAutoCreditEndTime(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));

						// add by liuyang 神策数据统计修改 20180927 start
						/*if ("10000000".equals(users.getInstCode())) {
							try {
								SensorsDataBean sensorsDataBean = new SensorsDataBean();
								sensorsDataBean.setUserId(userId);
								// 汇计划授权结果
								sensorsDataBean.setEventCode("plan_auth_result");
								sensorsDataBean.setOrderId(bean.getOrderId());
								// 授权类型
								sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_AUTO_CREDIT);
								this.sendSensorsDataMQ(sensorsDataBean);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}*/
						// add by liuyang 神策数据统计修改 20180927 end

					}
					break;
				case AuthBean.AUTH_TYPE_PAYMENT_AUTH:
					if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)){
						hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
						hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
						hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
						hjhUserAuth.setPaymentMaxAmt(bean.getPaymentMaxAmt());

						// add by liuyang 神策数据统计修改 20180927 start
						/*if ("10000000".equals(users.getInstCode())) {
							try {
								SensorsDataBean sensorsDataBean = new SensorsDataBean();
								sensorsDataBean.setUserId(userId);
								// 事件类型:服务费授权结果
								sensorsDataBean.setEventCode("fee_auth_result");
								sensorsDataBean.setOrderId(bean.getOrderId());
								// 授权类型
								sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_PAYMENT_AUTH);
								this.sendSensorsDataMQ(sensorsDataBean);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}*/
						// add by liuyang 神策数据统计修改 20180927 end
					}
					break;
				case AuthBean.AUTH_TYPE_REPAY_AUTH:
					if(StringUtils.isNotBlank(repayAuth)&&"1".equals(repayAuth)){
						hjhUserAuth.setAutoRepayStatus(Integer.parseInt(repayAuth));
						hjhUserAuth.setAutoRepayEndTime(bean.getRepayDeadline());
						hjhUserAuth.setAutoRepayTime(GetDate.getNowTime10());
						hjhUserAuth.setRepayMaxAmt(bean.getRepayMaxAmt());
					}
					break;
				case AuthBean.AUTH_TYPE_MERGE_AUTH:
					if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)){
						hjhUserAuth.setAutoOrderId(bean.getOrderId());
						hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
						hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
						hjhUserAuth.setAutoInvesStatus(Integer.parseInt(autoBidStatus));
						hjhUserAuth.setAutoBidEndTime(bean.getAutoBidDeadline());
						hjhUserAuth.setInvesMaxAmt(bean.getAutoBidMaxAmt());
					}
					if(StringUtils.isNotBlank(autoTransfer)&&"1".equals(autoTransfer)){

						hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
						hjhUserAuth.setAutoCreditStatus(Integer.parseInt(autoTransfer));
						hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
						hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
						hjhUserAuth.setCreditMaxAmt(config.getPersonalMaxAmount()+"");
						hjhUserAuth.setAutoCreditEndTime(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
					}
					if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)){
						hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
						hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
						hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
						hjhUserAuth.setPaymentMaxAmt(bean.getPaymentMaxAmt());
					}

					// add by liuyang 神策数据统计修改 20180927 start
					/*if ("10000000".equals(users.getInstCode())) {
						try {
							SensorsDataBean sensorsDataBean = new SensorsDataBean();
							sensorsDataBean.setUserId(userId);
							// 汇计划授权结果
							sensorsDataBean.setEventCode("plan_auth_result");
							sensorsDataBean.setOrderId(bean.getOrderId());
							// 授权类型
							sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_MERGE_AUTH);
							this.sendSensorsDataMQ(sensorsDataBean);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}*/
					// add by liuyang 神策数据统计修改 20180927 end
					break;
				default:
					break;
			}

		}else if(BankCallConstant.TXCODE_TERMS_AUTH_PAGE.equals(txcode)){
			//自动投标功能开通标志
			String autoBidStatus = bean.getAutoBid();
			//自动债转功能开通标志
			String autoTransfer = bean.getAutoTransfer();
			//缴费授权
			String paymentAuth = bean.getPaymentAuth();
			//还款授权
			String repayAuth = bean.getRepayAuth();
			if(StringUtils.isNotBlank(autoBidStatus)){
				hjhUserAuth.setAutoInvesStatus(Integer.parseInt(autoBidStatus));
				hjhUserAuth.setAutoOrderId(bean.getOrderId());
				hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
				hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
				hjhUserAuth.setAutoBidEndTime(bean.getAutoBidDeadline());
				hjhUserAuth.setInvesMaxAmt(bean.getAutoBidMaxAmt());
			}
			if(StringUtils.isNotBlank(autoTransfer)){
				hjhUserAuth.setAutoCreditStatus(Integer.parseInt(autoTransfer));
				hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
				hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
				hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
				hjhUserAuth.setCreditMaxAmt(bean.getAutoCreditMaxAmt());
				hjhUserAuth.setAutoCreditEndTime(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
			}
			if(StringUtils.isNotBlank(paymentAuth)){
				hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
				hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
				hjhUserAuth.setPaymentMaxAmt(bean.getPaymentMaxAmt());
				hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
			}
			if(StringUtils.isNotBlank(repayAuth)){
				hjhUserAuth.setAutoRepayStatus(Integer.parseInt(repayAuth));
				hjhUserAuth.setAutoRepayEndTime(bean.getRepayDeadline());
				hjhUserAuth.setRepayMaxAmt(bean.getRepayMaxAmt());
				hjhUserAuth.setAutoRepayTime(GetDate.getNowTime10());
			}
		}

	}
	@Override
	public void insertUserAuthLog(int userId, String orderId, Integer client, String authType) {
		Date nowTime = GetDate.getNowTime();
		UserVO user=this.getUsersById(userId);
		HjhUserAuthLogVO hjhUserAuthLog=new HjhUserAuthLogVO();
		hjhUserAuthLog.setUserId(user.getUserId());
		hjhUserAuthLog.setUserName(user.getUsername());
		hjhUserAuthLog.setOrderId(orderId);
		hjhUserAuthLog.setOrderStatus(2);
		if(authType!=null&&authType.equals(BankCallConstant.QUERY_TYPE_2)){
			hjhUserAuthLog.setAuthType(4);
		}else{
			hjhUserAuthLog.setAuthType(Integer.valueOf(authType));
		}
		hjhUserAuthLog.setOperateEsb(client);
		hjhUserAuthLog.setCreateUserId(user.getUserId());
		hjhUserAuthLog.setCreateTime(nowTime);
		hjhUserAuthLog.setUpdateTime(nowTime);
		hjhUserAuthLog.setUpdateUserId(userId);
		hjhUserAuthLog.setDelFlag(0);
		amUserClient.insertUserAuthLog(hjhUserAuthLog);
	}

	@Override
	public Map<String,Object> getCallbankMV(AuthBean authBean) {

		// 获取共同参数
		String orderDate = GetOrderIdUtils.getOrderDate();
		String txDate = GetOrderIdUtils.getTxDate();
		String txTime = GetOrderIdUtils.getTxTime();
		String seqNo = GetOrderIdUtils.getSeqNo(6);
		// 调用开户接口
		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		bean.setTxCode(BankCallConstant.TXCODE_TERMS_AUTH_PAGE);// 多合一授权
		bean.setTxDate(txDate);
		bean.setTxTime(txTime);
		bean.setSeqNo(seqNo);
		bean.setChannel(authBean.getChannel());


		bean.setAccountId(authBean.getAccountId());
		if(authBean.getUserType()==1){
			CorpOpenAccountRecordVO corpOpenAccountRecordVO=amUserClient.getCorpOpenAccountRecord(authBean.getUserId());
			if (corpOpenAccountRecordVO !=null) {
				bean.setName(corpOpenAccountRecordVO.getBusiName());
				bean.setIdNo(corpOpenAccountRecordVO.getBusiCode());
			}

		}else{
			bean.setName(authBean.getName());
			bean.setIdNo(authBean.getIdNo());
		}

		bean.setIdentity(authBean.getIdentity());
		HjhUserAuthConfigVO config=null;
		switch (authBean.getAuthType()) {
			case AuthBean.AUTH_TYPE_AUTO_BID:
				config=this.getAuthConfigFromCache(this.KEY_AUTO_TENDER_AUTH);
				bean.setAutoBid(AuthBean.AUTH_START_OPEN);
				if(authBean.getUserType()!=1){
					bean.setAutoBidMaxAmt(config.getPersonalMaxAmount()+"");
				}else{
					bean.setAutoBidMaxAmt(config.getEnterpriseMaxAmount()+"");
				}
				bean.setAutoBidDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
				bean.setLogRemark("自动投资授权");
				break;
			case AuthBean.AUTH_TYPE_AUTO_CREDIT:
				config=this.getAuthConfigFromCache(this.KEY_AUTO_CREDIT_AUTH);
				bean.setAutoCredit(AuthBean.AUTH_START_OPEN);
				if(authBean.getUserType()!=1){
					bean.setAutoCreditMaxAmt(config.getPersonalMaxAmount()+"");
				}else{
					bean.setAutoCreditMaxAmt(config.getEnterpriseMaxAmount()+"");
				}
				bean.setAutoCreditDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
				bean.setLogRemark("自动债转授权");
				break;
			case AuthBean.AUTH_TYPE_PAYMENT_AUTH:
				config=this.getAuthConfigFromCache(this.KEY_PAYMENT_AUTH);
				bean.setPaymentAuth(AuthBean.AUTH_START_OPEN);
				if(authBean.getUserType()!=1){
					bean.setPaymentMaxAmt(config.getPersonalMaxAmount()+"");
				}else{
					bean.setPaymentMaxAmt(config.getEnterpriseMaxAmount()+"");
				}
				bean.setPaymentDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
				bean.setLogRemark("服务费授权");
				break;
			case AuthBean.AUTH_TYPE_REPAY_AUTH:
				config=this.getAuthConfigFromCache(this.KEY_REPAYMENT_AUTH);
				bean.setRepayAuth(AuthBean.AUTH_START_OPEN);
				if(authBean.getUserType()!=1){
					bean.setRepayMaxAmt(config.getPersonalMaxAmount()+"");
				}else{
					bean.setRepayMaxAmt(config.getEnterpriseMaxAmount()+"");
				}
				bean.setRepayDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
				bean.setLogRemark("还款授权");
				break;
			case AuthBean.AUTH_TYPE_MERGE_AUTH:

				if(!this.checkIsAuth(authBean.getUserId(), AuthBean.AUTH_TYPE_AUTO_BID)){
					config=this.getAuthConfigFromCache(this.KEY_AUTO_TENDER_AUTH);
					bean.setAutoBid(AuthBean.AUTH_START_OPEN);
					if(authBean.getUserType()!=1){
						bean.setAutoBidMaxAmt(config.getPersonalMaxAmount()+"");
					}else{
						bean.setAutoBidMaxAmt(config.getEnterpriseMaxAmount()+"");
					}
					bean.setAutoBidDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
					authBean.setAutoBidStatus(true);
				}

				if(!this.checkIsAuth(authBean.getUserId(), AuthBean.AUTH_TYPE_AUTO_CREDIT)){
					config=this.getAuthConfigFromCache(this.KEY_AUTO_CREDIT_AUTH);
					bean.setAutoCredit(AuthBean.AUTH_START_OPEN);
					if(authBean.getUserType()!=1){
						bean.setAutoCreditMaxAmt(config.getPersonalMaxAmount()+"");
					}else{
						bean.setAutoCreditMaxAmt(config.getEnterpriseMaxAmount()+"");
					}
					bean.setAutoCreditDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
					authBean.setAutoCreditStatus(true);
				}
				if(!this.checkIsAuth(authBean.getUserId(), AuthBean.AUTH_TYPE_PAYMENT_AUTH)){
					config=this.getAuthConfigFromCache(this.KEY_PAYMENT_AUTH);
					bean.setPaymentAuth(AuthBean.AUTH_START_OPEN);
					if(authBean.getUserType()!=1){
						bean.setPaymentMaxAmt(config.getPersonalMaxAmount()+"");
					}else{
						bean.setPaymentMaxAmt(config.getEnterpriseMaxAmount()+"");
					}
					bean.setPaymentDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
					authBean.setPaymentAuthStatus(true);
				}
				bean.setLogRemark("多个授权");
				break;
			default:
				break;
		}

		bean.setRetUrl(authBean.getRetUrl());
		if(authBean.getRetUrl().indexOf("&isSuccess=")!=-1){
			authBean.getRetUrl().replace("&isSuccess=", "&isSuccess=1");
		}else{
			bean.setSuccessfulUrl(authBean.getRetUrl()+"&isSuccess=1");
		}

		bean.setNotifyUrl(authBean.getNotifyUrl());
		bean.setForgotPwdUrl(authBean.getForgotPwdUrl());

		// 页面调用必须传的
		bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_TERMS_AUTH_PAGE);
		bean.setLogOrderId(authBean.getOrderId());
		bean.setLogOrderDate(orderDate);
		bean.setLogUserId(String.valueOf(authBean.getUserId()));
		bean.setLogIp(authBean.getIp());
		bean.setLogClient(Integer.parseInt(authBean.getPlatform()));
		bean.setOrderId(authBean.getOrderId());
		try {
			Map<String,Object> map = BankCallUtils.callApiMap(bean);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BankCallBean getTermsAuthQuery(int userId, String channel) {
		BankOpenAccountVO bankOpenAccount = getBankOpenAccount(userId);
		// 调用查询投资人签约状态查询
		BankCallBean selectbean = new BankCallBean();
		selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		selectbean.setTxCode(BankCallConstant.TXCODE_TERMS_AUTH_QUERY);
		selectbean.setTxDate(GetOrderIdUtils.getTxDate());
		selectbean.setTxTime(GetOrderIdUtils.getTxTime());
		selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
		selectbean.setChannel(channel);
		selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));// 电子账号
		selectbean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
		// 操作者ID
		selectbean.setLogUserId(String.valueOf(userId));
		selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
		selectbean.setLogClient(0);
		// 返回参数
		BankCallBean retBean = null;
		// 调用接口
		retBean = BankCallUtils.callApiBg(selectbean);
		return retBean;
	}

	@Override
	public boolean checkIsAuth(Integer userId, String txcode) {
		HjhUserAuthVO hjhUserAuth = getHjhUserAuthByUserId(userId);
		String endTime = "";
		int status = 0;
		String nowTime = GetDate.date2Str(new Date(),GetDate.yyyyMMdd);
		// 缴费授权
		if(hjhUserAuth==null){
			return false;
		}
		if (AuthBean.AUTH_TYPE_PAYMENT_AUTH.equals(txcode)) {
			endTime = hjhUserAuth.getAutoPaymentEndTime()==null?"0":hjhUserAuth.getAutoPaymentEndTime();
			status = hjhUserAuth.getAutoPaymentStatus();
		}else if(AuthBean.AUTH_TYPE_REPAY_AUTH.equals(txcode)){
			endTime = hjhUserAuth.getAutoRepayEndTime()==null?"0":hjhUserAuth.getAutoRepayEndTime();
			status = hjhUserAuth.getAutoRepayStatus();
		}else if(AuthBean.AUTH_TYPE_AUTO_BID.equals(txcode)){
			endTime = hjhUserAuth.getAutoBidEndTime()==null?"0":hjhUserAuth.getAutoBidEndTime();
			status = hjhUserAuth.getAutoInvesStatus();
		}else if(AuthBean.AUTH_TYPE_AUTO_CREDIT.equals(txcode)){
			endTime = nowTime+1;
			status = hjhUserAuth.getAutoCreditStatus();
		}

		if(!AuthBean.AUTH_TYPE_MERGE_AUTH.equals(txcode)){
			// 0是未授权
			if (status - 0 == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				return false;
			}
		}else{
			boolean paymentflag=false;
			boolean invesflag=false;
			boolean creditflag=false;
			endTime = nowTime+1;
			status = hjhUserAuth.getAutoCreditStatus();
			// 0是未授权
			if (status - 0 == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				creditflag=true;
			}
			endTime = hjhUserAuth.getAutoBidEndTime()==null?"0":hjhUserAuth.getAutoBidEndTime();
			status = hjhUserAuth.getAutoInvesStatus();
			// 0是未授权
			if (status - 0 == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				invesflag=true;
			}
			endTime = hjhUserAuth.getAutoPaymentEndTime()==null?"0":hjhUserAuth.getAutoPaymentEndTime();
			status = hjhUserAuth.getAutoPaymentStatus();
			// 0是未授权
			if (status - 0 == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				paymentflag=true;
			}
			if(paymentflag||invesflag||creditflag){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean checkPaymentAuthStatus(Integer userId) {
		// 如果用户ID没有 直接成功吧 不会出现这种
		if (userId == null) {
			return true;
		}
		// 检查开关是否打开 没打开 不用校验
		if (this.getAuthConfigFromCache(this.KEY_PAYMENT_AUTH).getEnabledStatus() - 1 != 0) {
			return true;
		}
		HjhUserAuthVO auth = getHjhUserAuthByUserId(userId);
		if (auth == null || auth.getAutoPaymentStatus() - 1 != 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkRepayAuthStatus(Integer userId) {

		// 如果用户ID没有 直接成功吧 不会出现这种
		if (userId == null) {
			return true;
		}
		// 检查开关是否打开 没打开 不用校验
		if (this.getAuthConfigFromCache(this.KEY_REPAYMENT_AUTH).getEnabledStatus() - 1 != 0) {
			return true;
		}
		HjhUserAuthVO auth = getHjhUserAuthByUserId(userId);
		if (auth == null || auth.getAutoRepayStatus() - 1 != 0) {
			return false;
		}
		return true;
	}

	@Override
	public Integer checkAuthExpire(Integer userId, String txcode) {
		HjhUserAuthVO hjhUserAuth = getHjhUserAuthByUserId(userId);
		String endTime = "";
		int status = 0;
		String nowTime = GetDate.date2Str(new Date(),GetDate.yyyyMMdd);
		// 缴费授权
		if(hjhUserAuth==null){
			return 0;
		}
		if (AuthBean.AUTH_TYPE_PAYMENT_AUTH.equals(txcode)) {
			endTime = hjhUserAuth.getAutoPaymentEndTime();
			status = hjhUserAuth.getAutoPaymentStatus();
		}else if(AuthBean.AUTH_TYPE_REPAY_AUTH.equals(txcode)){
			endTime = hjhUserAuth.getAutoRepayEndTime();
			status = hjhUserAuth.getAutoRepayStatus();
		}else if(AuthBean.AUTH_TYPE_AUTO_BID.equals(txcode)){
			endTime = hjhUserAuth.getAutoBidEndTime();
			status = hjhUserAuth.getAutoInvesStatus();
		}else if(AuthBean.AUTH_TYPE_AUTO_CREDIT.equals(txcode)){
			endTime = nowTime+1;
			status = hjhUserAuth.getAutoCreditStatus();
		}

		if(!AuthBean.AUTH_TYPE_MERGE_AUTH.equals(txcode)){
			Integer endTimeInt=Integer.parseInt(endTime);
			Integer nowTimeInt=Integer.parseInt(nowTime);
			// 0是未授权
			if (status - 0 == 0) {
				return 0;
			}
			//20180731-20180630=101  20180228-20180131=97
			if(endTimeInt - nowTimeInt > 101){
				return 1;
			}else if(endTimeInt - nowTimeInt <= 101&&endTimeInt - nowTimeInt > 0){
				return 2;
			}else {
				return 3;
			}
		}else{
			Integer paymentflag=0;
			Integer invesflag=0;
			Integer creditflag=0;
			endTime = nowTime+101;
			status = hjhUserAuth.getAutoCreditStatus();
			// 0是未授权
			if (status - 0 != 0 ) {
				creditflag=1;
			}
			endTime = hjhUserAuth.getAutoBidEndTime();
			status = hjhUserAuth.getAutoInvesStatus();
			Integer endTimeInt=Integer.parseInt(endTime);
			Integer nowTimeInt=Integer.parseInt(nowTime);
			// 0是未授权
			if (status - 0 == 0) {
				invesflag= 0;
			}
			//20180731-20180630=101  20180228-20180131=97
			if(endTimeInt - nowTimeInt > 101){
				invesflag=1;
			}else if(endTimeInt - nowTimeInt <= 101&&endTimeInt - nowTimeInt > 0){
				invesflag= 2;
			}else {
				invesflag= 3;
			}

			endTime = hjhUserAuth.getAutoPaymentEndTime();
			status = hjhUserAuth.getAutoPaymentStatus();
			endTimeInt=Integer.parseInt(endTime);
			nowTimeInt=Integer.parseInt(nowTime);
			// 0是未授权
			if (status - 0 == 0) {
				paymentflag= 0;
			}
			//20180731-20180630=101  20180228-20180131=97
			if(endTimeInt - nowTimeInt > 101){
				paymentflag=1;
			}else if(endTimeInt - nowTimeInt <= 101&&endTimeInt - nowTimeInt > 0){
				paymentflag= 2;
			}else {
				paymentflag= 3;
			}
			if(invesflag==0||creditflag==0||paymentflag==0){
				return 0;
			}
			return invesflag>paymentflag?invesflag:paymentflag;
		}
	}

	@Override
	public boolean checkInvesAuthStatus(Integer userId) {
		// 如果用户ID没有 直接成功吧 不会出现这种
		if (userId == null) {
			return true;
		}
		// 检查开关是否打开 没打开 不用校验
		if (this.getAuthConfigFromCache(this.KEY_AUTO_TENDER_AUTH).getEnabledStatus() - 1 != 0) {
			return true;
		}
		HjhUserAuthVO auth = getHjhUserAuthByUserId(userId);
		if (auth == null || auth.getAutoInvesStatus() - 1 != 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkCreditAuthStatus(Integer userId) {
		// 如果用户ID没有 直接成功吧 不会出现这种
		if (userId == null) {
			return true;
		}
		// 检查开关是否打开 没打开 不用校验
		if (this.getAuthConfigFromCache(this.KEY_AUTO_CREDIT_AUTH).getEnabledStatus() - 1 != 0) {
			return true;
		}
		HjhUserAuthVO auth = getHjhUserAuthByUserId(userId);
		if (auth == null || auth.getAutoCreditStatus() - 1 != 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkDefaultConfig(BankCallBean bean, String authType) {
		UserVO user=this.getUsersById(Integer.parseInt(bean.getLogUserId()));
		// 授权类型
		String txcode = bean.getTxCode();
		if(BankCallConstant.TXCODE_TERMS_AUTH_QUERY.equals(txcode)){
			//自动投标功能开通标志
			String autoBidStatus = bean.getAutoBid();
			//缴费授权
			String paymentAuth = bean.getPaymentAuth();
			//还款授权
			String repayAuth = bean.getRepayAuth();
			switch (authType) {
				case AuthBean.AUTH_TYPE_AUTO_BID:
					if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)
							&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
						HjhUserAuthConfigVO config=this.getAuthConfigFromCache(this.KEY_AUTO_TENDER_AUTH);
						if(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getAutoBidDeadline())){
							return true;
						}
						if(user.getUserType()!=1){
							if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
								return true;
							}
						}else{
							if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
								return true;
							}
						}

					}
					break;
				case AuthBean.AUTH_TYPE_PAYMENT_AUTH:
					if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)
							&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
						HjhUserAuthConfigVO config=this.getAuthConfigFromCache(this.KEY_PAYMENT_AUTH);
						if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getPaymentDeadline())){
							return true;
						}
						if(user.getUserType()!=1){
							if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
								return true;
							}
						}else{
							if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
								return true;
							}
						}
					}
					break;
				case AuthBean.AUTH_TYPE_REPAY_AUTH:
					if(StringUtils.isNotBlank(repayAuth)&&"1".equals(repayAuth)
							&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
						HjhUserAuthConfigVO config=this.getAuthConfigFromCache(this.KEY_REPAYMENT_AUTH);
						if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getRepayDeadline())){
							return true;
						}
						if(user.getUserType()!=1){
							if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))){
								return true;
							}
						}else{
							if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))){
								return true;
							}
						}
					}
					break;
				case AuthBean.AUTH_TYPE_MERGE_AUTH:
					if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)
							&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),AuthBean.AUTH_TYPE_AUTO_BID)){
						HjhUserAuthConfigVO config=this.getAuthConfigFromCache(this.KEY_AUTO_TENDER_AUTH);
						if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getAutoBidDeadline())){
							return true;
						}
						if(user.getUserType()!=1){
							if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
								return true;
							}
						}else{
							if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
								return true;
							}
						}
					}
					if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)
							&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),AuthBean.AUTH_TYPE_PAYMENT_AUTH)){
						HjhUserAuthConfigVO config=this.getAuthConfigFromCache(this.KEY_PAYMENT_AUTH);
						if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getPaymentDeadline())){
							return true;
						}
						if(user.getUserType()!=1){
							if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
								return true;
							}
						}else{
							if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
								return true;
							}
						}
					}
					break;
				default:
					break;
			}
		}else if(BankCallConstant.TXCODE_TERMS_AUTH_PAGE.equals(txcode)){
			//自动投标功能开通标志
			String autoBidStatus = bean.getAutoBid();
			//缴费授权
			String paymentAuth = bean.getPaymentAuth();
			//还款授权
			String repayAuth = bean.getRepayAuth();
			if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)
					&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
				HjhUserAuthConfigVO config=this.getAuthConfigFromCache(this.KEY_AUTO_TENDER_AUTH);
				if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getAutoBidDeadline())){
					return true;
				}
				if(user.getUserType()!=1){
					if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
						return true;
					}
				}else{
					if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
						return true;
					}
				}
			}
			if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)
					&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
				HjhUserAuthConfigVO config=this.getAuthConfigFromCache(this.KEY_PAYMENT_AUTH);
				if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getPaymentDeadline())){
					return true;
				}
				if(user.getUserType()!=1){
					if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
						return true;
					}
				}else{
					if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
						return true;
					}
				}
			}
			if(StringUtils.isNotBlank(repayAuth)&&"1".equals(repayAuth)
					&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
				HjhUserAuthConfigVO config=this.getAuthConfigFromCache(this.KEY_REPAYMENT_AUTH);
				if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getRepayDeadline())){
					return true;
				}
				if(user.getUserType()!=1){
					if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))){
						return true;
					}
				}else{
					if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))){
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public WebResult<Object> seachUserAuthErrorMessgae(String logOrdId) {
		WebResult<Object> result = new WebResult<Object>();
		HjhUserAuthLogVO hjhUserAuthLogVO=amUserClient.selectByExample(logOrdId);
		result.setStatus(WebResult.SUCCESS);
		Map<String,String> map = new HashedMap();
		if(hjhUserAuthLogVO!=null){
			map.put("error",hjhUserAuthLogVO.getRemark());
		}else{
			map.put("error","系统异常，请稍后再试！");
		}
		result.setData(map);
		return result;
	}

	@Override
	public void updateUserAuthLog(String logOrderId, String message) {
		amUserClient.updateUserAuthLog(logOrderId,message);
	}

	public final static String KEY_PAYMENT_AUTH = "AUTHCONFIG:paymentAuth"; // 缴费授权
	public final static String KEY_REPAYMENT_AUTH = "AUTHCONFIG:repaymentAuth"; // 还款授权
	public final static String KEY_AUTO_TENDER_AUTH = "AUTHCONFIG:autoTenderAuth"; // 自动投标
	public final static String KEY_AUTO_CREDIT_AUTH = "AUTHCONFIG:autoCreditAuth"; // 自动债转
	public final static String KEY_IS_CHECK_USER_ROLES = "CHECKE:ISCHECKUSERROLES"; // 是否校验用户角色
	/**
	 * 从redis里面获取授权配置
	 * @param key
	 * @return
	 */
	public static HjhUserAuthConfigVO getAuthConfigFromCache(String key){
		HjhUserAuthConfigVO hjhUserAuthConfig=RedisUtils.getObj(key,HjhUserAuthConfigVO.class);
		return hjhUserAuthConfig;
	}
}
