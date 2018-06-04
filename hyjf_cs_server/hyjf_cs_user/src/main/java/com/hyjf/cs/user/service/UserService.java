package com.hyjf.cs.user.service;

import java.util.Map;

import javax.validation.Valid;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.beans.AutoPlusRequestBean;
import com.hyjf.cs.user.beans.BaseBean;
import com.hyjf.cs.user.beans.BaseMapBean;
import com.hyjf.cs.user.result.MobileModifyResultBean;
import com.hyjf.cs.user.vo.RegisterVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * @author xiasq
 * @version UserService, v0.1 2018/4/11 9:34
 */
public interface UserService {
	/**
	 * 注册
	 * @param registerVO
	 * @param ip
	 * @return
	 * @throws ReturnMessageException
	 */
	UserVO register(RegisterVO registerVO, String ip)
            throws ReturnMessageException;

	/**
	 * api端注册
	 * @param registerVO
	 * @param ipAddr
	 * @return
	 */
	UserVO apiRegister(@Valid RegisterVO registerVO, String ipAddr);

	/**
	 * 用户存在检查
	 * 
	 * @param mobile
	 * @return
	 */
	boolean existUser(String mobile);

	/**
	 *
	 * @param loginUserName
	 *            可以是手机号或者用户名
	 * @param loginPassword
	 * @param ip
	 */
	UserVO login(String loginUserName, String loginPassword, String ip);

	/**
	 * 授权自动债转、投资
	 * @param token
	 * @param client  0web 1wechat 2app
	 * @param type 1表示投资 2表示债转
	 * @param channel
	 * @param lastSrvAuthCode
	 * @param smsCode
	 * @return
	 */
    BankCallBean userCreditAuthInves(String token, Integer client, String type, String channel, String lastSrvAuthCode,String smsCode);

	/**
	 * web自动投资授权同步回调
	 * @param token
	 * @param bean
	 * @param urlType
	 * @param isSuccess
	 * @return
	 */
	Map<String,String> userAuthReturn(String token, BankCallBean bean, String urlType, String isSuccess);

	/**
	 * 异步回调接口
	 * @param bean
	 * @return
	 */
    String userBgreturn(BankCallBean bean);

	boolean verifyRequestSign(BaseBean paramBean, String methodName);

	JSONObject updatePassWd(Integer userId, String oldPW, String newPW);

	Map<String,String> checkParam(AutoPlusRequestBean payRequestBean);

	Map<String,String> getErrorMV(AutoPlusRequestBean payRequestBean, String status);


	/**
	 * 获取用戶通知配置信息
	 * @param userId
	 * @return
	 */
	UserVO queryUserByUserId(Integer userId);

	/**
	 * 保存用户通知设置
	 * @param userVO
	 * @return
	 */
	int updateUserByUserId(UserVO userVO);

	/**
	 * 账户设置查询
	 * @param token
	 * @return
	 */
	String safeInit(String token);

	MobileModifyResultBean queryForMobileModify(Integer userId);

	boolean checkForMobileModify(String newMobile, String smsCode);

	void checkForEmailSend(String email, Integer userId);

	boolean sendEmailActive(Integer userId, String email) throws MQException;

	boolean updateEmail(Integer userId, String email) throws MQException;

	void checkForEmailBind(String email, String userId, String activeCode, WebViewUser user);
}
