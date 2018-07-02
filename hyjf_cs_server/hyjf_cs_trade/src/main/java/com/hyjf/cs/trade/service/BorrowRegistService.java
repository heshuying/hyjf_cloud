/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

import com.hyjf.am.response.AdminResponse;

/**
 * @author wangjun
 * @version BorrowRegistService, v0.1 2018/7/2 14:06
 */
public interface BorrowRegistService {
    /**
     * 标的备案
     * @param borrowNid
     * @return
     */
    AdminResponse updateBorrowRegist(String borrowNid);
}
