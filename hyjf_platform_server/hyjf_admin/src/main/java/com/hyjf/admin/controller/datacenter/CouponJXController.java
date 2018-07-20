/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.datacenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.DataCenterCouponService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DataCenterCouponResponse;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fuqiang
 * @version CouponJXController, v0.1 2018/7/18 18:18
 */
@Api(value = "数据中心-加息券", description = "数据中心-加息券")
@RestController
@RequestMapping("/hyjf-admin/datacenter/couponJX")
public class CouponJXController extends BaseController {
	@Autowired
	private DataCenterCouponService couponService;

	@ApiOperation(value = "数据中心-加息券", notes = "数据中心-加息券列表查询")
	public AdminResult<ListResult<DataCenterCouponCustomizeVO>> getCouponList(DadaCenterCouponRequestBean requestBean) {
		DataCenterCouponResponse response = couponService.searchAction(requestBean, "JX");
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

}
