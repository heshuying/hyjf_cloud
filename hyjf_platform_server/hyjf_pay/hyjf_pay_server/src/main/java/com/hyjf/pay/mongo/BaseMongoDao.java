package com.hyjf.pay.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author xiasq
 * @version BaseMongoDao, v0.1 2018/4/18 15:22
 */
public class BaseMongoDao {
    private Logger logger = LoggerFactory.getLogger(BaseMongoDao.class);

    @Autowired
    protected MongoTemplate mongoTemplate;
}
