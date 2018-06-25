package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.statistics.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;

/**
 * @Description mongo查询类
 * @Author sunss
 * @Date 2018/6/23 9:28
 */
public interface AmMongoClient {

    /**
     * 根据userId查询用户渠道信息
     * @param userId
     * @return
     */
    AppChannelStatisticsDetailVO getAppChannelStatisticsDetailByUserId(Integer userId);
}
