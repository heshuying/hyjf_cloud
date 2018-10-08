package com.hyjf.pay.mongo;

import com.hyjf.pay.PayApplication;
import com.hyjf.pay.entity.BankExclusiveLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    BankExclusiveLogDao dao;
    
    @Value("${hyjf.arrayProps}")
    private String[] atests;

    @Test
    public void testMongo(){
    	
    	Query query = new Query();
        Criteria criteria = Criteria.where("ordid").is("111");
        query.addCriteria(criteria);
        
        BankExclusiveLog log = new BankExclusiveLog();
        
        log.setChannel("teste");
        log.setCmdid("sdf");
        log.setOrdid("111");
        
//        dao.insert(log);
        
        BankExclusiveLog oneEN = dao.findOne(query);
        
        logger.info(oneEN.getId()+"    "+oneEN.getCmdid());
        
        Query query2 = new Query();
        Update update = new Update();
        query2.addCriteria(Criteria.where("_id").is(oneEN.getId()));
//        query2.limit(4);
//        query2.limit(1);
        
        update.set("cmdid", "jjjjjjjjjjAAAAAAAAAAAAAAAAAAAAAAAAA").addToSet("aaa", 12).set("bbbb", "sdfsadf ");
        
        
        dao.findAndModify(query2, update);
        
        logger.info(oneEN.getCmdid());
    }

    @Test
    public void testProps(){
    	String[] tests = atests;
    	for (String aa : tests) {
        	System.out.println(aa);
		}
    }
}
