/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.service;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.callcenter.beans.ResultListBean;


/**
 * @author wangjun
 * @version CouponService, v0.1 2018/6/19 11:29
 */
public interface CouponService {
    /**
     * 查询优惠券
     * @param centerBaseRequest
     * @return List<CallCenterCouponUserVO>
     * @author wangjun
     */
     ResultListBean selectCouponUserList(CallCenterBaseRequest centerBaseRequest);

    /**
     * 查询优惠券使用（直投产品）
     * @param centerBaseRequest
     * @return List<CallCenterCouponTenderVO>
     * @author wangjun
     */
     ResultListBean selectCouponTenderList(CallCenterBaseRequest centerBaseRequest);

    /**
     * 查询优惠券回款（直投产品）
     * @param centerBaseRequest
     * @return List<CallCenterCouponBackMoneyVO>
     * @author wangjun
     */
     ResultListBean selectCouponBackMoneyList(CallCenterBaseRequest centerBaseRequest);
}
