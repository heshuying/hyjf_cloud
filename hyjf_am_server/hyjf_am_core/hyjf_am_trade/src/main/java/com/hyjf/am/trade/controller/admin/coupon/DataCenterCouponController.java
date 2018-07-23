/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.coupon;

import com.hyjf.am.response.admin.DataCenterCouponResponse;
import com.hyjf.am.resquest.trade.DataCenterCouponRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.admin.DataCenterCouponCustomize;
import com.hyjf.am.trade.service.admin.coupon.DataCenterCouponService;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fq
 * @version DataCenterCouponController, v0.1 2018/7/19 14:28
 */
@RestController
@RequestMapping("/am-trade/datacenter/coupon")
public class DataCenterCouponController extends BaseController {
	@Autowired
	private DataCenterCouponService couponService;

	/**
	 * 查询数据中心优惠券列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getdatacentercouponlist")
	public DataCenterCouponResponse getDataCenterCouponList(DataCenterCouponRequest request) {
		DataCenterCouponResponse response = new DataCenterCouponResponse();
		List<DataCenterCouponCustomize> list = couponService.getDataCenterCouponList(request);
		List<DataCenterCouponCustomizeVO> voList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(list)) {
			voList = CommonUtils.convertBeanList(list, DataCenterCouponCustomizeVO.class);
			response.setResultList(voList);
			return response;
		}
		return null;
	}
}
