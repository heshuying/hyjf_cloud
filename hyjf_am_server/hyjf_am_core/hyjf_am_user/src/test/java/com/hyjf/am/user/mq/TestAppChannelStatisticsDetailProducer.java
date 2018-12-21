package com.hyjf.am.user.mq;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.user.AmUserApplication;
import com.hyjf.am.user.mq.base.CommonProducer;
import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;

/**
 * @author xiasq
 * @version MqDemo, v0.1 2018/5/15 17:13
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmUserApplication.class)
public class TestAppChannelStatisticsDetailProducer {

	@Autowired
	CommonProducer producer;

	@Test
	public void testSave() throws MQException {

		AppUtmRegVO detail = new AppUtmRegVO();
		detail.setSourceId(1);
		detail.setSourceName("百度");
		detail.setUserId(123);
		detail.setUserName("小明子");
		detail.setRegisterTime(new Date());
		detail.setCumulativeInvest(BigDecimal.ZERO);
		// 发送
		producer.messageSend(new MessageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,
				MQConstant.APP_CHANNEL_STATISTICS_DETAIL_SAVE_TAG, detail));

	}

	@Test
	public void testUpate() throws MQException {

		Integer userId = 123;

		// 发送
        producer.messageSend(new MessageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,
                MQConstant.APP_CHANNEL_STATISTICS_DETAIL_UPDATE_TAG, userId));
	}
}
