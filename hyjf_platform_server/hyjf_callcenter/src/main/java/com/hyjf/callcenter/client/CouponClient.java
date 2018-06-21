/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.client;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.vo.callcenter.CallCenterCouponBackMoneyVO;
import com.hyjf.am.vo.callcenter.CallCenterCouponTenderVO;
import com.hyjf.am.vo.callcenter.CallCenterCouponUserVO;

import java.util.List;

/**
 * @author wangjun
 * @version CouponClient, v0.1 2018/6/19 11:51
 */
public interface CouponClient {
    /**
     * 查询优惠券
     * @param callCenterBaseRequest
     * @return List<CallCenterCouponUserVO>
     * @author wangjun
     */
     List<CallCenterCouponUserVO> selectCouponUserList(CallCenterBaseRequest callCenterBaseRequest);

    /**
     * 查询优惠券使用（直投产品）
     * @param callCenterBaseRequest
     * @return List<CallCenterCouponTenderVO>
     * @author wangjun
     */
     List<CallCenterCouponTenderVO> selectCouponTenderList(CallCenterBaseRequest callCenterBaseRequest);

    /**
     * 查询优惠券回款（直投产品）
     * @param callCenterBaseRequest
     * @return List<CallCenterCouponBackMoneyVO>
     * @author wangjun
     */
    List<CallCenterCouponBackMoneyVO> selectCouponBackMoneyList(CallCenterBaseRequest callCenterBaseRequest);

    /**
     * 查询优惠券内容
     * @param couponCode
     * @return String
     * @author wangjun
     */
    String getCouponContent(String couponCode);
}
