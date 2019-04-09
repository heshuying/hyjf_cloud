package com.hyjf.am.trade.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hyjf.am.AmAdminApplication;

/**
 * @author xiasq
 * @version TestTransactionProducer, v0.1 2018/6/26 14:57
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmAdminApplication.class)
@Transactional(rollbackFor = Exception.class)
@Rollback(value = false)
public class TestTransactionProducer {

	@Test
	public void test() {

	}
}
