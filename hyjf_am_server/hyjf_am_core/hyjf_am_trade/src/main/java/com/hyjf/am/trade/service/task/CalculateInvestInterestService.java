/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.common.exception.MQException;

import java.util.Map;

/**
 * @author yaoyong
 * @version CalculateInvestInterestService, v0.1 2018/9/20 10:13
 */
public interface CalculateInvestInterestService {
    /**
     * 插入出借统计表
     */
    void insertDataInfo(Map<String, Object> mapPeriod) throws MQException;

    /**
     * 插入上月出借记录
     */
    void insertAYearTenderInfo();

    /**
     * 查询融资期限分布
     * @return
     */
    Map<String,Object> selectPeriodInfo();
}
