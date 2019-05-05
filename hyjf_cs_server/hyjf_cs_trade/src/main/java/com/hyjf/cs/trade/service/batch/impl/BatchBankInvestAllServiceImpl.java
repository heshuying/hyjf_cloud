package com.hyjf.cs.trade.service.batch.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BorrowTenderTmpRequest;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.CsMessageClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.batch.BatchBankInvestAllService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author jijun
 * @date 20180623
 */
@Service
public class BatchBankInvestAllServiceImpl extends BaseTradeServiceImpl implements BatchBankInvestAllService {
    private static final Logger logger = LoggerFactory.getLogger(BatchBankInvestAllServiceImpl.class);
    @Autowired
	private CommonProducer commonProducer;
	/**
	 * client
	 */
	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private AmTradeClient amTradeClient;
	@Autowired
	private CsMessageClient amMongoClient;
	/**
	 * mq生产端
	 */
	@Autowired
	private SystemConfig systemConfig;

	@Override
	public void updateTender() {
		List<BorrowTenderTmpVO> borrowTenderTmpList =this.getBorrowTenderTmpList();
		if (CollectionUtils.isNotEmpty(borrowTenderTmpList)){
			for (int i = 0; i < borrowTenderTmpList.size(); i++) {
				String orderid = borrowTenderTmpList.get(i).getNid();
				logger.info("开始处理出借订单号为:[" + orderid + "]");
				Integer userId = borrowTenderTmpList.get(i).getUserId();
				UserVO user = this.amUserClient.findUserById(userId);
				UserInfoVO userInfo=this.amUserClient.findUsersInfoById(userId);
				BankOpenAccountVO bankOpenAccount = this.getBankOpenAccount(userId);
				String accountId = bankOpenAccount == null ? "" : bankOpenAccount.getAccount();
				// 根据相应信息接口查询订单
				BankCallBean bean = queryBorrowTenderList(accountId, orderid, String.valueOf(userId));
				if(bean == null){
					logger.error("调用银行失败！");
					return;
				}
				// 获得银行信息开始进行掉单处理
				BorrowTenderTmpRequest request = new BorrowTenderTmpRequest();
				request.setBankCallBean(CommonUtils.convertBean(bean,BankCallBeanVO.class));
				request.setOrderId(orderid);
				request.setUserId(userId);
				request.setBorrowTenderTmpVO(borrowTenderTmpList.get(i));
				request.setUser(user);
				request.setUserInfo(userInfo);
				boolean is51User = this.amUserClient.checkIs51UserCanInvest(userId);
				request.setIs51User(is51User);
				BankOpenAccountVO accountChinapnrTender = this.getBankOpenAccount(userId);
				request.setAccountChinapnrTender(accountChinapnrTender);
				if (bean!=null){
					String borrowId = bean.getProductId();// 借款Id
                    if(StringUtils.isNotBlank(borrowId)){
                        BorrowAndInfoVO borrow = this.amTradeClient.selectBorrowByNid(borrowId);
                        request.setBorrow(borrow);
                        BorrowInfoVO borrowInfo = this.amTradeClient.getBorrowInfoByNid(borrow.getBorrowNid());
                        request.setBorrowInfo(borrowInfo);
                        BankOpenAccountVO accountChinapnrBorrower = this.getBankOpenAccount(borrowInfo.getUserId());
                        request.setAccountChinapnrBorrower(accountChinapnrBorrower);
                    }
				}

				UserVO logUser = this.amUserClient.findUserById(Integer.parseInt(bean.getLogUserId()));
				request.setLogUser(logUser);
				UserInfoVO logUserInfo = this.amUserClient.findUsersInfoById(Integer.parseInt(bean.getLogUserId()));
				request.setLogUserInfo(logUserInfo);
				
				EmployeeCustomizeVO employeeCustomize =this.amUserClient.selectEmployeeByUserId(Integer.parseInt(bean.getLogUserId()));
				request.setEmployeeCustomize(employeeCustomize);
				
				SpreadsUserVO spreadsUser = this.amUserClient.querySpreadsUsersByUserId(Integer.parseInt(bean.getLogUserId()));
				if (Validator.isNotNull(spreadsUser)){
					UserVO userss=this.amUserClient.findUserById(spreadsUser.getSpreadsUserId());
					request.setUserss(userss);
					UserInfoVO refUsers=this.amUserClient.findUsersInfoById(spreadsUser.getSpreadsUserId());
					request.setRefUsers(refUsers);
					EmployeeCustomizeVO employeeCustomize_ref = this.amUserClient.selectEmployeeByUserId(spreadsUser.getSpreadsUserId());
					request.setEmployeeCustomize_ref(employeeCustomize_ref);
				}
				request.setNowTime(GetDate.getNowTime10());
				
				boolean ret = this.amTradeClient.updateTenderStart(request);
				if (!ret){
					logger.info("=============出借全部掉单异常处理失败! 失败订单: " + orderid);
					//更新失败不继续执行
					continue;
				}else {
					//更新渠道统计用户累计出借
					if (Validator.isNotNull(request.getLogUser())
							&& Validator.isNotNull(request.getBorrowInfo())
							&& request.getBorrowInfo().getProjectType()!=8){
						//发送mq
						AppUtmRegVO appUtmRegVO =
								this.amMongoClient.getAppChannelStatisticsDetailByUserId(Integer.parseInt(bean.getLogUserId()));
						if (Validator.isNotNull(appUtmRegVO)){
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("accountDecimal", new BigDecimal(bean.getTxAmount()));
							// 出借时间
							params.put("investTime", request.getNowTime());
							// 项目类型
							if (request.getBorrowInfo().getProjectType() == 13) {
								params.put("projectType", "汇金理财");
							} else {
								params.put("projectType", "汇直投");
							}
							// 首次投标项目期限
							String investProjectPeriod = "";
							if ("endday".equals(request.getBorrow().getBorrowStyle())) {
								investProjectPeriod = request.getBorrow().getBorrowPeriod() + "天";
							} else {
								investProjectPeriod = request.getBorrow().getBorrowPeriod() + "个月";
							}
							params.put("investProjectPeriod", investProjectPeriod);
							//根据investFlag标志位来决定更新哪种出借
							params.put("investFlag",request.getLogUser().getInvestflag() == 1 ? false:true);
							//压入消息队列
							try {
								commonProducer.messageSend(new MessageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,
										MQConstant.APP_CHANNEL_STATISTICS_DETAIL_INVEST_TAG, UUID.randomUUID().toString(), params));
							} catch (MQException e) {
								logger.error(e.getMessage());
								logger.error("渠道统计用户累计出借推送消息队列失败！！！");
							}
							logger.info("用户:" + userId + "***********************************预更新渠道统计表AppChannelStatisticsDetail，订单号：" + bean.getOrderId());
						}else{
							//更新首投信息
							updateUtmReg(bean, request);
						}
					}

					if(Validator.isNotNull(request.getLogUserInfo())) {
						JSONObject para = new JSONObject();
						para.put("userInfo", request.getLogUserInfo());
						para.put("nowTime", request.getNowTime());
						para.put("userId",Integer.parseInt(bean.getLogUserId()));
						para.put("orderId",bean.getOrderId());
						try {
							this.commonProducer.messageSend(new MessageContent(MQConstant.VIP_USER_TENDER_TOPIC,UUID.randomUUID().toString(), para));
						} catch (MQException e) {
							logger.error(e.getMessage());
							logger.info("保存VIP用户信息推送消息队列失败！！！");
						}
					}
					
				}

			}
		}


	}

	/**
	 * 更新首投信息
	 * @param bean
	 * @param request
	 */
	private void updateUtmReg(BankCallBean bean, BorrowTenderTmpRequest request) {
		UtmRegVO utmRegVO =this.amUserClient.findUtmRegByUserId(Integer.parseInt(bean.getLogUserId()));
		if(Validator.isNotNull(utmRegVO)){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", utmRegVO.getUserId());
			params.put("accountDecimal", new BigDecimal(bean.getTxAmount()));
			// 出借时间
			params.put("investTime", request.getNowTime());
			params.put("projectType", "散标");
			// 首次投标项目期限
			String investProjectPeriod = "";
			if ("endday".equals(request.getBorrow().getBorrowStyle())) {
				investProjectPeriod = request.getBorrow().getBorrowPeriod() + "天";
			} else {
				investProjectPeriod = request.getBorrow().getBorrowPeriod() + "个月";
			}
			params.put("investProjectPeriod", investProjectPeriod);
			// 根据investFlag标志位来决定更新哪种出借
			params.put("investFlag", checkIsNewUserCanInvest2(utmRegVO.getUserId()));
			try {
				commonProducer.messageSend(new MessageContent(MQConstant.STATISTICS_UTM_REG_TOPIC, UUID.randomUUID().toString(), params));
				logger.info("******首投信息推送消息队列******");
			} catch (MQException e) {
				logger.error(e.getMessage());
				logger.info("******首投信息推送消息队列失败******");
			}
		}
	}


	/**
	 * 根据相应信息接口查询投标申请
	 *
	 * @param accountId
	 * @param orgOrderId
	 * @return
	 */
	private BankCallBean queryBorrowTenderList(String accountId, String orgOrderId, String userId) {
		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		bean.setTxCode(BankCallConstant.TXCODE_BID_APPLY_QUERY);// 消息类型
		bean.setInstCode(systemConfig.getBankInstcode());// 机构代码
		bean.setBankCode(systemConfig.getBankBankcode());
		bean.setTxDate(GetOrderIdUtils.getTxDate());
		bean.setTxTime(GetOrderIdUtils.getTxTime());
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
		bean.setChannel(BankCallConstant.CHANNEL_PC);
		bean.setAccountId(accountId);// 电子账号
		bean.setOrgOrderId(orgOrderId);
		bean.setLogUserId(userId);
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(Integer.parseInt(userId)));
		bean.setLogRemark("出借人投标申请查询");
		// 调用接口
		return BankCallUtils.callApiBg(bean);
	}


	private List<BorrowTenderTmpVO> getBorrowTenderTmpList() {
		return this.amTradeClient.getBorrowTenderTmpList();
	}
	/**
	 * 投资全部掉单异常处理
	 */
	public void recharge(){
		amTradeClient.recharge();
	}

}
