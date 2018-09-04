/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.mq.producer.PcChannelStatisticsProducer;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;
import com.hyjf.cs.market.service.PcChannelStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
		// 查询所有pc渠道
		List<UtmVO> voList = amUserClient.selectUtmPlatList("pc");
		/*if (!CollectionUtils.isEmpty(voList)) {
			for (UtmVO vo : voList) {
				Integer sourceId = vo.getSourceId();
				// 访问数
				Integer accessNumber = amUserClient.getAccessNumber(sourceId, "pc") == null ? 0
						: amUserClient.getAccessNumber(sourceId, "pc");
				// 注册数
				Integer registNumber = amUserClient.getRegistNumber(sourceId, "pc") == null ? 0
						: amUserClient.getRegistNumber(sourceId, "pc");
				// 开户数
				Integer openaccountnumber = amUserClient.getOpenAccountNumber(sourceId, "pc") == null ? 0
						: amUserClient.getOpenAccountNumber(sourceId, "pc");
				// 投资人数
				Integer tenderNumber = amUserClient.getTenderNumber(sourceId, "pc") == null ? 0
						: amUserClient.getTenderNumber(sourceId, "pc");
				// 累计充值
				BigDecimal cumulativeRecharge = amUserClient.getCumulativeRecharge(sourceId, "pc") == null
						? BigDecimal.ZERO
						: amUserClient.getCumulativeRecharge(sourceId, "pc");
				// 汇直投投资金额
				BigDecimal hztTenderPrice = amUserClient.getHztTenderPrice(sourceId, "pc") == null ? BigDecimal.ZERO
						: amUserClient.getHztTenderPrice(sourceId, "pc");
				// 汇消费投资金额
				BigDecimal hxfTenderPrice = amUserClient.getHxfTenderPrice(sourceId, "pc") == null ? BigDecimal.ZERO
						: amUserClient.getHxfTenderPrice(sourceId, "pc");
				// 汇天利投资金额
				BigDecimal htlTenderPrice = amUserClient.getHtlTenderPrice(sourceId, "pc") == null ? BigDecimal.ZERO
						: amUserClient.getHtlTenderPrice(sourceId, "pc");
				// 汇添金投资金额
				BigDecimal htjTenderPrice = amUserClient.getHtjTenderPrice(sourceId, "pc") == null ? BigDecimal.ZERO
						: amUserClient.getHtjTenderPrice(sourceId, "pc");
				// 汇金理财投资金额
				BigDecimal rtbTenderPrice = amUserClient.getRtbTenderPrice(sourceId, "pc") == null ? BigDecimal.ZERO
						: amUserClient.getRtbTenderPrice(sourceId, "pc");
				// 汇转让投资金额
				BigDecimal hzrTenderPrice = amUserClient.getHzrTenderPrice(sourceId, "pc") == null ? BigDecimal.ZERO
						: amUserClient.getHzrTenderPrice(sourceId, "pc");
				PcChannelStatisticsVO statisticsVO = new PcChannelStatisticsVO(sourceId, vo.getSourceName(),
						accessNumber, registNumber, openaccountnumber, tenderNumber, cumulativeRecharge, hztTenderPrice,
						hxfTenderPrice, htlTenderPrice, htjTenderPrice, rtbTenderPrice, hzrTenderPrice, new Date());
				statisticsVO.setCumulativeInvestment(hztTenderPrice.add(hxfTenderPrice).add(htlTenderPrice)
						.add(htjTenderPrice).add(rtbTenderPrice).add(hzrTenderPrice));
				try {
					producer.messageSend(new MessageContent(MQConstant.PC_CHANNEL_STATISTICS_TOPIC,
							System.currentTimeMillis() + "", JSONObject.toJSONBytes(statisticsVO)));
				} catch (MQException e) {
					logger.error("PC渠道统计数据定时任务出错......", e);
				}
			}
		}*/

	}
}
