/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.coupon.CheckCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author yaoyong
 * @version CheckCouponController, v0.1 2018/7/6 16:15
 */
@RestController
@RequestMapping("/cs-trade/checkCoupon")
public class CheckCouponController extends BaseTradeController {

    @Autowired
    CheckCouponService checkCouponService;

    /**
     * 批量上传发券接口
     */
    @PostMapping("/getBatchCoupons")
    public JSONObject getBatchCoupons(Map<String,Object> map) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = checkCouponService.getBatchCoupons(map);
        } catch (MQException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
