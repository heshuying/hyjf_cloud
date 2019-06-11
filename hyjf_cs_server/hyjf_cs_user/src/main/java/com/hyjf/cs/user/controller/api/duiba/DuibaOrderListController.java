/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.duiba;

import com.hyjf.pay.lib.duiba.sdk.VirtualResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 兑吧订单（订单查询,订单发货,异常订单）列表
 *
 * @author wenxin
 * @version DuibaOrderListController, v0.1 2019/5/29 9:46
 */

@RestController
@RequestMapping("/am-market/pointsshop/duiba/order")
public class DuibaOrderListController {
    private static final Logger logger = LoggerFactory.getLogger(DuibaOrderListController.class);

    /**
     * 根据兑吧订单的兑吧订单号查询用户订单信息并发放优惠卷
     *
     * @param orderNum
     * @return
     */
    @RequestMapping("/releaseCoupons/{orderNum}")
    public VirtualResult selectCouponUserById(@PathVariable String orderNum) {

        return new VirtualResult("");
    }

    /**
     * 兑吧兑换结果通知接口（失败时设置订单无效）
     *
     * @param orderNum
     * @return
     */
    @RequestMapping("/activation/{orderNum}/{errorMessage}")
    public String activation(@PathVariable String orderNum, @PathVariable String errorMessage) {

        return "success";
    }

    /**
     * 兑吧兑换结果通知接口（成功设置优惠卷有效，更新虚拟商品充值状态为完成）
     *
     * @param orderNum
     * @return
     */
    @RequestMapping("/success/{orderNum}")
    public String success(@PathVariable String orderNum) {

        return "error";
    }
}
