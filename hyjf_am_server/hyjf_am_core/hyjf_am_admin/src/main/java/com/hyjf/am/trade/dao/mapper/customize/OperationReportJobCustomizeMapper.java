package com.hyjf.am.trade.dao.mapper.customize;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 统计运营报告的相关数据
 * 
 * @author tanyy
 *
 */
public interface OperationReportJobCustomizeMapper {
	/**
	 * 按月统计平台的交易总额
	 * 
	 * @param beginDate
	 *            统计月的第一天
	 * @param endDate
	 *            统计月的最后一天
	 * @return
	 */
	BigDecimal getAccountByMonth(@Param("beginDate") Date beginDate,@Param("endDate") Date endDate);

	/**
	 * 按月统计交易笔数
	 * @param beginDate 统计月的第一天
	 * @param endDate	统计月的最后一天
	 * @return
	 */
	int getTradeCountByMonth(Date beginDate, Date endDate);
	/**
	 * 累计交易笔数
	 * @return
	 */
	int getTradeCount();
	/**
	 * 统计投资人总数，截至日期为上个月的最后一天
	 * @param date 上个月的最后一天
	 * @return
	 */
	int getTenderCount(Date date);

	/**
	 * 统计累计投资总数，
	 *
	 * @return
	 */
	BigDecimal getTotalCount();
	/**
	 * 统计用户累计收益
	 * @return
	 */
	BigDecimal getTotalInterest();


	/**
	 * 平均满标时间
	 * @param date 统计月的最后一天
	 * @return
	 */
	float getFullBillAverageTime(Date date);
	/**
	 * 统计所有待偿金额，截至日期为上个月的最后一天
	 * @param date 上个月的最后一天
	 * @return
	 */
	BigDecimal getRepayTotal(Date date);
	/**
	 *
	 * @param date 上个月的最后一天
	 * @param firstAge  年龄下限
	 * @param endAge	年龄上限
	 * @return
	 */
	int getTenderAgeByRange(Date date, int firstAge, int endAge);


	/**
	 * 借贷笔数 
	 */
	int getLoanNum(Date date);
	
	/**
	 * 人均投资金额
	 */
	BigDecimal getPerInvestTotal();
	
	/**
	 * 获取截至日期的投资金额
	 */
	double getInvestLastDate(Date date);

	/**
	 * 累计交易笔数(实时)
	 * @return
	 */
	int countTotalInvestNum();

	/**
	 * 累计交易总额(实时)
	 * @return
	 */
	BigDecimal countTotalInvestAmount();

	/**
	 * 累计为用户赚取收益(实时)
	 * @return
	 */
	BigDecimal countTotalInterestAmount();
}
