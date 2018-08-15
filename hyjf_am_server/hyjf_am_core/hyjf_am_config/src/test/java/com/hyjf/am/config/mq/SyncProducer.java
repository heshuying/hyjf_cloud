package com.hyjf.am.config.mq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class SyncProducer {

	public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
		DefaultMQProducer producer = new DefaultMQProducer("TEST_GROUP");
		producer.setNamesrvAddr("47.104.250.73:9876;47.104.250.28:9876");
//		producer.setNamesrvAddr("47.104.250.73:9876");
//		producer.setNamesrvAddr("123.56.216.212:5555");
		
		
//		producer.
		
        //Launch the instance.	
        producer.start();
        for (int i = 0; i < 100; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TEST_TOPIC" /* Topic */,
                "TagA" /* Tag */,
                ("Hello RocketMQ " +
                    i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
