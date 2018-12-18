package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.batch.HjhSmsNoticeService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 逾期還款短信提醒
 * @author jun 20180626
 */
@Service
public class HjhSmsNoticeServiceImpl extends BaseTradeServiceImpl implements HjhSmsNoticeService {

	private static final Logger logger = LoggerFactory.getLogger(HjhSmsNoticeServiceImpl.class);

	@Autowired
	private CommonProducer commonProducer;

	/**
	 * 逾期还款标的短信通知
	 */
	@Override
	public void overdueSmsNotice() {
		logger.debug("-------------------逾期未还款标的统计发短信任务开始------------------");
		List<BorrowAndInfoVO> borrowList = amTradeClient.selectOverdueBorrowList();
		if (CollectionUtils.isEmpty(borrowList)) {
			logger.debug("当前没有逾期的标的信息");
			return;
		}

		for (BorrowAndInfoVO record : borrowList) {
			logger.debug("逾期未还款标的：" + record.getBorrowNid());

			try {
				this.sendSmsForManager(record.getBorrowNid(), record.getUserId());
			} catch (MQException e) {
				logger.error("逾期未还款标的统计发短信任务执行失败", e);
			}

		}

		logger.debug("-------------------逾期未还款标的统计发短信任务结束------------------");
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
		commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
	}

}
