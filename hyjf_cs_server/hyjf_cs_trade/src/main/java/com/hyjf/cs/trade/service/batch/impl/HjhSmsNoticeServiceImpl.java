package com.hyjf.cs.trade.service.batch.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.client.HjhSmsNoticeServiceClient;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.producer.SmsProducer;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.batch.HjhSmsNoticeService;

/**
 * 逾期還款短信提醒
 * @author jun 20180626
 */
@Service
public class HjhSmsNoticeServiceImpl extends BaseTradeServiceImpl implements HjhSmsNoticeService {

	private static final Logger logger = LoggerFactory.getLogger(HjhSmsNoticeServiceImpl.class);

	@Autowired
	private HjhSmsNoticeServiceClient hjhSmsNoticeServiceClient;
	@Autowired
	private SmsProducer smsProducer;

	@Override
	public void overdueSmsNotice() {
		logger.info("-------------------逾期未还款标的统计发短信任务开始------------------");
		List<BorrowVO> borrowList = hjhSmsNoticeServiceClient.selectOverdueBorrowList();
		if (CollectionUtils.isEmpty(borrowList)) {
			logger.info("当前没有逾期的标的信息");
			return;
		}

		for (BorrowVO record : borrowList) {
			logger.info("逾期未还款标的：" + record.getBorrowNid());

			try {
				this.sendSmsForManager(record.getBorrowNid(), record.getUserId());
			} catch (MQException e) {
				logger.error("逾期未还款标的统计发短信任务执行失败", e);
			}

		}

		logger.info("-------------------逾期未还款标的统计发短信任务结束------------------");
	}

	/**
	 * 逾期还款发送短信
	 * 
	 * @param borrowNid
	 * @param userId
	 * @throws MQException
	 */
	private void sendSmsForManager(String borrowNid, Integer userId) throws MQException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("val_title", borrowNid);
		SmsMessage smsMessage = new SmsMessage(userId, param, null, null, MessageConstant.SMS_SEND_FOR_USER, null,
				CustomConstants.PARAM_TPL_NOTICE_BORROW_REPAY_OVERDUE, CustomConstants.CHANNEL_TYPE_NORMAL);
		smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
	}

}
