package com.hyjf.am.borrow.dao;

import com.hyjf.am.trade.AmTradeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.model.auto.Account;

/**
 * @author xiasq
 * @version TestDb, v0.1 2018/4/19 11:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmTradeApplication.class)
public class TestDb {
	Logger logger = LoggerFactory.getLogger(TestDb.class);

	@Autowired
	AccountMapper accountMapper;

	@Test
	public void getUser() {
		Account account = accountMapper.selectByPrimaryKey(1);
		if (account != null)
			logger.info("account is :{}", JSONObject.toJSONString(account));
		logger.info("none this account");
	}
}
