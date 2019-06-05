 package com.hyjf.am.market.mq.base;

 import com.hyjf.common.constants.MQConstant;

 import java.io.Serializable;

 /**
  * 队列消息
  * @author dxj
  * @date 2018/07/06
  */
 public class MessageContent implements Serializable {
     private static final long serialVersionUID = -6846413929342308237L;
     public final String topic;
     public final String tag;
     public final String keys;
     public final Object body;

     public MessageContent(String topic, String tag, String keys, Object body) {
         this.topic = topic;
         this.tag = tag;
         this.keys = keys;
         this.body = body;
     }

     public MessageContent(String topic, String keys, Object body) {
         this(topic, MQConstant.HYJF_DEFAULT_TAG, keys, body);
     }


 }