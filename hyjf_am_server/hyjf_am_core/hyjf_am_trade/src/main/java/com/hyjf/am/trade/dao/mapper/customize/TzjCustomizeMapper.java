package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.bean.TzjDayReportBean;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Set;

/**
 * @author xiasq
 * @version TzjCustomizeMapper, v0.1 2018/7/9 9:38
 */
public interface TzjCustomizeMapper {
	/**
	 * 查询充值人数
	 * 
	 * @param tzjUserIds
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	int getRechargeCount(@Param("list") Set<Integer> tzjUserIds, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime);

	/**
	 * 查询投资人数、投资金额
	 * 
	 * @param tzjUserIds
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	TzjDayReportBean getTenderInfo(@Param("list") Set<Integer> tzjUserIds, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime);

	/**
	 * 查询首投人数、首投金额
	 * 
	 * @param tzjUserIds
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	TzjDayReportBean getTenderFirstInfo(@Param("list") Set<Integer> tzjUserIds, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime);

	/**
	 * 查询投之家当日新投人数
	 *
	 * @param tzjUserIds
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	TzjDayReportBean getTenderNewCount(@Param("list") Set<Integer> tzjUserIds, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime);
}
