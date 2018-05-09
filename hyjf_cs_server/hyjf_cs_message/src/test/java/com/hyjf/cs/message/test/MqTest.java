package com.hyjf.cs.message.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.message.CsMessageApplication;
import com.hyjf.cs.message.mq.MailProducer;
import com.hyjf.cs.message.mq.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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


        MailMessage mailMessage = new MailMessage(399044, null, "邮件主题aaa", "12345,上山打老虎",  null, null, null, MessageConstant.MAILSENDFORUSER);

        // 发送
        mailProducer.messageSend(new Producer.MassageContent(MQConstant.MAIL_TOPIC, JSON.toJSONBytes(mailMessage)));

    }

}
