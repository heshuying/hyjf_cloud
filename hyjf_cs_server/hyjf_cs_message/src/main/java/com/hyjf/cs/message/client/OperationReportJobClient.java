package com.hyjf.cs.message.client;

import com.hyjf.am.vo.config.IdCardCustomize;
import com.hyjf.am.vo.datacollect.OperationReportInfoVO;
import com.hyjf.am.vo.datacollect.OperationReportVO;
import com.hyjf.am.vo.trade.OperationReportJobVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author tanyy
 * @version OperationReportJobClient, v0.1 2018/5/4 10:00
 */
public interface OperationReportJobClient {

    /**
     * 按照省份统计投资人的分布  上个月的最后一天
     *
     * @param cityUserIds
     */
    List<OperationReportJobVO> getTenderCityGroupByUserIds(List<OperationReportJobVO> cityUserIds);

    /**
     * 按照省份统计投资人的分布  上个月的最后一天
     *
     * @param bms
     */
    List<OperationReportJobVO> getTenderCityGroupBy(List<OperationReportJobVO> bms);

    /**
     * 按照省份统计投资人的分布
     * @param date 上个月的最后一天
     */
    List<OperationReportJobVO> getTenderCityGroupByList(Date date);

    /**
     * 按照性别统计投资人的分布
     * @param date 上个月的最后一天
     */
    List<OperationReportJobVO>  getTenderSexGroupBy(Date date, List<OperationReportJobVO> ageRangeUserIds);

    /**
     * 按照性别统计投资人的分布
     * @param date 上个月的最后一天
     */
    List<OperationReportJobVO>  getTenderSexGroupByList(Date date);


    /**
     *投资人按照年龄分布 返回符合条件所有用户
     *
     * @param date 上个月的最后一天
     * @param firstAge  年龄下限
     * @param endAge	年龄上限
     * @return
     */
    List<OperationReportJobVO>  getTenderAgeByRangeList(Date date,int firstAge,int endAge);

    /**
     * 投资人按照年龄分布
     *
     * @param date 上个月的最后一天
     * @param firstAge  年龄下限
     * @param endAge	年龄上限
     * @return
     */
    int getTenderAgeByRange(Date date,int firstAge,int endAge, List<OperationReportJobVO> ageRangeUserIds);

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
    int getTradeCountByMonth(Date beginDate,Date endDate);



    /**
     * 借贷笔数
     */
    int getLoanNum(Date date);

    /**
     * 获取截至日期的投资金额
     */
    double getInvestLastDate(Date date);

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

    /**
     * 业绩总览
     */
    List<OperationReportJobVO> getPerformanceSum();

    /**
     * 通过时间统计平台注册人数
     * @param
     * @return
     *
     */
    int countRegistUser();

    /**
     * 当月、季、半年、全年业绩  下面的  成交金额,根据月份计算
     *
     * @param startMonth 开始月份
     * @param endMonth   结束月份
     * @return
     */
    List<OperationReportJobVO> getMonthDealMoney(int startMonth,int endMonth);

    /**
     * 今年这个时候到手收益 和 去年这个时候到手收益 和  预期收益率
     *
     * @param intervalMonth 今年间隔月份
     * @param startMonth    去年开始月份
     * @param endMonth      去年结束月份
     * @return
     */
    List<OperationReportJobVO> getRevenueAndYield(int intervalMonth,int startMonth,int endMonth);

    /**
     * 充值金额、充值笔数
     *
     * @param intervalMonth 今年间隔月份
     */
    List<OperationReportJobVO> getRechargeMoneyAndSum(int intervalMonth);

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
    List<OperationReportJobVO> getSexDistribute( int intervalMonth);
    /**
     * 用户分析 - 性别分布拆分
     *
     * @param list 用户ids
     * @return
     */
    List<OperationReportJobVO> getSexCount( List<OperationReportJobVO> list);
    /**
     * 用户分析 - 年龄分布
     *
     * @param intervalMonth 今年间隔月份
     * @return
     */
    List<OperationReportJobVO> getAgeDistribute( int intervalMonth);

    /**
     * 用户分析 - 年龄分布拆分
     *
     * @param list 用户ids
     * @return
     */
    List<OperationReportJobVO> getAgeCount(List<OperationReportJobVO> list);
    /**
     * 用户分析 - 金额分布
     *
     * @param intervalMonth 今年间隔月份
     * @return
     */
    List<OperationReportJobVO> getMoneyDistribute( int intervalMonth);


    /**
     * 十大投资人
     *
     * @param intervalMonth 今年间隔月份
     * @return
     */
    List<OperationReportJobVO> getTenMostMoney( int intervalMonth);


    /**
     * 十大投资人拆分
     *
     * @param list 多个用户id
     * @return
     */
    List<OperationReportJobVO> getUserNames( List<OperationReportJobVO> list);
    /**
     * 超活跃，投资笔数最多
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
     * @Author walter.tanyy
     * @user walter.tanyy
     * @Description  根据条件查询身份证地区
     * @Date 2018/7/31 16:37
     * @Param idCardCustomize
     * @return
     */
    IdCardCustomize getIdCardCustomize(IdCardCustomize idCardCustomize);
}
