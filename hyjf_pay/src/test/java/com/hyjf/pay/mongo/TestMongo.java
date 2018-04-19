package com.hyjf.pay.mongo;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.pay.PayApplication;
import com.hyjf.pay.bean.BankLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiasq
 * @version TestMongo, v0.1 2018/4/19 12:06
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayApplication.class)
public class TestMongo {
    Logger logger = LoggerFactory.getLogger(TestMongo.class);

    @Autowired
    BankLogMongoDao dao;

    @Test
    public void testMongo(){
        BankLog bankLog = new BankLog();
        bankLog.setUserId(1);
        bankLog.setIsbg(2);
        bankLog.setOrdid("1111111111111");
        dao.save(bankLog);
        BankLog bankLog1 = dao.getOneBeanById(1);
        logger.info("bankLog1: {}", JSONObject.toJSONString(bankLog1));
    }
}
