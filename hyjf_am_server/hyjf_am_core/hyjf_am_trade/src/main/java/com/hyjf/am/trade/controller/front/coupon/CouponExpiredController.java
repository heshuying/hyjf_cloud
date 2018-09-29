/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.coupon;

import com.hyjf.am.response.StringResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.coupon.CouponExpiredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaoy
 * @version CouponExpiredController, v0.1 2018/6/19 10:22 优惠劵过期提醒
 */
@RestController
@RequestMapping("/am-trade/batch/coupon")
public class CouponExpiredController extends BaseController {

	@Autowired
	private CouponExpiredService couponExpiredService;

	@RequestMapping("/expired")
	public StringResponse updateCouponExpired() {
		try {
			couponExpiredService.updateCouponExpired();
		} catch (Exception e) {
			logger.error("检查优惠券使用是否过期 失败", e);
			return new StringResponse("fail");
		}
		return new StringResponse("success");
	}
}
