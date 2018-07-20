package com.hyjf.cs.market.service.impl;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.market.client.AmTradeClient;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.mq.base.MessageContent;
import com.hyjf.cs.market.mq.producer.StatisticsTzjProducer;
import com.hyjf.cs.market.service.TzjDataCollectService;
import org.springframework.util.CollectionUtils;

/**
 * @author xiasq
 * @version TzjDataCollectService, v0.1 2018/7/6 10:57
 */
@Service
@Deprecated
public class TzjDataCollectServiceImpl implements TzjDataCollectService {
	private Logger logger = LoggerFactory.getLogger(TzjDataCollectServiceImpl.class);

	@Autowired
	AmUserClient amUserClient;
	@Autowired
	AmTradeClient amTradeClient;
	@Autowired
	StatisticsTzjProducer statisticsTzjProducer;

	@Override
	public void queryTzjDayReport() {
		TzjDayReportVO tzjDayReportVO = new TzjDayReportVO();
		tzjDayReportVO.setDay(GetDate.formatDate());
		// T日到T+1日之间
		Date startTime = GetDate.getDayStartOfSomeDay(new Date());
		Date endTime = GetDate.countDate(startTime, 5, 1);
		startTime = GetDate.countDate(startTime,1,-5 );
		// 1. 查询投之家当天注册人数、开户人数、绑卡人数
		TzjDayReportVO tzjUserVO = amUserClient.queryUserDataOnToday(startTime, endTime);
		Assert.notNull(tzjUserVO, "投之家日报查询新用户数据不能空...");
		tzjDayReportVO.setRegistCount(tzjUserVO.getRegistCount());
		tzjDayReportVO.setOpenCount(tzjUserVO.getOpenCount());
		tzjDayReportVO.setCardBindCount(tzjUserVO.getCardBindCount());

		// 2. 投之家当天注册人数
		Set<Integer> registerUserIds = amUserClient.queryRegisterUsersOnToday(startTime, endTime);
		if (!CollectionUtils.isEmpty(registerUserIds)) {
			// 2.1 新充人数 新投人数 （前提开户，所以用开户的用户ids）
			TzjDayReportVO tzjTradeNewVO = amTradeClient.queryTradeNewDataOnToday(registerUserIds, startTime, endTime);
			Assert.notNull(tzjTradeNewVO, "投之家日报查询新用户投资数据不能空...");
			tzjDayReportVO.setRechargeNewCount(tzjTradeNewVO.getRechargeNewCount());
			tzjDayReportVO.setTenderNewCount(tzjTradeNewVO.getTenderNewCount());
		}

		// 3. 查询投之家所有的用户
		Set<Integer> tzjUserIds = amUserClient.queryAllTzjUsers();
		if (!CollectionUtils.isEmpty(tzjUserIds)) {
			// 3.1 查询投之家每日充值人数、投资人数 、投资金额、首投金额、首投人数、复投人数
			TzjDayReportVO tzjTradeVO = amTradeClient.queryTradeDataOnToday(tzjUserIds, startTime, endTime);
			Assert.notNull(tzjTradeVO, "投之家日报查询用户投资数据不能空...");
			tzjDayReportVO.setRechargeCount(tzjTradeVO.getRechargeCount());
			tzjDayReportVO.setTenderCount(tzjTradeVO.getTenderCount());
			tzjDayReportVO.setTenderMoney(tzjTradeVO.getTenderMoney());
			tzjDayReportVO.setTenderFirstCount(tzjTradeVO.getTenderFirstCount());
			tzjDayReportVO.setTenderFirstMoney(tzjTradeVO.getTenderFirstMoney());
			tzjDayReportVO.setTenderAgainCount(tzjTradeVO.getTenderAgainCount());
		}

		// 4.mq通知
		try {
			statisticsTzjProducer.messageSend(new MessageContent(MQConstant.STATISTICS_TZJ_TOPIC,
					System.currentTimeMillis() + "", JSON.toJSONBytes(tzjDayReportVO)));
		} catch (MQException e) {
			logger.error("投之家日报表发送mq失败...", e);
		}
	}
}
