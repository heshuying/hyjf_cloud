package com.hyjf.am.config.dao;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.AmConfigApplication;
import com.hyjf.am.config.dao.mapper.auto.GatewayApiConfigMapper;
import com.hyjf.am.config.dao.model.auto.GatewayApiConfig;
import com.hyjf.am.config.dao.model.auto.GatewayApiConfigExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author xiasq
 * @version TestDao, v0.1 2018/4/18 20:16
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmConfigApplication.class)
public class TestDao {
    Logger logger = LoggerFactory.getLogger(TestDao.class);
    @Autowired
    GatewayApiConfigMapper mapper;

    @Test
    public void testDao(){
        List<GatewayApiConfig> list = mapper.selectByExample(new GatewayApiConfigExample());
        logger.info("list is : {}", JSONObject.toJSONString(list));
    }
}
