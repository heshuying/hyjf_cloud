package com.hyjf.cs.user.service.auth.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.resquest.user.UserAuthRequest;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.AemsMergeAuthPagePlusRequestBean;
import com.hyjf.cs.user.bean.ApiAuthRequesBean;
import com.hyjf.cs.user.bean.AuthBean;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.service.auth.AuthService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class AuthServiceImpl extends BaseUserServiceImpl implements AuthService {
	Logger _log = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private AmTradeClient amTradeClient;

	@Autowired
	private CommonProducer commonProducer;

	@Override
	public HjhUserAuthVO getHjhUserAuthByUserId(Integer userId) {
		HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(userId);
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
			if(retBean.getPaymentAuth()!=null&&!"".equals(retBean.getPaymentAuth())){
				user.setPaymentAuthStatus(Integer.parseInt(retBean.getPaymentAuth()));
				request.setUser(user);
			}


			//更新用户签约授权状态信息表
			if(hjhUserAuth==null||hjhUserAuth.getId()==null){
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
				request.setHjhUserAuth(updateHjhUserAuth);
			}
		}
		amUserClient.updateUserAuth(request);
	}

	// 设置状态
	private void setAuthType(HjhUserAuthVO hjhUserAuth, BankCallBean bean, String authType) {
		// 授权类型
		String txcode = bean.getTxCode();
		HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH);
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

					}
					break;
				case AuthBean.AUTH_TYPE_PAYMENT_AUTH:
					if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)){
						hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
						hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
						hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
						hjhUserAuth.setPaymentMaxAmt(bean.getPaymentMaxAmt());
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
					break;
				case AuthBean.AUTH_TYPE_PAY_REPAY_AUTH:
					if(StringUtils.isNotBlank(paymentAuth) && "1".equals(paymentAuth)){
						hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
						hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
						hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
						hjhUserAuth.setPaymentMaxAmt(bean.getPaymentMaxAmt());
					}
					if(StringUtils.isNotBlank(repayAuth) && "1".equals(repayAuth)){
						hjhUserAuth.setAutoRepayStatus(Integer.parseInt(repayAuth));
						hjhUserAuth.setAutoRepayEndTime(bean.getRepayDeadline());
						hjhUserAuth.setAutoRepayTime(GetDate.getNowTime10());
						hjhUserAuth.setRepayMaxAmt(bean.getRepayMaxAmt());
					}
					break;
				default:
					break;
			}

		}else if(BankCallConstant.TXCODE_TERMS_AUTH_PAGE.equals(txcode)){
			//自动投标功能开通标志
			String autoBidStatus = bean.getAutoBid();
			//自动债转功能开通标志
			String autoTransfer = bean.getAutoCredit();
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
			// 用户ID
			Integer userId = hjhUserAuth.getUserId();
			// 根据用户ID 查询用户信息
			UserVO users = this.getUsersById(userId);
			switch (authType) {
				case AuthBean.AUTH_TYPE_AUTO_BID:
					if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)){
						// add by liuyang 神策数据统计修改 20180927 start
						if ("10000000".equals(users.getInstCode())) {
							try {
								SensorsDataBean sensorsDataBean = new SensorsDataBean();
								sensorsDataBean.setUserId(userId);
								// 汇计划授权结果
								sensorsDataBean.setEventCode("plan_auth_result");
								sensorsDataBean.setOrderId(bean.getOrderId());
								// 授权类型
								logger.info("发送神策数据统计MQ,自动出借授权,授权订单号:[" + bean.getOrderId() + "],用户ID:[" + userId + "].");
								sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_AUTO_BID);
								this.sendSensorsDataMQ(sensorsDataBean);
							} catch (Exception e) {
								_log.info("自动投标授权 神策数据统计异常！用户id：{}", userId, this.getClass().getName(), e);
							}
						}
						// add by liuyang 神策数据统计修改 20180927 end

					}
					break;
				case AuthBean.AUTH_TYPE_AUTO_CREDIT:
					if(StringUtils.isNotBlank(autoTransfer)&&"1".equals(autoTransfer)){
						// add by liuyang 神策数据统计修改 20180927 start
						if ("10000000".equals(users.getInstCode())) {
							try {
								SensorsDataBean sensorsDataBean = new SensorsDataBean();
								sensorsDataBean.setUserId(userId);
								// 汇计划授权结果
								sensorsDataBean.setEventCode("plan_auth_result");
								sensorsDataBean.setOrderId(bean.getOrderId());
								// 授权类型
								logger.info("发送神策数据统计MQ,自动债转授权,授权订单号:[" + bean.getOrderId() + "],用户ID:[" + userId + "].");
								sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_AUTO_CREDIT);
								this.sendSensorsDataMQ(sensorsDataBean);
							} catch (Exception e) {
								_log.info("自动债转授权 神策数据统计异常！用户id：{}", userId, this.getClass().getName(), e);
							}
						}
						// add by liuyang 神策数据统计修改 20180927 end
					}
					break;
				case AuthBean.AUTH_TYPE_PAYMENT_AUTH:
					if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)){
						// add by liuyang 神策数据统计修改 20180927 start
						if ("10000000".equals(users.getInstCode())) {
							try {
								SensorsDataBean sensorsDataBean = new SensorsDataBean();
								sensorsDataBean.setUserId(userId);
								// 事件类型:服务费授权结果
								sensorsDataBean.setEventCode("fee_auth_result");
								sensorsDataBean.setOrderId(bean.getOrderId());
								// 授权类型
								logger.info("发送神策数据统计MQ,服务费授权,授权订单号:[" + bean.getOrderId() + "],用户ID:[" + userId + "].");
								sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_PAYMENT_AUTH);
								this.sendSensorsDataMQ(sensorsDataBean);
							} catch (Exception e) {
								_log.info("缴费授权 神策数据统计异常！用户id：{}", userId, this.getClass().getName(), e);
							}
						}
						// add by liuyang 神策数据统计修改 20180927 end
					}
					break;
				case AuthBean.AUTH_TYPE_REPAY_AUTH:
					break;
				case AuthBean.AUTH_TYPE_MERGE_AUTH:
					// add by liuyang 神策数据统计修改 20180927 start
					if ("10000000".equals(users.getInstCode())) {
						try {
							SensorsDataBean sensorsDataBean = new SensorsDataBean();
							sensorsDataBean.setUserId(userId);
							// 汇计划授权结果
							sensorsDataBean.setEventCode("plan_auth_result");
							sensorsDataBean.setOrderId(bean.getOrderId());
							// 授权类型
							sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_MERGE_AUTH);
							logger.info("发送神策数据统计MQ,多合一授权,授权订单号:[" + bean.getOrderId() + "],用户ID:[" + userId + "].");
							this.sendSensorsDataMQ(sensorsDataBean);
						} catch (Exception e) {
							_log.info("自动投标、自动债转、缴费授权 三合一授权 神策数据统计异常！用户id：{}", userId, this.getClass().getName(), e);
						}
					}
					// add by liuyang 神策数据统计修改 20180927 end
					break;
				case AuthBean.AUTH_TYPE_PAY_REPAY_AUTH:
					// add by dangzw 神策数据统计修改 20181225 start
					if ("10000000".equals(users.getInstCode())) {
						try {
							SensorsDataBean sensorsDataBean = new SensorsDataBean();
							sensorsDataBean.setUserId(userId);
							// 汇计划授权结果
							sensorsDataBean.setEventCode("plan_auth_result");
							sensorsDataBean.setOrderId(bean.getOrderId());
							// 授权类型
							sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_PAY_REPAY_AUTH);
							this.sendSensorsDataMQ(sensorsDataBean);
						} catch (Exception e) {
							_log.info("缴费、还款二合一授权 神策数据统计异常！用户id：{}", userId, this.getClass().getName(), e);
						}
					}
					// add by dangzw 神策数据统计修改 20181225 end
					break;
				default:
					break;
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
				config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH);
				bean.setAutoBid(AuthBean.AUTH_START_OPEN);
				if(authBean.getUserType()!=1){
					bean.setAutoBidMaxAmt(config.getPersonalMaxAmount()+"");
				}else{
					bean.setAutoBidMaxAmt(config.getEnterpriseMaxAmount()+"");
				}
				bean.setAutoBidDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
				bean.setLogRemark("自动出借授权");
				break;
			case AuthBean.AUTH_TYPE_AUTO_CREDIT:
				config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH);
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
				config=this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
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
				config=this.getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH);
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
					config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH);
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
					config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH);
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
					config=this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
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
			case AuthBean.AUTH_TYPE_PAY_REPAY_AUTH:
				if(!this.checkIsAuth(authBean.getUserId(), AuthBean.AUTH_TYPE_PAYMENT_AUTH)){
					config=this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
					bean.setPaymentAuth(AuthBean.AUTH_START_OPEN);
					if(authBean.getUserType()!=1){
						bean.setPaymentMaxAmt(config.getPersonalMaxAmount()+"");
					}else{
						bean.setPaymentMaxAmt(config.getEnterpriseMaxAmount()+"");
					}
					bean.setPaymentDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
					authBean.setPaymentAuthStatus(true);
				}
				if(!this.checkIsAuth(authBean.getUserId(), AuthBean.AUTH_TYPE_REPAY_AUTH)){
					config=this.getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH);
					bean.setRepayAuth(AuthBean.AUTH_START_OPEN);
					if(authBean.getUserType() != 1){
						bean.setRepayMaxAmt(config.getPersonalMaxAmount()+"");
					}else{
						bean.setRepayMaxAmt(config.getEnterpriseMaxAmount()+"");
					}
					bean.setRepayDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
					authBean.setRepayAuthAuthStatus(true);
				}
				bean.setLogRemark("服务费、还款二合一授权");
				break;
			default:
				break;
		}

		bean.setRetUrl(authBean.getRetUrl());
		bean.setSuccessfulUrl(authBean.getSuccessUrl());
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
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public BankCallBean getTermsAuthQuery(int userId, String channel) {
		BankOpenAccountVO bankOpenAccount = getBankOpenAccount(userId);
		// 调用查询出借人签约状态查询
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

		if(AuthBean.AUTH_TYPE_PAY_REPAY_AUTH.equals(txcode)){
			//（服务费授权、还款授权）二合一
			endTime = hjhUserAuth.getAutoPaymentEndTime()==null?"0":hjhUserAuth.getAutoPaymentEndTime();
			status = hjhUserAuth.getAutoPaymentStatus();
			if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				return false;
			}
			endTime = hjhUserAuth.getAutoRepayEndTime()==null?"0":hjhUserAuth.getAutoRepayEndTime();
			status = hjhUserAuth.getAutoRepayStatus();
			// 0是未授权
			if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				return false;
			}
		}else if(AuthBean.AUTH_TYPE_MERGE_AUTH.equals(txcode)){
			//合并授权（自动投标、自动债转、服务费授权）三合一
			//自动债转
			endTime = nowTime+1;
			status = hjhUserAuth.getAutoCreditStatus();
			if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				return false;
			}
			//自动投标
			endTime = hjhUserAuth.getAutoBidEndTime()==null?"0":hjhUserAuth.getAutoBidEndTime();
			status = hjhUserAuth.getAutoInvesStatus();
			if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				return false;
			}
			//服务费授权
			endTime = hjhUserAuth.getAutoPaymentEndTime()==null?"0":hjhUserAuth.getAutoPaymentEndTime();
			status = hjhUserAuth.getAutoPaymentStatus();
			if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				return false;
			}
		}else{
			//单个授权
			if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
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
		if (this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus() - 1 != 0) {
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
		if (this.getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH).getEnabledStatus() - 1 != 0) {
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

		if(AuthBean.AUTH_TYPE_PAY_REPAY_AUTH.equals(txcode)){
			Integer paymentflag = 0;
			Integer repayflag = 0;
			//缴费授权
			endTime = hjhUserAuth.getAutoPaymentEndTime();
			status = hjhUserAuth.getAutoPaymentStatus();
			// 0是未授权
			if (status != 0) {
				Integer endTimeInt=Integer.parseInt(endTime);
				Integer nowTimeInt=Integer.parseInt(nowTime);
				//20180731-20180630=101  20180228-20180131=97
				if(endTimeInt - nowTimeInt > 101){
					paymentflag = 1;
				}else if(endTimeInt - nowTimeInt <= 101&&endTimeInt - nowTimeInt > 0){
					paymentflag = 2;
				}else {
					paymentflag = 3;
				}
			}
			//还款授权
			endTime = hjhUserAuth.getAutoRepayEndTime();
			status = hjhUserAuth.getAutoRepayStatus();
			// 0是未授权
			if (status != 0) {
				Integer endTimeInt=Integer.parseInt(endTime);
				Integer nowTimeInt=Integer.parseInt(nowTime);
				//20180731-20180630=101  20180228-20180131=97
				if(endTimeInt - nowTimeInt > 101){
					repayflag = 1;
				}else if(endTimeInt - nowTimeInt <= 101&&endTimeInt - nowTimeInt > 0){
					repayflag = 2;
				}else {
					repayflag = 3;
				}
			}
			if(paymentflag==0 || repayflag==0){
				return 0;
			}
			return repayflag>paymentflag ? repayflag : paymentflag;
		}else if(AuthBean.AUTH_TYPE_MERGE_AUTH.equals(txcode)){
			Integer paymentflag;
			Integer invesflag;
			Integer creditflag;
			endTime = nowTime+101;
			status = hjhUserAuth.getAutoCreditStatus();
			// 0是未授权
			if (status - 0 != 0 ) {
				creditflag=1;
			}else{
				creditflag=0;
			}
			endTime = hjhUserAuth.getAutoBidEndTime();
			status = hjhUserAuth.getAutoInvesStatus();

			// 0是未授权
			if (status - 0 == 0) {
				invesflag= 0;
			}else{
				Integer endTimeInt=Integer.parseInt(endTime);
				Integer nowTimeInt=Integer.parseInt(nowTime);
				//20180731-20180630=101  20180228-20180131=97
				if(endTimeInt - nowTimeInt > 101){
					invesflag=1;
				}else if(endTimeInt - nowTimeInt <= 101&&endTimeInt - nowTimeInt > 0){
					invesflag= 2;
				}else {
					invesflag= 3;
				}
			}
			endTime = hjhUserAuth.getAutoPaymentEndTime();
			status = hjhUserAuth.getAutoPaymentStatus();
			// 0是未授权
			if (status - 0 == 0) {
				paymentflag= 0;
			}else{
				Integer endTimeInt=Integer.parseInt(endTime);
				Integer nowTimeInt=Integer.parseInt(nowTime);
				//20180731-20180630=101  20180228-20180131=97
				if(endTimeInt - nowTimeInt > 101){
					paymentflag=1;
				}else if(endTimeInt - nowTimeInt <= 101&&endTimeInt - nowTimeInt > 0){
					paymentflag= 2;
				}else {
					paymentflag= 3;
				}
			}
			if(invesflag==0||creditflag==0||paymentflag==0){
				return 0;
			}
			return invesflag>paymentflag?invesflag:paymentflag;
		}else{
			// 0是未授权
			if (status - 0 == 0) {
				return 0;
			}else{
				Integer endTimeInt=Integer.parseInt(endTime);
				Integer nowTimeInt=Integer.parseInt(nowTime);
				//20180731-20180630=101  20180228-20180131=97
				if(endTimeInt - nowTimeInt > 101){
					return 1;
				}else if(endTimeInt - nowTimeInt <= 101&&endTimeInt - nowTimeInt > 0){
					return 2;
				}else {
					return 3;
				}
			}
		}
	}

	@Override
	public boolean checkInvesAuthStatus(Integer userId) {
		// 如果用户ID没有 直接成功吧 不会出现这种
		if (userId == null) {
			return true;
		}
		// 检查开关是否打开 没打开 不用校验
		if (this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH).getEnabledStatus() - 1 != 0) {
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
		if (this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH).getEnabledStatus() - 1 != 0) {
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
		try {
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
							HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH);
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
							HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
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
							HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH);
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
							HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH);
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
							HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
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
					case AuthBean.AUTH_TYPE_PAY_REPAY_AUTH:
						if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)
								&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()), AuthBean.AUTH_TYPE_PAYMENT_AUTH)){
							HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
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
								&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()), AuthBean.AUTH_TYPE_REPAY_AUTH)){
							HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH);
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
						&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()), AuthBean.AUTH_TYPE_AUTO_BID)){
					HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH);
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
						&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()), AuthBean.AUTH_TYPE_PAYMENT_AUTH)){
					HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
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
						&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()), AuthBean.AUTH_TYPE_REPAY_AUTH)){
					HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH);
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
		}catch (Exception e){
			return true;
		}
		return false;
	}

	@Override
	public WebResult<Object> seachUserAuthErrorMessgae(String logOrdId) {


		try {
			logger.info("延迟1000毫秒以后查询");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
		WebResult<Object> result = new WebResult<Object>();
		HjhUserAuthLogVO hjhUserAuthLogVO = amUserClient.selectByExample(logOrdId);
		result.setStatus(WebResult.SUCCESS);
		Map<String,String> map = new HashedMap();
		if(hjhUserAuthLogVO != null || StringUtils.isNotBlank(hjhUserAuthLogVO.getRemark())){
			String remark = hjhUserAuthLogVO.getRemark();
			map.put("error", remark);
			logger.info("用户授权失败原因:[" + remark + "].");
		}else{
			map.put("error","系统异常，请稍后再试！");
		}
		result.setData(map);
		return result;
	}

	@Override
	public void updateUserAuthLog(String logOrderId, String message) {
		HjhUserAuthLogVO hjhUserAuthLog=new HjhUserAuthLogVO();
		hjhUserAuthLog.setOrderId(logOrderId);
		hjhUserAuthLog.setRemark(message);
		amUserClient.updateUserAuthLog(hjhUserAuthLog);
	}

	/**
	 *
	 * 检查还款授权和服务费授权状态
	 * @author sunss
	 * @param autoRepayStatus
	 * @param paymentAuthStatus
	 * @return
	 */
	@Override
	public Integer checkAuthStatus(Integer autoRepayStatus,Integer paymentAuthStatus){
		HjhUserAuthConfigVO paymenthCconfig = getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
		HjhUserAuthConfigVO repayCconfig = getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH);
		if (paymenthCconfig != null && repayCconfig != null && paymenthCconfig.getEnabledStatus() - 1 == 0
				&& repayCconfig.getEnabledStatus() - 1 == 0) {
			// 如果两个都开启了
			if ((paymentAuthStatus == null || paymentAuthStatus - 1 != 0)
					&& (autoRepayStatus == null || autoRepayStatus - 1 != 0)) {
				// 借款人必须服务费授权
				return 7;
			}
		}
		// 服务费授权
		if (paymenthCconfig != null && paymenthCconfig.getEnabledStatus() - 1 == 0) {
			if (paymentAuthStatus == null || paymentAuthStatus - 1 != 0) {
				// 借款人必须服务费授权
				return 5;
			}
		}
		// 还款授权
		if (repayCconfig != null && repayCconfig.getEnabledStatus() - 1 == 0) {
			if (autoRepayStatus == null || autoRepayStatus - 1 != 0) {
				// 借款人必须还款授权
				return 6;
			}
		}
		return 0;
	}


	/**
	 * 从redis里面获取授权配置
	 * @param key
	 * @return
	 */
	@Override
	public HjhUserAuthConfigVO getAuthConfigFromCache(String key){
		HjhUserAuthConfigVO hjhUserAuthConfig=RedisUtils.getObj(key,HjhUserAuthConfigVO.class);
		return hjhUserAuthConfig;
	}

	@Override
	public Map<String, String> checkApiParam(ApiAuthRequesBean requestBean) {
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("status", "0");
		if (Validator.isNull(requestBean.getInstCode())) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
			resultMap.put("statusDesc", "机构编号不能为空");
			return resultMap;
		}
		if (Validator.isNull(requestBean.getRetUrl())) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
			resultMap.put("mess", "同步地址不能为空");
			return resultMap;
		}
		if (Validator.isNull(requestBean.getNotifyUrl())) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
			resultMap.put("mess", "异步地址不能为空");
			return resultMap;
		}
		// 渠道
		if (Validator.isNull(requestBean.getChannel())) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
			resultMap.put("mess", "渠道号不能为空");
			return resultMap;
		}
		if (Validator.isNull(requestBean.getAuthType())) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_SQ000001);
			resultMap.put("mess", "授权类型不能为空");
			return resultMap;
		}
		if (!Arrays.asList(AuthBean.AUTH_TYPE_AUTO_CREDIT,
				AuthBean.AUTH_TYPE_AUTO_BID,
				AuthBean.AUTH_TYPE_PAYMENT_AUTH,
				AuthBean.AUTH_TYPE_REPAY_AUTH).contains(requestBean.getAuthType())) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_SQ000002);
			resultMap.put("mess", "授权类型不是指定类型");
			return resultMap;
		}
		// 验签
        if (!this.verifyRequestSign(requestBean, BaseDefine.METHOD_MERGE_AUTH_PAGE_PLUS)) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000002);
			resultMap.put("mess", "验签失败");
			return resultMap;
        }


		// 根据电子账户号查询用户ID
		BankOpenAccountVO bankOpenAccount = this.amUserClient.selectBankOpenAccountByAccountId(requestBean.getAccountId());
		if(bankOpenAccount == null){
			logger.info("没有根据电子银行卡找到用户[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000004);
			resultMap.put("mess", "没有根据电子银行卡找到用户");
			return resultMap;
		}


		// 检查用户是否存在
		UserVO user = this.getUsersById(bankOpenAccount.getUserId());//用户ID
		if (user == null) {
			logger.info("用户不存在汇盈金服账户[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000007);
			resultMap.put("mess", "用户不存在汇盈金服账户");
			return resultMap;
		}


		if (user.getBankOpenAccount().intValue() != 1) {// 未开户
			logger.info("用户未开户！[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000006);
			resultMap.put("mess", "用户未开户！");
			return resultMap;
		}

		// 检查是否设置交易密码
		Integer passwordFlag = user.getIsSetPassword();
		if (passwordFlag != 1) {// 未设置交易密码
			logger.info("未设置交易密码！[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_TP000002);
			resultMap.put("mess", "未设置交易密码！");
			return resultMap;
		}

		// 查询是否已经授权过
		boolean isAuth = this.checkIsAuth(user.getUserId(),requestBean.getAuthType());
		if(isAuth){
			logger.info("已授权,请勿重复授权！[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000009);
			resultMap.put("mess", "已授权,请勿重复授权！");
			return resultMap;
		}
		resultMap.put("status", "1");
		return resultMap;
	}

	@Override
	public ModelAndView getApiCallbankMV(AuthBean authBean) {
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
				config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH);
				bean.setAutoBid(AuthBean.AUTH_START_OPEN);
				if(authBean.getUserType()!=1){
					bean.setAutoBidMaxAmt(config.getPersonalMaxAmount()+"");
				}else{
					bean.setAutoBidMaxAmt(config.getEnterpriseMaxAmount()+"");
				}
				bean.setAutoBidDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
				bean.setLogRemark("自动出借授权");
				break;
			case AuthBean.AUTH_TYPE_AUTO_CREDIT:
				config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH);
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
				config=this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
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
				config=this.getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH);
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
					config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH);
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
					config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH);
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
					config=this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
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
			case AuthBean.AUTH_TYPE_PAY_REPAY_AUTH:
				if(!this.checkIsAuth(authBean.getUserId(), AuthBean.AUTH_TYPE_PAYMENT_AUTH)){
					config=this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
					bean.setPaymentAuth(AuthBean.AUTH_START_OPEN);
					if(authBean.getUserType()!=1){
						bean.setPaymentMaxAmt(config.getPersonalMaxAmount()+"");
					}else{
						bean.setPaymentMaxAmt(config.getEnterpriseMaxAmount()+"");
					}
					bean.setPaymentDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
					authBean.setPaymentAuthStatus(true);
				}
				if(!this.checkIsAuth(authBean.getUserId(), AuthBean.AUTH_TYPE_REPAY_AUTH)){
					config=this.getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH);
					bean.setRepayAuth(AuthBean.AUTH_START_OPEN);
					if(authBean.getUserType() != 1){
						bean.setRepayMaxAmt(config.getPersonalMaxAmount()+"");
					}else{
						bean.setRepayMaxAmt(config.getEnterpriseMaxAmount()+"");
					}
					bean.setRepayDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
					authBean.setRepayAuthAuthStatus(true);
				}
				bean.setLogRemark("服务费、还款二合一授权");
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
			ModelAndView model = BankCallUtils.callApi(bean);
			return model;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * AEMS系统:用户授权
	 *
	 * @param requestBean
	 * @return
	 */
	@Override
	public Map<String, String> checkAemsParam(@Valid AemsMergeAuthPagePlusRequestBean requestBean) {
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("status", "0");
		if (Validator.isNull(requestBean.getInstCode())) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
			resultMap.put("statusDesc", "机构编号不能为空");
			return resultMap;
		}
		if (Validator.isNull(requestBean.getRetUrl())) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
			resultMap.put("statusDesc", "同步地址不能为空");
			return resultMap;
		}
		if (Validator.isNull(requestBean.getNotifyUrl())) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
			resultMap.put("statusDesc", "异步地址不能为空");
			return resultMap;
		}
		// 渠道
		if (Validator.isNull(requestBean.getChannel())) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
			resultMap.put("statusDesc", "渠道号不能为空");
			return resultMap;
		}
		if (Validator.isNull(requestBean.getAuthType())) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_SQ000001);
			resultMap.put("statusDesc", "授权类型不能为空");
			return resultMap;
		}
		if (!Arrays.asList(AuthBean.AUTH_TYPE_AUTO_CREDIT,
				AuthBean.AUTH_TYPE_AUTO_BID,
				AuthBean.AUTH_TYPE_PAYMENT_AUTH,
				AuthBean.AUTH_TYPE_REPAY_AUTH).contains(requestBean.getAuthType())) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_SQ000002);
			resultMap.put("statusDesc", "授权类型不是指定类型");
			return resultMap;
		}
		// 验签
		if (!SignUtil.AEMSVerifyRequestSign(requestBean,"/aems/mergeauth/mergeAuth/mergeAuth")) {
			logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000002);
			resultMap.put("statusDesc", "验签失败");
			return resultMap;
		}


		// 根据电子账户号查询用户ID
		BankOpenAccountVO bankOpenAccount = this.amUserClient.selectBankOpenAccountByAccountId(requestBean.getAccountId());
		if(bankOpenAccount == null){
			logger.info("没有根据电子银行卡找到用户[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000004);
			resultMap.put("statusDesc", "没有根据电子银行卡找到用户");
			return resultMap;
		}


		// 检查用户是否存在
		UserVO user = this.getUsersById(bankOpenAccount.getUserId());//用户ID
		if (user == null) {
			logger.info("用户不存在汇盈金服账户[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000007);
			resultMap.put("statusDesc", "用户不存在汇盈金服账户");
			return resultMap;
		}


		if (user.getBankOpenAccount().intValue() != 1) {// 未开户
			logger.info("用户未开户！[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000006);
			resultMap.put("statusDesc", "用户未开户！");
			return resultMap;
		}

		// 检查是否设置交易密码
		Integer passwordFlag = user.getIsSetPassword();
		if (passwordFlag != 1) {// 未设置交易密码
			logger.info("未设置交易密码！[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_TP000002);
			resultMap.put("statusDesc", "未设置交易密码！");
			return resultMap;
		}

		// 查询是否已经授权过
		boolean isAuth = this.checkIsAuth(user.getUserId(),requestBean.getAuthType());
		if(isAuth){
			logger.info("已授权,请勿重复授权！[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_CE000009);
			resultMap.put("statusDesc", "已授权,请勿重复授权！");
			return resultMap;
		}
		resultMap.put("status", "1");
		return resultMap;
	}

	/**
	 * 获得所有协议类型
	 * @return
	 */
	@Override
	public List<ProtocolTemplateVO> getProtocolTypes() {
		return amTradeClient.getProtocolTypes();
	}

	/**
	 * 授权成功后,发送数据统计MQ
	 *
	 * @param sensorsDataBean
	 */
	private void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) throws MQException {
		this.commonProducer.messageSendDelay(new MessageContent(MQConstant.SENSORSDATA_AUTH_TOPIC, UUID.randomUUID().toString(), sensorsDataBean), 2);
	}

}
