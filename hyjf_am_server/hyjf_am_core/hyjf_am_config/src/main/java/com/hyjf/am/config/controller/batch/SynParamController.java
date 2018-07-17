/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.batch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.mq.base.MessageContent;
import com.hyjf.am.config.mq.producer.TestProducer;
import com.hyjf.am.config.service.SiteSettingService;
import com.hyjf.am.config.service.SynParamService;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.BaseVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.constants.MQConstant;

/**
 * @author dxj
 * @version SynParamController.java, v0.1 2018年6月13日 上午11:24:09
 */
@RestController
@RequestMapping("/am-config/sync")
public class SynParamController extends BaseConfigController {
	
    @Autowired
    private SynParamService synParamService;
    
    @Autowired
    SiteSettingService siteSettingService;
    
    @Autowired
    TestProducer testProducer;
    
    private static AtomicInteger painte = new AtomicInteger();

    
    /**
     *  同步param到redis
     * @return
     */
    @GetMapping("/synParam")
    public Response<BaseVO> synParam(){
    	Response<BaseVO>  response = new Response<BaseVO>();
    	
    	synParamService.synParam();
    	
        return response;
    }

    
    /**
     *  测试事务
     * @return
     * @throws Exception 
     */
    @GetMapping("/testTranaction")
    public Response<BaseVO> synParam2(){
        Response<BaseVO>  response = new Response<BaseVO>();
        
        try {
            siteSettingService.updateTest1();
        } catch (Exception e) {
             e.printStackTrace();
        }
        
        return response;
    }

    
    /**
     *  测试生产消息
     * @return
     * @throws Exception 
     */
    @GetMapping("/testMq")
    public Response<BaseVO> synParam3(){
        Response<BaseVO>  response = new Response<BaseVO>();
        
       
        for (int i = 0; i < 5; i++) {
            Map<String, String> maps = new HashMap<String, String>();
            int p1 = painte.incrementAndGet();
            maps.put("hehe", "sdf"+ p1);
            maps.put("uu", "僵死sdf"+ p1);
            MessageContent message = new MessageContent(MQConstant.TEST_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(maps));
            
//            try {
////                testProducer2.messageSend(message);
////                testProducer2
//            } catch (MQException e) {
//                 e.printStackTrace();
//            }
            
        }
        
        return response;
    }

    
    /**
     *  测试生产消息
     * @return
     * @throws Exception 
     */
    @GetMapping("/testRedis")
    public Map testRedis(){
    	
    	return CacheUtil.getParamNameMap("ACCOUNT_STATUS");
    }
    
}
