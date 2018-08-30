/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.coupon;

import com.hyjf.am.resquest.trade.DadaCenterCouponCustomizeRequest;
import com.hyjf.am.resquest.trade.DataCenterCouponRequest;
import com.hyjf.am.trade.dao.model.customize.DataCenterCouponCustomize;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

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

    /**
     * 获取加息券列表
     * @param request
     * @return
     */
    List<DataCenterCouponCustomize> getRecordListDJ(DadaCenterCouponCustomizeRequest request);

    /**
     * 获取加息券列表
     * @param request
     * @return
     */
    List<DataCenterCouponCustomize> getRecordListJX(DadaCenterCouponCustomizeRequest request);

}
