/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.trade.dao.model.customize.DataCenterCouponCustomize;

/**
 * @author fq
 * @version DataCenterCouponCustomizeMapper, v0.1 2018/7/23 9:07
 */
public interface DataCenterCouponCustomizeMapper {
    /**
     * 查询数据中心优惠券列表
     * @param params
     * @return
     */
    List<DataCenterCouponCustomize> getDataCenterCouponList(Map<String,Object> params);
}
