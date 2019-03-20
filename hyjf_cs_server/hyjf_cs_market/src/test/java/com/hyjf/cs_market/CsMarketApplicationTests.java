package com.hyjf.cs_market;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.market.CsMarketApplication;
import com.hyjf.cs.market.client.AmMarketClient;
import com.hyjf.cs.market.mq.base.CommonProducer;
import com.hyjf.cs.market.mq.base.MessageContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsMarketApplication.class)
public class CsMarketApplicationTests {
	@Autowired
	private AmMarketClient amMarketClient;
	@Autowired
	CommonProducer commonProducer;
	@Test
	public void contextLoads() {
		Date  currentDate = new Date();
		// 昨日结束日期
		Date yesterdayEndTime = GetDate.getYesterdayEndTime(currentDate);

		// 本月累计开始时间
		Date totalMonthStartTime = GetDate.getFirstDayOnMonth(yesterdayEndTime);

		insertSomeColumn(totalMonthStartTime, yesterdayEndTime,1);
	}


	private void insertSomeColumn(Date startTime, Date endTime, int column) {


		JSONObject params = new JSONObject();
		params.put("column", column);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		try {
			commonProducer.messageSend(new MessageContent(MQConstant.SELL_DAILY_TOPIC,
					MQConstant.SELL_DAILY_SELECT_TAG, UUID.randomUUID().toString(), params));
		} catch (MQException e) {

		}
	}
}
