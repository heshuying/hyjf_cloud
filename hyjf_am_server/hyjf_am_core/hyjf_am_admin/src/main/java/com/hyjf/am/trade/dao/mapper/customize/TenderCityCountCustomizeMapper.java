package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.TenderCityCount;
import com.hyjf.am.trade.dao.model.customize.TenderSexCount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/9/1  13:35
 */
public interface TenderCityCountCustomizeMapper {

    /**
     * 按照省份统计投资人的分布
     * @param date 上个月的最后一天
     */
    List<TenderCityCount> getTenderCityGroupBy(Date date);

    /**
     * 按照性别统计投资人的分布
     * @param date 上个月的最后一天
     */
    List<TenderSexCount>  getTenderSexGroupBy(Date date);

    /**
     *
     * @param date 上个月的最后一天
     * @param firstAge  年龄下限
     * @param endAge	年龄上限
     * @return
     */
    int getTenderAgeByRange(@Param("date")Date date, @Param("firstAge")int firstAge, @Param("endAge")int endAge);

    /**
     * 按月统计平台的交易总额
     *
     * @param beginDate
     *            统计月的第一天
     * @param endDate
     *            统计月的最后一天
     * @return
     */
    BigDecimal getAccountByMonth(@Param("beginDate")Date beginDate, @Param("endDate")Date endDate);

    /**
     * 按月统计交易笔数
     * @param beginDate 统计月的第一天
     * @param endDate	统计月的最后一天
     * @return
     */
    int getTradeCountByMonth(@Param("beginDate")Date beginDate,@Param("endDate")Date endDate);

    /**
     * 借贷笔数
     */
    int getLoanNum(@Param("date")Date date);

    /**
     * 获取截至日期的投资金额
     */
    BigDecimal getInvestLastDate(@Param("date")Date date);

    /**
     * 统计投资人总数，截至日期为上个月的最后一天
     * @param date 上个月的最后一天
     * @return
     */
    int getTenderCount(@Param("date")Date date);

    /**
     * 平均满标时间
     * @param date 统计月的最后一天
     * @return
     */
    float getFullBillAverageTime(@Param("date")Date date);

    /**
     * 统计所有待偿金额，截至日期为上个月的最后一天
     * @param date 上个月的最后一天
     * @return
     */
    BigDecimal getRepayTotal(@Param("date")Date date);

}
