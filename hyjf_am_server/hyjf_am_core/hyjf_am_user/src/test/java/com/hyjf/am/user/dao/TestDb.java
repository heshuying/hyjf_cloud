package com.hyjf.am.user.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.AmUserApplication;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.model.auto.User;

/**
 * @author xiasq
 * @version TestDb, v0.1 2018/4/19 11:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmUserApplication.class)
public class TestDb {
	Logger logger = LoggerFactory.getLogger(TestDb.class);

	@Autowired
	UserMapper usersMapper;

	@Test
	public void getUser() {
		User users = usersMapper.selectByPrimaryKey(1);
		if (users != null)
			logger.info("users is :{}", JSONObject.toJSONString(users));
		logger.info("none this userId");
	}
}
