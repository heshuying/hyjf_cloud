/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.am.response.admin.DataCenterCouponResponse;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;

import java.util.List;

/**
 * @author fq
 * @version DataCenterCouponService, v0.1 2018/7/19 9:53
 */
public interface DataCenterCouponService {
    /**
     * 查询数据中心优惠券数据
     * @param requestBean
     * @param type 优惠券类型
     * @return
     */
    DataCenterCouponResponse searchAction(DadaCenterCouponRequestBean requestBean, String type);

    /**
     * 获取加息券回款列表
     * @param dataCenterCouponCustomize
     * @return
     */
    List<DataCenterCouponCustomizeVO> getRecordListJX(DataCenterCouponCustomizeVO dataCenterCouponCustomize);

    /**
     * 获取代金券回款列表
     * @param dataCenterCouponCustomize
     * @return
     */
    List<DataCenterCouponCustomizeVO> getRecordListDJ(DataCenterCouponCustomizeVO dataCenterCouponCustomize);
}
