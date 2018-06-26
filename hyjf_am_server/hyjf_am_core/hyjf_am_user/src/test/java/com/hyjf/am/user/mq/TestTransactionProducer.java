package com.hyjf.am.user.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.user.AmUserApplication;
import com.hyjf.am.user.dao.mapper.auto.UserLogMapper;
import com.hyjf.am.user.dao.model.auto.UserLog;
import com.hyjf.am.user.service.UserService;
import com.hyjf.common.exception.MQException;

/**
 * @author xiasq
 * @version TestTransactionProducer, v0.1 2018/6/26 14:57
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmUserApplication.class)
public class TestTransactionProducer {
	private static final Logger logger = LoggerFactory.getLogger(TestTransactionProducer.class);

	@Autowired
	UserTProducer userTProducer;

	@Autowired
    UserLogMapper userLogMapper;

	@Autowired
	UserService userService;

	@Test
	@Transactional(rollbackFor = Exception.class)
	public void testCommintTransaction() throws MQException {
		logger.info("testCommintTransaction begin...");



        RegisterUserRequest r1 = new RegisterUserRequest();
        r1.setInstCode("1000001");
        r1.setLoginIp("127.0.0.1");
        r1.setMobile("15311062222");
        r1.setPassword("123");
        r1.setPlatform("1");
        r1.setSmsCode("123123");
        userService.register(r1);
        logger.info("第4步，save user_auth...");

		userTProducer.messageSend(new TransactionProducer.MassageContent("topic", JSON.toJSONBytes("xiasq")));
		logger.info("第二步，发送mq消息...");

		if (true) {
            logger.info("第三步，抛异常...");
			throw new RuntimeException("ex..");
		}

        UserLog u1 = new UserLog();
        u1.setUserId(123);
        userLogMapper.insert(u1);
        logger.info("第一步，插入user log....");
	}

	@Test
	public void testRollbackTransaction() {

	}
}
