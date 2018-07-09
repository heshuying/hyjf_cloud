package com.hyjf.cs.message.mq;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.message.CsMessageApplication;
import com.hyjf.cs.message.mq.base.MessageContent;
import com.hyjf.cs.message.mq.producer.SmsProducer;

/**
 * @author xiasq
 * @version SmsTest, v0.1 2018/5/11 16:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsMessageApplication.class)
public class SmsTest {

    @Autowired SmsProducer smsProducer;

    @Test
    public void sendSms() throws MQException {
        Map<String, String> param = new HashMap<String, String>();
        param.put("val_code", "123456");

        SmsMessage smsMessage = new SmsMessage(null, param, "15311062331", null, MessageConstant.SMS_SEND_FOR_MOBILE, null,
                "TPL_ZHUCE", CustomConstants.CHANNEL_TYPE_NORMAL);

        // 发送
        smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(smsMessage)));
    }
}
