package com.hyjf.am.trade.service.task.impl;

import com.hyjf.am.trade.bean.TzjDayReportBean;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.TzjService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 * @author xiasq
 * @version TzjServiceImpl, v0.1 2018/7/9 9:31
 */
@Service
public class TzjServiceImpl extends BaseServiceImpl implements TzjService {

	/**
	 * 查询投之家当日投资数据：每日充值人数、投资人数 、投资金额、首投金额、首投人数、复投人数
	 * 
	 * @param tzjUserIds 投之家所有注册用户
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public TzjDayReportBean queryTradeDataOnToday(Set<Integer> tzjUserIds, Date startTime, Date endTime) {

		TzjDayReportBean bean = new TzjDayReportBean();
		// 1. 查询每日充值人数
		bean.setRechargeCount(tzjCustomizeMapper.getRechargeCount(tzjUserIds, startTime, endTime));

		// 2. 查询每日投资人数、投资金额
		TzjDayReportBean tenderInfo = tzjCustomizeMapper.getTenderInfo(tzjUserIds, startTime, endTime);
		if (tenderInfo != null) {
			bean.setTenderCount(tenderInfo.getTenderCount());
			bean.setTenderMoney(tenderInfo.getTenderMoney());
		}

		// 3. 查询每日首投人数、首投金额
		TzjDayReportBean firstTenderInfo = tzjCustomizeMapper.getTenderFirstInfo(tzjUserIds, startTime, endTime);
		if (firstTenderInfo != null) {
			bean.setTenderFirstCount(firstTenderInfo.getTenderFirstCount());
			bean.setTenderFirstMoney(firstTenderInfo.getTenderFirstMoney());
		}

		// 4. 计算复投人数
		bean.setTenderAgainCount(bean.getTenderCount() - bean.getTenderFirstCount());
		return bean;
	}

	/**
	 * 查询投之家当日新充人数 新投人数
	 * 
	 * @param tzjUserIds  投之家当日注册用户
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public TzjDayReportBean queryTradeNewDataOnToday(Set<Integer> tzjUserIds, Date startTime, Date endTime) {
		TzjDayReportBean bean = new TzjDayReportBean();
		bean.setRechargeNewCount(tzjCustomizeMapper.getRechargeCount(tzjUserIds, startTime, endTime));

		TzjDayReportBean tenderBean = tzjCustomizeMapper.getTenderInfo(tzjUserIds, startTime, endTime);
		if (tenderBean != null) {
			bean.setTenderNewCount(tenderBean.getTenderCount());
		}
		return bean;
	}
}
