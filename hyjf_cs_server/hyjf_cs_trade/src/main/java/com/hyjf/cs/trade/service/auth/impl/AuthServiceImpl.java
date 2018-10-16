package com.hyjf.cs.trade.service.auth.impl;

import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.bean.AuthBean;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AuthServiceImpl extends BaseTradeServiceImpl implements AuthService {

	public HjhUserAuthVO getHjhUserAuthByUserId(Integer userId) {
		HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(userId);
		return hjhUserAuth;
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
		UserVO user=this.getUserByUserId(Integer.parseInt(bean.getLogUserId()));
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
		HjhUserAuthConfigVO paymenthCconfig = getAuthConfigFromCache(KEY_PAYMENT_AUTH);
		HjhUserAuthConfigVO repayCconfig = getAuthConfigFromCache(KEY_REPAYMENT_AUTH);
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
}
