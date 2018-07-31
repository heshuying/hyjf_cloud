/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client;

import java.math.BigDecimal;

/**
 * @author zhangqingqing
 * @version AmDataCollectClient, v0.1 2018/6/25 10:27
 */
public interface AmDataCollectClient {
    int isCompBindUser(Integer userId);

    String selectBankSmsSeq(Integer userId, String txcodeAutoBidAuthPlus);

    /**
     * 获取平台累计收益
     *
     * @return
     */
    BigDecimal selectInterestSum();
}
