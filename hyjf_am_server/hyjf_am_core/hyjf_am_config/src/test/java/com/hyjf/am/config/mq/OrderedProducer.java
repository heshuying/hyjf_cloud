package com.hyjf.am.config.mq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

public class OrderedProducer {

	public static void main(String[] args) throws Exception {
		// Instantiate with a producer group name.
		DefaultMQProducer producer = new DefaultMQProducer("TEST_GROUP");
		producer.setNamesrvAddr("47.104.250.73:9876,47.104.250.28:9876");
		
		
		// Launch the instance.
		producer.start();
		
		String[] tags = new String[] { "TagA", "TagB", "TagC", "TagD", "TagE" };
		
		
		int orderId = 123123123;
		
		// Create a message instance, specifying topic, tag and message body.
		Message msg = new Message("TEST_TOPIC", "TagC" , "KEY" + 123, ("C Hello RocketMQ 大婶 还款 ！@@ " + 1221).getBytes(RemotingHelper.DEFAULT_CHARSET));
		
		SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
			@Override
			public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
				Integer id = (Integer) arg;
				int index = id % mqs.size();
				System.out.println("订单："+id+" 当前index:"+index);
				return mqs.get(index);
			}
		}, orderId);
		

		System.out.printf("%s%n", sendResult);
		
		
		
		// server shutdown
		producer.shutdown();
	}
}
