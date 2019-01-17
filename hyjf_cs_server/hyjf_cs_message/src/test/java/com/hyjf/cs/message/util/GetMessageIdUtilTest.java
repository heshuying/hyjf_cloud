package com.hyjf.cs.message.util;

import com.hyjf.cs.message.CsMessageApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiasq
 * @version GetMessageIdUtilTest, v0.1 2019/1/17 14:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsMessageApplication.class)
public class GetMessageIdUtilTest {

    @Test
    public void test(){
        String value = GetMessageIdUtil.getNewMsgCode("xiasq");
        System.out.println("value:" + value);
    }
}
