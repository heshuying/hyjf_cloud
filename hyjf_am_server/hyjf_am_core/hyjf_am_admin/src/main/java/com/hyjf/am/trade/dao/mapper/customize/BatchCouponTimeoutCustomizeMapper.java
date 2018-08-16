/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.BatchCouponTimeoutCommonCustomize;

import java.util.List;
import java.util.Map; /**
 * @author yaoy
 * @version BatchCouponTimeoutCustomizeMapper, v0.1 2018/6/22 16:22
 */
public interface BatchCouponTimeoutCustomizeMapper {
    /**
     * 根据用户编号及优惠券的过期时间，取得待过期的体验金金额
     *
     * @return
     */
    List<BatchCouponTimeoutCommonCustomize> selectCouponQuota(Map<String, Object> map);
}
