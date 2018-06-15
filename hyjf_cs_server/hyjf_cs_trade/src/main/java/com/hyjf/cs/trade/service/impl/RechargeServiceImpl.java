package com.hyjf.cs.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.BankCardVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.UserDirectRechargeBean;
import com.hyjf.cs.trade.client.RechargeClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.constants.RechargeError;
import com.hyjf.cs.trade.mq.AppMessageProducer;
import com.hyjf.cs.trade.mq.Producer;
import com.hyjf.cs.trade.mq.SmsProducer;
import com.hyjf.cs.trade.service.RechargeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户充值Service实现类
 * 
 * @author zhangqingqing
 *
 */
@Service
public class RechargeServiceImpl  extends BaseServiceImpl  implements RechargeService  {
	Logger  logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

	@Autowired
	RechargeClient rechargeClient;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	AppMessageProducer appMessageProducer;

	@Autowired
	SmsProducer smsProducer;

	// 充值状态:充值中
	private static final int RECHARGE_STATUS_WAIT = 1;
	// 充值状态:失败
	private static final int RECHARGE_STATUS_FAIL = 3;
	// 充值状态:成功
	private static final int RECHARGE_STATUS_SUCCESS = 2;

	@Override
	public BankCardVO selectBankCardByUserId(Integer userId) {
		BankCardVO bankCard = rechargeClient.selectBankCardByUserId(userId);
		return bankCard;
	}

	@Override
	public AccountVO getAccount(Integer userId) {
		AccountVO account = rechargeClient.getAccount(userId);
		return account;
	}

	@Override
	public UserVO getUsers(Integer userId) {
		UserVO users = rechargeClient.getUsers(userId);
		return users;
	}

	@Override
	public int insertRechargeInfo(BankCallBean bean) {
		String ordId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId();
		int ret = rechargeClient.selectByOrdId(ordId);
		if (ret == 0) {
			return ret;
		}
		String cardNo = bean.getCardNo();
		BankCardVO bankCard = rechargeClient.getBankCardByCardNo(Integer.parseInt(bean.getLogUserId()), cardNo);
		BankRequest bankRequest = new BankRequest();
		BeanUtils.copyProperties(bean,bankRequest);
		bankRequest.setBank(bankCard.getBank());
		int response = rechargeClient.insertSelectiveBank(bankRequest);
		return response;
	}
	/**
	 * 充值后,回调处理
	 *
	 * @param bean
	 * @param params
	 * @return
	 */
	@Override
	public JSONObject handleRechargeInfo(BankCallBean bean, Map<String, String> params) {
		// 用户Id
		Integer userId = Integer.parseInt(bean.getLogUserId());
		// 充值订单号
		String orderId = bean.getLogOrderId();
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 错误信息
		String errorMsg = this.getBankRetMsg(bean.getRetCode());
		// ip
		String ip = params.get("ip");
		// 交易日期
		String txDate = bean.getTxDate();
		// 交易时间
		String txTime = bean.getTxTime();
		// 交易流水号
		String seqNo = bean.getSeqNo();
		// 电子账户
		String accountId = bean.getAccountId();
		// 充值成功
		if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
			// 查询充值记录
			AccountRechargeVO accountRecharge = rechargeClient.selectByOrderId(orderId);
			// 如果没有充值记录
			if (accountRecharge != null) {
				//redis防重校验
				boolean reslut = RedisUtils.tranactionSet("recharge_orderid" + orderId, 10);
				if(!reslut){
					return jsonMessage("充值成功", "0");
				}
				// 交易金额
				BigDecimal txAmount = bean.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT);
				// 判断充值记录状态
				if (accountRecharge.getStatus() == RECHARGE_STATUS_SUCCESS) {
					// 如果已经是成功状态
					return jsonMessage("充值成功", "0");
				} else {
					// 如果不是成功状态
					try {
						// 将数据封装更新至充值记录
						accountRecharge.setFee(BigDecimal.ZERO);
						accountRecharge.setDianfuFee(BigDecimal.ZERO);
						accountRecharge.setBalance(txAmount);
						accountRecharge.setUpdateTime(nowTime);
						accountRecharge.setStatus(RECHARGE_STATUS_SUCCESS);
						accountRecharge.setAccountId(accountId);
						accountRecharge.setBankSeqNo(txDate + txTime + seqNo);
						accountRecharge.setLogOrderId(bean.getLogOrderId());
						accountRecharge.setLogUserId(bean.getLogUserId());
						accountRecharge.setTxAmount(bean.getTxAmount());
						accountRecharge.setTxDate(Integer.parseInt(null==bean.getTxDate()?"0":bean.getTxDate()));
						accountRecharge.setTxTime(Integer.parseInt(null==bean.getTxTime()?"0":bean.getTxTime()));
						BankAccountBeanRequest bankAccountBeanRequest = new BankAccountBeanRequest();
						bankAccountBeanRequest.setAccountRecharge(accountRecharge);
						bankAccountBeanRequest.setIp(ip);
						boolean flag = rechargeClient.updateBanks(bankAccountBeanRequest);
						if (flag) {
							UserVO users = rechargeClient.getUsers(userId);
							// 可以发送充值短信时
							if (users != null && users.getRechargeSms() != null && users.getRechargeSms() == 0) {
								// 替换参数
								Map<String, String> replaceMap = new HashMap<String, String>();
								replaceMap.put("val_amount", txAmount.toString());
								replaceMap.put("val_fee", "0");
								UserInfoVO info = getUsersInfoByUserId(userId);
								replaceMap.put("val_name", info.getTruename().substring(0, 1));
								replaceMap.put("val_sex", info.getSex() == 2 ? "女士" : "先生");
								SmsMessage smsMessage = new SmsMessage(userId, replaceMap, null, null,
										MessageConstant.SMSSENDFORUSER, null,
										CustomConstants.PARAM_TPL_CHONGZHI_SUCCESS,
										CustomConstants.CHANNEL_TYPE_NORMAL);
								AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null,
										MessageConstant.APPMSSENDFORUSER, CustomConstants.JYTZ_TPL_CHONGZHI_SUCCESS);
								smsProducer.messageSend(new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC,
										JSON.toJSONBytes(smsMessage)));
								appMessageProducer.messageSend(new Producer.MassageContent(MQConstant.APP_MESSAGE_TOPIC,
										JSON.toJSONBytes(appMsMessage)));
							}else{
								// 替换参数
								Map<String, String> replaceMap = new HashMap<String, String>();
								replaceMap.put("val_amount", txAmount.toString());
								replaceMap.put("val_fee", "0");
								UserInfoVO info = getUsersInfoByUserId(userId);
								replaceMap.put("val_name", info.getTruename().substring(0, 1));
								replaceMap.put("val_sex", info.getSex() == 2 ? "女士" : "先生");
								AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null,
										MessageConstant.APPMSSENDFORUSER, CustomConstants.JYTZ_TPL_CHONGZHI_SUCCESS);
								appMessageProducer.messageSend(new Producer.MassageContent(MQConstant.APP_MESSAGE_TOPIC,
										JSON.toJSONBytes(appMsMessage)));
							}
							return jsonMessage("充值成功!", "0");
						} else {
							// 查询充值交易状态
                            accountRecharge = rechargeClient.selectByOrderId(orderId);
							if (RECHARGE_STATUS_SUCCESS == accountRecharge.getStatus()) {
								return jsonMessage("充值成功!", "0");
							} else {
								// 账户数据过期，请查看交易明细 跳转中间页
								return jsonMessage("账户数据过期，请查看交易明细", "1");
							}
						}
					} catch (Exception e) {
						System.err.println(e);
					}
				}
			} else {
				logger.info("充值失败,未查询到相应的充值记录." + "用户ID:" + userId + ",充值订单号:" + orderId);
				return jsonMessage("充值失败,未查询到相应的充值记录", "1");
			}
		} else {
			// 更新订单信息
			AccountRechargeVO accountRecharge =this.rechargeClient.selectByOrderId(orderId);
			if (accountRecharge != null ) {
				if (RECHARGE_STATUS_WAIT == accountRecharge.getStatus()) {
					accountRecharge.setStatus(RECHARGE_STATUS_FAIL);
					accountRecharge.setUpdateTime(nowTime);
					accountRecharge.setMessage(errorMsg);
					accountRecharge.setAccountId(accountId);
					accountRecharge.setBankSeqNo(txDate + txTime + seqNo);
					this.rechargeClient.updateByPrimaryKeySelective(accountRecharge);
				}
			}
			return jsonMessage(errorMsg, "1");
		}
		return null;
	}


	@Override
	public String getBankRetMsg(String retCode) {
		if (StringUtils.isNotBlank(retCode)) {
			BankReturnCodeConfigVO codeConfig = this.rechargeClient.getBankReturnCodeConfig(retCode);
			if (codeConfig != null ) {
				String retMsg = codeConfig.getErrorMsg();
				if (StringUtils.isNotBlank(retMsg)) {
					return retMsg;
				} else {
					return "请联系客服！";
				}
			} else {
				return "请联系客服！";
			}
		} else {
			return "操作失败！";
		}
	}

	/**
	 * 拼装返回信息
	 *
	 * @param errorDesc
	 * @param
	 * @return
	 */
	public JSONObject jsonMessage(String errorDesc, String error) {
		JSONObject jo = null;
		if (Validator.isNotNull(errorDesc)) {
			jo = new JSONObject();
			jo.put("error", error);
			jo.put("errorDesc", errorDesc);
		}
		return jo;
	}

	/**
	 * 根据用户ID取得用户信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfoVO getUsersInfoByUserId(Integer userId) {
		if (userId != null) {
			UserInfoVO usersInfo = this.rechargeClient.findUsersInfoById(userId);
			return usersInfo;
		}
		return null;
	}

	@Override
	public BankCallBean insertGetMV(UserDirectRechargeBean rechargeBean) throws Exception {
		// 充值订单号
		String logOrderId = GetOrderIdUtils.getOrderId2(rechargeBean.getUserId());
		String orderDate = GetOrderIdUtils.getOrderDate();
		// 调用 2.3.4联机绑定卡到电子账户充值
		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);
		bean.setTxCode(BankCallMethodConstant.TXCODE_DIRECT_RECHARGE_PAGE);
		bean.setTxDate(GetOrderIdUtils.getTxDate());
		bean.setTxTime(GetOrderIdUtils.getTxTime());
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
		bean.setChannel(rechargeBean.getChannel());
		bean.setAccountId(rechargeBean.getAccountId());
		bean.setCardNo(rechargeBean.getCardNo());
		bean.setCurrency(BankCallConstant.CURRENCY_TYPE_RMB);
		bean.setTxAmount(rechargeBean.getTxAmount());
		bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
		bean.setIdNo(rechargeBean.getIdNo());
		bean.setName(rechargeBean.getName());
		bean.setMobile(rechargeBean.getMobile());
		bean.setForgotPwdUrl(rechargeBean.getForgotPwdUrl());
		bean.setUserIP(rechargeBean.getIp());
		bean.setRetUrl(rechargeBean.getRetUrl());
		bean.setNotifyUrl(rechargeBean.getNotifyUrl());
		bean.setLogOrderId(logOrderId);
		bean.setLogOrderDate(orderDate);
		bean.setLogUserId(String.valueOf(rechargeBean.getUserId()));
		bean.setLogUserName(rechargeBean.getUserName());
		bean.setLogRemark("充值页面");
		bean.setLogClient(Integer.parseInt(rechargeBean.getPlatform()));
		// 充值成功后跳转的url
		bean.setSuccessfulUrl(bean.getRetUrl()+"&isSuccess=1");
		// 页面调用必须传的
		bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_DIRECT_RECHARGE_PAGE);
		// 插入充值记录
		int result = insertRechargeInfo(bean);
		if (result == 0) {
			throw new Exception("插入充值记录失败,userid=["+rechargeBean.getUserId()+"].accountid=["+rechargeBean.getAccountId()+"]");
		}
		return bean;
	}

	@Override
	public BankCallBean rechargeService(String  token, String ipAddr, String mobile, String money) throws Exception {
		WebViewUser user = RedisUtils.getObj(token, WebViewUser.class);
		Integer userId = user.getUserId();
		// 信息校验
		BankCardVO bankCard = this.selectBankCardByUserId(userId);
		this.checkUserMessage(bankCard,userId,money);
		BankOpenAccountVO account = this.getBankOpenAccount(userId);
		String cardNo = bankCard.getCardNo() == null ? "" : bankCard.getCardNo();
		UserInfoVO userInfo = this.getUsersInfoByUserId(userId);
		String idNo = userInfo.getIdcard();
		String name = userInfo.getTruename();
		// 拼装参数 调用江西银行
		String retUrl = systemConfig.getWebHost() + "/return" + "?txAmount="+money;
		String bgRetUrl = systemConfig.getWebHost() + "/bgreturn" + "?phone="+mobile;
		UserDirectRechargeBean directRechargeBean = new UserDirectRechargeBean();
		directRechargeBean.setTxAmount(money);
		directRechargeBean.setIdNo(idNo);
		directRechargeBean.setName(name);
		directRechargeBean.setCardNo(cardNo);
		directRechargeBean.setMobile(mobile);
		directRechargeBean.setUserId(userId);
		directRechargeBean.setIp(ipAddr);
		directRechargeBean.setUserName("test");
		directRechargeBean.setRetUrl(retUrl);
		directRechargeBean.setNotifyUrl(bgRetUrl);
		directRechargeBean.setPlatform("0");
		directRechargeBean.setChannel(BankCallConstant.CHANNEL_PC);
		directRechargeBean.setAccountId(account.getAccount());
		String forgetPassworedUrl = "http://www.hyjf.com";
		directRechargeBean.setForgotPwdUrl(forgetPassworedUrl);
		BankCallBean bean = this.insertGetMV(directRechargeBean);
		return bean;
	}

	public void checkUserMessage(BankCardVO bankCard,Integer userId,String money){
		ModelAndView modelAndView = new ModelAndView();
		UserVO users=this.getUsers(userId);
		if (users.getBankOpenAccount()==0) {
			throw new ReturnMessageException(RechargeError.NOT_OPENACCOUNT_ERROR);
		}
		if (users.getIsSetPassword() == 0) {
			throw new ReturnMessageException(RechargeError.NOT_PASSWD_ERROR);
		}
		if (bankCard == null) {
			throw new ReturnMessageException(RechargeError.BANKCARD_ERROR);
		}

		if (StringUtils.isEmpty(money)) {
			throw new ReturnMessageException(RechargeError.MONEY_NOT_NULL_ERROR);
		}
		if (!money.matches("-?[0-9]+.*[0-9]*")) {
			throw new ReturnMessageException(RechargeError.FORMAT_ERROR);
		}
		if(money.indexOf(".")>=0){
			String l = money.substring(money.indexOf(".")+1,money.length());
			if(l.length()>2){
				throw new ReturnMessageException(RechargeError.MORE_DECIMAL_ERROR);
			}
		}
	}


}
