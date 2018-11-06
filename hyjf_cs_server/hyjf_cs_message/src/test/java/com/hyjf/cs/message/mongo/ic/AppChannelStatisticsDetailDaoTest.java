package com.hyjf.cs.message.mongo.ic;

import com.hyjf.cs.message.CsMessageApplication;
import com.hyjf.cs.message.bean.ic.AppChannelStatisticsDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author xiasq
 * @version AppChannelStatisticsDetailDaoTest, v0.1 2018/11/6 9:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsMessageApplication.class)
public class AppChannelStatisticsDetailDaoTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AppChannelStatisticsDetailDao appChannelStatisticsDetailDao;

    @Test
    public void testQuery(){
        Query query = new Query();
        logger.info("start query...");
        List<AppChannelStatisticsDetail> list = appChannelStatisticsDetailDao.find(query);

        logger.info("end query, list is {}", list);


    }
}

