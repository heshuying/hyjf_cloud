/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.security;

import com.hyjf.cs.common.service.BaseService;

import java.math.BigDecimal;

/**
 * @author fq
 * @version SecurityService, v0.1 2018/7/20 14:49
 */
public interface SecurityService extends BaseService {
    /**
     * 查询累计投资
     * @return
     */
    BigDecimal selectTotalInvest();

    /**
     * 查询累计收益
     * @return
     */
    BigDecimal selectTotalInterest();

    /**
     * 获取累计交易笔数
     * @return
     */
    int selectTotalTradeSum();
}
