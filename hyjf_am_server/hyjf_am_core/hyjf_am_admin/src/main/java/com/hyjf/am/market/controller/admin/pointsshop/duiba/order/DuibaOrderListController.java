/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.controller.admin.pointsshop.duiba.order;

import com.hyjf.am.market.service.pointsshop.duiba.order.DuibaOrderListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 兑吧订单（订单查询,订单发货,异常订单）列表
 * @author wenxin
 * @version DuibaOrderListController, v0.1 2019/5/29 9:46
 */

@RestController
@RequestMapping("/am-market/pointsshop/duiba/order")
public class DuibaOrderListController {
    private static final Logger logger = LoggerFactory.getLogger(DuibaOrderListController.class);

    @Autowired
    private DuibaOrderListService duibaOrderListService;



}
