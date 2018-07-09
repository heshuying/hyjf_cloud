package com.hyjf.am.config.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyjf.am.config.AmConfigApplication;
import com.hyjf.am.config.service.SiteSettingService;

/**
 * @author dxj
 * @version TestTransaction, v0.1 2018/4/18 20:16
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmConfigApplication.class)
public class TestTransaction {
    Logger logger = LoggerFactory.getLogger(TestTransaction.class);
    
    @Autowired
    SiteSettingService siteSettingService;

    @Test
    public void test1() throws Exception{
//        siteSettingService.xxupdateTest1();
//        
//        logger.info("ok");
    }
}
