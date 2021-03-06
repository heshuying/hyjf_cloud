package com.hyjf.cs.user.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.CsUserApplication;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @author xiasq
 * @version TestMQ, v0.1 2018/4/19 11:35
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsUserApplication.class)
public class TestMQ {
	@Autowired
    CommonProducer commonProducer;

	@Test
	@Deprecated  // 过时方法 具体调用参照 SmsCodeServiceImpl
	public void sendSmscode() throws MQException {

		JSONObject params = new JSONObject();
		params.put("checkCode", "1234");
		params.put("validCodeType", "aa");
		params.put("mobile", "15311112222");

		// 发送
		commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), params));

	}

}
