/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.CouponRecover; /**
 * @author yaoy
 * @version CouponRecoverService, v0.1 2018/6/26 10:54
 */
public interface CouponRecoverService {
    int updateByPrimaryKeySelective(CouponRecover cr);
}
