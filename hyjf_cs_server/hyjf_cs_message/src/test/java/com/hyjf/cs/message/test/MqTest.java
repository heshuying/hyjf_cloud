package com.hyjf.cs.message.test;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.message.CsMessageApplication;
import com.hyjf.cs.message.mq.base.MessageContent;
import com.hyjf.cs.message.mq.producer.MailProducer;

/**
 * @author xiasq
 * @version MqTest, v0.1 2018/5/8 16:31
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsMessageApplication.class)
public class MqTest {

    @Autowired
    private MailProducer mailProducer;

    @Test
    public void sendSmscode() throws MQException {


        MailMessage mailMessage = new MailMessage(399044, null, "邮件主题aaa", "12345,上山打老虎",  null, null, null, MessageConstant.MAIL_SEND_FOR_USER);

        // 发送
        mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(mailMessage)));

    }

}
