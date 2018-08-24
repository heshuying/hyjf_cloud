package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.trade.dao.model.customize.CallCenterCouponBackMoneyCustomize;
import com.hyjf.am.trade.dao.model.customize.CallCenterCouponTenderCustomize;
import com.hyjf.am.trade.dao.model.customize.CallCenterCouponUserCustomize;

public interface CallCenterCouponUserCustomizeMapper {

	/**
	 * 查询优惠券
	 *
	 * @param centerBaseRequest
	 * @return
	 */
	List<CallCenterCouponUserCustomize> selectCouponUserList(CallCenterBaseRequest centerBaseRequest);

	/**
     * 查询优惠券使用（直投产品）
     *
     * @param centerBaseRequest
     * @return
     */
    List<CallCenterCouponTenderCustomize> selectCouponTenderList(CallCenterBaseRequest centerBaseRequest);

    /**
     * 查询优惠券回款（直投产品）
     *
     * @param centerBaseRequest
     * @return
     */
    List<CallCenterCouponBackMoneyCustomize> selectCouponBackMoneyList(CallCenterBaseRequest centerBaseRequest);
}