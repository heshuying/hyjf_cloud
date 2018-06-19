/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.common.GetOrderIdUtils;
import com.hyjf.am.trade.dao.mapper.auto.BorrowApicronMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.BorrowApicronExample;
import com.hyjf.am.trade.mq.BorrowLoanRepayProducer;
import com.hyjf.am.trade.mq.Producer;
import com.hyjf.am.trade.mq.SmsProducer;
import com.hyjf.am.trade.service.task.BorrowLoanRepayToMQService;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

/**
 * @author dxj
 * @version BorrowLoanRepayToMQServiceImpl.java, v0.1 2018年6月19日 上午11:27:25
 */
@Service
public class BorrowLoanRepayToMQServiceImpl implements BorrowLoanRepayToMQService {
	

    private Logger _log = LoggerFactory.getLogger(BorrowLoanRepayToMQServiceImpl.class);
	
	/** 任务类别（1：还款） */
	private static final Integer TASK_REPAY_TYPE = 1;
	/**任务类别（0：放款） */
	private static final Integer TASK_LOAN_TYPE = 0;

    @Autowired
    protected BorrowApicronMapper borrowApicronMapper;
    
    @Autowired
    private SmsProducer smsProducer;
    
    @Autowired
    private BorrowLoanRepayProducer borrowLoanRepayProducer;
    
    
	
    public void sendMessage(String key,Object message){
    	
        try {
			borrowLoanRepayProducer.messageSend(
			        new Producer.MassageContent(key, JSON.toJSONBytes(message)));
			
		} catch (MQException e) {
			e.printStackTrace();
		}
    }

	/**
	 * 对放款、还款任务进行分派
	 *
	 * @return
	 */
    @Override
	public void taskAssign() {
		
        // 还款请求件数
        int repayRequestCount = 0;
		_log.info("自动放/还款消息推送任务开始。");
		try {
			// 取得直投类未还款任务
			List<BorrowApicron> listZhitouApicron = getBorrowApicronList();
			// 如果当前是否存在放款或还款任务
			if(listZhitouApicron == null || listZhitouApicron.size() == 0){
				return;
			}
			// 循环进行将还款任务压入队列中还款
			for (BorrowApicron apicron : listZhitouApicron) {
				_log.info("任务缓存:项目编号:[" + apicron.getBorrowNid() + "].计划编号:[" + (StringUtils.isEmpty(apicron.getPlanNid()) ? "" : apicron.getPlanNid()) + "].");
				
				Integer status = apicron.getStatus();
				// 放、还款请求
				if(status == CustomConstants.BANK_BATCH_STATUS_SENDING || status == CustomConstants.BANK_BATCH_STATUS_SEND_FAIL){
					if(TASK_REPAY_TYPE == apicron.getApiType()){
						_log.info("发起还款消息通知:还款项目编号:[" + apicron.getBorrowNid() + ",还款期数:[第" + apicron.getPeriodNow() + "],计划编号:[" + (StringUtils.isEmpty(apicron.getPlanNid()) ? "" : apicron.getPlanNid()));
						// 还款请求
						
						sendMessage(MQConstant.BORROW_REPAY_REQUEST_TOPIC, apicron);
                        repayRequestCount++;
                        _log.info("发起还款请求总数:[" + repayRequestCount + "].");
                    }else if(TASK_LOAN_TYPE == apicron.getApiType() && StringUtils.isBlank(apicron.getPlanNid())){//直投放款请求
                    	_log.info("------------------标的:" + apicron.getBorrowNid() + ",发送实时放款请求~------------------------");
                    	sendMessage(MQConstant.BORROW_REALTIMELOAN_ZT_REQUEST_TOPIC, apicron);
                    }else if(TASK_LOAN_TYPE == apicron.getApiType() && StringUtils.isNotBlank(apicron.getPlanNid())){//计划放款请求
                    	_log.info("------------------标的:" + apicron.getBorrowNid() + ",发送实时放款请求~------------------------");
                    	sendMessage(MQConstant.BORROW_REALTIMELOAN_PLAN_REQUEST_TOPIC, apicron);
                    }
				}else if(status == CustomConstants.BANK_BATCH_STATUS_SENDED || status == CustomConstants.BANK_BATCH_STATUS_VERIFY_SUCCESS
						|| status == CustomConstants.BANK_BATCH_STATUS_PART_FAIL){
					// 放、还款任务
					if(TASK_REPAY_TYPE == apicron.getApiType() && StringUtils.isBlank(apicron.getPlanNid())){
						// 直投类还款
						sendMessage(MQConstant.BORROW_REPAY_ZT_RESULT_TOPIC, apicron);
					}else if(TASK_REPAY_TYPE == apicron.getApiType() && StringUtils.isNotBlank(apicron.getPlanNid())){
						// 计划类还款
						sendMessage(MQConstant.BORROW_REPAY_PLAN_RESULT_TOPIC, apicron);
					}
				}else if(status == CustomConstants.BANK_BATCH_STATUS_FAIL){
					//放款部分成功或放款失败异常处理最终版
					if (TASK_LOAN_TYPE == apicron.getApiType()) {
						//直投类异常修复
						boolean flag = updateBatchFailLoan(apicron);
						if (flag) {
							_log.info("------------开始修复标的号:" + apicron.getBorrowNid() + ",放款异常!计划编号:" + apicron.getPlanNid());
							if (StringUtils.isBlank(apicron.getPlanNid())) {//直投标的
								sendMessage(MQConstant.BORROW_REALTIMELOAN_ZT_REQUEST_TOPIC, apicron);
							}else if(StringUtils.isNotBlank(apicron.getPlanNid())){//计划标的
								sendMessage(MQConstant.BORROW_REALTIMELOAN_PLAN_REQUEST_TOPIC, apicron);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				_log.info("---------" + borrowNid + "放款失败异常修复次数超出3次,请联系开发人员!---------");
				return false;
			}
			String bankSeqNo = apicron.getBankSeqNo();
			BankCallBean batchResult = batchQuery(apicron);
			if (Validator.isNull(batchResult)) {
				throw new Exception("放款状态查询失败！[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
			}
			_log.info("标的编号："+ borrowNid +"，批次查询成功了！");
			apicron.setStatus(1);
			this.borrowApicronMapper.updateByPrimaryKey(apicron);
			return true;
		} catch (Exception e) {
			apicron.setFailTimes(failTimes + 1);
			this.borrowApicronMapper.updateByPrimaryKey(apicron);
			_log.info("放款失败处理异常:" + borrowNid + e.getMessage());
			//TODO 发送放款失败的短信
			sendSmsForManager(borrowNid);
		}
		return false;
	}
	
	private BankCallBean batchQuery(BorrowApicron apicron) {
		//开始处理
		String orderId = apicron.getOrdid();
		_log.info("标的号:" + apicron.getBorrowNid() + "开始查询批次结果,orderId is :" + orderId);
		String channel = BankCallConstant.CHANNEL_PC;
		String txDate = GetOrderIdUtils.getTxDate();
		String txTime = GetOrderIdUtils.getTxTime();
		String seqNo = GetOrderIdUtils.getSeqNo(6);
		// 根据借款人用户ID查询借款人用户电子账户号
		
		// TODO: 电子账户从资金表放
//		BankOpenAccount borrowUserAccount = this.getBankOpenAccount(apicron.getUserId());
//		if(borrowUserAccount == null || StringUtils.isEmpty(borrowUserAccount.getAccount())){
//			_log.info("根据借款人用户ID查询借款人电子账户号失败,借款人用户ID:["+apicron.getUserId()+"]");
//			return null;
//		}
		// 借款人用户ID
		String borrowUserAccountId = "TODO";
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
		if (Validator.isNotNull(queryResult)) {
			String retCode = StringUtils.isNotBlank(queryResult.getRetCode()) ? queryResult.getRetCode() : "";
			if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
				return queryResult;
			}
		} 
		return null;
	}
	
	private void sendSmsForManager(String borrowNid) {
		// 管理员发送成功短信
		Map<String, String> replaceStrs = new HashMap<String, String>();
		replaceStrs.put("val_title", borrowNid);
		SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null, MessageConstant.SMSSENDFORMANAGER, null, CustomConstants.PARAM_TPL_FANGKUAN_SHIBAI, CustomConstants.CHANNEL_TYPE_NORMAL);

        try {
            smsProducer.messageSend(
                    new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(smsMessage)));
        } catch (MQException e) {
        	_log.error("短信发送失败...", e);
        }

	}
	

	
	

}
