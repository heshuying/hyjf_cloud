package com.hyjf.cs.user.service.auth;

import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import com.hyjf.am.vo.user.HjhUserAuthConfigVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.AemsMergeAuthPagePlusRequestBean;
import com.hyjf.cs.user.bean.ApiAuthRequesBean;
import com.hyjf.cs.user.bean.AuthBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface AuthService extends BaseUserService {
/*    String KEY_PAYMENT_AUTH = "AUTHCONFIG:paymentAuth"; // 缴费授权
    String KEY_REPAYMENT_AUTH = "AUTHCONFIG:repaymentAuth"; // 还款授权
    String KEY_AUTO_TENDER_AUTH = "AUTHCONFIG:autoTenderAuth"; // 自动投标
    String KEY_AUTO_CREDIT_AUTH = "AUTHCONFIG:autoCreditAuth"; // 自动债转
    String KEY_IS_CHECK_USER_ROLES = "CHECKE:ISCHECKUSERROLES"; // 是否校验用户角色*/
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
     * 检查自动出借授权状态
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

    Integer checkAuthStatus(Integer autoRepayStatus,Integer paymentAuthStatus);
    HjhUserAuthConfigVO getAuthConfigFromCache(String key);

    Map<String,String> checkApiParam(ApiAuthRequesBean requestBean);

    ModelAndView getApiCallbankMV(AuthBean authBean);

    /**
     * AEMS系统多合一授权参数校验
     * @param requestBean
     * @return
     */
    Map<String,String> checkAemsParam(@Valid AemsMergeAuthPagePlusRequestBean requestBean);

    /** * 获得所有协议类型
     * @return
     */
    List<ProtocolTemplateVO> getProtocolTypes();
}
