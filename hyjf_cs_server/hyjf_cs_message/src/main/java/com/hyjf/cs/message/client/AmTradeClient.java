/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.client;

import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;

/**
 * @author fq
 * @version AmTradeClient, v0.1 2018/7/31 11:42
 */
public interface AmTradeClient {
    /**
     * 获取统计数据
     * @return
     */
    TotalInvestAndInterestVO getTotalInvestAndInterest();
}
