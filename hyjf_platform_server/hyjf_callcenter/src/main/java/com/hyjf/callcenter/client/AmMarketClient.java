package com.hyjf.callcenter.client;

/**
 * @author wangjun
 * @version AmMarketClient, v0.1 2018/7/6 17:12
 */
public interface AmMarketClient {
    /**
     * 查询优惠券内容(活动发放)
     * @param activityId
     * @return String
     * @author wangjun
     */
    String getCouponContent(int activityId);
}
