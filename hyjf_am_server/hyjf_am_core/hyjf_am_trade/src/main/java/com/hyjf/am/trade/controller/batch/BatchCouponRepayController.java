/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.response.trade.CouponRecoverCustomizeResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.trade.CouponRecoverCustomize;
import com.hyjf.am.trade.service.task.CouponRepayService;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version BatchCouponRepayController, v0.1 2018/8/2 16:10
 * 体验金收益期限还款
 */
@RestController
@RequestMapping("/am-trade/couponPeriodRepay")
public class BatchCouponRepayController extends BaseController {

    @Autowired
    private CouponRepayService couponRepayService;

    /**
     *优惠券单独投资还款
     * @return
     */
    @PostMapping("/selectNidForCouponOnly")
    public List<String> selectNidForCouponOnly() {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        List<String> recoverNidList = couponRepayService.selectNidForCouponOnly(paramMap);
        if (!CollectionUtils.isEmpty(recoverNidList)) {
            return recoverNidList;
        }
        return null;
    }
}
