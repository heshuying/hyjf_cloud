/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AdminCouponRepayMonitorCustomizeResponse;
import com.hyjf.am.resquest.admin.CouponRepayRequest;
import com.hyjf.am.vo.admin.AdminCouponRepayMonitorCustomizeVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version CouponRepayService, v0.1 2018/7/9 11:13
 */
public interface CouponRepayService {

    List<AdminCouponRepayMonitorCustomizeVO> selectRecordList(CouponRepayRequest form);

    AdminCouponRepayMonitorCustomizeResponse couponRepayMonitorCreatePage(CouponRepayRequest form);

    List<AdminCouponRepayMonitorCustomizeVO> selectInterestSum(CouponRepayRequest form);
}
