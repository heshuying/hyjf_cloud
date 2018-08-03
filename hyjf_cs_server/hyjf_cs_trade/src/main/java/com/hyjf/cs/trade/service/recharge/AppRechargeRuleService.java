/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.recharge;

import com.hyjf.cs.trade.bean.app.AppRechargeDescResultBean;

/**
 * @author wangjun
 * @version AppRechargeRuleService, v0.1 2018/7/25 15:05
 */
public interface AppRechargeRuleService {
    /**
     * app端获取充值规则
     *
     * @return
     */
    AppRechargeDescResultBean getRechargeRule();
}
