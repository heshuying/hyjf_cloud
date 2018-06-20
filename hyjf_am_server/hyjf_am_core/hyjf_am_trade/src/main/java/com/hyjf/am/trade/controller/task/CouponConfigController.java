/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.task;

import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.service.CouponConfigService;
import com.hyjf.am.vo.trade.CouponConfigVo;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yaoy
 * @version CouponConfigController, v0.1 2018/6/19 19:20
 */
@RestController
@RequestMapping("/am-trade/couponConfig")
public class CouponConfigController {
	private static final Logger logger = LoggerFactory.getLogger(CouponConfigController.class);

	@Autowired
	private CouponConfigService couponConfigService;

	@RequestMapping("/selectCouponConfig/{couponCode}")
	public CouponConfigResponse selectCouponConfig(@PathVariable String couponCode) {
		CouponConfigResponse response = new CouponConfigResponse();
		CouponConfig couponConfig = couponConfigService.selectCouponConfig(couponCode);

		if (couponConfig != null) {
			response.setResult(CommonUtils.convertBean(couponConfig, CouponConfigVo.class));
		}
		return response;
	}
}
