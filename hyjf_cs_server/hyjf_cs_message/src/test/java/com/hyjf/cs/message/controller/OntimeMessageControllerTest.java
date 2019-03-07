package com.hyjf.cs.message.controller;

import com.hyjf.cs.message.CsMessageApplication;
import com.hyjf.cs.message.bean.mc.SmsOntime;
import com.hyjf.cs.message.service.message.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author xiasq
 * @version OntimeMessageControllerTest, v0.1 2018/12/27 11:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsMessageApplication.class)
public class OntimeMessageControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(OntimeMessageControllerTest.class);
    private static final Integer STATUS_WAIT = 0;

    @Autowired
    private MessageService messageService;

    @Test
    public void testOntimeSms(){
        List<SmsOntime> smsOntimeList = messageService.getOntimeList(STATUS_WAIT);
        if (!CollectionUtils.isEmpty(smsOntimeList)) {
            for (SmsOntime smsOntime : smsOntimeList) {
                try {
                    messageService.sendMessage(smsOntime);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }


}
