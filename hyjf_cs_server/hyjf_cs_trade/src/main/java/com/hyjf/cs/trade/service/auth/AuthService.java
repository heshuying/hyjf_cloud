package com.hyjf.cs.trade.service.auth;

import com.hyjf.am.vo.user.HjhUserAuthConfigVO;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;


public interface AuthService extends BaseTradeService {

    public final static String KEY_PAYMENT_AUTH = "AUTHCONFIG:paymentAuth"; // 缴费授权
    public final static String KEY_REPAYMENT_AUTH = "AUTHCONFIG:repaymentAuth"; // 还款授权
    public final static String KEY_AUTO_TENDER_AUTH = "AUTHCONFIG:autoTenderAuth"; // 自动投标
    public final static String KEY_AUTO_CREDIT_AUTH = "AUTHCONFIG:autoCreditAuth"; // 自动债转
    public final static String KEY_IS_CHECK_USER_ROLES = "CHECKE:ISCHECKUSERROLES"; // 是否校验用户角色
    /**
     * 
     * 检查是否授权过了
     * @author pcc
     * @param userId
     * @return
     */
    boolean checkIsAuth(Integer userId, String txcode);
	
	/**
     * 
     * 检查服务费授权状态
     * @author pcc
     * @param userId
     * @return
     */
    boolean checkPaymentAuthStatus(Integer userId);
    
    /**
     * 
     * 检查还款授权状态
     * @author pcc
     * @param userId
     * @return
     */
    boolean checkRepayAuthStatus(Integer userId);
    
    /**
     * 
     * 检查授权过期
     * @author pcc
     * @param userId
     * @return
     */
    Integer checkAuthExpire(Integer userId, String txcode);
    /**
     * 
     * 检查自动投资授权状态
     * @author pcc
     * @param userId
     * @return
     */
	boolean checkInvesAuthStatus(Integer userId);
	/**
     * 
     * 检查自动债转授权状态
     * @author pcc
     * @param userId
     * @return
     */
	boolean checkCreditAuthStatus(Integer userId);
	/**
     * 校验是否默认配置
     * @param bean
     * @param authType
     * @return
     */
	boolean checkDefaultConfig(BankCallBean bean, String authType);

	Integer checkAuthStatus(Integer autoRepayStatus,Integer paymentAuthStatus);
    HjhUserAuthConfigVO getAuthConfigFromCache(String key);
}
