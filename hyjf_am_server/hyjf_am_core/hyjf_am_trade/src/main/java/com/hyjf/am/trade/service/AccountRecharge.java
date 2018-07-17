/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fuqiang
 * @version AccountRecharge, v0.1 2018/7/17 11:11
 */
public interface AccountRecharge {
    /**
     * 获取充值金额
     * @param list 用户id集合
     * @return
     */
    BigDecimal getRechargePrice(List<Integer> list);
}
