package com.hyjf.cs.message.handle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyjf.cs.message.CsMessageApplication;
import com.hyjf.cs.message.handler.SmsHandler;

/**
 * @author xiasq
 * @version SmsHandlerTest, v0.1 2019/1/9 11:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsMessageApplication.class)
public class SmsHandlerTest {

	@Autowired
	private SmsHandler smsHandler;

	@Test
	public void sendTest() throws Exception {
		smsHandler.sendMessage("15311062331", "一花一世界", "", "", "normal");
	}
}
