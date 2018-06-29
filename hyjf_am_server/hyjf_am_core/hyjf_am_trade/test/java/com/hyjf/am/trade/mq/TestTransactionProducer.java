package com.hyjf.am.trade.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.AmTradeApplication;
import com.hyjf.am.trade.mq.transactionmq.AccountTProducer;
import com.hyjf.am.trade.mq.transactionmq.TransactionProducer;
import com.hyjf.am.trade.service.UserService;
import com.hyjf.common.exception.MQException;

import java.util.UUID;

/**
 * @author xiasq
 * @version TestTransactionProducer, v0.1 2018/6/26 14:57
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmTradeApplication.class)
@Transactional(rollbackFor = Exception.class)
@Rollback(value = false)
public class TestTransactionProducer {
	private static final Logger logger = LoggerFactory.getLogger(TestTransactionProducer.class);

	@Autowired
	AccountTProducer accountTProducer;

	@Autowired
	UserService userService;

	@Test
	public void testCommintTransaction() throws MQException {
		logger.info("testCommintTransaction begin...");

		accountTProducer.messageSend(new TransactionProducer.MassageContent("userTransationTest", UUID.randomUUID().toString(),JSON.toJSONBytes("25")));
		logger.info("第2步，发送mq消息...");
	}

	@Test
	public void testRollbackTransaction() {

	}
}
