/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.customize.trade.CouponRecoverCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.CouponTenderCustomize;

import java.util.List;

/**
 * @author yaoy
 * @version DataRecoverService, v0.1 2018/6/25 14:45
 */
public interface DataRecoverService {
    List<CouponTenderCustomize> selectCouponRecover(String borrowNid, int repayTimeConfig);

    CouponRecoverCustomize selectCurrentCouponRecover(String couponTenderNid, int periodNow);

    int updateOfRepayTender(Account account);
}
