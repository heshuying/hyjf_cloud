package com.hyjf.cs.user.service;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.util.ApiSignUtil;
import com.hyjf.cs.user.beans.AutoPlusRequestBean;
import com.hyjf.cs.user.beans.AutoStateQueryRequest;
import com.hyjf.cs.user.beans.BaseBean;
import com.hyjf.cs.user.beans.BaseDefine;
import com.hyjf.cs.user.client.AmUserClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl implements BaseService {

	Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	@Autowired
	AmUserClient amUserClient;



	/**
	 * @param token
	 * @Description 根据token查询user
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:34
	 */
	@Override
	public WebViewUser getUsersByToken(String token) {
		WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
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
		WebViewUser user = getUsersByToken(token);
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
}
