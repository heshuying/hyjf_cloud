package com.hyjf.am.market.service;

import com.hyjf.am.vo.market.SellDailyVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version SellDailyService, v0.1 2018/11/22 10:39
 */
public interface SellDailyService {
    /**
     * 查询本月份规模业绩
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalInvestOnMonth(Date startTime, Date endTime, Integer type);

    /**
     * 重载方法
     *
     * @param primaryDivision
     * @param twoDivision
     * @return
     */
    SellDailyVO constructionSellDaily(String primaryDivision, String twoDivision);

    /**
     * 查询本月累计已还款
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalRepayOnMonth(Date startTime, Date endTime, Integer type);

    /**
     * 查询上月份规模业绩
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalInvestOnPreviousMonth(Date startTime, Date endTime, Integer type);

    /**
     * 本月累计提现
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalWithdrawOnMonth(Date startTime, Date endTime, Integer type);

    /**
     * 本月累计充值
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalRechargeOnMonth(Date startTime, Date endTime, Integer type);

    /**
     * 本月累计年化业绩
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalAnnualInvestOnMonth(Date startTime, Date endTime, Integer type);

    /**
     * 上月累计年化业绩
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalAnnualInvestOnPreviousMonth(Date startTime, Date endTime, Integer type);

    /**
     * 昨日规模业绩
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalTenderYesterday(Date startTime, Date endTime, Integer type);

    /**
     * 昨日已还款
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalRepayYesterday(Date startTime, Date endTime, Integer type);

    /**
     * 昨日年化业绩
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalAnnualInvestYesterday(Date startTime, Date endTime, Integer type);

    /**
     * 昨日提现
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalWithdrawYesterday(Date startTime, Date endTime, Integer type);

    /**
     * 昨日充值
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalRechargeYesterday(Date startTime, Date endTime, Integer type);

    /**
     * 查询当日待还（工作日计算当天， 如果工作日次日是节假日，那么计算当天到节假日过后第一个工作日）
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countNoneRepayToday(Date startTime, Date endTime, Integer type);

    List<SellDailyVO> countRegisterTotalYesterday(Date startTime, Date endTime, Integer queryAllDivisionType);

    List<SellDailyVO> countRechargeGt3000UserNum(Date startTime, Date endTime, Integer queryAllDivisionType);

    List<SellDailyVO> countInvestGt3000UserNum(Date startTime, Date endTime, Integer queryAllDivisionType);

    List<SellDailyVO> countInvestGt3000MonthUserNum(Date startTime, Date endTime, Integer queryAllDivisionType);

    /**
     * drawOrder=2特殊分部的数值累加
     *
     * @param tmp
     * @param column
     * @param entity
     * @return
     */
    BigDecimal addValue(BigDecimal tmp, String column, SellDailyVO entity);

    /**
     * 重载方法
     *
     * @param ocSellDaily
     * @param primaryDivision
     * @param twoDivision
     * @param drawOrder
     * @param storeNum
     * @return
     */
    SellDailyVO constructionSellDaily(SellDailyVO ocSellDaily, String primaryDivision, String twoDivision,
                                      int drawOrder, int storeNum);

    /**
     * 特殊部门赋值
     *
     * @param tmp
     *            临时值
     * @param column
     *            第几列
     * @param sellDaily
     * @param sellDaily
     *            扣减值
     * @return
     */
    SellDailyVO setValue(BigDecimal tmp, int column, SellDailyVO sellDaily, SellDailyVO reduceSellDaily);

    /**
     * 无主单扣除千乐和vip客户组
     * @param tmp
     * @param column
     * @param sellDaily
     * @param reduceSellDaily
     * @param qianleSellDaily
     * @param vipTmp
     * @return
     */
    SellDailyVO setValue(BigDecimal tmp, int column, SellDailyVO sellDaily, SellDailyVO reduceSellDaily, SellDailyVO qianleSellDaily, BigDecimal vipTmp);

    /**
     *
     * @param list
     */
    void batchUpdate(List<SellDailyVO> list);

    void update(SellDailyVO vo);

    /**
     * 查询千乐本月投资总额
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countTotalInvestOnMonthQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐本月累计还款
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countTotalRepayOnMonthQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐上月累计投资
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countTotalInvestOnPreviousMonthQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐本月提现
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countTotalWithdrawOnMonthQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐本月充值
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countTotalRechargeOnMonthQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐本月累计年化投资
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countTotalAnnualInvestOnMonthQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐上月累计年化投资
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countTotalAnnualInvestOnPreviousMonthQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐昨日投资
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countTotalTenderYesterdayQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐昨日还款
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countTotalRepayYesterdayQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐昨日年化投资
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countTotalAnnualInvestYesterdayQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐昨日提现
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countTotalWithdrawYesterdayQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐昨日充值
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countTotalRechargeYesterdayQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐当日待还
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countNoneRepayTodayQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐昨日注册人数
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countRegisterTotalYesterdayQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐充值大于3000的人数
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countRechargeGt3000UserNumQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐投资大于3000的人数
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countInvestGt3000UserNumQl(Date startTime, Date endTime, String sourceid);

    /**
     * 查询千乐本月累计出借3000以上新客户数
     * @param startTime
     * @param endTime
     * @param sourceid
     * @return
     */
    SellDailyVO countInvestGt3000MonthUserNumQl(Date startTime, Date endTime, String sourceid);

    /**
     * 惠众扣除千乐加上vip
     * @param hzTotalTmp
     * @param column
     * @param hzRecord
     * @param shOCSellDaily
     * @param qianleSellDaily
     * @param vipTmp
     * @return
     */
    SellDailyVO setValueHz(BigDecimal hzTotalTmp, int column, SellDailyVO hzRecord, SellDailyVO shOCSellDaily, SellDailyVO qianleSellDaily, BigDecimal vipTmp);
}
