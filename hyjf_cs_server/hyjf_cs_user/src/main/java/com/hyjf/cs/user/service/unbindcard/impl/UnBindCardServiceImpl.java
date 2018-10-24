package com.hyjf.cs.user.service.unbindcard.impl;

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
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BindCardPageBean;
import com.hyjf.cs.user.bean.BindCardPageRequestBean;
import com.hyjf.cs.user.bean.DeleteCardPageBean;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.unbindcard.UnBindCardService;
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
import java.util.*;

/**
 * 解绑银行卡服务类
 * @author nxl
 */
@Service
public class UnBindCardServiceImpl extends BaseUserServiceImpl implements UnBindCardService {
	private static final Logger logger = LoggerFactory.getLogger(UnBindCardServiceImpl.class);
	@Autowired
	SystemConfig systemConfig;

	@Autowired
	AmUserClient amUserClient;

	@Autowired
	AmTradeClient amTradeClient;

	@Autowired
	AmConfigClient amConfigClient;

	/**
	 * 查询用户电子账户可用余额
	 * @param userId
	 * @param accountId
	 * @return
	 */
	@Override
	public BigDecimal queryBankBlance(Integer userId, String accountId) {
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
	 * 根据电子账号查询用户在江西银行的可用余额
	 * @param userId
	 * @param accountId
	 * @return
	 */
	@Override
	public BigDecimal getBankBalance(Integer userId, String accountId) {
		return this.queryBankBlance(userId, accountId);
	}


	/**
	 * 获取用户account信息
	 * @param userId
	 * @return
	 */
	@Override
	public AccountVO getAccountByUserId(int userId){
		return amTradeClient.getAccount(userId);
	}

	/**
	 * 根据用户Id,银行卡Id查询用户银行卡信息
	 * @param userId
	 * @param cardId
	 * @return
	 */
	@Override
	public  BankCardVO getBankCardByUserAndId(Integer userId, String cardId){
		return amUserClient.getBankCardById(userId,cardId);
	}
	/**
	 * 验证解绑银行卡参数
	 * @param user
	 * @param accountChinapnrTender
	 * @param accountVO
	 */
	@Override
	public void checkParamUnBindCardPage(WebViewUserVO user,BankOpenAccountVO accountChinapnrTender,AccountVO accountVO,BankCardVO bankCardVO){
		if(null == user){
			CheckUtil.check(false,MsgEnum.ERR_OBJECT_GET,"用户信息");// 获取用户信息失败
		}
		//企业用户解绑请联系客服
		if (user.getUserType()==1){
			CheckUtil.check(false,MsgEnum.ERR_CARD_ENTERPRISE);
		}
		// 取得用户在汇付天下的客户号
		if (accountChinapnrTender == null || StringUtils.isEmpty(accountChinapnrTender.getAccount())) {
			CheckUtil.check(false,MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
		}
		// 根据用户id查找账户信息管理
		//
		BigDecimal bankBalance = this.queryBankBlance(user.getUserId(),accountChinapnrTender.getAccount());
		if ((Validator.isNotNull(accountVO.getBankBalance()) && accountVO.getBankBalance().compareTo(BigDecimal.ZERO) > 0)
				|| ((Validator.isNotNull(bankBalance) && bankBalance.compareTo(BigDecimal.ZERO) > 0))) {
			CheckUtil.check(false,MsgEnum.ERR_CARD_UNBIND_HAVE_BALANCE);
		}
		if (bankCardVO == null || StringUtils.isEmpty(bankCardVO.getCardNo())) {
			logger.info("获取用户银行卡信息失败");
			CheckUtil.check(false,MsgEnum.STATUS_BC000002);
		}

	}

	/**
	 * 解绑银行卡接口请求
	 * @param user
	 * @param accountChinapnrTender
	 * @param bankCardVO
	 * @param userInfoVO
	 * @param channel
	 * @return
	 */
	@Override
	public Map<String,Object> callUnBindCardPage(WebViewUserVO user, BankOpenAccountVO accountChinapnrTender,BankCardVO bankCardVO, UserInfoVO userInfoVO,String channel,String sign,String bgRetUrl){
		// 失败页面
		String errorPath = "/bank/user/bankcardNew/closebindcard-error";
		// 成功页面
		String successPath = "/bank/user/bankcardNew/closebindcard-success";
		// 回调路径
		String retUrl = super.getFrontHost(systemConfig,channel+"") + errorPath +"?userId="+user.getUserId();
		String successUrl = super.getFrontHost(systemConfig,channel) + successPath;
		if(!channel.contains(BankCallConstant.CHANNEL_PC)){
			//todo 返回路径
			errorPath = "/user/bankCard/unbind/result/failed";
			successPath = "/user/bankCard/unbind/result/success";
			// 同步地址  是否跳转到前端页面
			retUrl = super.getFrontHost(systemConfig,channel+"") + errorPath +"?status=99&userId="+user.getUserId();
			successUrl = super.getFrontHost(systemConfig,channel+"") + successPath+"?status=000&statusDesc=";
			retUrl += "&token=1&sign=" +sign;
			successUrl += "&token=1&sign=" +sign;
		}

		// 忘记密码跳转链接
		String forgetPassworedUrl =  systemConfig.getForgetpassword();
		DeleteCardPageBean bean = new DeleteCardPageBean();
		//
		bean.setUserId(user.getUserId());
		bean.setTxCode(BankCallConstant.TXCODE_ACCOUNT_UNBINDCARD_PAGE);
		bean.setAccountId(accountChinapnrTender.getAccount());
		bean.setName(userInfoVO.getTruename());
		bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
		bean.setIdNo(userInfoVO.getIdcard());
		bean.setCardNo(bankCardVO.getCardNo());// 银行卡号
		bean.setMobile(user.getMobile());
		bean.setForgotPwdUrl(forgetPassworedUrl);
		bean.setRetUrl(retUrl);
		bean.setSuccessfulUrl(successUrl);
		bean.setNotifyUrl(bgRetUrl);
		bean.setChannel(channel);// 交易渠道
		bean.setPlatform("0");
		try {
			// 拼装参数 调用江西银行
			Map<String,Object> map = getCallbankMV(bean);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 解卡页面调用(合规四期需求)
	 * @param bean
	 * @return
	 */
	public  Map<String, Object> getCallbankMV(DeleteCardPageBean bean) {
		Map<String, Object> mv = new HashMap<>();
		// 获取共同参数
		String bankCode = systemConfig.getBankCode();
		String bankInstCode = systemConfig.getBankInstcode();
		String orderDate = GetOrderIdUtils.getOrderDate();
		String txDate = GetOrderIdUtils.getTxDate();
		String txTime = GetOrderIdUtils.getTxTime();
		String seqNo = GetOrderIdUtils.getSeqNo(6);
		String idType = BankCallConstant.ID_TYPE_IDCARD;

		// 调用开户接口
		BankCallBean bindCardBean = new BankCallBean();
		//通用必填参数
		bindCardBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		bindCardBean.setInstCode(bankInstCode);// 机构代码
		bindCardBean.setBankCode(bankCode);
		bindCardBean.setTxDate(txDate);
		bindCardBean.setTxTime(txTime);
		bindCardBean.setSeqNo(seqNo);
		bindCardBean.setChannel(bean.getChannel());

		//解卡参数
		bindCardBean.setTxCode(BankCallConstant.TXCODE_ACCOUNT_UNBINDCARD_PAGE);
		bindCardBean.setAccountId(bean.getAccountId());
		bindCardBean.setName(bean.getName());
		bindCardBean.setIdType(idType);
		bindCardBean.setIdNo(bean.getIdNo());
		bindCardBean.setCardNo(bean.getCardNo());
		bindCardBean.setMobile(bean.getMobile());
		bindCardBean.setRetUrl(bean.getRetUrl());
		bindCardBean.setForgotPwdUrl(bean.getForgotPwdUrl());
		bindCardBean.setSuccessfulUrl(bean.getSuccessfulUrl());
		bindCardBean.setNotifyUrl(bean.getNotifyUrl());

		// 页面调用必须传的
		String orderId = GetOrderIdUtils.getOrderId2(bean.getUserId());
		bindCardBean.setLogBankDetailUrl(BankCallConstant.BANK_URL_UNBIND_CARD_PAGE);
		bindCardBean.setLogOrderId(orderId);
		bindCardBean.setLogOrderDate(orderDate);
		bindCardBean.setLogUserId(String.valueOf(bean.getUserId()));
		bindCardBean.setLogRemark("外部服务接口:绑卡页面");
		bindCardBean.setLogIp(bean.getIp());
		bindCardBean.setLogClient(Integer.parseInt(bean.getPlatform()));
		try {
			// 拼装参数 调用江西银行
			mv = BankCallUtils.callApiMap(bindCardBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * 解绑银行卡后(异步回调删除)
	 * 合规四期(解卡页面调用)
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean updateAfterUnBindCard(BankCallBean bean, Integer userId) {
		boolean ret = false;
		BankCardVO bankCardVO = amUserClient.getBankCardByUserId(userId);
		WebViewUserVO user = this.getUserFromCache(userId);
		// 插入操作记录表
		BankCardLogRequest bankCardLogRequest = new BankCardLogRequest();
		bankCardLogRequest.setUserId(userId);
		bankCardLogRequest.setUserName(user.getUsername());

		bankCardLogRequest.setBankCode(String.valueOf(bankCardVO.getBankId()));
		bankCardLogRequest.setCardNo(bankCardVO.getCardNo());
		bankCardLogRequest.setBankName(bankCardVO.getBank());
		//删除银行卡
		int intDelete = amUserClient.deleteUserCardByUserId(userId.toString());
		if(intDelete>0){
			logger.info("==========银行卡删除成功==========");
		}else {
			throw new CheckException(MsgEnum.ERR_CARD_DELETE);
		}
		bankCardLogRequest.setCardType(0);// 卡类型 0普通提现卡1默认卡2快捷支付卡
		bankCardLogRequest.setOperationType(1);// 操作类型 0绑定 1删除
		bankCardLogRequest.setStatus(0);// 成功
		bankCardLogRequest.setCreateTime(new Date());// 操作时间

		boolean isUpdateFlag = amUserClient.insertBindCardLog(bankCardLogRequest) > 0 ? true : false;
		if(isUpdateFlag){
			logger.info("==========银行卡删除记录插入成功==========");
		}else {
			throw new CheckException(MsgEnum.ERR_CARD_DELETE);
		}
		if(intDelete>0&&isUpdateFlag){
			ret = true;
		}
		return ret;
	}
}

	