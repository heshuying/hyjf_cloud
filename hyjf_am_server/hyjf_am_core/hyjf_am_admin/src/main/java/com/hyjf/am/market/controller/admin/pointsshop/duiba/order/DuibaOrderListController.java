/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.controller.admin.pointsshop.duiba.order;

import com.hyjf.am.market.service.pointsshop.duiba.order.DuibaOrderListService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaOrderResponse;
import com.hyjf.am.resquest.admin.DuibaOrderRequest;
import com.hyjf.am.vo.admin.DuibaOrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/findOrderList")
    public DuibaOrderResponse findOrderList(@RequestBody DuibaOrderRequest request){
        DuibaOrderResponse response = new DuibaOrderResponse();
        // Integer count = duibaOrderListService.findOrderListCount(request);
        Integer count = duibaOrderListService.selectOrderListCount(request);
        if (count > 0) {
            //List<DuibaOrders> list = this.duibaOrderListService.findOrderList(request);
            List<DuibaOrderVO> list = this.duibaOrderListService.selectOrderList(request);
            if (!CollectionUtils.isEmpty(list)) {
                // 拷贝list
                // List<DuibaOrderVO> duibaOrderVOS = CommonUtils.convertBeanList(list, DuibaOrderVO.class);
                // response.setResultList(duibaOrderVOS);
                response.setResultList(list);
                response.setCount(count);
            }else {
                response.setRtn(Response.FAIL);
            }
        }
        return response;
    }

}
