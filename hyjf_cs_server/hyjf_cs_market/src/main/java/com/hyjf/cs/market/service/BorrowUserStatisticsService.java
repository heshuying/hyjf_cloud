/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service;

/**
 * @author fuqiang
 * @version BorrowUserStatisticsService, v0.1 2018/7/18 15:44
 */
public interface BorrowUserStatisticsService extends BaseMarketService {
    /**
     * 运营数据统计（借款人相关）
     */
    void insertStatistics();
}
