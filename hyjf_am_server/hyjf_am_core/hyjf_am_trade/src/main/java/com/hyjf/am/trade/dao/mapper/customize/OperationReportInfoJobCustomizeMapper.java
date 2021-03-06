/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.trade.OperationReportJobVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author tanyy
 * @version OperationReportInfoJobCustomizeMapper, v0.1 2018/6/20 10:09
 */
public interface OperationReportInfoJobCustomizeMapper {

    /**
     * 业绩总览
     */
    List<OperationReportJobVO> getPerformanceSum();

    /**
     * 当月、季、半年、全年业绩  下面的  成交金额,根据月份计算
     *
     * @param startMonth 开始月份
     * @param endMonth   结束月份
     * @return
     */
    List<OperationReportJobVO> getMonthDealMoney(@Param("startMonth") int startMonth, @Param("endMonth") int endMonth);

    /**
     * 今年这个时候到手收益 和 去年这个时候到手收益 和  预期收益率
     *
     * @param intervalMonth 今年间隔月份
     * @param startMonth    去年开始月份
     * @param endMonth      去年结束月份
     * @return
     */
    List<OperationReportJobVO> getRevenueAndYield(@Param("intervalMonth") int intervalMonth, @Param("startMonth") int startMonth, @Param("endMonth") int endMonth);

    /**
     * 充值金额、充值笔数
     *
     * @param intervalMonth 今年间隔月份
     */
    List<OperationReportJobVO> getRechargeMoneyAndSum(int intervalMonth);

    /**
     * 按照性别统计出借人的分布
     * @param date 上个月的最后一天
     */
    List<OperationReportJobVO>  getTenderSexGroupByList(@Param("date") Date date);

    /**
     * 渠道分析 ，成交笔数
     *
     * @param intervalMonth 今年间隔月份
     * @return
     */
    List<OperationReportJobVO> getCompleteCount(int intervalMonth);

    /**
     * 借款期限
     *
     * @param intervalMonth 今年间隔月份
     * @return
     */
    List<OperationReportJobVO> getBorrowPeriod(int intervalMonth);

    /**
     * 用户分析 - 性别分布
     *
     * @param intervalMonth 今年间隔月份
     * @return
     */
    List<OperationReportJobVO> getSexDistribute(int intervalMonth);

    /**
     * 用户分析 - 年龄分布
     *
     * @param intervalMonth 今年间隔月份
     * @return
     */
    List<OperationReportJobVO> getAgeDistribute(int intervalMonth);

    /**
     * 用户分析 - 金额分布
     *
     * @param intervalMonth 今年间隔月份
     * @return
     */
    List<OperationReportJobVO> getMoneyDistribute(int intervalMonth);

    /**
     * 十大出借人
     *
     * @param intervalMonth 今年间隔月份
     * @return
     */
    List<OperationReportJobVO> getTenMostMoney(int intervalMonth);

    /**
     * 超活跃，出借笔数最多
     *
     * @param intervalMonth 今年间隔月份
     * @return
     */
    List<OperationReportJobVO> getOneInvestMost(int intervalMonth);

    /**
     * 大赢家，收益最高
     *
     * @param intervalMonth 今年间隔月份
     * @return
     */
    List<OperationReportJobVO> getOneInterestsMost(int intervalMonth);

    /**
     * 通过用户ID查询 用户年龄，用户地区
     *
     * @param userId 用户ID
     * @return
     */
    OperationReportJobVO getUserAgeAndArea(Integer userId);

    /**
     *出借人按照年龄分布 返回符合条件所有用户
     *
     * @param date 上个月的最后一天
     * @return
     */
    List<OperationReportJobVO>  getTenderAgeByRangeList(@Param("date") Date date);

    /**
     * 按照省份统计出借人的分布
     * @param date 上个月的最后一天
     */
    List<OperationReportJobVO> getTenderCityGroupBy(Date date);
}
