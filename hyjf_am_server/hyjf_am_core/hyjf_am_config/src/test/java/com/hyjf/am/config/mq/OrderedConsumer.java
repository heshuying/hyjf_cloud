package com.hyjf.am.config.mq;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

public class OrderedConsumer {

	public static void main(String[] args) throws Exception {
		
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TEST_GROUP3");
        
        consumer.setNamesrvAddr("123.56.216.212:5555");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.subscribe("TEST_TOPIC", "*");

        consumer.registerMessageListener(new MessageListenerOrderly() {

//            AtomicLong consumeTimes = new AtomicLong(0);
            
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                       ConsumeOrderlyContext context) {
//                context.setAutoCommit(false);
                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs.size() + "%n");
                System.out.printf("--> " + new String(msgs.get(0).getBody()) + "%n");
                
//                this.consumeTimes.incrementAndGet();
                
                
//                if ((this.consumeTimes.get() % 2) == 0) {
//                    return ConsumeOrderlyStatus.SUCCESS;
//                } else if ((this.consumeTimes.get() % 3) == 0) {
//                    return ConsumeOrderlyStatus.ROLLBACK;
//                } else if ((this.consumeTimes.get() % 4) == 0) {
//                    return ConsumeOrderlyStatus.COMMIT;
//                } else if ((this.consumeTimes.get() % 5) == 0) {
//                    context.setSuspendCurrentQueueTimeMillis(3000);
//                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
//                }
                
                return ConsumeOrderlyStatus.SUCCESS;

            }
        });
        
        


        consumer.registerMessageListener(new MessageListenerConcurrently() {

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				System.out.println(msgs.get(0).getTags()+"  "+new String(msgs.get(0).getBody()));
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
        	
        });
        
        

        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
