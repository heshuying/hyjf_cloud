/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.AppAccesStatisticsVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.client.CsMessageClient;
import com.hyjf.cs.market.mq.base.MessageContent;
import com.hyjf.cs.market.mq.producer.AppChannelStatisticsProducer;
import com.hyjf.cs.market.service.AppChannelStatisticsService;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version AppChannelStatisticsServiceImpl, v0.1 2018/7/16 14:18
 */
@Service
public class AppChannelStatisticsServiceImpl extends BaseMarketServiceImpl implements AppChannelStatisticsService {
	@Autowired
	private AmUserClient amUserClient;

	@Autowired
	private CsMessageClient csMessageClient;

	@Autowired
	private AppChannelStatisticsProducer producer;

	@Override
	public void insertStatistics() {
		logger.info("----------------APP渠道统计定时任务Start-------------");

		AppChannelStatisticsRequest request = new AppChannelStatisticsRequest();
		String nowDate = "2018-08-21";
//		String nowDate = GetDate.date2Str(GetDate.date_sdf);
		request.setTimeStartSrch(GetDate.getDayStart(nowDate));
		request.setTimeEndSrch(GetDate.getDayEnd(nowDate));

		// 查询所有app渠道
		List<UtmVO> voList = amUserClient.selectUtmPlatList("app");
		if (!CollectionUtils.isEmpty(voList)) {
			for (UtmVO vo : voList) {
				Integer sourceId = vo.getSourceId();
				request.setSourceId(String.valueOf(sourceId));
				// 访问数
				Integer accessNumber = getAccessNumber(request);
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
				// app渠道主单注册数
				BigDecimal registerAttrCount = amUserClient.getRegisterAttrCount(sourceId) == null ? BigDecimal.ZERO
						: amUserClient.getRegisterAttrCount(sourceId);
				// 查询相应的app渠道Ios开户数
				Integer accountNumberIos = amUserClient.getAccountNumberIos(sourceId) == null ? 0
						: amUserClient.getAccountNumberIos(sourceId);
				// 查询相应的app渠道PC开户数
				Integer accountNumberPc = amUserClient.getAccountNumberPc(sourceId) == null ? 0
						: amUserClient.getAccountNumberPc(sourceId);
				// 查询相应的app渠道安卓开户数
				Integer accountNumberAndroid = amUserClient.getAccountNumberAndroid(sourceId) == null ? 0
						: amUserClient.getAccountNumberAndroid(sourceId);
				// 查询相应的app渠道微信开户数
				Integer accountNumberWechat = amUserClient.getAccountNumberWechat(sourceId) == null ? 0
						: amUserClient.getAccountNumberWechat(sourceId);
				// 查询相应的app渠道用户Android投资数
				Integer tenderNumberAndroid = amUserClient.getTenderNumberAndroid(sourceId) == null ? 0
						: amUserClient.getTenderNumberAndroid(sourceId);
				// 查询相应的app渠道用户IOS投资数
				Integer tenderNumberIos = amUserClient.getTenderNumberIos(sourceId) == null ? 0
						: amUserClient.getTenderNumberIos(sourceId);
				// 查询相应的app渠道用户PC投资数
				Integer tenderNumberPc = amUserClient.getTenderNumberPc(sourceId) == null ? 0
						: amUserClient.getTenderNumberPc(sourceId);
				// 查询相应的app渠道用户微信投资数
				Integer tenderNumberWechat = amUserClient.getTenderNumberWechat(sourceId) == null ? 0
						: amUserClient.getTenderNumberWechat(sourceId);
				// 查询相应的app渠道无主单用户充值数
				BigDecimal cumulativeAttrCharge = amUserClient.getCumulativeAttrCharge(sourceId) == null
						? BigDecimal.ZERO
						: amUserClient.getCumulativeAttrCharge(sourceId);
				// 查询相应的app渠道无主单开户数
				Integer openAccountAttrCount = amUserClient.getOpenAccountAttrCount(sourceId) == null ? 0
						: amUserClient.getOpenAccountAttrCount(sourceId);
				// 查询相应的app渠道投资无主单用户数
				Integer investAttrNumber = amUserClient.getInvestAttrNumber(sourceId) == null ? 0
						: amUserClient.getInvestAttrNumber(sourceId);
				// 查询相应的app渠道用户投资总额
				BigDecimal cumulativeAttrInvest = amUserClient.getCumulativeAttrInvest(sourceId);
				AppChannelStatisticsVO statisticsVO = new AppChannelStatisticsVO(sourceId, vo.getSourceName(),
						accessNumber, registNumber, openaccountnumber, tenderNumber, cumulativeRecharge, hztTenderPrice,
						hxfTenderPrice, htlTenderPrice, htjTenderPrice, rtbTenderPrice, hzrTenderPrice, new Date(),
						registerAttrCount, accountNumberIos, accountNumberPc, accountNumberAndroid, accountNumberWechat,
						tenderNumberAndroid, tenderNumberIos, tenderNumberPc, tenderNumberWechat, cumulativeAttrCharge,
						openAccountAttrCount, investAttrNumber, cumulativeAttrInvest);
				try {
					producer.messageSend(new MessageContent(MQConstant.APP_CHANNEL_STATISTICS_TOPIC,
							System.currentTimeMillis() + "", JSONObject.toJSONBytes(statisticsVO)));
				} catch (MQException e) {
					logger.error("APP渠道统计数据定时任务出错......", e);
				}
			}
		}
	}

	/**
	 * 访问数
	 * @param request
	 * @return
	 */
	public Integer getAccessNumber(AppChannelStatisticsRequest request){
		List<AppAccesStatisticsVO> list = csMessageClient.getAppAccesStatisticsVO(request);
		return 0;
	}
}
