/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.controller.admin.pointsshop.duiba.order;

import com.hyjf.am.market.dao.model.auto.DuibaOrders;
import com.hyjf.am.market.service.pointsshop.duiba.order.DuibaOrderListService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaOrderResponse;
import com.hyjf.am.resquest.admin.DuibaOrderRequest;
import com.hyjf.am.vo.admin.DuibaOrderVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Integer count = duibaOrderListService.findOrderListCount(request);
        if (count > 0) {
            List<DuibaOrders> list = this.duibaOrderListService.findOrderList(request);
            if (!CollectionUtils.isEmpty(list)) {
                // 拷贝list
                List<DuibaOrderVO> duibaOrderVOS = CommonUtils.convertBeanList(list, DuibaOrderVO.class);
                response.setResultList(duibaOrderVOS);
                response.setCount(count);
            }else {
                response.setRtn(Response.FAIL);
            }
        }
        return response;
    }

    public Map<String, Object> beanToMap(DuibaOrderRequest request){
        Map<String, Object> paraMap = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(request.getDuibaOrderIdSerach())){
            paraMap.put("duibaOrderId", request.getDuibaOrderIdSerach().trim());
        }
        if(StringUtils.isNotBlank(request.getUserNameSerach())){
            paraMap.put("userName", request.getUserNameSerach().trim());
        }
        if(StringUtils.isNotBlank(request.getTrueNameSerach())){
            paraMap.put("trueName", request.getTrueNameSerach().trim());
        }
        if(StringUtils.isNotBlank(request.getExchangeContentSerach())){
            paraMap.put("exchangeContent", request.getExchangeContentSerach().trim());
        }
        if(StringUtils.isNotBlank(request.getProductTypeSerach())){
            paraMap.put("productType", request.getProductTypeSerach().trim());
        }
        if(StringUtils.isNotBlank(request.getOrderStatusSerach())){
            paraMap.put("orderStatus", request.getOrderStatusSerach());
        }
        if(StringUtils.isNotBlank(request.getOrderTimeSerachStart())){
            paraMap.put("orderTimeStart", request.getOrderTimeSerachStart().trim()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(request.getOrderTimeSerachEnd())){
            paraMap.put("orderTimeEnd", request.getOrderTimeSerachEnd().trim()+" 23:59:59");
        }
        if(StringUtils.isNotBlank(request.getCompletionTimeSerachStart())){
            paraMap.put("completionTimeStart", request.getCompletionTimeSerachStart().trim()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(request.getCompletionTimeSerachEnd())){
            paraMap.put("completionTimeEnd", request.getCompletionTimeSerachEnd().trim()+" 23:59:59");
        }
        if(StringUtils.isNotBlank(request.getDeliveryStatusSerach())){
            paraMap.put("deliveryStatus", request.getDeliveryStatusSerach());
        }
        if(StringUtils.isNotBlank(request.getRechargeStateSerach())){
            paraMap.put("rechargeState", request.getRechargeStateSerach());
        }
        if(StringUtils.isNotBlank(request.getProcessingStateSerach())){
            paraMap.put("processingState", request.getProcessingStateSerach());
        }
        if(StringUtils.isNotBlank(request.getOrderTypeTab())){
            paraMap.put("orderTypeTab", request.getOrderTypeTab());
        }
        return paraMap;
    }

}
