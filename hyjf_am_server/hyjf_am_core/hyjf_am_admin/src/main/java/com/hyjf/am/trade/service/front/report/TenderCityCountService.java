package com.hyjf.am.trade.service.front.report;

import com.hyjf.am.vo.trade.TenderCityCountVO;
import com.hyjf.am.vo.trade.TenderSexCountVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/9/1  13:32
 */
public interface TenderCityCountService {

    /**
     * 投资人按照地域分布
     * @param lastDay 一个月的最后一天
     * @return
     */
    List<TenderCityCountVO> getTenderCityGroupBy(Date lastDay);

    /**
     * 按照性别统计投资人的分布
     * @param date 上个月的最后一天
     */
    List<TenderSexCountVO>  getTenderSexGroupBy(Date date);

    /**
     *
     * @param date 上个月的最后一天
     * @param firstAge  年龄下限
     * @param endAge	年龄上限
     * @return
     */
    int getTenderAgeByRange(Date date,int firstAge,int endAge);

    /**
     * 按月统计平台的交易总额
     *
     * @param beginDate
     *            统计月的第一天
     * @param endDate
     *            统计月的最后一天
     * @return
     */
    BigDecimal getAccountByMonth(Date beginDate, Date endDate);

    /**
     * 按月统计交易笔数
     * @param beginDate 统计月的第一天
     * @param endDate	统计月的最后一天
     * @return
     */
    int getTradeCountByMonth(Date beginDate, Date endDate);

    /**
     * 借贷笔数
     */
    int getLoanNum(Date date);

    /**
     * 获取截至日期的投资金额
     */
    BigDecimal getInvestLastDate(Date date);

    /**
     * 统计投资人总数，截至日期为上个月的最后一天
     * @param date 上个月的最后一天
     * @return
     */
    int getTenderCount(Date date);

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

}
