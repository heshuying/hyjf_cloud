/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import java.util.List;

import com.hyjf.am.vo.trade.coupon.CouponRepayMonitorVO;

/**
 * @author yaoy
 * @version CouponRepayMonitorClient, v0.1 2018/6/20 16:08
 */
public interface CouponRepayMonitorClient {

    /**
     * 根据时间查询
     * @param nowDay
     * @return
     */
    List<CouponRepayMonitorVO> selectCouponRepayMonitor(String nowDay);

    /**
     * 插入数据
     * @param monitor
     * @return
     */
    int insertCouponRepayMonitor(CouponRepayMonitorVO monitor);

    /**
     * 更新数据
     * @param monitor
     * @return
     */
    int updateCouponRepayMonitor(CouponRepayMonitorVO monitor);
}
