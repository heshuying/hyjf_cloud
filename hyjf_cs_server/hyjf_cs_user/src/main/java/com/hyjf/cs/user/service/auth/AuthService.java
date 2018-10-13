package com.hyjf.cs.user.service.auth;

import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.user.bean.AuthBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public interface AuthService extends BaseUserService {
    
    /**
     * 
     * 根据用户id查询用户签约授权信息
     * @param userId
     * @return
     */
    HjhUserAuthVO getHjhUserAuthByUserId(Integer userId);
    /**
     * 
     * 根据用户id更新用户签约授权信息
     * @param userId
     * @param bean 
     * @param authType 
     */
    void updateUserAuth(Integer userId, BankCallBean bean, String authType);
    /**
     * 
     * 插入用户签约授权log
     * @param userId
     * @param orderId
     * @param authType
     * @param client
     */
    void insertUserAuthLog(int userId, String orderId, Integer client, String authType);

    Map<String,Object> getCallbankMV(AuthBean authBean);
    
    /**
     * 用户授权查询接口
     * @author pcc
     * @return
     */
    BankCallBean getTermsAuthQuery(int userId, String channel);
    
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

    /**
     * 查询授权错误信息
     * @param logOrdId
     * @return
     */
    WebResult<Object> seachUserAuthErrorMessgae(String logOrdId);

    /**
     * 更新授权日志信息
     * @param logOrderId
     * @param message
     */
    void updateUserAuthLog(String logOrderId, String message);
}
