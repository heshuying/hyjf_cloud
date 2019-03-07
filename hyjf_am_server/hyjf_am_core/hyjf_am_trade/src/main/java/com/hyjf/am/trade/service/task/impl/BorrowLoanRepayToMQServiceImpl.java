/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task.impl;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountExample;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.BorrowApicronExample;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.BorrowLoanRepayToMQService;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 放款，还款请求银行，发送队列任务
 * @author dxj
 * @version BorrowLoanRepayToMQServiceImpl.java, v0.1 2018年6月19日 上午11:27:25
 */
@Service
public class BorrowLoanRepayToMQServiceImpl extends BaseServiceImpl implements BorrowLoanRepayToMQService {

	
	/** 任务类别（1：还款） */
	private static final Integer TASK_REPAY_TYPE = 1;
	/**任务类别（0：放款） */
	private static final Integer TASK_LOAN_TYPE = 0;

    @Autowired
	private CommonProducer commonProducer;

	/**
	 * 对放款、还款任务进行分派
	 *
	 * @return
	 */
    @Override
	public void taskAssign() {
		
        // 还款请求件数
        int repayRequestCount = 0;
		logger.info("自动放/还款消息推送任务开始。");
		
		try {
			// 取得直投类未还款任务，状态不是 0，6 的
			List<BorrowApicron> listZhitouApicron = getBorrowApicronList();
			// 如果当前是否存在放款或还款任务
			if(listZhitouApicron == null || listZhitouApicron.size() == 0){
				return;
			}
	        logger.info("任务总数："+listZhitouApicron.size());
	        
			// 循环进行将还款任务压入队列中还款
			for (BorrowApicron apicron : listZhitouApicron) {
				logger.info(apicron.getApiType()+" :项目编号:[" + apicron.getBorrowNid() + "].计划编号:[" + (StringUtils.isEmpty(apicron.getPlanNid()) ? "" : apicron.getPlanNid()) + "].");
				
				Integer status = apicron.getStatus();
				// 放、还款请求，
				if(status.equals(CustomConstants.BANK_BATCH_STATUS_SENDING) || status.equals(CustomConstants.BANK_BATCH_STATUS_SEND_FAIL)){
					if(TASK_REPAY_TYPE.equals(apicron.getApiType())){
						logger.info("发起还款消息:还款项目编号:[" + apicron.getBorrowNid() + ",还款期数:[第" + apicron.getPeriodNow() + "],计划编号:[" + (StringUtils.isEmpty(apicron.getPlanNid()) ? "" : apicron.getPlanNid()));
						// 还款请求
						sendMessage(MQConstant.BORROW_REPAY_REQUEST_TOPIC, apicron);
                        repayRequestCount++;
                        logger.info("发起还款请求总数:[" + repayRequestCount + "].");
                        
                    }else if(TASK_LOAN_TYPE.equals(apicron.getApiType()) && StringUtils.isBlank(apicron.getPlanNid())){
                    	//直投放款请求
                    	sendMessage(MQConstant.BORROW_REALTIMELOAN_ZT_REQUEST_TOPIC, apicron);
                    	logger.info("发送放款请求:还款项目编号:[" + apicron.getBorrowNid() + "],计划编号:" + apicron.getPlanNid());
                    }else if(TASK_LOAN_TYPE.equals(apicron.getApiType()) && StringUtils.isNotBlank(apicron.getPlanNid())){
                    	//计划放款请求
                    	sendMessage(MQConstant.BORROW_REALTIMELOAN_PLAN_REQUEST_TOPIC, apicron);
                    	logger.info("发送放款请求:还款项目编号:[" + apicron.getBorrowNid() + "],计划编号:" + apicron.getPlanNid());
                    }
					
				}else if(status.equals(CustomConstants.BANK_BATCH_STATUS_SENDED) || status.equals(CustomConstants.BANK_BATCH_STATUS_VERIFY_SUCCESS)
						|| status.equals(CustomConstants.BANK_BATCH_STATUS_PART_FAIL)){
					// 放、还款任务
					if(TASK_REPAY_TYPE.equals(apicron.getApiType()) && StringUtils.isBlank(apicron.getPlanNid())){
						// 直投类还款
						sendMessage(MQConstant.BORROW_REPAY_ZT_RESULT_TOPIC, apicron);
                    	logger.info("发送散标还款结果处理消息:还款项目编号:[" + apicron.getBorrowNid() + "]");
						
					}else if(TASK_REPAY_TYPE.equals(apicron.getApiType()) && StringUtils.isNotBlank(apicron.getPlanNid())){
						// 计划类还款
						sendMessage(MQConstant.BORROW_REPAY_PLAN_RESULT_TOPIC, apicron);
                    	logger.info("发送计划还款结果处理消息:还款项目编号:[" + apicron.getBorrowNid() + "],计划编号:" + apicron.getPlanNid());
                    	
					}else if(TASK_LOAN_TYPE.equals(apicron.getApiType()) && StringUtils.isBlank(apicron.getPlanNid()) ) {
						sendMessage(MQConstant.BORROW_REALTIMELOAN_ZT_REQUEST_TOPIC, apicron);
                    	logger.info("发送散标放款结果处理:项目编号:[" + apicron.getBorrowNid() + "]");
                    	
					}else if(TASK_LOAN_TYPE.equals(apicron.getApiType()) && StringUtils.isNotBlank(apicron.getPlanNid()) ) {
						sendMessage(MQConstant.BORROW_REALTIMELOAN_PLAN_REQUEST_TOPIC, apicron);
                    	logger.info("发送计划放款结果处理:项目编号:[" + apicron.getBorrowNid() + "],计划编号:" + apicron.getPlanNid());
					}
					
				}else if(status.equals(CustomConstants.BANK_BATCH_STATUS_FAIL)){
					//放款部分成功或放款失败异常处理最终版
					if (TASK_LOAN_TYPE.equals(apicron.getApiType())) {
						//直投类异常修复
						boolean flag = updateBatchFailLoan(apicron);
						if (flag) {
							logger.info("------------开始修复标的号:" + apicron.getBorrowNid() + ",放款异常!计划编号:" + apicron.getPlanNid());
							if (StringUtils.isBlank(apicron.getPlanNid())) {//直投标的
								sendMessage(MQConstant.BORROW_REALTIMELOAN_ZT_REQUEST_TOPIC, apicron);
		                    	logger.info("发送散标放款异常结果处理:项目编号:[" + apicron.getBorrowNid() + "]");
							}else if(StringUtils.isNotBlank(apicron.getPlanNid())){//计划标的
								sendMessage(MQConstant.BORROW_REALTIMELOAN_PLAN_REQUEST_TOPIC, apicron);
		                    	logger.info("发送计划放款异常结果处理:项目编号:[" + apicron.getBorrowNid() + "],计划编号:" + apicron.getPlanNid());
							}
						}
					}
					
					
				}
			}
		} catch (Exception e) {
			logger.error("batch 单笔标的更新发生异常 ", e);
		} finally {
			
		}
	}
	
	private List<BorrowApicron> getBorrowApicronList() {
		BorrowApicronExample example = new BorrowApicronExample();
		BorrowApicronExample.Criteria criteria = example.createCriteria();
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(0);
		statusList.add(6);
		criteria.andStatusNotIn(statusList);
		example.setOrderByClause(" id asc ");
		List<BorrowApicron> list = this.borrowApicronMapper.selectByExample(example);
		return list;
	}
	
	public Boolean updateBatchFailLoan(BorrowApicron apicron) {
		Integer failTimes = apicron.getFailTimes();
		String borrowNid = apicron.getBorrowNid();
		try {
			if (failTimes >= 3) {
				logger.info("---------" + borrowNid + "放款失败异常修复次数超出3次,请联系开发人员!---------");
				return false;
			}
			String bankSeqNo = apicron.getBankSeqNo();
			BankCallBean queryResult = autoLendQuery(apicron);
			if (queryResult == null) {
				logger.error("放款状态查询失败！[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
//				throw new Exception("放款状态查询失败！[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
				return false;
			}
			logger.info(apicron.getBorrowNid()+" 满标自动放款查:"+queryResult.getRetCode());
			
			// 这里利用了重复提交失败的原因,暂时不用
			if (BankCallConstant.RESPCODE_SUCCESS.equals(queryResult.getRetCode())) {
				apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_SENDED);
			}else {
				apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_SENDING);
			}
			this.borrowApicronMapper.updateByPrimaryKey(apicron);
			return true;
		} catch (Exception e) {
			logger.error("放款失败处理异常:" + borrowNid, e);
			apicron.setFailTimes(failTimes + 1);
			this.borrowApicronMapper.updateByPrimaryKey(apicron);
			sendSmsForManager(borrowNid);
		}
		return false;
	}
	
	private BankCallBean autoLendQuery(BorrowApicron apicron) {
		//开始处理
		String orderId = apicron.getOrdid();
		logger.info("标的号:" + apicron.getBorrowNid() + "开始查询批次结果,orderId is :" + orderId);
		String channel = BankCallConstant.CHANNEL_PC;
		String txDate = GetOrderIdUtils.getTxDate();
		String txTime = GetOrderIdUtils.getTxTime();
		String seqNo = GetOrderIdUtils.getSeqNo(6);
		// 根据借款人用户ID查询借款人用户电子账户号
		
		// 电子账户从资金表放
		Account borrowUserAccount = this.getAccountByUserId(apicron.getUserId());
		if(borrowUserAccount == null || StringUtils.isEmpty(borrowUserAccount.getAccountId())){
			logger.info("根据借款人用户ID查询借款人电子账户号失败,借款人用户ID:["+apicron.getUserId()+"]");
			return null;
		}
		// 借款人用户ID
		String borrowUserAccountId = borrowUserAccount.getAccountId();
		// 调用满标自动放款查询
		BankCallBean bean = new BankCallBean();
		// 版本号
		bean.setVersion(BankCallConstant.VERSION_10);
		// 交易代码
		bean.setTxCode(BankCallConstant.TXCODE_AUTOLEND_PAY_QUERY);
		// 渠道
		bean.setChannel(channel);
		// 交易日期
		bean.setTxDate(txDate);
		// 交易时间
		bean.setTxTime(txTime);
		// 流水号
		bean.setSeqNo(seqNo);
		// 借款人电子账号
		bean.setAccountId(borrowUserAccountId);
		// 申请订单号(满标放款交易订单号)
		bean.setLendPayOrderId(orderId);
		// 标的编号
		bean.setProductId(apicron.getBorrowNid());
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
		bean.setLogUserId(String.valueOf(apicron.getUserId()));
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(apicron.getUserId()));
		bean.setLogRemark("满标自动放款查询");
		BankCallBean queryResult = BankCallUtils.callApiBg(bean);
		return queryResult;
	}
	
	private void sendSmsForManager(String borrowNid) {
		// 管理员发送成功短信
		Map<String, String> replaceStrs = new HashMap<String, String>();
		replaceStrs.put("val_title", borrowNid);
		SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null, MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_FANGKUAN_SHIBAI, CustomConstants.CHANNEL_TYPE_NORMAL);

        try {
			commonProducer.messageSend(
                    new MessageContent(MQConstant.SMS_CODE_TOPIC, borrowNid, smsMessage));
        } catch (MQException e) {
        	logger.error("短信发送失败...", e);
        }

	}
	

	/**
	 * 取出账户信息
	 *
	 * @param userId
	 * @return
	 */
	public Account getAccountByUserId(Integer userId) {
		AccountExample accountExample = new AccountExample();
		accountExample.createCriteria().andUserIdEqualTo(userId);
		List<Account> listAccount = this.accountMapper.selectByExample(accountExample);
		if (listAccount != null && listAccount.size() > 0) {
			return listAccount.get(0);
		}
		return null;
	}
    
    
	/**
	 * 发送消息
	 * @param topic
	 * @param apiCron
	 */
    public void sendMessage(String topic, BorrowApicron apiCron){
    	
        try {
			commonProducer.messageSendDelay(
			        new MessageContent(topic, apiCron.getBorrowNid(), apiCron), 2);
			
		} catch (MQException e) {
			logger.error(e.getMessage());
		}
    }
	

}
