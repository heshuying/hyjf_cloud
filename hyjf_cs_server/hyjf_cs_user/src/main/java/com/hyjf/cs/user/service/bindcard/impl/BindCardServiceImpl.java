package com.hyjf.cs.user.service.bindcard.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankCardUpdateRequest;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.StringUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BindCardPageBean;
import com.hyjf.cs.user.bean.BindCardPageRequestBean;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.vo.BindCardVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 绑卡服务类
 * @author hesy
 */
@Service
public class BindCardServiceImpl extends BaseUserServiceImpl implements BindCardService{
	private static final Logger logger = LoggerFactory.getLogger(BindCardServiceImpl.class);
	@Autowired
	SystemConfig systemConfig;

	@Autowired
	AmUserClient amUserClient;

	@Autowired
	AmTradeClient amTradeClient;

	@Autowired
	AmConfigClient amConfigClient;
	
	/**
	 * 发送验证码请求参数校验
	 * @param
	 * @param userId
	 */
	@Override
	public void checkParamSendcode(Integer userId, String mobile, String cardNo) {
        // 手机号校验
        if(StringUtils.isBlank(mobile)) {
			throw new CheckException(MsgEnum.ERR_MOBILE_BLANK);
        }
        // 银行卡号校验
        if(StringUtils.isEmpty(cardNo)) {
			throw new CheckException(MsgEnum.ERR_CARD_BLANK);
        }
        // 开户校验
        if (!this.checkIsOpen(userId)) {
			throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
	}
	


	/**
	 * 请求参数校验
	 * @param bindCardVO
	 * @param userId
	 */
	@Override
	public void checkParamBindCard(BindCardVO bindCardVO, Integer userId) {
		// 短信授权码校验
        if (StringUtils.isBlank(bindCardVO.getLastSrvAuthCode())) {
        	throw new CheckException(MsgEnum.ERR_AUTHORIZE_CODE_BLANK);
        }
        // 手机号校验
        if(StringUtils.isBlank(bindCardVO.getMobile())) {
        	throw new CheckException(MsgEnum.ERR_MOBILE_BLANK);
        }
        // 银行卡号校验
        if(StringUtils.isEmpty(bindCardVO.getCardNo())) {
        	throw new CheckException(MsgEnum.ERR_CARD_BLANK);
        }
        // 短信验证码校验
        if(StringUtils.isBlank(bindCardVO.getSmsCode())) {
        	throw new CheckException(MsgEnum.ERR_SMSCODE_BLANK);
        }

        // 开户校验
        if (!this.checkIsOpen(userId)) {
        	throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
	}

    /**
     * 绑卡校验
     * @param user
     */
	@Override
	public void checkParamBindCardPage(WebViewUserVO user) {
		// 登录校验
		CheckUtil.check(user != null, MsgEnum.ERR_USER_NOT_LOGIN);
		// 开户校验
		CheckUtil.check(this.checkIsOpen(user.getUserId()), MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
		// 交易密码设置校验
//		CheckUtil.check(user.getIsSetPassword() == 1, MsgEnum.STATUS_TP000002);
		// 已绑卡校验
		int count = amUserClient.countUserCardValid(String.valueOf(user.getUserId()));
		CheckUtil.check(count<=0, MsgEnum.STATUS_BC000001);

	}

	/**
	 * 绑卡校验APP
	 * @param user
	 */
	@Override
	public String checkParamBindCardPageAPP(WebViewUserVO user) {
		if(user == null){
			return "用户未登录";
		}
		if(!this.checkIsOpen(user.getUserId())){
			return "用户未开户";
		}
		if(user.getIsSetPassword() != 1){
			return "未设置过交易密码，请先设置交易密码";
		}
		int count = amUserClient.countUserCardValid(String.valueOf(user.getUserId()));
		if(count > 0){
			return "用户已绑定银行卡,请先解除绑定,然后重新操作！";
		}

		return "";
	}

	/**
	 * 绑卡校验wechat
	 * @param user
	 * @return
	 */
	@Override
	public ResultEnum checkParamBindCardPageWeChat(WebViewUserVO user) {
		if(user == null){
			return ResultEnum.NOTLOGIN;
		}
		if(!this.checkIsOpen(user.getUserId())){
			return ResultEnum.USER_ERROR_200;
		}
		if(user.getIsSetPassword() != 1){
			return ResultEnum.USER_ERROR_200;
		}
		int count = amUserClient.countUserCardValid(String.valueOf(user.getUserId()));
		if(count > 0){
			return ResultEnum.USER_ERROR_217;
		}

		return null;
	}

	/**
	 * 绑卡校验API端
	 * @param bankCardRequestBean
	 * @return
	 */
	@Override
	public Map<String,String> checkParamBindCardPageApi(BindCardPageRequestBean bankCardRequestBean) {
		Map<String,String> resultMap = new HashMap<>();
		BankOpenAccountVO openAccountVO = amUserClient.selectBankOpenAccountByAccountId(bankCardRequestBean.getAccountId());
		if(openAccountVO == null){
			resultMap.put("key","CE000004");
			resultMap.put("msg","没有根据电子银行卡找到用户");
			logger.info("没有根据电子银行卡找到用户");
			return resultMap;
		}
		UserVO userVO = this.getUsersById(openAccountVO.getUserId());
		if(userVO.getIsSetPassword() != 1){
			resultMap.put("key","TP000002");
			resultMap.put("msg","用户未设置交易密码");
			logger.info("用户未设置交易密码");
			return resultMap;
		}
		int count = amUserClient.countUserCardValid(String.valueOf(userVO.getUserId()));
		if(count > 0){
			resultMap.put("key","BC000001");
			resultMap.put("msg","用户已绑定银行卡,请先解除绑定,然后重新操作");
			logger.info("用户已绑定银行卡,请先解除绑定,然后重新操作");
			return resultMap;
		}

		return resultMap;
	}

	/**
	 * 绑卡接口请求
	 * @auther: hesy
	 * @date: 2018/6/22
	 */
	@Override
	public Map<String,Object> callBankBindCardPage(WebViewUserVO user, String userIp, String urlstatus) throws Exception {
        // 页面调用必须传的
        String orderId = GetOrderIdUtils.getOrderId2(user.getUserId());

        // 回调路径
        String retUrl = super.getFrontHost(systemConfig,String.valueOf(ClientConstants.WEB_CLIENT)).trim() + "/user/bindCardError?logOrdId=" + orderId + "&bind=true&unbind=false&msg=";
        // 交易成功跳转链接
        String successfulUrl = super.getFrontHost(systemConfig,String.valueOf(ClientConstants.WEB_CLIENT)).trim() + "/user/bindCardSuccess?bind=true&unbind=false&msg=";
		// 商户后台应答地址(必须)
		String notifyUrl = "http://CS-USER/hyjf-web/user/card/bgReturn?userId=" + user.getUserId()+"&urlstatus="+urlstatus+"&phone="+user.getMobile();
        // 忘记密码跳转链接
        String forgotPwdUrl = systemConfig.getFrontHost()+systemConfig.getForgetpassword();

		// 调用开户接口
		BankCallBean bindCardBean = new BankCallBean();
		bindCardBean.setTxCode(BankCallConstant.TXCODE_BIND_CARD_PAGE);
		bindCardBean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
		bindCardBean.setIdNo(user.getIdcard());
		bindCardBean.setName(user.getTruename());
		bindCardBean.setAccountId(user.getBankAccount());
		bindCardBean.setUserIP(userIp);
		bindCardBean.setRetUrl(retUrl);
		bindCardBean.setSuccessfulUrl(successfulUrl);
		bindCardBean.setForgotPwdUrl(forgotPwdUrl);
		bindCardBean.setNotifyUrl(notifyUrl);

		bindCardBean.setLogBankDetailUrl(BankCallConstant.BANK_URL_BIND_CARD_PAGE);
		bindCardBean.setLogOrderId(orderId);
		bindCardBean.setLogUserId(String.valueOf(user.getUserId()));
		bindCardBean.setLogRemark("外部服务接口:绑卡页面");
		bindCardBean.setLogIp(userIp);
		bindCardBean.setLogClient(ClientConstants.WEB_CLIENT);

		Map<String,Object> map = BankCallUtils.callApiMap(bindCardBean);

		if(map == null){
			return new HashMap<>();
		}
		return map;
	}
	
	/**
	 * 请求银行绑卡接口
	 */
	@Override
	public BankCallBean callBankBindCard(BindCardVO bindCardVO, Integer userId, String userIp) {
		BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
		UserInfoVO  userInfo = amUserClient.findUserInfoById(userId);
		
		// 请求银行绑卡接口
		BankCallBean retBean = null;
        BankCallBean bean = new BankCallBean();
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserId(StringUtil.valueOf(userId));
        bean.setLogRemark("用户绑卡增强");
        bean.setTxCode(BankCallConstant.TXCODE_CARD_BIND_PLUS);
        bean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
        bean.setAccountId(bankAccount.getAccount());// 存管平台分配的账号
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);// 证件类型01身份证
        bean.setIdNo(userInfo.getIdcard());// 证件号
        bean.setName(userInfo.getTruename());// 姓名
        bean.setMobile(bindCardVO.getMobile());// 手机号
        bean.setCardNo(bindCardVO.getCardNo());// 银行卡号
        bean.setLastSrvAuthCode(bindCardVO.getLastSrvAuthCode());
        bean.setSmsCode(bindCardVO.getSmsCode());
        bean.setUserIP(userIp);// 客户IP
        LogAcqResBean logAcq = new LogAcqResBean();
        logAcq.setCardNo(bindCardVO.getCardNo());
        logAcq.setMobile(bindCardVO.getMobile());
        bean.setLogAcqResBean(logAcq);
        
        try {
            retBean  = BankCallUtils.callApiBg(bean);
        } catch (Exception e) {
            logger.error("绑卡请求银行接口失败", e);
            return null;
        }
        
        return retBean;
	}

	@Override
	public Map<String,Object> getCallbankMap(BindCardPageBean bean, String sign, String token, String platform) {
		UserVO userVO = this.getUsersById(bean.getUserId());

		// 获取共同参数
		String orderDate = GetOrderIdUtils.getOrderDate();
		String idType = BankCallConstant.ID_TYPE_IDCARD;
		String orderId = GetOrderIdUtils.getOrderId2(bean.getUserId());

		// 调用开户接口
		BankCallBean bindCardBean = new BankCallBean();
		bindCardBean.setTxCode(bean.getTxCode());// 消息类型(用户开户)
		bindCardBean.setIdType(idType);
		bindCardBean.setIdNo(bean.getIdNo());
		bindCardBean.setName(bean.getName());
		bindCardBean.setAccountId(bean.getAccountId());
		bindCardBean.setUserIP(bean.getUserIP());

		// 同步调用路径
		String retUrl = systemConfig.getAppFrontHost() + "/user/bankCard/bind/result/failed?logOrdId=" + orderId + "&sign=" + sign + "&token=1" + "&platform=" + platform;
		String successUrl = systemConfig.getAppFrontHost() + "/user/bankCard/bind/result/success?sign=" + sign + "&token=1" + "&platform=" + platform;
		// 异步调用路
		String bgRetUrl = "http://CS-USER/hyjf-app/bank/user/bindCardPage/notifyReturn?phone=" + userVO.getMobile();
		// 拼装参数 调用江西银行
		String forgetPassworedUrl = systemConfig.getAppFrontHost()+systemConfig.getAppForgetpassword();

		bindCardBean.setRetUrl(retUrl);
		bindCardBean.setSuccessfulUrl(successUrl);
		bindCardBean.setForgotPwdUrl(forgetPassworedUrl);
		bindCardBean.setNotifyUrl(bgRetUrl);
		// 页面调用必须传的
		bindCardBean.setLogBankDetailUrl(BankCallConstant.BANK_URL_BIND_CARD_PAGE);
		bindCardBean.setLogOrderId(orderId);
		bindCardBean.setLogOrderDate(orderDate);
		bindCardBean.setLogUserId(String.valueOf(bean.getUserId()));
		bindCardBean.setLogRemark("外部服务接口:绑卡页面");
		bindCardBean.setLogIp(bean.getUserIP());
		bindCardBean.setLogClient(Integer.parseInt(bean.getPlatform()));
		try {
			Map<String,Object> map = BankCallUtils.callApiMap(bindCardBean);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ModelAndView getCallbankMV(BindCardPageBean bean) {
		ModelAndView mv = new ModelAndView();
		// 获取共同参数
		String orderDate = GetOrderIdUtils.getOrderDate();
		String idType = BankCallConstant.ID_TYPE_IDCARD;

		// 调用开户接口
		BankCallBean bindCardBean = new BankCallBean();
		bindCardBean.setTxCode(bean.getTxCode());// 消息类型(用户开户)
		bindCardBean.setIdType(idType);
		bindCardBean.setIdNo(bean.getIdNo());
		bindCardBean.setName(bean.getName());
		bindCardBean.setAccountId(bean.getAccountId());
		bindCardBean.setUserIP(bean.getUserIP());
		bindCardBean.setRetUrl(bean.getRetUrl());
		bindCardBean.setSuccessfulUrl(bean.getSuccessfulUrl());
		bindCardBean.setForgotPwdUrl(bean.getForgetPassworedUrl());
		bindCardBean.setNotifyUrl(bean.getNotifyUrl());
		// 页面调用必须传的
		String orderId = GetOrderIdUtils.getOrderId2(bean.getUserId());
		bindCardBean.setLogBankDetailUrl(BankCallConstant.BANK_URL_BIND_CARD_PAGE);
		bindCardBean.setLogOrderId(orderId);
		bindCardBean.setLogOrderDate(orderDate);
		bindCardBean.setLogUserId(String.valueOf(bean.getUserId()));
		bindCardBean.setLogRemark("外部服务接口:绑卡页面");
		bindCardBean.setLogIp(bean.getUserIP());
		bindCardBean.setLogClient(Integer.parseInt(bean.getPlatform()));
		try {
			mv = BankCallUtils.callApi(bindCardBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 绑卡接口请求成功后业务处理
	 * @param bean
	 * @throws ParseException
	 */
	@Override
	public void updateAfterBindCard(BankCallBean bean) throws ParseException {
		Integer userId = Integer.parseInt(bean.getLogUserId());
		// 查询用户信息
		UserVO userVO = amUserClient.findUserById(userId);
		// 获取用户已绑卡数量
		int count = amUserClient.countUserCardValid(bean.getLogUserId());
		// 如果已经有绑卡则不做操作
		if(count > 0) {
			return;
		}

		String cardNo = "";
		
		// 同步银行卡信息
		BankCallBean callBean = new BankCallBean();
		callBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		callBean.setTxCode(BankCallConstant.TXCODE_CARD_BIND_DETAILS_QUERY);
		callBean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
		callBean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
		callBean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
		callBean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
		callBean.setAccountId(bean.getAccountId());// 存管平台分配的账号
		callBean.setState("1"); // 查询状态 0-所有（默认） 1-当前有效的绑定卡
		callBean.setLogOrderId(GetOrderIdUtils.getOrderId2(Integer.parseInt(bean.getLogUserId())));
		callBean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
		callBean.setLogRemark("绑卡关系查询");
		callBean.setLogUserId(String.valueOf(bean.getLogUserId()));
		// 调用汇付接口 4.4.11 银行卡查询接口
		BankCallBean bankCallBean = BankCallUtils.callApiBg(callBean);
		logger.info("请求银行接口cardBindDetailsQuery返回：" + JSON.toJSONString(bankCallBean));
		String respCode = bankCallBean == null ? "" : bankCallBean.getRetCode();
		// 如果接口调用成功
		if (BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
			String usrCardInfolist = bankCallBean.getSubPacks();
			JSONArray array = JSONObject.parseArray(usrCardInfolist);
			if (array != null && array.size() != 0) {
				List<BankCardRequest> bankCardList = new ArrayList<BankCardRequest>();
				for (int j = 0; j < array.size(); j++) {
					JSONObject obj = array.getJSONObject(j);
					cardNo = obj.getString("cardNo");
					// 根据银行卡号查询银行ID
					String bankId = amConfigClient.queryBankIdByCardNo(cardNo);
					if(bankId == null) {
						bankId = "0";
					}
					logger.info("根据银行卡号查询出的bankId：" + bankId);
					// 查询银行配置信息
					JxBankConfigVO bankConfig = amConfigClient.getJxBankConfigById(Integer.parseInt(bankId));
					
					BankCardRequest bank = new BankCardRequest();
					bank.setUserId(userId);
					bank.setUserName(userVO.getUsername());
					bank.setStatus(1);// 默认都是1
					bank.setCardNo(cardNo);
					bank.setBankId(bankId == null ? 0 : Integer.valueOf(bankId));
					if(bankConfig != null){
						bank.setBank(bankConfig.getBankName());
					}
					// 银行联号
					String payAllianceCode = "";
					// 调用江西银行接口查询银行联号
					BankCallBean payAllianceCodeQueryBean = this.payAllianceCodeQuery(cardNo, userId);
					if (payAllianceCodeQueryBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(payAllianceCodeQueryBean.getRetCode())) {
						payAllianceCode = payAllianceCodeQueryBean.getPayAllianceCode();
					}
					// 如果此时银联行号还是为空,根据bankId查询本地存的银联行号
					if (StringUtils.isBlank(payAllianceCode) && bankConfig != null) {
						payAllianceCode = bankConfig.getPayAllianceCode();
					}
					bank.setPayAllianceCode(payAllianceCode);
					SimpleDateFormat sdf = GetDate.yyyymmddhhmmss;
					bank.setCreateTime(sdf.parse(obj.getString("txnDate") + obj.getString("txnTime")));
					bank.setCreateUserId(userId);
					bank.setCreateUsername(userVO.getUsername());
					bankCardList.add(bank);
				}

				for (BankCardRequest bank : bankCardList) {
					boolean isInsertFlag = amUserClient.insertUserCard(bank) > 0 ? true : false;
					if (!isInsertFlag) {
						throw new CheckException(MsgEnum.ERR_CARD_SAVE);
					}
				}

				// 插入操作记录表
				String bankId = amConfigClient.queryBankIdByCardNo(cardNo);
				if(bankId == null) {
					bankId = "0";
				}
				// 查询银行配置信息
				JxBankConfigVO bankConfig = amConfigClient.getJxBankConfigById(Integer.parseInt(bankId));

				BankCardLogRequest bankCardLogRequest = new BankCardLogRequest();
				bankCardLogRequest.setUserId(userId);
				bankCardLogRequest.setUserName(userVO.getUsername());
				bankCardLogRequest.setBankCode(String.valueOf(bankId));
				bankCardLogRequest.setCardNo(cardNo);
				if(bankConfig != null){
					bankCardLogRequest.setBankName(bankConfig.getBankName());
				}
				bankCardLogRequest.setCardType(0);// 卡类型 0普通提现卡1默认卡2快捷支付卡
				bankCardLogRequest.setOperationType(0);// 操作类型 0绑定 1删除
				bankCardLogRequest.setStatus(0);// 成功
				bankCardLogRequest.setCreateTime(GetDate.getNowTime());// 操作时间
				boolean isUpdateFlag = amUserClient.insertBindCardLog(bankCardLogRequest) > 0 ? true : false;
				if (!isUpdateFlag) {
					throw new CheckException(MsgEnum.ERR_CARD_SAVE);
				}
				BankCardVO retCard = amUserClient.queryUserCardValid(String.valueOf(userId), cardNo);
				if (retCard != null && StringUtils.isNotBlank(bean.getMobile())) {
					BankCardRequest bankCard=new BankCardRequest();
					bankCard.setId(retCard.getId());
					bankCard.setMobile(bean.getMobile());
					amUserClient.updateUserCard(bankCard);
				}
			}
		}


	}
	
	/**
	 * 银联行号查询
	 * @param cardNo
	 * @param userId
	 * @return
	 */
	private BankCallBean payAllianceCodeQuery(String cardNo,Integer userId) {
		BankCallBean bean = new BankCallBean();
		String channel = BankCallConstant.CHANNEL_PC;
		String orderDate = GetOrderIdUtils.getOrderDate();
		String txDate = GetOrderIdUtils.getTxDate();
		String txTime = GetOrderIdUtils.getTxTime();
		String seqNo = GetOrderIdUtils.getSeqNo(6);
		bean.setVersion(BankCallConstant.VERSION_10);// 版本号
		bean.setTxCode(BankCallConstant.TXCODE_PAY_ALLIANCE_CODE_QUERY);// 交易代码
		bean.setTxDate(txDate);
		bean.setTxTime(txTime);
		bean.setSeqNo(seqNo);
		bean.setChannel(channel);
		bean.setAccountId(cardNo);
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
		bean.setLogOrderDate(orderDate);
		bean.setLogUserId(String.valueOf(userId));
		bean.setLogRemark("联行号查询");
		bean.setLogClient(0);
		return BankCallUtils.callApiBg(bean);
	}
	
	/**
	 * 请求银行解绑卡接口
	 */
	@Override
	public BankCallBean callBankUnBindCard(String cardNo, Integer userId) {
		BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
		UserInfoVO  userInfo = amUserClient.findUserInfoById(userId);
		UserVO user = amUserClient.findUserById(userId);
		
		BankCallBean bean = new BankCallBean();
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
		bean.setLogUserId(String.valueOf(userId));
		bean.setLogRemark("解绑银行卡");
		bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		bean.setTxCode(BankCallMethodConstant.TXCODE_CARD_UNBIND);
		bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
		bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
		bean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
		bean.setAccountId(bankAccount.getAccount());// 存管平台分配的账号
		bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);// 证件类型01身份证
		bean.setIdNo(userInfo.getIdcard());// 证件号
		bean.setName(userInfo.getTruename());// 姓名
		bean.setMobile(user.getMobile());// 手机号
		bean.setCardNo(cardNo);// 银行卡号
		LogAcqResBean logAcqResBean = new LogAcqResBean();
		logAcqResBean.setCardNo(cardNo);// 银行卡号
		bean.setLogAcqResBean(logAcqResBean);
		
		return BankCallUtils.callApiBg(bean);
	}
	
	/**
	 * 查询用户电子账户可用余额
	 * @param userId
	 * @param accountId
	 * @return
	 */
	private BigDecimal queryBankBlance(Integer userId, String accountId) {
		BigDecimal balance = BigDecimal.ZERO;
		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);// 版本号
		bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_QUERY);// 交易代码
		bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
		bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
		bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
		bean.setAccountId(accountId);// 电子账号
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));// 订单号
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
		bean.setLogUserId(String.valueOf(userId));
		bean.setLogRemark("电子账户余额查询");
		bean.setLogClient(0);// 平台
		try {
			BankCallBean resultBean = BankCallUtils.callApiBg(bean);
			if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
				balance = new BigDecimal(resultBean.getAvailBal().replace(",", ""));
			}
		} catch (Exception e) {
			logger.error("获取账户可用余额异常", e);
			return null;
		}

		return balance;
	}
	
	/**
	 * 可解绑条件校验
	 */
	@Override
	public void checkParamUnBindCard(BindCardVO bindCardVO, Integer userId) {
        // 银行卡号校验
        if(StringUtils.isEmpty(bindCardVO.getCardNo())) {
        	throw new CheckException(MsgEnum.ERR_CARD_BLANK);
        }

        // 开户校验
        BankOpenAccountVO openAccount = amUserClient.selectById(userId);
        if (openAccount == null || StringUtils.isBlank(openAccount.getAccount())) {
        	throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        
        // 账户余额校验
        AccountVO account = amTradeClient.getAccount(userId);
        BigDecimal bankBalance = this.queryBankBlance(userId, openAccount.getAccount());
        if ((Validator.isNotNull(account.getBankBalance()) && account.getBankBalance().compareTo(BigDecimal.ZERO) > 0)
				|| ((Validator.isNotNull(bankBalance) && bankBalance.compareTo(BigDecimal.ZERO) > 0))) {
        	throw new CheckException(MsgEnum.ERR_CARD_UNBIND_HAVE_BALANCE);
		}
        
        // 待解绑卡校验
        BankCardVO bankCard = amUserClient.queryUserCardValid(String.valueOf(userId), bindCardVO.getCardNo());
        if (bankCard == null || StringUtils.isEmpty(bankCard.getCardNo())) {
        	throw new CheckException(MsgEnum.ERR_CARD_NOT_EXIST);
		}
        
	}

	/**
	 * app端解绑银行卡校验
	 * @param webViewUserVO
	 * @param cardNo
	 * @return
	 */
	@Override
	public String checkParamUnBindCardAPP(WebViewUserVO webViewUserVO, String cardNo) {
		// 银行卡号校验
		if(StringUtils.isBlank(cardNo)) {
			return "银行卡号未填写";
		}

		// 开户校验
		if (!webViewUserVO.isBankOpenAccount()) {
			return "用户未开户";
		}

		// 账户余额校验
		AccountVO account = amTradeClient.getAccount(webViewUserVO.getUserId());
		BigDecimal bankBalance = this.queryBankBlance(webViewUserVO.getUserId(), webViewUserVO.getBankAccount());
		if ((Validator.isNotNull(account.getBankBalance()) && account.getBankBalance().compareTo(BigDecimal.ZERO) > 0)
				|| ((Validator.isNotNull(bankBalance) && bankBalance.compareTo(BigDecimal.ZERO) > 0))) {
			return "抱歉，请先清空当前余额和待收后，再申请解绑。";
		}

		// 待解绑卡校验
		BankCardVO bankCard = amUserClient.queryUserCardValid(String.valueOf(webViewUserVO.getUserId()), cardNo);
		if (bankCard == null || StringUtils.isEmpty(bankCard.getCardNo())) {
			return "没有要解绑的银行卡";
		}

		return "";

	}
	
	/**
	 * 解绑接口请求后业务处理
	 * @param bean
	 */
	@Override
	public boolean updateAfterUnBindCard(BankCallBean bean) {
		LogAcqResBean logAcqResBean = bean.getLogAcqResBean();
		UserVO user = amUserClient.findUserById(Integer.parseInt(bean.getLogUserId()));
		BankCardVO bankCard = amUserClient.queryUserCardValid(String.valueOf(bean.getLogUserId()), logAcqResBean.getCardNo());
		if(bankCard != null) {
			amUserClient.deleteUserCardByUserId(bean.getLogUserId());
			
			// 插入操作记录表
			BankCardLogRequest bankCardLogRequest = new BankCardLogRequest();
			bankCardLogRequest.setUserId(Integer.parseInt(bean.getLogUserId()));
			bankCardLogRequest.setUserName(user.getUsername());
			bankCardLogRequest.setCardNo(logAcqResBean.getCardNo());
			bankCardLogRequest.setBankName(bankCard.getBank());
			bankCardLogRequest.setCardType(0);// 卡类型 0普通提现卡1默认卡2快捷支付卡
			bankCardLogRequest.setOperationType(1);// 操作类型 0绑定 1删除
			bankCardLogRequest.setStatus(0);// 成功
//			bankCardLogRequest.setCreateTime(GetDate.getNowTime());// 操作时间
			boolean isUpdateFlag = amUserClient.insertBindCardLog(bankCardLogRequest) > 0 ? true : false;
			if (!isUpdateFlag) {
				throw new CheckException(MsgEnum.ERR_CARD_DELETE);
			}
			return isUpdateFlag;
		}
		return true;
	}

	/**
	 * 用户删除银行卡后调用方法
	 */
	@Override
	public boolean updateAfterDeleteCard(Integer userId, String userName, String cardNo){
		BankCardVO bankCard = amUserClient.queryUserCardValid(String.valueOf(userId), cardNo);
		if(bankCard == null){
			return false;
		}

		BankCardUpdateRequest requestBean = new BankCardUpdateRequest();
		requestBean.setUserId(userId);
		requestBean.setCardNo(cardNo);
		requestBean.setUserName(userName);
		return amUserClient.updateAfterDeleteCard(requestBean);
	}

	/**
	 * 判断江西银行绑卡使用新/旧接口
	 * @param type
	 * @return
	 */
	@Override
	public Integer getBankInterfaceFlagByType(String type) {
		return amConfigClient.getBankInterfaceFlagByType(type);
	}

	/**
	 * 查询用户已绑定的有效卡
	 * @param userId
	 * @param cardNo
	 * @return
	 */
	@Override
	public BankCardVO queryUserCardValid(String userId, String cardNo) {
		return amUserClient.queryUserCardValid(userId, cardNo);
	}

	/**
	 * 根据银行卡id获取银行配置信息
	 */
	@Override
	public JxBankConfigVO getBankConfigById(Integer bankId) {
		return amConfigClient.getJxBankConfigById(bankId);
	}

	/**
	 * 根据电子账号查询用户在江西银行的可用余额
	 * @param userId
	 * @param accountId
	 * @return
	 */
	@Override
	public BigDecimal getBankBalance(Integer userId, String accountId) {
		return this.queryBankBlance(userId, accountId);
	}
}

	