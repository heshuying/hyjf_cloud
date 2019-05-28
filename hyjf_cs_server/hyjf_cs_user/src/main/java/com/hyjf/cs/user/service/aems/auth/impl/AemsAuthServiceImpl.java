package com.hyjf.cs.user.service.aems.auth.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.UserAuthRequest;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthLogVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.AemsMergeAuthPagePlusRequestBean;
import com.hyjf.cs.user.bean.AuthBean;
import com.hyjf.cs.user.constants.AemsAuthEnum;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.service.aems.auth.AemsAuthService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AemsAuthServiceImpl extends BaseUserServiceImpl implements AemsAuthService {

	/**
	 * AEMS系统:用户授权
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

		//授权类型校验
		String[] authTypeArray = requestBean.getAuthType().split(",");
		if (authTypeArray.length<1 || authTypeArray.length > 4){
			logger.info("授权类型过长[" + JSONObject.toJSONString(requestBean, true) + "]");
			resultMap.put("status", ErrorCodeConstant.STATUS_SQ000006);
			resultMap.put("statusDesc", "授权类型过长");
			return resultMap;
		}
		for (String authType : authTypeArray) {
			// 1：自动投标授权 2：自动债转授权 3：缴费授权 4：还款授权
			if (!Arrays.asList("1", "2", "3", "4").contains(authType)) {
				logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
				resultMap.put("status", ErrorCodeConstant.STATUS_SQ000002);
				resultMap.put("statusDesc", "授权类型不是指定类型");
				return resultMap;
			}
		}

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
		logger.info("根据用户id查询用户开户状态："+user.getBankOpenAccount()+" 设置密码状态："+user.getIsSetPassword());
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
		boolean isAuth = this.checkIsAuth(user.getUserId(), requestBean.getAuthType());
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
	 * 检查是否授权过了
	 * @author pcc
	 * @param userId
	 * @return
	 */
	@Override
	public boolean checkIsAuth(Integer userId, String authTypeString) {
		HjhUserAuthVO hjhUserAuth = getHjhUserAuthByUserId(userId);
		String nowTime = GetDate.date2Str(new Date(), GetDate.yyyyMMdd);

		// 授权信息为空
		if(hjhUserAuth == null){
			return false;
		}

		boolean autoBidFlag = true;;
		boolean autoCreditFlag = true;;
		boolean autoPaymentFlag = true;;
		boolean autoRepayFlag = true;;
		// 1：自动投标授权 2：自动债转授权 3：缴费授权 4：还款授权
		for (String authType : authTypeString.split(",")) {
			if (authType.equals("1")) {
				autoBidFlag = checkAutoBidAuthStatus(hjhUserAuth, nowTime);
			}
			if (authType.equals("2")) {
				autoCreditFlag = checkAutoCreditAuthStatus(hjhUserAuth, nowTime);
			}
			if (authType.equals("3")) {
				autoPaymentFlag = checkAutoPaymentAuthStatus(hjhUserAuth, nowTime);
			}
			if (authType.equals("4")) {
				autoRepayFlag = checkAutoRepayAuthStatus(hjhUserAuth, nowTime);
			}
		}

		if (autoBidFlag && autoCreditFlag && autoPaymentFlag && autoRepayFlag) {
			return true;
		}

		return false;
	}

	/**
	 * 校验用户角色和授权类型是否一致
	 * @param authBean
	 * @param authTypeString
	 * @param resultMap
	 * @return
	 */
	public Map<String, String> checkUserRoleAndAuthType(AuthBean authBean, String authTypeString, Map<String, String> resultMap){
		logger.info("所有用户角色:[1-投资人 2-借款人 3-垫付机构], 所有授权:[1：自动投标授权 2：自动债转授权 3：缴费授权 4：还款授权]");
		logger.info("正在进行授权的，用户角色:[" +authBean.getIdentity()+ "], 此次进行的授权:["+ authBean.getAuthType() +"]");
		//Map<String, String> resultMap = new HashMap<>();
		String[] authTypeArray = authTypeString.split(",");
		// 1-投资人 2-借款人 3-垫付机构
		if (authBean.getIdentity().equals("1")){
			for (String authType : authTypeArray) {
				if (authType.equals("4")) {
					logger.info("授权类型与角色不匹配，出借人只能进行 1-自动投资授权 2-自动债转授权 3-缴费授权，此次进行的授权:["+ authBean.getAuthType() +"]");
					resultMap.put("status", ErrorCodeConstant.STATUS_SQ000003);
					resultMap.put("statusDesc", "授权类型与角色不匹配！");
					return resultMap;
				}
			}
		}else if(authBean.getIdentity().equals("2")){
			for (String authType : authTypeArray) {
				if (authType.equals("1") || authType.equals("2")) {
					logger.info("授权类型与角色不匹配，借款人只能进行 3-缴费授权 4-还款授权，此次进行的授权:["+ authBean.getAuthType() +"]");
					resultMap.put("status", ErrorCodeConstant.STATUS_SQ000004);
					resultMap.put("statusDesc", "授权类型与角色不匹配！");
					return resultMap;
				}
			}
		}else if(authBean.getIdentity().equals("3")){
			for (String authType : authTypeArray) {
				if (!authType.equals("3")) {
					logger.info("授权类型与角色不匹配，垫付机构只能进行 3-缴费授权，此次进行的授权:["+ authBean.getAuthType() +"]");
					resultMap.put("status", ErrorCodeConstant.STATUS_SQ000005);
					resultMap.put("statusDesc", "授权类型与角色不匹配！");
					return resultMap;
				}
			}
		}
		resultMap.put("status", "1");
		return resultMap;
	}

	/**
	 * 补充数据，请求银行
	 * @param authBean
	 * @return
	 */
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

		if(authBean.getUserType() == 1){
			CorpOpenAccountRecordVO corpOpenAccountRecordVO = amUserClient.getCorpOpenAccountRecord(authBean.getUserId());
			if (corpOpenAccountRecordVO != null) {
				bean.setName(corpOpenAccountRecordVO.getBusiName());
				bean.setIdNo(corpOpenAccountRecordVO.getBusiCode());
			}

		}else{
			bean.setName(authBean.getName());
			bean.setIdNo(authBean.getIdNo());
		}
		bean.setIdentity(authBean.getIdentity());

		// 1：自动投标授权 2：自动债转授权 3：缴费授权 4：还款授权
		String autoBidString = "";
		String autoCreditString = "";
		String autoPaymentString = "";
		String autoRepayString = "";
		String[] authTypeArray = authBean.getAuthType().split(",");
		for (String authType : authTypeArray) {
			if (authType.equals("1")) {
				autoBidAuthMax(bean, authBean);
				autoBidString = "自动投资授权、";
			}
			if (authType.equals("2")) {
				autoCreditAuthMax(bean, authBean);
				autoCreditString = "自动债转授权、";
			}
			if (authType.equals("3")) {
				autoPaymentAuthMax(bean, authBean);
				autoPaymentString = "服务费授权、";
			}
			if (authType.equals("4")) {
				autoRepayAuthMax(bean, authBean);
				autoRepayString = "还款授权、";
			}
		}
		String remarkString = autoBidString+autoCreditString+autoPaymentString+autoRepayString;

		bean.setLogRemark(remarkString.substring(0, remarkString.length() - 1));

		if(authBean.getRetUrl().indexOf("&isSuccess=") != -1){
			authBean.getRetUrl().replace("&isSuccess=", "&isSuccess=1");
		}else{
			bean.setSuccessfulUrl(authBean.getRetUrl()+"&isSuccess=1");
		}
		bean.setRetUrl(authBean.getRetUrl());
		bean.setNotifyUrl(authBean.getNotifyUrl());
		bean.setForgotPwdUrl(authBean.getForgotPwdUrl());

		// 页面调用必须传的
		bean.setLogIp(authBean.getIp());
		bean.setLogOrderDate(orderDate);
		bean.setOrderId(authBean.getOrderId());
		bean.setLogOrderId(authBean.getOrderId());
		bean.setLogUserId(String.valueOf(authBean.getUserId()));
		bean.setLogClient(Integer.parseInt(authBean.getPlatform()));
		bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_TERMS_AUTH_PAGE);
		try {
			ModelAndView model = BankCallUtils.callApi(bean);
			return model;
		} catch (Exception e) {
			logger.info("AEMS多合一授权银行返回错误信息:["+ JSONObject.toJSONString(e) +"]");
		}
		return null;
	}

	/**
	 * 判断授权类型，保存至授权日志表
	 * @param authBean
	 * @param orderId
	 */
	public void saveUserAuthLog(AuthBean authBean, String orderId){
		// 将授权类型数字 顺序排序
		String[] authTypeArray = authBean.getAuthType().split(",");
		int[] authTypeIntArray = new int[authTypeArray.length];

		for(int i=0; i<authTypeArray.length; i++){
			authTypeIntArray[i] = Integer.parseInt(authTypeArray[i]);
		}
		int temp;
		int size = authTypeIntArray.length;
		for(int i = 0 ; i < size-1; i ++)
		{
			for(int j = 0 ;j < size-1-i ; j++)
			{
				if(authTypeIntArray[j] > authTypeIntArray[j+1])
				{
					temp = authTypeIntArray[j];
					authTypeIntArray[j] = authTypeIntArray[j+1];
					authTypeIntArray[j+1] = temp;
				}
			}
		}
		StringBuffer sbf = new StringBuffer();
		for(int i=0; i<authTypeIntArray.length; i++){
			sbf.append(String.valueOf(authTypeIntArray[i]));
			if (i < authTypeIntArray.length-1){
				sbf.append(",");
			}
		}
		// 插入授权日志表
		insertUserAuthLog(authBean.getUserId(), orderId, Integer.parseInt(authBean.getPlatform()), AemsAuthEnum.getAuthTypeNum(sbf.toString()));
	}

	/**
	 * 校验是否默认配置
	 * @param bean
	 * @param authTypeString
	 * @return
	 */
	@Override
	public boolean checkDefaultConfig(BankCallBean bean, String authTypeString) {
		try {
			boolean autoBidFlag = false;
			boolean autoPaymentFlag = false;
			boolean autoRepayFlag = false;
			UserVO user = this.getUsersById(Integer.parseInt(bean.getLogUserId()));
			// 授权类型
			String txcode = bean.getTxCode();
			//自动投标功能开通标志
			String autoBidAuthStatus = bean.getAutoBid();
			//缴费授权
			String paymentAuthStatus = bean.getPaymentAuth();
			//还款授权
			String repayAuthStatus = bean.getRepayAuth();
			if (BankCallConstant.TXCODE_TERMS_AUTH_QUERY.equals(txcode)) {
				// 1：自动投标授权 2：自动债转授权 3：缴费授权 4：还款授权
				String[] authTypeArray = authTypeString.split(",");
				for (String authType : authTypeArray) {
					if (authType.equals("1")) {
						autoBidFlag = autoBidAuthDefaultConfig(autoBidAuthStatus, bean, authType, user);
					}
					if (authType.equals("3")) {
						autoPaymentFlag = autoPaymentAuthDefaultConfig(paymentAuthStatus, bean, authType, user);
					}
					if (authType.equals("4")) {
						autoRepayFlag = autoRepayAuthDefaultConfig(repayAuthStatus, bean, authType, user);
					}
				}
				if(autoBidFlag || autoPaymentFlag || autoRepayFlag){
					return true;
				}
			} else if (BankCallConstant.TXCODE_TERMS_AUTH_PAGE.equals(txcode)) {
				autoBidFlag = autoBidAuthDefaultConfig(autoBidAuthStatus, bean, authTypeString, user);
				autoPaymentFlag = autoPaymentAuthDefaultConfig(paymentAuthStatus, bean, authTypeString, user);
				autoRepayFlag = autoRepayAuthDefaultConfig(repayAuthStatus, bean, authTypeString, user);
				if(autoBidFlag || autoPaymentFlag || autoRepayFlag){
					return true;
				}
			}
		}catch (Exception e){
			logger.error("AEMS多合一授权校验是否默认配置异常，错误信息:["+ e +"]");
			return true;
		}
		return false;
	}

	/**
	 * 更新授权日志信息
	 * @param logOrderId
	 * @param message
	 */
	@Override
	public void updateUserAuthLog(String logOrderId, String message) {
		HjhUserAuthLogVO hjhUserAuthLog = new HjhUserAuthLogVO();
		hjhUserAuthLog.setOrderId(logOrderId);
		hjhUserAuthLog.setRemark(message);
		amUserClient.updateUserAuthLog(hjhUserAuthLog);
	}

	/**
	 * 更新用户授权状态
	 * @param userId
	 * @param retBean
	 * @param authType
	 */
	@Override
	public void updateUserAuth(Integer userId, BankCallBean retBean, String authType) {
		// 获取用户授权信息通过用户Id
		Date nowTime = GetDate.getNowTime();
		String orderId = retBean.getOrderId();
		UserAuthRequest request = new UserAuthRequest();
		HjhUserAuthVO hjhUserAuth = this.getHjhUserAuthByUserId(userId);
		if (StringUtils.isNotBlank(orderId)) {
			HjhUserAuthLogVO hjhUserAuthLog = amUserClient.selectByExample(orderId);
			// 更新用户签约授权日志表
			if (hjhUserAuthLog != null) {
				hjhUserAuthLog.setOrderStatus(1);
				hjhUserAuthLog.setRemark("授权成功");
				hjhUserAuthLog.setUpdateTime(nowTime);
				hjhUserAuthLog.setUpdateUserId(userId);
				hjhUserAuthLog.setAuthCreateTime(GetDate.getNowTime10());
				request.setHjhUserAuthLogVO(hjhUserAuthLog);
			}
		}

		if (retBean!=null && BankCallConstant.RESPCODE_SUCCESS.equals(retBean.get(BankCallConstant.PARAM_RETCODE))) {
			// 更新user表状态为授权成功
			UserVO user = this.getUsersById(userId);
			if(retBean.getPaymentAuth()!=null && !"".equals(retBean.getPaymentAuth())){
				user.setPaymentAuthStatus(Integer.parseInt(retBean.getPaymentAuth()));
				request.setUser(user);
			}

			//更新用户签约授权状态信息表
			if(hjhUserAuth==null || hjhUserAuth.getId()==null){
				hjhUserAuth = new HjhUserAuthVO();
				hjhUserAuth.setDelFlag(0);
				hjhUserAuth.setCreateTime(nowTime);
				hjhUserAuth.setUpdateTime(nowTime);
				hjhUserAuth.setUpdateUserId(userId);
				hjhUserAuth.setUserId(user.getUserId());
				hjhUserAuth.setUserName(user.getUsername());
				hjhUserAuth.setCreateUserId(user.getUserId());
				// 设置状态
				setAuthType(hjhUserAuth, retBean, authType);
				request.setHjhUserAuth(hjhUserAuth);
			}else{
				HjhUserAuthVO updateHjhUserAuth = new HjhUserAuthVO();
				updateHjhUserAuth.setUpdateTime(nowTime);
				updateHjhUserAuth.setUpdateUserId(userId);
				updateHjhUserAuth.setId(hjhUserAuth.getId());
				updateHjhUserAuth.setUserId(user.getUserId());
				// 设置状态
				setAuthType(updateHjhUserAuth, retBean, authType);
				request.setHjhUserAuth(updateHjhUserAuth);
			}
		}
		amUserClient.updateUserAuth(request);
	}

	/**
	 * 查询授权错误信息
	 * @param orderId
	 * @return
	 */
	@Override
	public String seachUserAuthErrorMessgae(String orderId) {
		try {
			logger.info("延迟1000毫秒以后查询");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.info("查询授权错误信息异常,异常报文如下:{}", e);
		}
		HjhUserAuthLogVO hjhUserAuthLogVO = amUserClient.selectByExample(orderId);
		String remark = hjhUserAuthLogVO.getRemark();
		if(StringUtils.isBlank(remark)){
			return "系统异常，请稍后再试!";
		}
		logger.info("用户授权失败原因:[" + remark + "].");
		return remark;
	}

	/**
	 * 获取用户授权信息通过用户Id
	 * @param userId
	 * @return
	 */
	public HjhUserAuthVO getHjhUserAuthByUserId(Integer userId) {
		HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(userId);
		return hjhUserAuth;
	}

	/**
	 * 添加用户授权日志
	 * @param userId
	 * @param orderId
	 * @param client
	 * @param authType
	 */
	public void insertUserAuthLog(int userId, String orderId, Integer client, String authType) {
		Date nowTime = GetDate.getNowTime();
		UserVO user = this.getUsersById(userId);

		HjhUserAuthLogVO hjhUserAuthLog = new HjhUserAuthLogVO();
		hjhUserAuthLog.setOrderStatus(2);
		hjhUserAuthLog.setOrderId(orderId);
		hjhUserAuthLog.setUserId(user.getUserId());
		hjhUserAuthLog.setUserName(user.getUsername());
		if(authType!=null && authType.equals(BankCallConstant.QUERY_TYPE_2)){
			hjhUserAuthLog.setAuthType(4);
		}else{
			hjhUserAuthLog.setAuthType(Integer.valueOf(authType));
		}
		hjhUserAuthLog.setDelFlag(0);
		hjhUserAuthLog.setOperateEsb(client);
		hjhUserAuthLog.setCreateTime(nowTime);
		hjhUserAuthLog.setUpdateTime(nowTime);
		hjhUserAuthLog.setUpdateUserId(userId);
		hjhUserAuthLog.setCreateUserId(user.getUserId());
		amUserClient.insertUserAuthLog(hjhUserAuthLog);
	}

	/**
	 * 设置授权类型
	 * @param hjhUserAuth
	 * @param bean
	 * @param authTypes
	 */
	private void setAuthType(HjhUserAuthVO hjhUserAuth, BankCallBean bean, String authTypes) {
		// 授权类型
		String txcode = bean.getTxCode();
		//自动投标功能开通标志
		String autoBidStatus = bean.getAutoBid();
		//自动债转功能开通标志
		String autoTransfer = bean.getAutoTransfer();
		//缴费授权
		String paymentAuth = bean.getPaymentAuth();
		//还款授权
		String repayAuth = bean.getRepayAuth();
		//自动债转配置信息
		com.hyjf.common.util.HjhUserAuthConfigVO config = CommonUtils.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH);

		if (BankCallConstant.TXCODE_TERMS_AUTH_QUERY.equals(txcode)) {
			// 1：自动投标授权 2：自动债转授权 3：缴费授权 4：还款授权
			String[] authTypeArray = authTypes.split(",");
			for (String authType : authTypeArray) {
				if (authType.equals("1")) {
					setAutoBidAuthType(autoBidStatus, hjhUserAuth, bean);
				}
				if (authType.equals("2")) {
					setAutoCreditAuthType(autoTransfer, hjhUserAuth, bean, config);
				}
				if (authType.equals("3")) {
					setAutoPaymentAuthType(paymentAuth, hjhUserAuth, bean);
				}
				if (authType.equals("4")) {
					setAutoRepayAuthType(repayAuth, hjhUserAuth, bean);
				}
			}
		}else if(BankCallConstant.TXCODE_TERMS_AUTH_PAGE.equals(txcode)) {
			setAutoBidAuthType(autoBidStatus, hjhUserAuth, bean);
			setAutoCreditAuthType(autoTransfer, hjhUserAuth, bean, config);
			setAutoPaymentAuthType(paymentAuth, hjhUserAuth, bean);
			setAutoRepayAuthType(repayAuth, hjhUserAuth, bean);
		}
	}

	/**
	 * 代码复用-自动投标授权(判断是否授权)
	 * @param hjhUserAuth
	 * @param nowTime
	 * @return
	 */
	public boolean checkAutoBidAuthStatus(HjhUserAuthVO hjhUserAuth, String nowTime){
		int status = hjhUserAuth.getAutoInvesStatus();
		String endTime = hjhUserAuth.getAutoBidEndTime() == null ? "0" : hjhUserAuth.getAutoBidEndTime();
		if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
			return false;
		}
		return true;
	}

	/**
	 * 代码复用-自动债转授权(判断是否授权)
	 * @param hjhUserAuth
	 * @param nowTime
	 * @return
	 */
	public boolean checkAutoCreditAuthStatus(HjhUserAuthVO hjhUserAuth, String nowTime){
		String endTime = nowTime + 1;
		int status = hjhUserAuth.getAutoCreditStatus();
		if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
			return false;
		}
		return true;
	}

	/**
	 * 代码复用-缴费授权(判断是否授权)
	 * @param hjhUserAuth
	 * @param nowTime
	 * @return
	 */
	public boolean checkAutoPaymentAuthStatus(HjhUserAuthVO hjhUserAuth, String nowTime){
		int status = hjhUserAuth.getAutoPaymentStatus();
		String endTime = hjhUserAuth.getAutoPaymentEndTime() == null ? "0" : hjhUserAuth.getAutoPaymentEndTime();
		if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
			return false;
		}
		return true;
	}

	/**
	 * 代码复用-还款授权(判断是否授权)
	 * @param hjhUserAuth
	 * @param nowTime
	 * @return
	 */
	public boolean checkAutoRepayAuthStatus(HjhUserAuthVO hjhUserAuth, String nowTime){
		int status = hjhUserAuth.getAutoRepayStatus();
		String endTime = hjhUserAuth.getAutoRepayEndTime() == null ? "0" : hjhUserAuth.getAutoRepayEndTime();
		if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
			return false;
		}
		return true;
	}

	/**
	 * 代码复用-自动投标(设置最大可投金额和授权到期日)
	 * @param bean
	 * @param aemsMergeAuthBean
	 */
	private void autoBidAuthMax(BankCallBean bean, AuthBean aemsMergeAuthBean){
		if (!this.checkIsAuth(aemsMergeAuthBean.getUserId(), aemsMergeAuthBean.getAuthType())) {
			bean.setAutoBid(AuthBean.AUTH_START_OPEN);
			com.hyjf.common.util.HjhUserAuthConfigVO config = CommonUtils.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH);
			if (aemsMergeAuthBean.getUserType() != 1) {
				bean.setAutoBidMaxAmt(String.valueOf(config.getPersonalMaxAmount()));
			} else {
				bean.setAutoBidMaxAmt(String.valueOf(config.getEnterpriseMaxAmount()));
			}
			bean.setAutoBidDeadline(GetDate.date2Str(GetDate.countDate(1, config.getAuthPeriod()), new SimpleDateFormat("yyyyMMdd")));
		}
	}

	/**
	 * 代码复用-自动债转授权(设置最大可投金额和授权到期日)
	 * @param bean
	 * @param aemsMergeAuthBean
	 */
	private void autoCreditAuthMax(BankCallBean bean, AuthBean aemsMergeAuthBean){
		if (!this.checkIsAuth(aemsMergeAuthBean.getUserId(), aemsMergeAuthBean.getAuthType())) {
			bean.setAutoCredit(AuthBean.AUTH_START_OPEN);
			com.hyjf.common.util.HjhUserAuthConfigVO config = CommonUtils.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH);
			if (aemsMergeAuthBean.getUserType() != 1) {
				bean.setAutoCreditMaxAmt(String.valueOf(config.getPersonalMaxAmount()));
			} else {
				bean.setAutoCreditMaxAmt(String.valueOf(config.getEnterpriseMaxAmount()));
			}
			bean.setAutoCreditDeadline(GetDate.date2Str(GetDate.countDate(1, config.getAuthPeriod()), new SimpleDateFormat("yyyyMMdd")));
		}
	}

	/**
	 * 代码复用-缴费授权(设置最大可投金额和授权到期日)
	 * @param bean
	 * @param aemsMergeAuthBean
	 */
	private void autoPaymentAuthMax(BankCallBean bean, AuthBean aemsMergeAuthBean){
		if (!this.checkIsAuth(aemsMergeAuthBean.getUserId(), aemsMergeAuthBean.getAuthType())) {
			bean.setPaymentAuth(AuthBean.AUTH_START_OPEN);
			com.hyjf.common.util.HjhUserAuthConfigVO config = CommonUtils.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
			if (aemsMergeAuthBean.getUserType() != 1) {
				bean.setPaymentMaxAmt(String.valueOf(config.getPersonalMaxAmount()));
			} else {
				bean.setPaymentMaxAmt(String.valueOf(config.getEnterpriseMaxAmount()));
			}
			bean.setPaymentDeadline(GetDate.date2Str(GetDate.countDate(1, config.getAuthPeriod()), new SimpleDateFormat("yyyyMMdd")));
		}
	}

	/**
	 * 代码复用-还款授权(设置最大可投金额和授权到期日)
	 * @param bean
	 * @param aemsMergeAuthBean
	 */
	private void autoRepayAuthMax(BankCallBean bean, AuthBean aemsMergeAuthBean){
		if (!this.checkIsAuth(aemsMergeAuthBean.getUserId(), aemsMergeAuthBean.getAuthType())) {
			bean.setRepayAuth(AuthBean.AUTH_START_OPEN);
			com.hyjf.common.util.HjhUserAuthConfigVO config = CommonUtils.getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH);
			if (aemsMergeAuthBean.getUserType() != 1) {
				bean.setRepayMaxAmt(String.valueOf(config.getPersonalMaxAmount()));
			} else {
				bean.setRepayMaxAmt(String.valueOf(config.getEnterpriseMaxAmount()));
			}
			bean.setRepayDeadline(GetDate.date2Str(GetDate.countDate(1, config.getAuthPeriod()), new SimpleDateFormat("yyyyMMdd")));
		}
	}

	/**
	 * 代码复用-自动投标(校验是否默认数据)
	 */
	private boolean autoBidAuthDefaultConfig(String autoBidStatus, BankCallBean bean, String authType, UserVO user){
		if (StringUtils.isNotBlank(autoBidStatus)
			&& "1".equals(autoBidStatus)
			&& !this.checkIsAuth(Integer.parseInt(bean.getLogUserId()), authType)
			) {
				com.hyjf.common.util.HjhUserAuthConfigVO config = CommonUtils.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH);
				if (!GetDate.date2Str(GetDate.countDate(1, config.getAuthPeriod()), new SimpleDateFormat("yyyyMMdd")).equals(bean.getAutoBidDeadline())) {
					return true;
				}
				if (user.getUserType() != 1) {
					if (!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))) {
						return true;
					}
				} else {
					if (!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))) {
						return true;
					}
				}
			}
		return false;
	}

	/**
	 * 代码复用-缴费授权(校验是否默认数据)
	 */
	private boolean autoPaymentAuthDefaultConfig(String paymentAuth, BankCallBean bean, String authType, UserVO user){
		if (StringUtils.isNotBlank(paymentAuth)
			&& "1".equals(paymentAuth)
			&& !this.checkIsAuth(Integer.parseInt(bean.getLogUserId()), authType)
			) {
				com.hyjf.common.util.HjhUserAuthConfigVO config = CommonUtils.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
				if (!GetDate.date2Str(GetDate.countDate(1, config.getAuthPeriod()), new SimpleDateFormat("yyyyMMdd")).equals(bean.getPaymentDeadline())) {
					return true;
				}
				if (user.getUserType() != 1) {
					if (!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))) {
						return true;
					}
				} else {
					if (!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))) {
						return true;
					}
				}
			}
		return false;
	}

	/**
	 * 代码复用-还款授权(校验是否默认数据)
	 */
	private boolean autoRepayAuthDefaultConfig(String repayAuth, BankCallBean bean, String authType, UserVO user){
		if (StringUtils.isNotBlank(repayAuth)
			&& "1".equals(repayAuth)
			&& !this.checkIsAuth(Integer.parseInt(bean.getLogUserId()), authType)
			) {
				com.hyjf.common.util.HjhUserAuthConfigVO config = CommonUtils.getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH);
				if (!GetDate.date2Str(GetDate.countDate(1, config.getAuthPeriod()), new SimpleDateFormat("yyyyMMdd")).equals(bean.getRepayDeadline())) {
					return true;
				}
				if (user.getUserType() != 1) {
					if (!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))) {
						return true;
					}
				} else {
					if (!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))) {
						return true;
					}
				}
			}
		return false;
	}

	/**
	 * 代码复用-自动投标授权(保存授权表前赋值)
	 * @return
	 */
	public void setAutoBidAuthType(String autoBidStatus, HjhUserAuthVO hjhUserAuth, BankCallBean bean){
		if (StringUtils.isNotBlank(autoBidStatus) && "1".equals(autoBidStatus)) {
			hjhUserAuth.setAutoOrderId(bean.getOrderId());
			hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
			hjhUserAuth.setInvesMaxAmt(bean.getAutoBidMaxAmt());
			hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
			hjhUserAuth.setAutoBidEndTime(bean.getAutoBidDeadline());
			hjhUserAuth.setAutoInvesStatus(Integer.parseInt(autoBidStatus));
			// add by liuyang 神策数据统计修改 20180927 start
                        /*if ("10000000".equals(users.getInstCode())) {
                            try {
                                SensorsDataBean sensorsDataBean = new SensorsDataBean();
                                sensorsDataBean.setUserId(userId);
                                // 汇计划授权结果
                                sensorsDataBean.setEventCode("plan_auth_result");
                                sensorsDataBean.setOrderId(bean.getOrderId());
                                // 授权类型
                                sensorsDataBean.setAuthType(AemsMergeAuthBean.AUTH_TYPE_AUTO_BID);
                                this.sendSensorsDataMQ(sensorsDataBean);
                            } catch (Exception e) {
                                logger.error(e.getMessage());
                            }
                        }*/
			// add by liuyang 神策数据统计修改 20180927 end

		}
	}

	/**
	 * 代码复用-自动债转授权(保存授权表前赋值)
	 * @return
	 */
	public void setAutoCreditAuthType(String autoTransfer, HjhUserAuthVO hjhUserAuth, BankCallBean bean, com.hyjf.common.util.HjhUserAuthConfigVO config){
		if (StringUtils.isNotBlank(autoTransfer) && "1".equals(autoTransfer)) {
			hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
			hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
			hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
			hjhUserAuth.setAutoCreditStatus(Integer.parseInt(autoTransfer));
			hjhUserAuth.setCreditMaxAmt(config.getPersonalMaxAmount() == null ? null : String.valueOf(config.getPersonalMaxAmount()));
			hjhUserAuth.setAutoCreditEndTime(GetDate.date2Str(GetDate.countDate(1, config.getAuthPeriod()), new SimpleDateFormat("yyyyMMdd")));
			// add by liuyang 神策数据统计修改 20180927 start
                        /*if ("10000000".equals(users.getInstCode())) {
                            try {
                                SensorsDataBean sensorsDataBean = new SensorsDataBean();
                                sensorsDataBean.setUserId(userId);
                                // 汇计划授权结果
                                sensorsDataBean.setEventCode("plan_auth_result");
                                sensorsDataBean.setOrderId(bean.getOrderId());
                                // 授权类型
                                sensorsDataBean.setAuthType(AemsMergeAuthBean.AUTH_TYPE_AUTO_CREDIT);
                                this.sendSensorsDataMQ(sensorsDataBean);
                            } catch (Exception e) {
                                logger.error(e.getMessage());
                            }
                        }*/
			// add by liuyang 神策数据统计修改 20180927 end

		}
	}

	/**
	 * 代码复用-缴费授权(保存授权表前赋值)
	 * @return
	 */
	public void setAutoPaymentAuthType(String paymentAuth, HjhUserAuthVO hjhUserAuth, BankCallBean bean){
		if (StringUtils.isNotBlank(paymentAuth) && "1".equals(paymentAuth)) {
			hjhUserAuth.setPaymentMaxAmt(bean.getPaymentMaxAmt());
			hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
			hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
			hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
			// add by liuyang 神策数据统计修改 20180927 start
                        /*if ("10000000".equals(users.getInstCode())) {
                            try {
                                SensorsDataBean sensorsDataBean = new SensorsDataBean();
                                sensorsDataBean.setUserId(userId);
                                // 事件类型:服务费授权结果
                                sensorsDataBean.setEventCode("fee_auth_result");
                                sensorsDataBean.setOrderId(bean.getOrderId());
                                // 授权类型
                                sensorsDataBean.setAuthType(AemsMergeAuthBean.AUTH_TYPE_PAYMENT_AUTH);
                                this.sendSensorsDataMQ(sensorsDataBean);
                            } catch (Exception e) {
                                logger.error(e.getMessage());
                            }
                        }*/
			// add by liuyang 神策数据统计修改 20180927 end
		}
	}

	/**
	 * 代码复用-还款授权(保存授权表前赋值)
	 * @return
	 */
	public void setAutoRepayAuthType(String repayAuth, HjhUserAuthVO hjhUserAuth, BankCallBean bean){
		if (StringUtils.isNotBlank(repayAuth) && "1".equals(repayAuth)) {
			hjhUserAuth.setRepayMaxAmt(bean.getRepayMaxAmt());
			hjhUserAuth.setAutoRepayTime(GetDate.getNowTime10());
			hjhUserAuth.setAutoRepayEndTime(bean.getRepayDeadline());
			hjhUserAuth.setAutoRepayStatus(Integer.parseInt(repayAuth));
		}
	}
}
