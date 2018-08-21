/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client;

/**
 * @author zhangqingqing
 * @version AmDataCollectClient, v0.1 2018/6/25 10:27
 */
public interface AmDataCollectClient {
    int isCompBindUser(Integer userId);

    String selectBankSmsSeq(Integer userId, String txcodeAutoBidAuthPlus);

    /**
     * 根据ordId获取retCode
     * @param logOrdId
     * @return
     */
    String getRetCode(String logOrdId);
}
