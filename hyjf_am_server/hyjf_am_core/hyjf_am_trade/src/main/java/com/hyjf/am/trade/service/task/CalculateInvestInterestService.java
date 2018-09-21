/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.common.exception.MQException;

/**
 * @author yaoyong
 * @version CalculateInvestInterestService, v0.1 2018/9/20 10:13
 */
public interface CalculateInvestInterestService {
    /**
     * 插入投资统计表
     */
    void insertDataInfo() throws MQException;

    /**
     * 插入上月投资记录
     */
    void insertAYearTenderInfo();
}
