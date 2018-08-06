/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.datacollect.BorrowUserStatisticVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.market.client.CsMessageClient;
import com.hyjf.cs.market.client.AmTradeClient;
import com.hyjf.cs.market.mq.base.MessageContent;
import com.hyjf.cs.market.mq.producer.BorrowUserStatisticsProducer;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;
import com.hyjf.cs.market.service.BorrowUserStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @author fuqiang
 * @version BorrowUserStatisticsServiceImpl, v0.1 2018/7/18 15:44
 */
@Service
public class BorrowUserStatisticsServiceImpl extends BaseMarketServiceImpl implements BorrowUserStatisticsService {
	@Autowired
	private AmTradeClient amTradeClient;
	@Autowired
	private CsMessageClient amDataCollectClient;
	@Autowired
	private BorrowUserStatisticsProducer statisticsProducer;

	@Override
	public void insertStatistics() {
		// 累计借款人（定义：系统累计到现在进行过发表的底层借款人数量）
		Integer countBorrowUser = amTradeClient.countBorrowUser();
		// 当前借款人（定义：当前有尚未结清债权的底层借款人数量）
		Integer countBorrowUserCurrent = amTradeClient.countCurrentBorrowUser();
		// 当前投资人（定义：当前代还金额不为0的用户数量）
		Integer countCurrentTenderUser = amTradeClient.countCurrentTenderUser();
		Calendar calendar = Calendar.getInstance();
		// 要统计前一个月的数据，所以月份要减一
		calendar.add(Calendar.MONTH, -1);
		// 代还总金额
		BigDecimal sumBorrowUserMoney = amTradeClient.sumBorrowUserMoney(getLastDay(calendar));
		// 前十大借款人待还金额
		BigDecimal sumBorrowUserMoneyTopTen = amTradeClient.sumBorrowUserMoneyTopTen();
		// 最大单一借款人待还金额
		BigDecimal sumBorrowUserMoneyTopOne = amTradeClient.sumBorrowUserMoneyTopOne();
		BorrowUserStatisticVO statisticVO = amDataCollectClient.selectBorrowUserStatistic();
		BorrowUserStatisticVO record = new BorrowUserStatisticVO();
		record.setBorrowuserCountTotal(countBorrowUser);
		record.setBorrowuserCountCurrent(countBorrowUserCurrent);
		record.setTenderuserCountCurrent(countCurrentTenderUser);
		record.setBorrowuserMoneyTotal(sumBorrowUserMoney);
		record.setBorrowuserMoneyTopone(sumBorrowUserMoneyTopOne);
		record.setBorrowuserMoneyTopten(sumBorrowUserMoneyTopTen);
		if (statisticVO == null) {
			// 第一次插入
			record.setAddTime(GetDate.getNowTime10());
			try {
				statisticsProducer.messageSend(new MessageContent(MQConstant.BORROW_USER_STATISTICS_TOPIC,
						System.currentTimeMillis() + "", JSONObject.toJSONBytes(record)));
			} catch (MQException e) {
				logger.error("更新借款用户运营数据失败......", e);
			}
		} else {
			record.setId(statisticVO.getId());
			record.setUpdateTime(GetDate.getNowTime10());
			try {
				statisticsProducer.messageSend(new MessageContent(MQConstant.BORROW_USER_STATISTICS_TOPIC,
						System.currentTimeMillis() + "", JSONObject.toJSONBytes(record)));
			} catch (MQException e) {
				logger.error("更新借款用户运营数据失败......", e);
			}
		}
	}

	/**
	 * 通过输入的日期，获取这个日期所在月份的最后一天
	 *
	 * @param cal
	 * @return
	 */
	public static Date getLastDay(Calendar cal) {
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
}
