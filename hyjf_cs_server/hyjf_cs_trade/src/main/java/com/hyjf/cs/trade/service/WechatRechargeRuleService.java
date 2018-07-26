/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

import com.hyjf.cs.trade.bean.WxRechargeDescResultBean;

/**
 * @author wangjun
 * @version WechatRechargeRuleService, v0.1 2018/7/26 9:25
 */
public interface WechatRechargeRuleService {
    /**
     * wechat端获取充值规则
     *
     * @return
     */
    WxRechargeDescResultBean getRechargeRule();
}
