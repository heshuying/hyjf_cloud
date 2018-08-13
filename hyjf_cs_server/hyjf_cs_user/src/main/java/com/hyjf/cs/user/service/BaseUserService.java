package com.hyjf.cs.user.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BankSmsLogRequest;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.user.bean.BaseBean;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;


public interface BaseUserService extends BaseService{

	/**
	 * 判断用户是否存在
	 * @param mobile
	 * @return
	 */
	 boolean existUser(String mobile);

	/**
	 * @Description 根据手机号查询user
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:36
	 */
	UserVO getUsersByMobile(String mobile);

	HjhUserAuthVO getHjhUserAuth(Integer userId);

	/**
	 * @Description 根据userid查询用户
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:37
	 */
	UserVO getUsersById(Integer userId);

	/**
	 * 验证验证码
	 * @param mobile
	 * @param code
	 * @param validCodeType
	 * @param clientPc
	 * @param ckcodeYiyan
	 * @param ckcodeYiyan1
	 * @return
	 */
	int updateCheckMobileCode(String mobile, String code, String validCodeType, String clientPc, Integer ckcodeYiyan, Integer ckcodeYiyan1,boolean isUpdate);

	/**
	 * 验证外部请求签名
	 * @param paramBean
	 * @param methodName
	 * @return
	 */
	boolean verifyRequestSign(BaseBean paramBean, String methodName);

	/**
	 * 获取用户在银行的开户信息
	 * @param userId
	 * @return
	 */
    BankOpenAccountVO getBankOpenAccount(Integer userId);

    BankOpenAccountVO getBankOpenAccountByAccount(String accountId);

    /**
	 * 校验用户是否已开户
	 * @param userId
	 * @return
	 */
	boolean checkIsOpen(Integer userId);

	/**
	 * 更新用户信息
	 * @param userVO
	 * @return
	 */
	Integer updateUserByUserId(UserVO userVO);

	/**
	 * 请求验证码接口
	 * @param userId
	 * @param mobile
	 * @param txCode
	 * @param client
	 * @param cardNo
	 * @return
	 */
	BankCallBean callSendCode(Integer userId, String mobile, String txCode, String client, String cardNo);

	/**
	 * 更新绑卡短信验证码
	 * @param request
	 * @return
	 */
	boolean updateAfterSendCode(BankSmsLogRequest request);

	/**
	 * 根据用户ID查询企业用户信息
	 * @param userId
	 * @return
	 */
	CorpOpenAccountRecordVO getCorpOpenAccountRecord(Integer userId);

	/**
	 * @Description 根据token查询user
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:34
	 */
	WebViewUserVO getUserFromCache(int userId);

	/**
	 * 设置token
	 * @param
	 * @param webViewUserVO
	 * @return
	 */
	WebViewUserVO setToken(WebViewUserVO webViewUserVO);

	/**
	 * 获取UserInfo
	 * @param userId
	 * @return
	 */
	UserInfoVO getUserInfo(int userId);

	/**
	 * 更新用户缓存信息
	 * @param webViewUserVO
	 * @return
	 */
    WebViewUserVO updateUserToCache(WebViewUserVO webViewUserVO);

    /**
	 * 登录
	 * @param userId
	 * @return
	 */
	WebViewUserVO getWebViewUserByUserId(Integer userId);
	/**
	 * @Description 根据用户信息查询用户绑卡列表
	 * @Author pangchengchao
	 * @Version v0.1
	 * @Date
	 */
	List<BankCardVO> getBankOpenAccountById(UserVO userVO);

    String getBankReturnErrorMsg(String retCode);

    String strEncode(String str);

	JSONObject synBalance(String account, String instcode, String webHost, String aopAccesskey);

	AccountVO getAccountByUserId(Integer userId);

    String getBankRetMsg(String retCode);
}
