/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.service.CheckCouponService;
import com.hyjf.common.exception.MQException;
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
@RequestMapping("/am-user/checkCoupon")
public class CheckCouponController extends BaseController {

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
