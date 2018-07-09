package com.hyjf.cs.market.service.impl;

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

/**
 * @author xiasq
 * @version TzjDataCollectService, v0.1 2018/7/6 10:57
 */
@Service
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

		// 1. 查询投之家当天注册人数、开户人数、绑卡人数
        TzjDayReportVO tzjUserVO = amUserClient.queryUserDataOnToday();
        Assert.notNull(tzjUserVO, "投之家日报查询新用户数据不能空...");
        BeanUtils.copyProperties(tzjUserVO,tzjDayReportVO);

        //2. 投之家当天注册人数
        Set<Integer> registerUserIds = amUserClient.queryRegisterUsersOnToday();
        //2.1 新充人数 新投人数  （前提开户，所以用开户的用户ids）
        TzjDayReportVO tzjTradeNewVO = amTradeClient.queryTradeNewDataOnToday(registerUserIds);
        Assert.notNull(tzjTradeNewVO, "投之家日报查询新用户投资数据不能空...");
        BeanUtils.copyProperties(tzjTradeNewVO,tzjDayReportVO);

		// 3. 查询投之家所有的用户
		Set<Integer> tzjUserIds = amUserClient.queryAllTzjUsers();
        // 3.1 查询投之家每日充值人数、投资人数 、投资金额、首投金额、首投人数、复投人数
        TzjDayReportVO tzjTradeVO = amTradeClient.queryTradeDataOnToday(tzjUserIds);
        Assert.notNull(tzjTradeVO, "投之家日报查询用户投资数据不能空...");
        BeanUtils.copyProperties(tzjTradeVO,tzjDayReportVO);

        //4.mq通知
        try {
            statisticsTzjProducer.messageSend(new MessageContent(MQConstant.STATISTICS_TZJ_TOPIC, System.currentTimeMillis()+"", JSON.toJSONBytes(tzjDayReportVO)));
        } catch (MQException e) {
            logger.error("投之家日报表发送mq失败...", e);
        }
    }
}
