/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.PcChannelStatisticsVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.mq.base.MessageContent;
import com.hyjf.cs.market.mq.producer.PcChannelStatisticsProducer;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;
import com.hyjf.cs.market.service.PcChannelStatisticsService;

/**
 * @author fuqiang
 * @version PcChannelStatisticsServiceImpl, v0.1 2018/7/16 10:27
 */
@Service
public class PcChannelStatisticsServiceImpl extends BaseMarketServiceImpl implements PcChannelStatisticsService {
	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private PcChannelStatisticsProducer producer;

	@Override
	public void insertStatistics() {
		logger.info("----------------PC渠道统计定时任务Start-------------");
		List<UtmVO> voList = amUserClient.selectUtmPlatList();
		if (!CollectionUtils.isEmpty(voList)) {
			for (UtmVO vo : voList) {
				Integer sourceId = vo.getSourceId();

				// 访问数
				Integer accessNumber = amUserClient.getAccessNumber(sourceId);
				// 注册数
				Integer registNumber = amUserClient.getRegistNumber(sourceId);
				// 开户数
				Integer openaccountnumber = amUserClient.getOpenAccountNumber(sourceId);
				// 投资人数
				Integer tenderNumber = amUserClient.getTenderNumber(sourceId);
				// 累计充值
				BigDecimal cumulativeRecharge = amUserClient.getCumulativeRecharge(sourceId);
				// 汇直投投资金额
				BigDecimal hztTenderPrice = amUserClient.getHztTenderPrice(sourceId);
				// 汇消费投资金额
				BigDecimal hxfTenderPrice = amUserClient.getHxfTenderPrice(sourceId);
				// 汇天利投资金额
				BigDecimal htlTenderPrice = amUserClient.gethtlTenderPrice(sourceId);
				// 汇添金投资金额
				BigDecimal htjTenderPrice = amUserClient.getHtjTenderPrice(sourceId);
				// 汇金理财投资金额
				BigDecimal rtbTenderPrice = amUserClient.getRtbTenderPrice(sourceId);
				// 汇转让投资金额
				BigDecimal hzrTenderPrice = amUserClient.getHzrTenderPrice(sourceId);
				PcChannelStatisticsVO statisticsVO = new PcChannelStatisticsVO(sourceId, vo.getSourceName(),
						accessNumber, registNumber, openaccountnumber, tenderNumber, cumulativeRecharge, hztTenderPrice,
						hxfTenderPrice, htlTenderPrice, htjTenderPrice, rtbTenderPrice, hzrTenderPrice, new Date());
				try {
					producer.messageSend(new MessageContent(MQConstant.PC_CHANNEL_STATISTICS_TOPIC,
							System.currentTimeMillis() + "", JSONObject.toJSONBytes(statisticsVO)));
				} catch (MQException e) {
					logger.error("PC渠道统计数据定时出错......", e);
				}
			}
		}

	}
}
