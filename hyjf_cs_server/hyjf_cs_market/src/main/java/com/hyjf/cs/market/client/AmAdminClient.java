package com.hyjf.cs.market.client;

import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.admin.SmsConfigRequest;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.vo.trade.TenderCityCountVO;
import com.hyjf.am.vo.trade.TenderSexCountVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/9/1  13:51
 */
public interface AmAdminClient {

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
     * 查询千乐渠道散标数据
     * @return
     */
    DataSearchCustomizeResponse querySanList(DataSearchRequest dataSearchRequest);

    /**
     * 查询千乐渠道计划数据
     * @return
     */
    DataSearchCustomizeResponse queryPlanList(DataSearchRequest dataSearchRequest);
    /**
     * 查询千乐渠道全部数据
     * @return
     */
    DataSearchCustomizeResponse queryQianleList(DataSearchRequest dataSearchRequest);


    /**
     * 查询短信加固数据
     *
     * @param request
     * @return
     * @author xiehuili
     */
     SmsConfigResponse initSmsConfig(SmsConfigRequest request);

    /**
     * 月交易金额
     * @param firstDay 月第一天
     * @param lastDay 月最后一天
     * @return
     */
    BigDecimal getAccountByMonth(Date firstDay, Date lastDay);

    Integer getTradeCountByMonth(Date firstDay, Date lastDay);

    Integer getLoanNum(Date date);

    BigDecimal getInvestLastDate(Date date);

    Integer getTenderCount(Date date);

    float getFullBillAverageTime(Date date);

    BigDecimal getRepayTotal(Date date);

}
