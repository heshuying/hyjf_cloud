/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch;

/**
 * @author wenxin
 * @version AutoHjhPlanCapitalService, v0.1 2019/04/15
 */
public interface AutoHjhPlanCapitalService {
    /**
     * 计算预计日期的债转额和复投额（预计资金计划）
     */
    void autoCapitalPrediction();

    /**
     * 计算预计日期的债转额和复投额（实际资金计划）
     */
    void autoCapitalActual();
}
