/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.admin.DataCenterCouponResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.coupon.DataCenterCouponService;

/**
 * @author fq
 * @version DataCenterCouponController, v0.1 2018/7/19 14:28
 */
@RestController
@RequestMapping("/am-trade/datacenter/coupon")
public class DataCenterCouponController extends BaseController {
	@Autowired
	private DataCenterCouponService couponService;

	@RequestMapping("/getdatacentercouponlist")
	public DataCenterCouponResponse getDataCenterCouponList() {
		DataCenterCouponResponse response = new DataCenterCouponResponse();
		// couponService.getDataCenterCouponList();// todo fuqiang
		return null;
	}
}
