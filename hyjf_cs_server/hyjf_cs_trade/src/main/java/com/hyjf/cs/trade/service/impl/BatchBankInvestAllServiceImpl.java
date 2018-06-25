package com.hyjf.cs.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BorrowTenderTmpRequest;
import com.hyjf.am.vo.statistics.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.trade.BankCallBeanVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.mq.AppChannelStatisticsProducer;
import com.hyjf.cs.trade.mq.FddProducer;
import com.hyjf.cs.trade.mq.Producer;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.BatchBankInvestAllService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jijun
 * @date 20180623
 */
@Service
public class BatchBankInvestAllServiceImpl extends BaseTradeServiceImpl implements BatchBankInvestAllService {
    private static final Logger logger = LoggerFactory.getLogger(BatchBankInvestAllServiceImpl.class);

    @Autowired
    private BatchBankInvestAllClient batchBankInvestAllClient;//银行提现掉单
	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private AmBorrowClient amBorrowClient;
	@Autowired
	private AmMongoClient amMongoClient;

	@Autowired
	private AppChannelStatisticsProducer appChannelStatisticsProducer;


	@Value("${hyjf.bank.instcode}")
	private String BANK_INSTCODE;

	@Value("${hyjf.bank.bankcode}")
	private String BANK_BANKCODE;


	@Override
	public void updateTender() {
		List<BorrowTenderTmpVO> borrowTenderTmpList =this.getBorrowTenderTmpList();
		if (CollectionUtils.isNotEmpty(borrowTenderTmpList)){
			for (int i = 0; i < borrowTenderTmpList.size(); i++) {
				String orderid = borrowTenderTmpList.get(i).getNid();
				logger.info("开始处理投资订单号为:[" + orderid + "]");
				Integer userId = borrowTenderTmpList.get(i).getUserId();
				UserVO user = this.amUserClient.findUserById(userId);
				UserInfoVO userInfo=this.amUserClient.findUsersInfoById(userId);
				BankOpenAccountVO bankOpenAccount = this.getBankOpenAccount(userId);
				String accountId = bankOpenAccount == null ? "" : bankOpenAccount.getAccount();
				// 根据相应信息接口查询订单
				BankCallBean bean = queryBorrowTenderList(accountId, orderid, String.valueOf(userId));
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
					BorrowVO borrow =this.amBorrowClient.getBorrowByNid(borrowId);
					request.setBorrow(borrow);
					String borrowNid = borrowId == null ? "" : borrow.getBorrowNid();// 项目编号
					BorrowInfoVO borrowInfo = this.amBorrowClient.getBorrowInfoByNid(borrowNid);
					request.setBorrowInfo(borrowInfo);
					BankOpenAccountVO accountChinapnrBorrower = this.getBankOpenAccount(borrowInfo.getUserId());
					request.setAccountChinapnrBorrower(accountChinapnrBorrower);

				}

				UserVO logUser = this.amUserClient.findUserById(Integer.parseInt(bean.getLogUserId()));
				request.setLogUser(logUser);
				UserInfoVO logUserInfo = this.amUserClient.findUsersInfoById(Integer.parseInt(bean.getLogUserId()));
				request.setLogUserInfo(logUserInfo);
				
				EmployeeCustomizeVO employeeCustomize =this.amUserClient.selectEmployeeByUserId(Integer.parseInt(bean.getLogUserId()));
				request.setEmployeeCustomize(employeeCustomize);
				
				SpreadsUserVO spreadsUser = this.amUserClient.querySpreadsUsersByUserId(Integer.parseInt(bean.getLogUserId()));
				UserVO userss=this.amUserClient.findUserById(spreadsUser.getSpreadsUserId());
				UserInfoVO refUsers=this.amUserClient.findUsersInfoById(spreadsUser.getSpreadsUserId());
				EmployeeCustomizeVO employeeCustomize_ref = this.amUserClient.selectEmployeeByUserId(spreadsUser.getSpreadsUserId());
				request.setUserss(userss);
				request.setRefUsers(refUsers);
				request.setEmployeeCustomize_ref(employeeCustomize_ref);
				request.setNowTime(GetDate.getNowTime10());
				
				boolean ret = this.batchBankInvestAllClient.updateTenderStart(request);
				if (!ret){
					logger.info("=============投资全部掉单异常处理失败! 失败订单: " + orderid);
				}else {

					//更新渠道统计用户累计投资
					if (Validator.isNotNull(request.getLogUser()) && request.getBorrowInfo().getProjectType()!=8){
						//发送mq
						AppChannelStatisticsDetailVO appChannelStatisticsDetailVO =
								this.amMongoClient.getAppChannelStatisticsDetailByUserId(Integer.parseInt(bean.getLogUserId()));
						if (Validator.isNotNull(appChannelStatisticsDetailVO)){
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("accountDecimal", new BigDecimal(bean.getTxAmount()));
							// 投资时间
							params.put("investTime", request.getNowTime());
							// 项目类型
							if (request.getBorrowInfo().getProjectType() == 13) {
								params.put("projectType", "汇金理财");
							} else {
								params.put("projectType", "汇直投");
							}
							// 首次投标项目期限
							String investProjectPeriod = "";
							if (request.getBorrow().getBorrowStyle().equals("endday")) {
								investProjectPeriod = request.getBorrow().getBorrowPeriod() + "天";
							} else {
								investProjectPeriod = request.getBorrow().getBorrowPeriod() + "个月";
							}
							params.put("investProjectPeriod", investProjectPeriod);
							//根据investFlag标志位来决定更新哪种投资
							params.put("investFlag",request.getLogUser().getInvestflag());
							params.put("isNew",0);
							//压入消息队列
							try {
								appChannelStatisticsProducer.messageSend(new Producer.MassageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,JSON.toJSONBytes(params)));
							} catch (MQException e) {
								e.printStackTrace();
								logger.error("渠道统计用户累计投资推送消息队列失败！！！");
							}
							logger.info("用户:" + userId + "***********************************预更新渠道统计表AppChannelStatisticsDetail，订单号：" + bean.getOrderId());
						}else{
							//更新手投信息
							UtmRegVO utmRegVO =this.amUserClient.findUtmRegByUserId(Integer.parseInt(bean.getLogUserId()));
							if(Validator.isNotNull(utmRegVO)){
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("id", utmRegVO.getId());
								params.put("accountDecimal", new BigDecimal(bean.getTxAmount()));
								// 投资时间
								params.put("investTime", request.getNowTime());
								// 项目类型
								if (request.getBorrowInfo().getProjectType() == 13) {
									params.put("projectType", "汇金理财");
								} else {
									params.put("projectType", "汇直投");
								}
								// 首次投标项目期限
								String investProjectPeriod = "";
								if (request.getBorrow().getBorrowStyle().equals("endday")) {
									investProjectPeriod = request.getBorrow().getBorrowPeriod() + "天";
								} else {
									investProjectPeriod = request.getBorrow().getBorrowPeriod() + "个月";
								}
								params.put("investProjectPeriod", investProjectPeriod);
								// 更新渠道统计用户累计投资
								params.put("investFlag",request.getLogUser().getInvestflag());
								params.put("isNew",1);

								try {
									appChannelStatisticsProducer.messageSend(new Producer.MassageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,JSON.toJSONBytes(params)));
								} catch (MQException e) {
									e.printStackTrace();
									logger.error(" 更新渠道统计用户累计投资推送消息队列失败！！！");
								}
								logger.info("用户:" + userId + "***********************************预更新渠道统计表AppChannelStatisticsDetail，订单号：" + bean.getOrderId());
							}
						}
					}



					//更新用户投资标记
					if(Validator.isNotNull(request.getLogUser())) {
						JSONObject para = new JSONObject();
						para.put("userId", bean.getLogUserId());
						para.put("user", request.getLogUser());
						this.amUserClient.updateUserInvestFlag(para);

					}
					
					if(Validator.isNotNull(request.getLogUserInfo())) {
						JSONObject para = new JSONObject();
						para.put("userInfo", request.getLogUserInfo());
						para.put("nowTime", request.getNowTime());
						para.put("userId",Integer.parseInt(bean.getLogUserId()));
						para.put("orderId",bean.getOrderId());
						boolean ret1=this.amUserClient.insertVipUserTender(para);
						if (ret1){
							logger.info("插入VIP用户信息成功！！！");
						}else{
							logger.info("插入VIP用户信息失败！！！");
						}
					}
					
				}

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
		bean.setInstCode(BANK_INSTCODE);// 机构代码
		bean.setBankCode(BANK_BANKCODE);
		bean.setTxDate(GetOrderIdUtils.getTxDate());
		bean.setTxTime(GetOrderIdUtils.getTxTime());
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
		bean.setChannel(BankCallConstant.CHANNEL_PC);
		bean.setAccountId(accountId);// 电子账号
		bean.setOrgOrderId(orgOrderId);
		bean.setLogUserId(userId);
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(Integer.parseInt(userId)));
		bean.setLogRemark("投资人投标申请查询");
		// 调用接口
		return BankCallUtils.callApiBg(bean);
	}


	private List<BorrowTenderTmpVO> getBorrowTenderTmpList() {
		return batchBankInvestAllClient.getBorrowTenderTmpList();
	}


}
