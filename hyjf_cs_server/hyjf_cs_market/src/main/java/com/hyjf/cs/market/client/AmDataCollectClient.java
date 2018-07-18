/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client;

import com.hyjf.am.vo.datacollect.BorrowUserStatisticVO;

/**
 * @author fuqiang
 * @version AmDataCollectClient, v0.1 2018/7/18 13:56
 */
public interface AmDataCollectClient {
    /**
     * 获取借款用户数据
     * @return
     */
    BorrowUserStatisticVO selectBorrowUserStatistic();
}
