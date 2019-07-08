package com.hyjf.am.market.service;

import com.hyjf.am.vo.market.SellDailyVO;

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

    /**
     * 统计昨日注册数
     * @param startTime
     * @param endTime
     * @param queryAllDivisionType
     * @return
     */
    List<SellDailyVO> countRegisterTotalYesterday(Date startTime, Date endTime, Integer queryAllDivisionType);

    /**
     * 统计昨日充值金额超过3000用户
     * @param startTime
     * @param endTime
     * @param queryAllDivisionType
     * @return
     */
    List<SellDailyVO> countRechargeGt3000UserNum(Date startTime, Date endTime, Integer queryAllDivisionType);

    /**
     * 统计昨日出借金额超过3000用户
     * @param startTime
     * @param endTime
     * @param queryAllDivisionType
     * @return
     */
    List<SellDailyVO> countInvestGt3000UserNum(Date startTime, Date endTime, Integer queryAllDivisionType);

    /**
     * 统计本月累计出借超过3000新客
     * @param startTime
     * @param endTime
     * @param queryAllDivisionType
     * @return
     */
    List<SellDailyVO> countInvestGt3000MonthUserNum(Date startTime, Date endTime, Integer queryAllDivisionType);
    
    /**
     * 查询千乐本月投资总额
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countTotalInvestOnMonthQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐本月累计还款
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countTotalRepayOnMonthQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐上月累计投资
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countTotalInvestOnPreviousMonthQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐本月提现
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countTotalWithdrawOnMonthQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐本月充值
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countTotalRechargeOnMonthQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐本月累计年化投资
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countTotalAnnualInvestOnMonthQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐上月累计年化投资
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countTotalAnnualInvestOnPreviousMonthQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐昨日投资
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countTotalTenderYesterdayQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐昨日还款
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countTotalRepayYesterdayQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐昨日年化投资
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countTotalAnnualInvestYesterdayQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐昨日提现
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countTotalWithdrawYesterdayQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐昨日充值
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countTotalRechargeYesterdayQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐当日待还
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countNoneRepayTodayQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐昨日注册人数
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countRegisterTotalYesterdayQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐充值大于3000的人数
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countRechargeGt3000UserNumQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐投资大于3000的人数
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countInvestGt3000UserNumQl(Date startTime, Date endTime, String sourceId);

    /**
     * 查询千乐本月累计出借3000以上新客户数
     * @param startTime
     * @param endTime
     * @param sourceId
     * @return
     */
    SellDailyVO countInvestGt3000MonthUserNumQl(Date startTime, Date endTime, String sourceId);

    /**
     * 债转数据
     * @param startTime
     * @param endTime
     * @return
     */
    List<SellDailyVO> countTotalCredit(Date startTime, Date endTime);

    /**
     * 批量更新
     * @param list
     */
    void batchUpdate(List<SellDailyVO> list);

    /**
     * 单个更新
     * @param vo
     */
    void update(SellDailyVO vo);

    /**
     *
     * @param source    参考值
     * @param target    目标值
     * @param column    列
     * @param operateType  操作类型  1-累加  -1-扣减
     * @return
     */
    SellDailyVO addValue(SellDailyVO source, SellDailyVO target, int column, int operateType);

    /**
     * 债转数据
     * @param startTime
     * @param endTime
     * @return
     */
    List<SellDailyVO> countTotalRepayCredit(Date startTime, Date endTime);
}
