package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.vo.market.SellDailyVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version SellDailyCustomizeMapper, v0.1 2018/11/22 17:08
 */
public interface SellDailyCustomizeMapper {

    /**
     * 查询运营中心合计
     *
     * @param dateStr
     * @return
     */
    SellDailyVO selectOCSum(@Param("dateStr") String dateStr);

    /**
     * 查询总合计
     *
     * @param dateStr
     * @return
     */
    SellDailyVO selectAllSum(@Param("dateStr") String dateStr);

    /**
     * 查询一级分部合计
     *
     * @param dateStr
     * @param drawOrder
     * @return
     */
    SellDailyVO selectPrimaryDivisionSum(@Param("dateStr") String dateStr, @Param("drawOrder") int drawOrder);

    /**
     * 查询当日待还（工作日计算当天， 如果工作日次日是节假日，那么计算当天到节假日过后第一个工作日）
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countNoneRepayToday(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                          @Param("type") Integer type);

    /**
     * 查询本月份规模业绩
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalInvestOnMonth(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                              @Param("type") Integer type);

    /**
     * 查询上月份规模业绩
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalInvestOnPreviousMonth(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                      @Param("type") Integer type);

    /**
     * 查询本月累计已还款
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalRepayOnMonth(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                             @Param("type") Integer type);

    /**
     * 本月累计提现
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalWithdrawOnMonth(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                @Param("type") Integer type);

    /**
     * 本月累计充值
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalRechargeOnMonth(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                @Param("type") Integer type);

    /**
     * 本月累计年化业绩
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalAnnualInvestOnMonth(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                    @Param("type") Integer type);

    /**
     * 上月累计年化业绩
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalAnnualInvestOnPreviousMonth(@Param("startTime") Date startTime,
                                                            @Param("endTime") Date endTime, @Param("type") Integer type);

    /**
     * 昨日规模业绩
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalTenderYesterday(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                @Param("type") Integer type);

    /**
     * 昨日已还款
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalRepayYesterday(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                               @Param("type") Integer type);

    /**
     * 昨日年化业绩
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalAnnualInvestYesterday(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                      @Param("type") Integer type);

    /**
     * 昨日充值
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalRechargeYesterday(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                  @Param("type") Integer type);

    /**
     * 昨日提现
     *
     * @param startTime
     * @param endTime
     * @param type
     *            1-查询所有部门 2-上海运营中心-网络运营部 3-查询app推广
     * @return
     */
    List<SellDailyVO> countTotalWithdrawYesterday(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                  @Param("type") Integer type);

    /**
     * 计算第四、六、十列速率,第十六列净资金流
     */
    void calculateRate();

    /**
     * 批量插入
     *
     * @param currentMonthTotalTenderList
     */
    void batchInsert(List<SellDailyVO> currentMonthTotalTenderList);

    /**
     * 批量更新
     *
     * @param list
     */
    void batchUpdate(List<SellDailyVO> list);

    /**
     * 根据一级部门查询二级部门
     *
     * @param primaryDivision
     * @return
     */
    List<String> selectTwoDivisionByPrimaryDivision(String primaryDivision);

    List<SellDailyVO> countRegisterTotalYesterday(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                  @Param("type") Integer type);

    List<SellDailyVO> countRechargeGt3000UserNum(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                 @Param("type") Integer type);

    List<SellDailyVO> countInvestGt3000UserNum(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                               @Param("type") Integer type);

    List<SellDailyVO> countInvestGt3000MonthUserNum(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                    @Param("type") Integer type);

    void update(SellDailyVO vo);
}
