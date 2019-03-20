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
     *
     * @param list
     */
    void batchUpdate(List<SellDailyVO> list);

    void update(SellDailyVO vo);
}
