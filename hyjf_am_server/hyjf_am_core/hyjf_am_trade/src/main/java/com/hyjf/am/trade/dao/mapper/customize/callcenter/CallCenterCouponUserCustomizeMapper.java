package com.hyjf.am.trade.dao.mapper.customize.callcenter;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.trade.dao.model.customize.callcenter.CallCenterCouponBackMoneyCustomize;
import com.hyjf.am.trade.dao.model.customize.callcenter.CallCenterCouponTenderCustomize;
import com.hyjf.am.trade.dao.model.customize.callcenter.CallCenterCouponUserCustomize;

import java.util.List;

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