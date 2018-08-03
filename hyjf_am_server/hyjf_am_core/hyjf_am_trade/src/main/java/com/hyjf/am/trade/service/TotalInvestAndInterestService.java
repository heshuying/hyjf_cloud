/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;

/**
 * @author fq
 * @version TotalInvestAndInterestService, v0.1 2018/7/31 11:25
 */
public interface TotalInvestAndInterestService {
    /**
     * 获取累计数据
     * @return
     */
    TotalInvestAndInterestVO getTotalInvestAndInterest();
}
