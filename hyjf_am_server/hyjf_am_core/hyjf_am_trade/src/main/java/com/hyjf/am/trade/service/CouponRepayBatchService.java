/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import java.util.List;

/**
 * @author yaoy
 * @version CouponRepayBatchService, v0.1 2018/6/15 17:32
 */
public interface CouponRepayBatchService {
    List<String> selectNidForCouponOnly();
}
