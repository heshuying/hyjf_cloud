package com.hyjf.cs.user.mq;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.CsUserApplication;


/**
 * 
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsUserApplication.class)
public class TestRedis {
	
	
	@Test
	public void saveAllParamToRedis() throws MQException {

		String key = "hyjf_param_name:ACCOUNT_STATUS";
		Map<String,String> hash = new HashMap<String,String>();
		
		hash.put("0", "未开户");
		hash.put("1", "已开户");
		hash.put("12", "已开户11");
		
		
		RedisUtils.hmset(key, hash);
		hash.put("13", "已开户12");
		hash.put("14", "已开户1212");
		
		
		RedisUtils.hmset(key, hash);
		
		
		String val1 = RedisUtils.hget(key, "14");
		System.out.println(val1);
		
		Map<String,String> hash2 = RedisUtils.hgetall(key);
		
		for (Entry<String,String>  onekey : hash2.entrySet()) {
			System.out.println(onekey.getKey()+"  "+onekey.getValue());
		
		}
		

	}

}
