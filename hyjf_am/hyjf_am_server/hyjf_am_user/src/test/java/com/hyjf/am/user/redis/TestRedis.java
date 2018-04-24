package com.hyjf.am.user.redis;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyjf.am.user.AmUserApplication;

/**
 * @author xiasq
 * @version TestDao, v0.1 2018/4/19 9:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmUserApplication.class)
public class TestRedis {
	Logger logger = LoggerFactory.getLogger(TestRedis.class);

	@Autowired
    RedisUtil redisUtil;

	@Autowired
	StringRedisUtil stringRedisUtil;

	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void testStringRedisWrite() {
		stringRedisUtil.set("key", "value");
	}

	@Test
	public void testStringRedisRead() {
		String value = stringRedisUtil.get("key");
		logger.info("value: {}", value);
	}

	@Test
	public void testObjectRedisWrite() {
		redisUtil.set("key", Arrays.asList("xiasq", "xiaom", "xiaoh"));
	}

	@Test
	public void testObjectRedisRead() {
		Object object = redisUtil.get("key");
		logger.info("value: {}", object);
	}

	@Test
	public void testBaseRedisOpertator() {
		redisTemplate.opsForValue().set("base", "testValue1");
		Object value = redisTemplate.opsForValue().get("base");
		logger.info("testBaseRedisOpertator value is :{}", value.toString());
	}

}
