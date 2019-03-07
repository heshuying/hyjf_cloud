/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.batch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.mq.base.MessageContent;
import com.hyjf.am.config.service.SiteSettingService;
import com.hyjf.am.config.service.SynParamService;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.BaseVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

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
    
//    @Autowired
//    TestProducer testProducer;
    
    private static AtomicInteger painte = new AtomicInteger();
    


	public static JedisPool poolNew = RedisUtils.getPool();
	
	private static String appkeytempPath = "D:\\tmpp\\apprediskey.txt";

    
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
             logger.error(e.getMessage());
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
            MessageContent message = new MessageContent(MQConstant.TEST_TOPIC, UUID.randomUUID().toString(),maps);
            
//            try {
//                testProducer.messageSend(message);
//            } catch (MQException e) {
//                 logger.error(e.getMessage());
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

    
    /**
     *  测试生产消息
     * @return
     * @throws Exception 
     */
    @GetMapping("/testRedis2")
    public String testRedis2(){
    	
         scanALlKey();
		
		expireAppKey();
		
		return "OK";
    }
	

	
	/**
	 * 扫描所有app key，写到文件中
	 * @param key
	 * @param value
	 */
	private void scanALlKey(){

		Jedis jedis = poolNew.getResource();
		File appfilePath= new File(appkeytempPath);
		
		// 写入文件
		try {
			List<String> keys = new ArrayList<String>();
			FileUtils.writeLines(appfilePath, keys);
			logger.info("清除临时文件成功  "+appkeytempPath);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		
		String cursor = ScanParams.SCAN_POINTER_START;
		int total = 0;
		int apptotal = 0;
		ScanParams sp = new ScanParams();
//		sp.match("web");
		sp.count(100);
		
		do {
			ScanResult<String> sResult = jedis.scan(cursor,sp);
			
			cursor = sResult.getStringCursor();
			List<String> keys = sResult.getResult();
			List<String> appkeys = new ArrayList<String>();
			
			for (String key : keys) {
				total = total+1;
				if (StringUtils.startsWith(key, "web")) {
					apptotal = apptotal+1;
					appkeys.add(key);
				}
			}
			
			// 写入文件
			try {
				FileUtils.writeLines(appfilePath, appkeys, true);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			
		} while (!"0".equals(cursor));

		logger.info(total+" keys "+apptotal);
		jedis.close();
		
	}
	
	/**
	 * 批量设置有效期
	 * @param key
	 * @param value
	 */
	private void expireAppKey(){

		Jedis jedis = poolNew.getResource();
		File appfilePath= new File(appkeytempPath);
		int apptotal = 0;
		
		try {

			logger.info("开始遍历所有APPKEY  "+appkeytempPath);
			LineIterator ll = FileUtils.lineIterator(appfilePath);
			
			while (ll.hasNext()) {
				String key = (String) ll.next();
				long ttl = jedis.ttl(key);
				apptotal = apptotal+1;
				long result = jedis.expire(key, 60);
			}
			
			logger.info("processed keys "+apptotal);
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}
	}
    
}
