/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.trade.dao.model.customize.trade.CouponRecoverCustomize;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponRecoverCustomizeMapper, v0.1 2018/6/21 19:13
 */
public interface CouponRecoverCustomizeMapper {

    List<CouponRecoverCustomize> selectCouponInterestWaitToday(Map<String, Object> map);

    BigDecimal selectCouponInterestReceivedToday(Map<String, Object> map);
}
