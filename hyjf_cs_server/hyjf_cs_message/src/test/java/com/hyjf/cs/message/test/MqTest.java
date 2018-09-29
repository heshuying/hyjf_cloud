package com.hyjf.cs.message.test;

import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.CsMessageApplication;
import com.hyjf.cs.message.mq.producer.MailProducer;
import com.hyjf.cs.message.service.HjhPlanCapitalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author xiasq
 * @version MqTest, v0.1 2018/5/8 16:31
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsMessageApplication.class)
public class MqTest {

    @Autowired
    private MailProducer mailProducer;
    @Autowired
    private HjhPlanCapitalService hjhPlanCapitalService;

    @Test
    public void sendSmscode() throws MQException {


        //MailMessage mailMessage = new MailMessage(399044, null, "邮件主题aaa", "12345,上山打老虎",  null, null, null, MessageConstant.MAIL_SEND_FOR_USER);

        // 发送
        //mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(mailMessage)));
        // 取得当前日期为基准日期
        Date nowDate = GetDate.stringToDate2(GetDate.dateToString2(new Date()));
        Date yDate = GetDate.countDate(nowDate, 5, -1);
        hjhPlanCapitalService.getPlanCapitalForActList(yDate);

    }

}
