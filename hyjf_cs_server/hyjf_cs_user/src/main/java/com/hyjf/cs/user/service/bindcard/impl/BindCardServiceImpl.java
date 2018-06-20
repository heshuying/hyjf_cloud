package com.hyjf.cs.user.service.bindcard.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.hyjf.cs.user.service.BaseUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.StringUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.BankOpenClient;
import com.hyjf.cs.user.client.BindCardClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.BindCardError;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.cs.user.vo.BindCardVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

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
	BindCardClient bindCardClient;
	@Autowired
    BankOpenClient bankOpenClient;
	
	/**
	 * 发送验证码请求参数校验
	 * @param
	 * @param userId
	 */
	@Override
	public void checkParamSendcode(Integer userId, String mobile, String cardNo) {
        // 手机号校验
        if(StringUtils.isBlank(mobile)) {
        	throw new ReturnMessageException(BindCardError.MOBILE_ERROR);
        }
        // 银行卡号校验
        if(StringUtils.isEmpty(cardNo)) {
        	throw new ReturnMessageException(BindCardError.CARD_NO_ERROR);
        }
        // 开户校验
        if (!this.checkIsOpen(userId)) {
        	throw new ReturnMessageException(BindCardError.BANK_NOT_OPEN_ERROR);
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
        	throw new ReturnMessageException(BindCardError.AUTH_CODE_ERROR);
        }
        // 手机号校验
        if(StringUtils.isBlank(bindCardVO.getMobile())) {
        	throw new ReturnMessageException(BindCardError.MOBILE_ERROR);
        }
        // 银行卡号校验
        if(StringUtils.isEmpty(bindCardVO.getCardNo())) {
        	throw new ReturnMessageException(BindCardError.CARD_NO_ERROR);
        }
        // 短信验证码校验
        if(StringUtils.isBlank(bindCardVO.getSmsCode())) {
        	throw new ReturnMessageException(BindCardError.SMSCODE_ERROR);
        }

        // 开户校验
        if (!this.checkIsOpen(userId)) {
        	throw new ReturnMessageException(BindCardError.BANK_NOT_OPEN_ERROR);
        }
	}
	
	/**
	 * 请求银行绑卡接口
	 */
	@Override
	public BankCallBean callBankBindCard(BindCardVO bindCardVO, Integer userId, String userIp) {
		BankOpenAccountVO bankAccount = bankOpenClient.selectById(userId);
		UserInfoVO  userInfo = amUserClient.findUserInfoById(userId);
		
		// 请求银行绑卡接口
		BankCallBean retBean = null;
        BankCallBean bean = new BankCallBean();
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserId(StringUtil.valueOf(userId));
        bean.setLogRemark("用户绑卡增强");
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_CARD_BIND_PLUS);
        bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
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
        bean.setLogAcqResBean(logAcq);
        
        try {
            retBean  = BankCallUtils.callApiBg(bean);
        } catch (Exception e) {
            logger.error("绑卡请求银行接口失败", e);
            return null;
        }
        
        return retBean;
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
		int count = bindCardClient.countUserCardValid(bean.getLogUserId());
		// 如果已经有绑卡则不做操作
		if(count > 0) {
			return;
		}
		
		// 同步银行卡信息
		BankCallBean callBean = new BankCallBean();
		callBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		callBean.setTxCode(BankCallConstant.TXCODE_CARD_BIND_DETAILS_QUERY);
		callBean.setInstCode(systemConfig.getBankInstcode());// 机构代码
		callBean.setBankCode(systemConfig.getBankCode());// 银行代码
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
		String respCode = bankCallBean == null ? "" : bankCallBean.getRetCode();
		// 如果接口调用成功
		if (BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
			String usrCardInfolist = bankCallBean.getSubPacks();
			JSONArray array = JSONObject.parseArray(usrCardInfolist);
			if (array != null && array.size() != 0) {
				List<BankCardRequest> bankCardList = new ArrayList<BankCardRequest>();
				for (int j = 0; j < array.size(); j++) {
					JSONObject obj = array.getJSONObject(j);
					// 根据银行卡号查询银行ID
					String bankId = bindCardClient.queryBankIdByCardNo(obj.getString("cardNo"));
					if(bankId == null) {
						bankId = "0";
					}
					// 查询银行配置信息
					BanksConfigVO bankConfig = bindCardClient.getBanksConfigByBankId(bankId);
					
					BankCardRequest bank = new BankCardRequest();
					bank.setUserId(userId);
					bank.setUserName(userVO.getUsername());
					bank.setStatus(1);// 默认都是1
					bank.setCardNo(obj.getString("cardNo"));
					bank.setBankId(bankId == null ? 0 : Integer.valueOf(bankId));
					bank.setBank(bankConfig.getBankName());
					// 银行联号
					String payAllianceCode = "";
					// 调用江西银行接口查询银行联号
					BankCallBean payAllianceCodeQueryBean = this.payAllianceCodeQuery(obj.getString("cardNo"), userId);
					if (payAllianceCodeQueryBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(payAllianceCodeQueryBean.getRetCode())) {
						payAllianceCode = payAllianceCodeQueryBean.getPayAllianceCode();
					}
					// 如果此时银联行号还是为空,根据bankId查询本地存的银联行号
					if (StringUtils.isBlank(payAllianceCode)) {
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
					boolean isInsertFlag = bindCardClient.insertUserCard(bank) > 0 ? true : false;
					if (!isInsertFlag) {
						throw new ReturnMessageException(BindCardError.CARD_SAVE_ERROR);
					}
				}
			}
		}
		
		// 插入操作记录表
		LogAcqResBean logAcq = bean.getLogAcqResBean();
		String bankId = bindCardClient.queryBankIdByCardNo(logAcq.getCardNo());
		if(bankId == null) {
			bankId = "0";
		}
		// 查询银行配置信息
		BanksConfigVO bankConfig = bindCardClient.getBanksConfigByBankId(bankId);
		
		BankCardLogRequest bankCardLogRequest = new BankCardLogRequest();
		bankCardLogRequest.setUserId(userId);
		bankCardLogRequest.setUserName(userVO.getUsername());
		bankCardLogRequest.setBankCode(String.valueOf(bankId));
		bankCardLogRequest.setBankName(bankConfig.getBankName());
		bankCardLogRequest.setCardType(0);// 卡类型 0普通提现卡1默认卡2快捷支付卡
		bankCardLogRequest.setOperationType(0);// 操作类型 0绑定 1删除
		bankCardLogRequest.setStatus(0);// 成功
		bankCardLogRequest.setCreateTime(GetDate.getNowTime());// 操作时间
		boolean isUpdateFlag = bindCardClient.insertBindCardLog(bankCardLogRequest) > 0 ? true : false;
		if (!isUpdateFlag) {
			throw new ReturnMessageException(BindCardError.CARD_SAVE_ERROR);
		}
		BankCardVO retCard = bindCardClient.queryUserCardValid(String.valueOf(userId), logAcq.getCardNo());
        if (retCard != null) {
            BankCardRequest bankCard=new BankCardRequest();
            bankCard.setId(retCard.getId());
            bankCard.setMobile(callBean.getMobile());
            bindCardClient.updateUserCard(bankCard);
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
	 * @param bindCardVO
	 * @param userId
	 * @return
	 */
	@Override
	public BankCallBean callBankUnBindCard(BindCardVO bindCardVO, Integer userId) {
		BankOpenAccountVO bankAccount = bankOpenClient.selectById(userId);
		UserInfoVO  userInfo = amUserClient.findUserInfoById(userId);
		UserVO user = amUserClient.findUserById(userId);
		
		BankCallBean retBean = null;
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
		bean.setCardNo(bindCardVO.getCardNo());// 银行卡号
		LogAcqResBean logAcqResBean = new LogAcqResBean();
		logAcqResBean.setCardNo(bindCardVO.getCardNo());// 银行卡号
		bean.setLogAcqResBean(logAcqResBean);
		
		try {
			retBean = BankCallUtils.callApiBg(bean);
		} catch (Exception e) {
			logger.info("解绑卡请求银行接口失败", e);
            return null;
		}
        
        return retBean;
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
        	throw new ReturnMessageException(BindCardError.CARD_NO_ERROR);
        }

        // 开户校验
        BankOpenAccountVO openAccount = bankOpenClient.selectById(userId);
        if (openAccount == null || StringUtils.isBlank(openAccount.getAccount())) {
        	throw new ReturnMessageException(BindCardError.BANK_NOT_OPEN_ERROR);
        }
        
        // 账户余额校验
        AccountVO account = bindCardClient.getAccount(userId);
        BigDecimal bankBalance = this.queryBankBlance(userId, openAccount.getAccount());
        if ((Validator.isNotNull(account.getBankBalance()) && account.getBankBalance().compareTo(BigDecimal.ZERO) > 0)
				|| ((Validator.isNotNull(bankBalance) && bankBalance.compareTo(BigDecimal.ZERO) > 0))) {
        	throw new ReturnMessageException(BindCardError.BANK_BALANCE_ERROR);
		}
        
        // 待解绑卡校验
        BankCardVO bankCard = bindCardClient.queryUserCardValid(String.valueOf(userId), bindCardVO.getCardNo());
        if (bankCard == null || StringUtils.isEmpty(bankCard.getCardNo())) {
        	throw new ReturnMessageException(BindCardError.CARD_NOT_EXIST_ERROR);
		}
        
	}
	
	/**
	 * 解绑接口请求后业务处理
	 * @param bean
	 */
	@Override
	public void updateAfterUnBindCard(BankCallBean bean) {
		LogAcqResBean logAcqResBean = bean.getLogAcqResBean();
		UserVO user = amUserClient.findUserById(Integer.parseInt(bean.getLogUserId()));
		BankCardVO bankCard = bindCardClient.queryUserCardValid(String.valueOf(bean.getLogUserId()), logAcqResBean.getCardNo());
		if(bankCard != null) {
			bindCardClient.deleteUserCardByUserId(bean.getLogUserId());
			
			// 插入操作记录表
			BankCardLogRequest bankCardLogRequest = new BankCardLogRequest();
			bankCardLogRequest.setUserId(Integer.parseInt(bean.getLogUserId()));
			bankCardLogRequest.setUserName(user.getUsername());
			bankCardLogRequest.setCardNo(logAcqResBean.getCardNo());
			bankCardLogRequest.setBankName(bankCard.getBank());
			bankCardLogRequest.setCardType(0);// 卡类型 0普通提现卡1默认卡2快捷支付卡
			bankCardLogRequest.setOperationType(1);// 操作类型 0绑定 1删除
			bankCardLogRequest.setStatus(0);// 成功
			bankCardLogRequest.setCreateTime(GetDate.getNowTime());// 操作时间
			boolean isUpdateFlag = bindCardClient.insertBindCardLog(bankCardLogRequest) > 0 ? true : false;
			if (!isUpdateFlag) {
				throw new ReturnMessageException(BindCardError.CARD_DELETE_ERROR);
			}
		}
		
	}
}

	