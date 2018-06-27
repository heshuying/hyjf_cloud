package com.hyjf.am.trade.mq;

import com.hyjf.am.trade.AmTradeApplication;
import com.hyjf.am.trade.mq.transactionmq.TransactionProducer;
import com.hyjf.am.trade.mq.transactionmq.UserTProducer;
import com.hyjf.am.trade.service.UserService;
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
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.common.exception.MQException;

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
	UserTProducer userTProducer;

	@Autowired
	UserService userService;

	@Test
	public void testCommintTransaction() throws MQException {
		logger.info("testCommintTransaction begin...");

		userTProducer.messageSend(new TransactionProducer.MassageContent("userTransationTest", JSON.toJSONBytes("25")));
		logger.info("第2步，发送mq消息...");

		// if (true) {
		// logger.info("第三步，抛异常...");
		// throw new RuntimeException("ex..");
		// }

		// UserLog u1 = new UserLog();
		// u1.setUserId(123);
		// userLogMapper.insert(u1);
		// logger.info("第3步，插入user log....");
	}

	@Test
	public void testRollbackTransaction() {

	}
}
