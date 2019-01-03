package com.hyjf.cs.message.handle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyjf.cs.message.CsMessageApplication;

/**
 * @author xiasq
 * @version MsgPushHandlerTest, v0.1 2019/1/3 10:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsMessageApplication.class)
public class MsgPushHandlerTest {
    @Autowired
    private MsgPushHandle msgPushHandle;


    @Test
    public void sendTest(){

        msgPushHandle.sendMessages("5bd03bbdef9d26416c6e8eb4");
    }


}
