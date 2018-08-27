/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.coupon;

import java.util.List;

import com.hyjf.am.resquest.trade.DataCenterCouponRequest;
import com.hyjf.am.trade.dao.model.customize.DataCenterCouponCustomize;
import com.hyjf.am.trade.service.BaseService;

/**
 * @author fq
 * @version DataCenterCouponService, v0.1 2018/7/19 14:34
 */
public interface DataCenterCouponService extends BaseService {
    /**
     * 查询数据中心优惠券列表
     * @param request
     * @return
     */
    List<DataCenterCouponCustomize> getDataCenterCouponList(DataCenterCouponRequest request);
}
