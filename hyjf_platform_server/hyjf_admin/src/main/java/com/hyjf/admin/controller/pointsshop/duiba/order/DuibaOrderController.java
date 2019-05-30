/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.pointsshop.duiba.order;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.vo.DuibaOrderCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.pointsshop.duiba.order.DuibaOrderListService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaOrderResponse;
import com.hyjf.am.resquest.admin.DuibaOrderRequest;
import com.hyjf.am.vo.admin.DuibaOrderVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 兑吧订单（订单查询,订单发货,异常订单）列表
 * @author WENXIN
 * @version DuibaOrderController, v0.1 2019/5/29 9:46
 */
@Api(value = "产品中心-兑吧订单管理",tags ="产品中心-兑吧订单管理")
@RestController
@RequestMapping("/hyjf-admin/pointsshop/duiba/order")
public class DuibaOrderController  extends BaseController {
    /** 查看权限 */
    public static final String PERMISSIONS = "pointsshoporder";
    @Autowired
    private DuibaOrderListService duibaOrderListService;

    /**
     * 初始化页面订单信息（商品类型，订单状态，发货状态，处理状态）
     *
     * @param duibaOrderRequest
     * @return
     */
    @ApiOperation(value = "初始化信息")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<DuibaOrderResponse> orderInfoAction(@RequestBody @Valid DuibaOrderRequest duibaOrderRequest) {
        DuibaOrderResponse duibaOrderResponse = duibaOrderListService.findOrderList(duibaOrderRequest);
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
     * @param duibaOrderRequest
     * @return
     */
    @ApiOperation(value = "列表查询信息")
    @PostMapping("/orderList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<DuibaOrderCustomizeVO>> orderList(@RequestBody @Valid DuibaOrderRequest duibaOrderRequest) {
        DuibaOrderResponse duibaOrderResponse = duibaOrderListService.findOrderList(duibaOrderRequest);
        if(duibaOrderResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(duibaOrderResponse)) {
            return new AdminResult<>(FAIL, duibaOrderResponse.getMessage());

        }
        List<DuibaOrderVO> duibaOrderCustomizeVOList = duibaOrderResponse.getResultList();
        List<DuibaOrderCustomizeVO> duibaOrderCustomizeVO = new ArrayList<DuibaOrderCustomizeVO>();
        if(null!=duibaOrderCustomizeVOList&&duibaOrderCustomizeVOList.size()>0){
            duibaOrderCustomizeVO = CommonUtils.convertBeanList(duibaOrderCustomizeVOList, DuibaOrderCustomizeVO.class);
        }
        return new AdminResult<ListResult<DuibaOrderCustomizeVO>>(ListResult.build(duibaOrderCustomizeVO, duibaOrderResponse.getRecordTotal())) ;
    }

    /**
     * 同步处理中的订单信息（订单列表）
     *
     * @param orderId
     * @return
     */
    @ApiOperation(value = "同步")
    @PostMapping("/synchronization")
    public AdminResult synchronization(@RequestParam("orderId") String orderId){
        logger.info("调用同步接口start,orderId:{}", orderId);
        AdminResult adminResult = new AdminResult();
        JSONObject data = new JSONObject();
        String retMsg = duibaOrderListService.synchronization(orderId);
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(retMsg)){
            data.put("error",retMsg);
            adminResult.setStatus("同步失败！");
        }else{
            adminResult.setStatus("同步成功！");
        }
        adminResult.setData(data);
        return adminResult;
    }

    /**
     * 根据条件导出订单信息（订单列表，订单发货，异常订单）
     *
     * @param duibaOrderRequest
     * @return
     */
    @ApiOperation(value = "导出订单列表", notes = "导出订单列表")
    @PostMapping(value = "/exportOrderList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public void exportOrderListExcel(HttpServletRequest httpRequest, HttpServletResponse response, @RequestBody DuibaOrderRequest duibaOrderRequest) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "";
        if("1".equals(duibaOrderRequest.getOrderTypeTab())){
            sheetName = "订单列表明细";
        }else if("2".equals(duibaOrderRequest.getOrderTypeTab())){
            sheetName = "订单发货明细";
        }else if("3".equals(duibaOrderRequest.getOrderTypeTab())){
            sheetName = "异常订单明细";
        }
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        int sheetCount = 0;
        String sheetNameTmp = sheetName + "_第1页";
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        if("1".equals(duibaOrderRequest.getOrderTypeTab())){
            beanPropertyColumnMap = buildMap();
        }else if("2".equals(duibaOrderRequest.getOrderTypeTab())){
            beanPropertyColumnMap = buildMap1();
        }else if("3".equals(duibaOrderRequest.getOrderTypeTab())){
            beanPropertyColumnMap = buildMap2();
        }
        duibaOrderRequest.setCurrPage(1);
        duibaOrderRequest.setPageSize(defaultRowMaxCount);

        DuibaOrderRequest duibaOrderRequest1 = new DuibaOrderRequest();
        BeanUtils.copyProperties(duibaOrderRequest,duibaOrderRequest1);
        //查找全部数据
        //requestAccountDetail.setLimitFlg(true);
        DuibaOrderResponse duibaOrderResponse = duibaOrderListService.findOrderList(duibaOrderRequest1);
        if (duibaOrderResponse == null || duibaOrderResponse.getRecordTotal() <= 0){
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            int totalCount = duibaOrderResponse.getRecordTotal();
            sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, duibaOrderResponse.getResultList());
        }

        for (int i = 1; i < sheetCount; i++) {
            duibaOrderRequest1.setCurrPage(i+1);
            DuibaOrderResponse DuibaOrderResponse2 = duibaOrderListService.findOrderList(duibaOrderRequest1);
            if (DuibaOrderResponse2 != null && DuibaOrderResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  DuibaOrderResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(httpRequest, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("duibaOrderId", "兑吧订单号");
        map.put("hyOrderId", "汇盈订单号");
        map.put("userName", "账户名");
        map.put("trueName", "姓名");
        map.put("exchangeContent", "兑换内容");
        map.put("productType", "商品类型");
        map.put("sellingPrice", "售价");
        map.put("markingPrice", "划线价");
        map.put("cost", "成本");
        map.put("orderStatus", "订单状态");
        map.put("orderTime", "下单时间");
        map.put("completionTime", "完成时间");
        return map;
    }

    private Map<String, String> buildMap1() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("duibaOrderId", "兑吧订单号");
        map.put("hyOrderId", "汇盈订单号");
        map.put("userName", "账户名");
        map.put("trueName", "姓名");
        map.put("exchangeContent", "兑换内容");
        map.put("deliveryStatus", "发货状态");
        map.put("receivingInformation", "收货信息");
        map.put("orderTime", "下单时间");
        map.put("completionTime", "完成时间");
        return map;
    }

    private Map<String, String> buildMap2() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("duibaOrderId", "兑吧订单号");
        map.put("hyOrderId", "汇盈订单号");
        map.put("userName", "账户名");
        map.put("trueName", "姓名");
        map.put("exchangeContent", "兑换内容");
        map.put("rechargeState", "虚拟商品充值状态");
        map.put("orderTime", "下单时间");
        map.put("completionTime", "完成时间");
        map.put("processingState", "处理状态");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter productTypeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String productType = (String) object;
                Map<String, String> userType = CacheUtil.getParamNameMap("PRODUCT_TYPE");
                return userType.getOrDefault(productType, null);
            }
        };
        IValueFormatter orderStatusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String orderStatus = (String) object;
                Map<String, String> userType = CacheUtil.getParamNameMap("ORDER_STATUS");
                return userType.getOrDefault(orderStatus, null);
            }
        };
        IValueFormatter deliveryStatusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String deliveryStatus = (String) object;
                Map<String, String> userType = CacheUtil.getParamNameMap("DELIVERY_STATUS");
                return userType.getOrDefault(deliveryStatus, null);
            }
        };
        IValueFormatter processingStateAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String processingState = (String) object;
                Map<String, String> userType = CacheUtil.getParamNameMap("PROCESSING_STATE");
                return userType.getOrDefault(processingState, null);
            }
        };
        IValueFormatter sellingPriceAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal sellingPrice = (BigDecimal) object;
                return sellingPrice + "";
            }
        };
        IValueFormatter markingPriceAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal markingPrice = (BigDecimal) object;
                return markingPrice + "";
            }
        };
        IValueFormatter costAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal cost = (BigDecimal) object;
                return cost + "";
            }
        };

        IValueFormatter orderTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String orderTime = (String) object;
                return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(Integer.valueOf(orderTime));
            }
        };
        IValueFormatter completionTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String completionTime = (String) object;
                return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(Integer.valueOf(completionTime));
            }
        };
        mapAdapter.put("productType",productTypeAdapter);
        mapAdapter.put("orderStatus",orderStatusAdapter);
        mapAdapter.put("deliveryStatus",deliveryStatusAdapter);
        mapAdapter.put("processingState",processingStateAdapter);
        mapAdapter.put("sellingPrice",sellingPriceAdapter);
        mapAdapter.put("markingPrice",markingPriceAdapter);
        mapAdapter.put("cost",costAdapter);
        mapAdapter.put("orderTime",orderTimeAdapter);
        mapAdapter.put("completionTime",completionTimeAdapter);
        return mapAdapter;
    }



}
