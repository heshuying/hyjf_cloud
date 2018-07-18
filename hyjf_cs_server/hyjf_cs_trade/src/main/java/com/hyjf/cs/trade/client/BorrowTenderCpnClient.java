/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;

/**
 * @author yaoy
 * @version BorrowTenderCpnClient, v0.1 2018/6/25 15:45
 */
public interface BorrowTenderCpnClient {
    /**
     * 取得优惠券投资信息
     * @param couponTenderNid
     * @return
     */
    BorrowTenderCpnVO getCouponTenderInfo(String couponTenderNid);

    /**
     * 获取用户优惠券投资信息
     * @param userId
     * @param borrowNid
     * @param logOrdId
     * @param couponGrantId
     * @return
     */
    BorrowTenderCpnVO getCouponTenderByTender(Integer userId, String borrowNid, String logOrdId, Integer couponGrantId);

    /**
     * @Author walter.limeng
     * @Description  更新borrowTenderCpn表
     * @Date 10:57 2018/7/18
     * @Param borrowTenderCpn
     * @return
     */
    int updateBorrowTenderCpn(BorrowTenderCpnVO borrowTenderCpn);
}
