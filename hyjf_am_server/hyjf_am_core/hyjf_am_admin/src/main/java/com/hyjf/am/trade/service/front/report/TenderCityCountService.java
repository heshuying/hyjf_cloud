package com.hyjf.am.trade.service.front.report;

import com.hyjf.am.vo.trade.TenderCityCountVO;
import com.hyjf.am.vo.trade.TenderSexCountVO;

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
}
