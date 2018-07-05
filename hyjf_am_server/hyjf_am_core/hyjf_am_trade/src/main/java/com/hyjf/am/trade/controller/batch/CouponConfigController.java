/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.service.CouponConfigService;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;

/**
 * @author yaoy
 * @version CouponConfigController, v0.1 2018/6/19 19:20
 */
@RestController
@RequestMapping("/am-trade/couponConfig")
public class CouponConfigController extends BaseController {
	@Autowired
	private CouponConfigService couponConfigService;

	@RequestMapping("/selectCouponConfig/{couponCode}")
	public CouponConfigResponse selectCouponConfig(@PathVariable String couponCode) {
		CouponConfigResponse response = new CouponConfigResponse();
		CouponConfig couponConfig = couponConfigService.selectCouponConfig(couponCode);
		if (couponConfig != null) {
			CouponConfigVO couponConfigVO = new CouponConfigVO();
			BeanUtils.copyProperties(couponConfig, couponConfigVO);
			response.setResult(couponConfigVO);
		}
		return response;
	}
}
