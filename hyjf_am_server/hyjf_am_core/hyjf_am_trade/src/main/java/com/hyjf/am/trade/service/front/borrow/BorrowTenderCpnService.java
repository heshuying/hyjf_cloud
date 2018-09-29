/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.trade.dao.model.auto.BorrowTenderCpn;

/**
 * @author yaoy
 * @version BorrowTenderCpnService, v0.1 2018/6/26 10:37
 */
public interface BorrowTenderCpnService {
    /**
     * 更新优惠券投资信息
     * @param borrowTenderCpn1
     * @return
     */
    int updateByPrimaryKeySelective(BorrowTenderCpn borrowTenderCpn1);

    /**
     * 查询优惠券投资信息
     * @param couponTenderNid
     * @return
     */
    BorrowTenderCpn getCouponTenderInfo(String couponTenderNid);
}
