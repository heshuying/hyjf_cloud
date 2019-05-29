/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.pointsshop.duiba.order;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.pointsshop.duiba.order.DuibaOrderListService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaOrderResponse;
import com.hyjf.am.resquest.admin.DuibaOrderRequest;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

import static com.hyjf.admin.common.result.BaseResult.FAIL;
import static com.hyjf.admin.common.result.BaseResult.FAIL_DESC;

/**
 * 兑吧订单（订单查询,订单发货,异常订单）列表
 * @author WENXIN
 * @version DuibaOrderController, v0.1 2019/5/29 9:46
 */
@Api(value = "产品中心-兑吧订单管理",tags ="产品中心-兑吧订单管理")
@RestController
@RequestMapping("/hyjf-admin/pointsshop/duiba/order")
public class DuibaOrderController {
    /** 查看权限 */
    public static final String PERMISSIONS = "pointsshoporder";
    @Autowired
    private DuibaOrderListService duibaOrderListService;

    // 显示权限控制
    // @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)

    /**
     * 初始化页面订单信息（商品类型，订单状态，发货状态，处理状态）
     *
     * @param duibaOrderRequest
     * @return
     */
    @ApiOperation(value = "查询信息")
    @PostMapping("/infoAction")
    public AdminResult<DuibaOrderResponse> orderInfoAction(@RequestBody @Valid DuibaOrderRequest duibaOrderRequest) {
        DuibaOrderResponse duibaOrderResponse = duibaOrderListService.orderInfoAction(duibaOrderRequest);
        if(duibaOrderResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(duibaOrderResponse)) {
            return new AdminResult<>(FAIL, duibaOrderResponse.getMessage());

        }
        // 商品类型
        Map<String ,String> productTypeMap = CacheUtil.getParamNameMap(CustomConstants.PRODUCT_TYPE);
        duibaOrderResponse.setProductTypeList(duibaOrderListService.mapToParamNameVO(productTypeMap));
        // 订单状态
        Map<String ,String> orderStatusMap = CacheUtil.getParamNameMap(CustomConstants.ORDER_STATUS);
        duibaOrderResponse.setOrderStatusList(duibaOrderListService.mapToParamNameVO(orderStatusMap));
        // 发货状态
        Map<String ,String> deliveryStatusMap = CacheUtil.getParamNameMap(CustomConstants.DELIVERY_STATUS);
        duibaOrderResponse.setDeliveryStatusList(duibaOrderListService.mapToParamNameVO(deliveryStatusMap));
        // 处理状态
        Map<String ,String> processingStateMap = CacheUtil.getParamNameMap(CustomConstants.PROCESSING_STATE);
        duibaOrderResponse.setProcessingStateList(duibaOrderListService.mapToParamNameVO(processingStateMap));
        return new AdminResult<DuibaOrderResponse>(duibaOrderResponse);
    }

    /**
     * 根据条件查询订单信息（订单列表，订单发货，异常订单）
     *
     * @param request
     * @return
     */



    /**
     * 根据条件导出订单信息（订单列表，订单发货，异常订单）
     *
     * @param request
     * @return
     */



}
