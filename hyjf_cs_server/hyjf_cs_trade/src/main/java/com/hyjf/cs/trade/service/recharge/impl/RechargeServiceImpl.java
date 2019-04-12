package com.hyjf.cs.trade.service.recharge.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.UserDirectRechargeBean;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.recharge.RechargeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 用户充值Service实现类
 * 
 * @author zhangqingqing
 *
 */
@Service
public class RechargeServiceImpl extends BaseTradeServiceImpl implements RechargeService  {
	Logger  logger = LoggerFactory.getLogger(RechargeServiceImpl.class);
	@Autowired
	private CommonProducer commonProducer;

	@Autowired
	AmTradeClient bindCardClient;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	AmUserClient amUserClient;

	@Autowired
	AmConfigClient amConfigClient;
	@Autowired
	private AuthService authService;

	// 充值状态:充值中
	private static final int RECHARGE_STATUS_WAIT = 1;
	// 充值状态:失败
	private static final int RECHARGE_STATUS_FAIL = 3;
	// 充值状态:成功
	private static final int RECHARGE_STATUS_SUCCESS = 2;

	@Override
	public AccountVO getAccount(Integer userId) {
		AccountVO account = amTradeClient.getAccount(userId);
		return account;
	}


	@Override
	public int insertRechargeInfo(BankCallBean bean) {
		String ordId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId();
		int ret = amTradeClient.selectByOrdId(ordId);
		if (ret == 0) {
			return ret;
		}
		String cardNo = bean.getCardNo();
		BankCardVO bankCard = amUserClient.getBankCardByCardNo(Integer.parseInt(bean.getLogUserId()), cardNo);
		BankRequest bankRequest = new BankRequest();
		BeanUtils.copyProperties(bean,bankRequest);
		bankRequest.setBank(bankCard.getBank());
		int response = amTradeClient.insertSelectiveBank(bankRequest);
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
		// 当前日期
		Date nowDate = new Date();
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
        logger.info("errorMsg:" + errorMsg +"    bean.getRetCode():"+bean.getRetCode());
		// 充值成功
		if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
			// 查询充值记录
			AccountRechargeVO accountRecharge = amTradeClient.selectByOrderId(orderId);
			// 如果没有充值记录
			if (accountRecharge != null) {
				//redis防重校验
				boolean reslut = RedisUtils.tranactionSet(RedisConstants.RECHARGE_ORDERID + orderId, 10);
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
						// accountRecharge.setDianfuFee(BigDecimal.ZERO);
						accountRecharge.setBalance(txAmount);
						accountRecharge.setUpdateTime(nowDate);
						accountRecharge.setStatus(RECHARGE_STATUS_SUCCESS);
						accountRecharge.setAccountId(accountId);
						accountRecharge.setBankSeqNo(txDate + txTime + seqNo);
						accountRecharge.setLogOrderId(bean.getLogOrderId());
						accountRecharge.setLogUserId(bean.getLogUserId());
						accountRecharge.setTxAmount(bean.getTxAmount());
						accountRecharge.setTxDate(Integer.parseInt(null==bean.getTxDate()?"0":bean.getTxDate()));
						accountRecharge.setTxTime(Integer.parseInt(null==bean.getTxTime()?"0":bean.getTxTime()));
						accountRecharge.setSeqNo(Integer.parseInt(seqNo));
						BankAccountBeanRequest bankAccountBeanRequest = new BankAccountBeanRequest();
						bankAccountBeanRequest.setAccountRecharge(accountRecharge);
						bankAccountBeanRequest.setIp(accountRecharge.getAddIp());
						boolean flag = amTradeClient.updateBanks(bankAccountBeanRequest);
						if (flag) {
							BankCardVO bankCardVO=amUserClient.getBankCardByUserId(userId);
							BankCardVO updateBankCardVO=new BankCardVO();
							updateBankCardVO.setId(bankCardVO.getId());
							updateBankCardVO.setMobile(params.get("mobile"));
							amUserClient.updateBankCard(updateBankCardVO);
							UserVO users = amUserClient.findUserById(userId);
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
										MessageConstant.SMS_SEND_FOR_USER, null,
										CustomConstants.PARAM_TPL_CHONGZHI_SUCCESS,
										CustomConstants.CHANNEL_TYPE_NORMAL);
								AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null,
										MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_TPL_CHONGZHI_SUCCESS);
								commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC,
										UUID.randomUUID().toString(), smsMessage));
								commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC,
										UUID.randomUUID().toString(), appMsMessage));
							}else{
								// 替换参数
								Map<String, String> replaceMap = new HashMap<String, String>();
								replaceMap.put("val_amount", txAmount.toString());
								replaceMap.put("val_fee", "0");
								UserInfoVO info = getUsersInfoByUserId(userId);
								replaceMap.put("val_name", info.getTruename().substring(0, 1));
								replaceMap.put("val_sex", info.getSex() == 2 ? "女士" : "先生");
								AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null,
										MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_TPL_CHONGZHI_SUCCESS);
								commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC,
										UUID.randomUUID().toString(), appMsMessage));
							}

							// 神策数据统计 add by liuyang 20180725 start
							try {
								SensorsDataBean sensorsDataBean = new SensorsDataBean();
								sensorsDataBean.setEventCode("recharge_result");
								sensorsDataBean.setOrderId(bean.getLogOrderId());
								sensorsDataBean.setUserId(Integer.parseInt(bean.getLogUserId()));
								this.sendSensorsDataMQ(sensorsDataBean);
							} catch (Exception e) {
								logger.error(e.getMessage());
							}
							try{
								// 充值成功后,发送大屏数据统计MQ
								ScreenDataBean screenDataBean = new ScreenDataBean(users.getUserId(),users.getUsername(),txAmount,2);
								logger.info("充值成功后发送大屏数据"+JSONObject.toJSONString(screenDataBean));
								this.sendScreenDataMQ(screenDataBean);
							}catch (Exception e){
								logger.error("充值成功后,发送大屏数据统计MQ失败",e.getMessage());
							}
							// 神策数据统计 add by liuyang 20180725 end
							return jsonMessage("充值成功!", "0");
						} else {
							// 查询充值交易状态
                            accountRecharge = amTradeClient.selectByOrderId(orderId);
							if (RECHARGE_STATUS_SUCCESS == accountRecharge.getStatus()) {
								return jsonMessage("充值成功!", "0");
							} else {
								// 账户数据过期，请查看交易明细 跳转中间页
								return jsonMessage("账户数据过期，请查看交易明细", "1");
							}
						}
					} catch (Exception e) {
						logger.error("充值错误，错误原因："+e);
					}
				}
			} else {
				logger.info("充值失败,未查询到相应的充值记录." + "用户ID:" + userId + ",充值订单号:" + orderId);
				return jsonMessage("充值失败,未查询到相应的充值记录", "1");
			}
		} else {
			// 更新订单信息
			AccountRechargeVO accountRecharge =this.amTradeClient.selectByOrderId(orderId);
			if (accountRecharge != null ) {
				if (RECHARGE_STATUS_WAIT == accountRecharge.getStatus()) {
					accountRecharge.setStatus(RECHARGE_STATUS_FAIL);
					accountRecharge.setUpdateTime(nowDate);
					accountRecharge.setMessage(errorMsg);
					accountRecharge.setAccountId(accountId);
					accountRecharge.setBankSeqNo(txDate + txTime + seqNo);
					this.amTradeClient.updateAccountRecharge(accountRecharge);
				}
			}
			return jsonMessage(errorMsg, "1");
		}
		return null;
	}


	@Override
	public String getBankRetMsg(String retCode) {
		if (StringUtils.isNotBlank(retCode)) {
			BankReturnCodeConfigVO codeConfig = this.amUserClient.getBankReturnCodeConfig(retCode);
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
			UserInfoVO usersInfo = this.amUserClient.findUsersInfoById(userId);
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
		bean.setRetUrl(rechargeBean.getRetUrl()+"&status=99&statusDesc=充值失败&logOrdId="+logOrderId);
		bean.setNotifyUrl(rechargeBean.getNotifyUrl());
		bean.setLogOrderId(logOrderId);
		bean.setLogOrderDate(orderDate);
		bean.setLogUserId(String.valueOf(rechargeBean.getUserId()));
		bean.setLogUserName(rechargeBean.getUserName());
		bean.setLogRemark("充值页面");
		bean.setLogClient(Integer.parseInt(rechargeBean.getPlatform()));
		// 充值成功后跳转的url
		bean.setSuccessfulUrl(rechargeBean.getSuccessfulUrl()+"&isSuccess=1&logOrdId="+bean.getLogOrderId()+"&status=000&statusDesc=");
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
	@HystrixCommand(commandKey = "充值(三端)-rechargeService",fallbackMethod = "fallBackRecharge",ignoreExceptions = CheckException.class,commandProperties = {
			//设置断路器生效
			@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
			//一个统计窗口内熔断触发的最小个数3/10s
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
			@HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "50"),
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			//熔断5秒后去尝试请求
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
			//失败率达到30百分比后熔断
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30")})
	public BankCallBean rechargeService(UserDirectRechargeBean directRechargeBean,int userId, String ipAddr, String mobile, String money) throws Exception {
		WebViewUserVO user=this.getUserFromCache(userId);
		// 信息校验
		BankCardVO bankCard = this.selectBankCardByUserId(userId);
		this.checkUserMessage(bankCard,userId,money);
		BankOpenAccountVO account = this.getBankOpenAccount(userId);
		CheckUtil.check(null!=account&&null!=account.getAccount(),MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
		String cardNo = bankCard.getCardNo() == null ? "" : bankCard.getCardNo();
		UserInfoVO userInfo = this.getUsersInfoByUserId(userId);
		String idNo = userInfo.getIdcard();
		String name = userInfo.getTruename();

		directRechargeBean.setTxAmount(money);
		directRechargeBean.setIdNo(idNo);
		directRechargeBean.setName(name);
		directRechargeBean.setCardNo(cardNo);
		directRechargeBean.setMobile(mobile);
		directRechargeBean.setUserId(userId);
		directRechargeBean.setIp(ipAddr);
		directRechargeBean.setUserName(user.getUsername());
		directRechargeBean.setPlatform(directRechargeBean.getPlatform());
		directRechargeBean.setChannel(directRechargeBean.getChannel());
		directRechargeBean.setAccountId(account.getAccount());
		BankCallBean bean = this.insertGetMV(directRechargeBean);
		return bean;
	}

    /**
     * 充值(三端) fallBackRecharge 方法
     * @param directRechargeBean
     * @param userId
     * @param ipAddr
     * @param mobile
     * @param money
     * @return
     * @throws Exception
     */
    public BankCallBean fallBackRecharge(UserDirectRechargeBean directRechargeBean,int userId, String ipAddr, String mobile, String money) throws Exception {
        logger.info("==================已进入 充值(三端) fallBackRecharge 方法================");
        return null;
    }

    @Override
	public WebResult<Object> toRecharge(WebViewUserVO user) {
		WebResult<Object> result = new WebResult<Object>();
		UserVO userVO=this.getUsers(user.getUserId());
		JSONObject ret = new JSONObject();
		if(user==null){
			result.setStatus(MsgEnum.ERR_USER_LOGIN_RETRY.getCode());
			result.setStatusDesc(MsgEnum.ERR_USER_LOGIN_RETRY.getMsg());
			return result;
		}
		Integer userId = user.getUserId();
		// 判断用户是否开户
		if (!user.isBankOpenAccount()) {
			result.setStatus(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN.getCode());
			result.setStatusDesc(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN.getMsg());
			return result;
		}
		// 银行卡号
		String cardNo = "";
		// 银行
		String bank ="";
		// 银行卡Id
		String cardId = "";
		// 用户是否绑卡: 0:未绑卡,1:已绑卡
		Integer isBundCardFlag = 0;
		// 单笔限额
		String singleQuota = "";
		// 单卡单日限额
		String singleCardQuota = "";
		// 单卡单月限额
		String monthCardQuota = "";
		//单笔、单日，单月 合并
		String concatAllQuota = ",";
		// 根据用户Id查询用户银行卡号
		// 查询页面上可以挂载的银行列表
		BankCardVO bankCard = amUserClient.selectBankCardByUserId(userId);

		if (bankCard != null) {
			cardNo = BankCardUtil.getCardNo(bankCard.getCardNo());
			cardId = String.valueOf(bankCard.getId());
			bank = bankCard.getBank();
			isBundCardFlag = 1;

			Integer bankId = bankCard.getBankId();
			JxBankConfigVO jxBankConfigVO = amConfigClient.getBankNameByBankId(bankId + "");
			if (jxBankConfigVO != null && jxBankConfigVO.getQuickPayment() == 1 && jxBankConfigVO.getSingleQuota() != null && jxBankConfigVO.getSingleCardQuota() != null) {
				if(jxBankConfigVO.getSingleQuota().compareTo(BigDecimal.ZERO) > 0){
					singleQuota = CommonUtils.formatBigDecimal(jxBankConfigVO.getSingleQuota().divide(new BigDecimal(10000))) + "万";
					concatAllQuota =" 单笔"+singleQuota;
				}else{
					singleQuota = "不限";
				}

				if(jxBankConfigVO.getSingleCardQuota().compareTo(BigDecimal.ZERO) > 0){
					singleCardQuota = CommonUtils.formatBigDecimal(jxBankConfigVO.getSingleCardQuota().divide(new BigDecimal(10000))) + "万";
					concatAllQuota+=", 单日"+singleCardQuota;
				}else{
					singleCardQuota = "不限";
				}

				if(jxBankConfigVO.getMonthCardQuota().compareTo(BigDecimal.ZERO) > 0){
					monthCardQuota = CommonUtils.formatBigDecimal(jxBankConfigVO.getMonthCardQuota().divide(new BigDecimal(10000))) + "万";
					concatAllQuota+=", 单月"+monthCardQuota;
				}else{
					monthCardQuota = "不限";
				}
			}
		}
		// 用户是否绑卡
		ret.put("isBundCardFlag", isBundCardFlag);
		//银行
		ret.put("bank",bank);
		// 卡号
		ret.put("cardNo", cardNo);
		// 银行卡Id
		ret.put("cardId", cardId);
		ret.put("singleQuota", singleQuota);
		ret.put("singleCardQuota", singleCardQuota);
		ret.put("monthCardQuota", monthCardQuota);
		ret.put("concatAllQuota", concatAllQuota.substring(1));
		AccountVO account = this.bindCardClient.getAccount(userId);
		// 可用余额
		ret.put("userBalance", account.getBankBalance());
		String trueName = user.getTruename();
		// 用户类型 0普通用户 1企业用户
		Integer userType = user.getUserType();
		if (userType != null && userType == 1) {
			// 根据用户ID查询企业用户账户信息
			CorpOpenAccountRecordVO record = amUserClient.selectCorpOpenAccountRecordByUserId(user.getUserId());
			trueName = record.getBusiName();
		}
		ret.put("userType", userType);
		// 姓名
		ret.put("trueName", trueName);
		// 是否设置交易密码
		ret.put("isSetPassword", userVO.getIsSetPassword());
		if(bankCard != null){
			ret.put("mobile", bankCard.getMobile());
		}
		// 缴费授权
		HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(userId);
		// 是否开启服务费授权 0未开启  1已开启
		ret.put("paymentAuthStatus", hjhUserAuth==null?"":hjhUserAuth.getAutoPaymentStatus());
		// 是否开启服务费授权 0未开启  1已开启
		ret.put("paymentAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
		logger.info("paymentAuthOn:"+authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
		// 江西银行客户号
		ret.put("accountId", user.getBankAccount());
		//充值提示语
		ret.put("rcvAccountName", "惠众商务顾问（北京）有限公司<br/>");
		ret.put("rcvAccount", "7919 1314 9300 306<br/>");
		ret.put("rcvOpenBankName", "江西银行南昌铁路支行<br/>");
		ret.put("kindlyReminder", "①用户必须使用在平台唯一绑定银行卡进行充值；<br/>②银行转账时，请选择（城市商业银行）江西银行或者南昌银行；<br/>③线下充值的到账时间一般为1-3天（具体到账时间以银行的实际到账时间为准）；");
		//ret.put("bindCardUrl", RechargeDefine.HOST+BindCardPageDefine.REQUEST_MAPPING+BindCardPageDefine.REQUEST_BINDCARDPAGE+".do");
		ret.put("bindCardUrl", "");
		// 江西银行绑卡接口修改 update by wj 2018-5-17 start
		ret.put("bindType",this.amConfigClient.getBankInterfaceFlagByType("BIND_CARD"));
		// 江西银行绑卡接口修改 update by wj 2018-5-17 end

		result.setData(ret);
		return result;
	}

	@Override
	public WebResult<Object> seachUserBankRechargeErrorMessgae(String logOrdId) {
		WebResult<Object> result = new WebResult<Object>();
		AccountRechargeVO vo = amTradeClient.selectByOrderId(logOrdId);
		result.setStatus(WebResult.SUCCESS);
		Map<String,String> map = new HashedMap();
		if(vo!=null&&vo.getMessage().length()>0){
			map.put("error",vo.getMessage());
		}else{
			map.put("error","系统异常，请稍后再试！");
		}
		result.setData(map);
		return result;
	}

	@Override
	public List<JxBankConfigVO> getRechargeQuotaLimit() {
		return amConfigClient.getRechargeQuotaLimit();
	}

	public void checkUserMessage(BankCardVO bankCard,Integer userId,String money){
		UserVO users=this.getUsers(userId);
		if (users.getBankOpenAccount()==0) {
			throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
		}
		if (users.getIsSetPassword() == 0) {
			throw new CheckException(MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
		}

		if (users.getStatus() == 1) {
			throw new CheckException(MsgEnum.ERR_USER_INVALID);
		}
		if (!this.authService.checkPaymentAuthStatus(userId)) {
			throw new CheckException(MsgEnum.ERR_AUTH_USER_PAYMENT);
		}
		if (bankCard == null) {
			throw new CheckException(MsgEnum.ERR_AMT_RECHARGE_BANK_CARD_GET);
		}

		if (StringUtils.isEmpty(money)) {
			throw new CheckException(MsgEnum.ERR_AMT_RECHARGE_MONEY_REQUIRED);
		}
		if (!money.matches("-?[0-9]+.*[0-9]*")) {
			throw new CheckException(MsgEnum.ERR_FMT_MONEY);
		}
		if(money.indexOf(".")>=0){
			String l = money.substring(money.indexOf(".")+1,money.length());
			if(l.length()>2){
				throw new CheckException(MsgEnum.ERR_AMT_RECHARGE_MONEY_MORE_DECIMAL);
			}
		}
	}


	/**
	 * 充值成功后,发送神策数据统计MQ
	 *
	 * @param sensorsDataBean
	 */
	private void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) throws MQException {
		this.commonProducer.messageSendDelay(new MessageContent(MQConstant.SENSORSDATA_RECHARGE_TOPIC, UUID.randomUUID().toString(), sensorsDataBean), 2);
	}
	/**
	 * 充值成功后,发送大屏数据统计MQ
	 *
	 * @param screenDataBean
	 */
	private void sendScreenDataMQ(ScreenDataBean screenDataBean) throws MQException {
		this.commonProducer.messageSendDelay(new MessageContent(MQConstant.SCREEN_DATA_TOPIC, UUID.randomUUID().toString(),screenDataBean), 2);
	}

}
