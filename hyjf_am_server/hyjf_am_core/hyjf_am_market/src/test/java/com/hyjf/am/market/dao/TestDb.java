package com.hyjf.am.market.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.market.AmMarketApplication;
import com.hyjf.am.market.dao.mapper.auto.AdsMapper;
import com.hyjf.am.market.dao.model.auto.Ads;

/**
 * @author xiasq
 * @version TestDb, v0.1 2018/4/19 11:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmMarketApplication.class)
public class TestDb {
	Logger logger = LoggerFactory.getLogger(TestDb.class);

	@Autowired
	AdsMapper adsMapper;

	@Test
	public void getUser() {
		Ads ads = adsMapper.selectByPrimaryKey(1);
		if (ads != null)
			logger.info("ads is :{}", JSONObject.toJSONString(ads));
		logger.info("none this ads");
	}
}
