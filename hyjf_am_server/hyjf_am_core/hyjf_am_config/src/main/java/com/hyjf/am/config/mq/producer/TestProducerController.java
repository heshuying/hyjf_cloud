package com.hyjf.am.config.mq.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.mq.base.CommonProducer;
import com.hyjf.am.config.mq.base.MessageContent;
import com.hyjf.common.exception.MQException;

/**
 * 未来会删除，测试用
 * @author dxj
 * @date 2018/07/06
 */
@RestController
public class TestProducerController {
	private static final Logger logger = LoggerFactory.getLogger(TestProducerController.class);
	
	@Autowired
    private RocketMQTemplate rocketMQTemplate;
	
	@Autowired
    private CommonProducer commonProducer;
	
	

//	@Override
//	protected ProducerFieldsWrapper getFieldsWrapper() {
//		ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
//		wrapper.setGroup(MQConstant.TEST_GROUP);
//		wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
//		return wrapper;
//	}
//
//	@Override
//	public boolean messageSend(MessageContent messageContent) throws MQException {
//		return super.messageSend(messageContent);
//	}
	
	@RequestMapping("/sendSyncM1")
	public Object sendSyncM1(String mqTopic) {
		
		if(StringUtils.isEmpty(mqTopic)) {
			mqTopic = "TEST_TOPIC";
		}
		
		MessageContent messageContent = new MessageContent(mqTopic,UUID.randomUUID().toString(),"123213",9999999);
		
		try {
			return commonProducer.messageSend(messageContent);
		} catch (MQException e) {
			logger.error(e.getMessage());
		}
		return messageContent;
		
	}
	
	@RequestMapping("/sendSyncM2")
	public Object sendSyncM2(String mqTopic) {
		
		if(StringUtils.isEmpty(mqTopic)) {
			mqTopic = "TEST_TOPIC2";
		}
		
		SendResult sendResult = rocketMQTemplate.syncSend(mqTopic, "Hello, World22222222222!");
		System.out.printf("syncSend2 to topic %s sendResult=%s %n", mqTopic, sendResult);
		
		return sendResult;
	}
	
	@RequestMapping("/sendSyncM3")
	public Object sendSyncM3(String mqTopic) {
		
		if(StringUtils.isEmpty(mqTopic)) {
			mqTopic = "TEST_TOPIC";
		}
		
		Map<String, Object> mkap = new HashMap<String, Object>();
		mkap.put("aa", 11);mkap.put("222", "我是测试消息");
		
		SendResult sendResult = rocketMQTemplate.syncSend(mqTopic, mkap);
		System.out.printf("syncSend2 to topic %s sendResult=%s %n", mqTopic, sendResult);
		
		return sendResult;
	}
	
	@RequestMapping("/sendSyncM4")
	public Object sendSyncM4(String mqTopic) {
		
		if(StringUtils.isEmpty(mqTopic)) {
			mqTopic = "TEST_TOPIC";
		}
		
		Map<String, Object> mkap = new HashMap<String, Object>();
		mkap.put("aa", 11);mkap.put("4444", "我是测试消息");
		
		 rocketMQTemplate.convertAndSend(mqTopic, mkap);
		System.out.printf("syncSend2 to topic %s ", mqTopic);
		
		return "OK";
	}

	@RequestMapping("/send")
	public void send() {
		rocketMQTemplate.syncSend("BROADCAST_TEST_TOPIC", "Hello, World!11111111");
	}
}
