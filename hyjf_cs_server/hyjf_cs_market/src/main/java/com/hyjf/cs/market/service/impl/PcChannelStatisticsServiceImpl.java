/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.datacollect.PcChannelStatisticsVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.mq.base.MessageContent;
import com.hyjf.cs.market.mq.producer.PcChannelStatisticsAdminProducer;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;
import com.hyjf.cs.market.service.PcChannelStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fuqiang
 * @version PcChannelStatisticsServiceImpl, v0.1 2018/7/16 10:27
 */
@Service
public class PcChannelStatisticsServiceImpl extends BaseMarketServiceImpl implements PcChannelStatisticsService {
	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private PcChannelStatisticsAdminProducer producer;

	@Override
	public void insertStatistics() {
		logger.info("----------------PC渠道统计定时任务Start-------------");
		try {
			PcChannelStatisticsVO statisticsVO = new PcChannelStatisticsVO();
			producer.messageSend(new MessageContent(MQConstant.PC_CHANNEL_STATISTICS_ADMIN_TOPIC,
					System.currentTimeMillis() + "", JSONObject.toJSONBytes(statisticsVO) ));
		} catch (MQException e) {
			logger.error("PC渠道统计数据发送失败......", e);
		}
		/*// 查询所有pc渠道
		List<UtmVO> voList = amUserClient.selectUtmPlatList("pc");
		if (!CollectionUtils.isEmpty(voList)) {
			for (UtmVO vo : voList) {
				Integer sourceId = vo.getSourceId();
				// 访问数
				Integer accessNumber = amUserClient.getAccessNumber(sourceId, "pc");
				if(accessNumber==null){
					accessNumber=0;
				}
				// 注册数
				Integer registNumber = amUserClient.getRegistNumber(sourceId, "pc");
				if(registNumber==null){
					registNumber=0;
				}
				// 开户数
				Integer openaccountnumber = amUserClient.getOpenAccountNumber(sourceId, "pc");
				if(openaccountnumber==null){
					openaccountnumber=0;
				}
				// 投资人数
				Integer tenderNumber = amUserClient.getTenderNumber(sourceId, "pc");
				if(tenderNumber==null){
					tenderNumber=0;
				}
				// 累计充值
				BigDecimal cumulativeRecharge = amUserClient.getCumulativeRecharge(sourceId, "pc");
				if(cumulativeRecharge==null){
					cumulativeRecharge=BigDecimal.ZERO;
				}
				// 汇直投投资金额
				BigDecimal hztTenderPrice = amUserClient.getHztTenderPrice(sourceId, "pc");
				if(hztTenderPrice==null){
					hztTenderPrice=BigDecimal.ZERO;
				}
				// 汇消费投资金额
				BigDecimal hxfTenderPrice = amUserClient.getHxfTenderPrice(sourceId, "pc");
				if(hxfTenderPrice==null){
					hxfTenderPrice=BigDecimal.ZERO;
				}
				// 汇天利投资金额
				BigDecimal htlTenderPrice = amUserClient.getHtlTenderPrice(sourceId, "pc");
				if(htlTenderPrice==null){
					htlTenderPrice=BigDecimal.ZERO;
				}
				// 汇添金投资金额
				BigDecimal htjTenderPrice = amUserClient.getHtjTenderPrice(sourceId, "pc");
				if(htjTenderPrice==null){
					htjTenderPrice=BigDecimal.ZERO;
				}
				// 汇金理财投资金额
				BigDecimal rtbTenderPrice = amUserClient.getRtbTenderPrice(sourceId, "pc");
				if(rtbTenderPrice==null){
					rtbTenderPrice=BigDecimal.ZERO;
				}
				// 汇转让投资金额//
				BigDecimal hzrTenderPrice = amUserClient.getHzrTenderPrice(sourceId, "pc");
				if(hzrTenderPrice==null){
					hzrTenderPrice=BigDecimal.ZERO;
				}
				PcChannelStatisticsVO statisticsVO = new PcChannelStatisticsVO(sourceId, vo.getSourceName(),
						accessNumber, registNumber, openaccountnumber, tenderNumber, cumulativeRecharge, hztTenderPrice,
						hxfTenderPrice, htlTenderPrice, htjTenderPrice, rtbTenderPrice, hzrTenderPrice, new Date());
				statisticsVO.setCumulativeInvestment(hztTenderPrice.add(hxfTenderPrice).add(htlTenderPrice)
						.add(htjTenderPrice).add(rtbTenderPrice).add(hzrTenderPrice));
				try {
					//  对应INSERT
					producer.messageSend(new MessageContent(MQConstant.PC_CHANNEL_STATISTICS_TOPIC,
							System.currentTimeMillis() + "", JSONObject.toJSONBytes(statisticsVO)));
				} catch (MQException e) {
					logger.error("PC渠道统计数据定时任务出错......", e);
				}
			}
		}*/

	}
}
