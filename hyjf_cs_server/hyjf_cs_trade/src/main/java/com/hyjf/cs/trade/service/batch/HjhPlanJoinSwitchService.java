/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch;

import com.hyjf.am.response.BooleanResponse;

/**
 * @author yaoyong
 * @version HjhPlanJoinSwitchService, v0.1 2018/12/18 15:43
 */
public interface HjhPlanJoinSwitchService {
    /**
     * 汇计划定时关闭任务
     * @return
     */
    BooleanResponse hjhPlanJoinOff();

    /**
     * 计划定时开启任务
     * @return
     */
    BooleanResponse hjhPlanJoinOn();
}
