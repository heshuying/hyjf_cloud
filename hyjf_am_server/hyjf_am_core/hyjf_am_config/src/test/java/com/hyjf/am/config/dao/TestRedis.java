package com.hyjf.am.config.dao;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.config.AmConfigApplication;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiasq
 * @version TestRedis, v0.1 2018/6/6 14:59
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmConfigApplication.class)
public class TestRedis {
	Logger logger = LoggerFactory.getLogger(TestDao.class);

	@Test
	public void testRedis() {
		String result = RedisUtils.set("key", "value");
		logger.info("result：" + result);
		String value = RedisUtils.get("key");
		logger.info("value:" + value);

		UserVO vo = new UserVO();
		vo.setUserId(123);
		String result1 = RedisUtils.setObj("obj", vo);
		logger.info("result1：" + result1);

		UserVO value2 = RedisUtils.getObj("obj", UserVO.class);
		logger.info("value2:" + JSON.toJSONString(value2));

		List<UserVO> vos = new ArrayList<>(5);
		for (int i = 0; i < 3; i++) {
			vo = new UserVO();
			vo.setUserId(22 + i);
			vos.add(vo);
		}
		logger.info("aaa:" + RedisUtils.setObj("obj1", vos));

		List<UserVO> vos1 = RedisUtils.getObj("obj1", List.class);
		logger.info("vos1:" + JSON.toJSONString(vos1));
	}
}
