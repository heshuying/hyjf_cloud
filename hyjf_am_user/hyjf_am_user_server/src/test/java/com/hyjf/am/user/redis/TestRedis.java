package com.hyjf.am.user.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PathVariable;

import com.hyjf.am.user.AmUserApplication;

import java.util.Arrays;
import java.util.List;

/**
 * @author xiasq
 * @version TestDao, v0.1 2018/4/19 9:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmUserApplication.class)
public class TestRedis {
    Logger logger = LoggerFactory.getLogger(TestRedis.class);

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    StringRedisUtil stringRedisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testStringRedisWrite(){
        stringRedisUtil.set("key","value");
    }

    @Test
    public void testStringRedisRead(){
        String value = stringRedisUtil.get("key");
        logger.info("value: {}", value);
    }

    @Test
    public void testObjectRedisWrite(){
        redisUtils.set("key", Arrays.asList("xiasq","xiaom","xiaoh"));
    }

    @Test
    public void testObjectRedisRead(){
        List<String> list = (List<String>) redisUtils.get("key");
        logger.info("value: {}", list);
    }

    @Test
    public void testBaseRedisOpertator(){
        redisTemplate.opsForValue().set("base","testValue1");
        Object value = redisTemplate.opsForValue().get("base");
        logger.info("testBaseRedisOpertator value is :{}", value.toString());
    }

}
