/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.controller.admin.pointsshop.duiba.order;

import com.hyjf.am.market.service.pointsshop.duiba.order.DuibaOrderListService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaOrderResponse;
import com.hyjf.am.resquest.admin.DuibaOrderRequest;
import com.hyjf.am.vo.admin.DuibaOrderVO;
import com.hyjf.pay.lib.duiba.bean.DuiBaCallBean;
import com.hyjf.pay.lib.duiba.bean.DuiBaCallResultBean;
import com.hyjf.pay.lib.duiba.util.DuiBaCallConstant;
import com.hyjf.pay.lib.duiba.util.DuiBaCallUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
        Integer count = duibaOrderListService.selectOrderListCount(request);
        if (count > 0) {
            List<DuibaOrderVO> list = this.duibaOrderListService.selectOrderList(request);
            if (!CollectionUtils.isEmpty(list)) {
                response.setResultList(list);
                response.setRecordTotal(count);
            }else {
                response.setRtn(Response.FAIL);
            }
        }
        return response;
    }

    @GetMapping ("/synchronization/{orderId}")
    public String synchronization(@PathVariable Integer orderId){
        // 同步接口开始 DuiBaCallUtils.duiBaCall()
        logger.info("订单同步接口开始同步，orderId  = {orderId}");
        // 查询需要同步的订单信息（兑吧订单表）
        DuibaOrderVO duibaOrderVO = duibaOrderListService.findOneOrder(orderId);
        DuiBaCallBean duiBaCallBean = new DuiBaCallBean();
        duiBaCallBean.setOrderNum(duibaOrderVO.getDuibaOrderId());
        duiBaCallBean.setUserId(duibaOrderVO.getUserId());
        duiBaCallBean.setBizId(duibaOrderVO.getHyOrderId());
        duiBaCallBean.setMsgType(DuiBaCallConstant.TYPE_ORDER_QUERY);
        DuiBaCallResultBean duiBaCallResultBean = DuiBaCallUtils.duiBaCall(duiBaCallBean);
        // 根据兑吧回传的结果进行数据处理及赋值操作
        if(duiBaCallResultBean.isSuccess()){
            // 返回成功更新订单状态（success为true的时候返回，分为：create 创建订单后的初始状态、process 处理中、success 兑换成功、fail 兑换失败）
            if("process".equals(duiBaCallResultBean.getStatus())){
                duibaOrderVO.setOrderStatus(2);
            }else if("success".equals(duiBaCallResultBean.getStatus())){
                duibaOrderVO.setOrderStatus(0);
            }else if("fail".equals(duiBaCallResultBean.getStatus())){
                duibaOrderVO.setOrderStatus(1);
            }
            // 更新订单发货状态（订单子状态，success为true的时候返回，分为：create 创建订单后的初始状态、process 处理中、waitAudit 待审核、waitSend 待发货、success 兑换成功、fail 兑换失败）
            if("waitSend".equals(duiBaCallResultBean.getStatus())){
                duibaOrderVO.setDeliveryStatus(0);
            }else if("success".equals(duiBaCallResultBean.getStatus())){
                duibaOrderVO.setDeliveryStatus(1);
            }
            // 执行更新
            duibaOrderListService.updateOneOrderByPrimaryKey(duibaOrderVO);

        }else{
            return duiBaCallResultBean.getErrorMessage();
        }
        return "订单同步成功！";
    }

}
