/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.duiba;

import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.duiba.sdk.VirtualResult;

/**
 * @author zhangqingqing
 * @version EvaluationService, v0.1 2018/6/15 19:11
 */
public interface DuibaOrderListService extends BaseUserService {

    /**
     * 根据兑吧订单的兑吧订单号查询用户订单信息并发放优惠卷
     *
     * @param orderNum
     * @return
     */
    VirtualResult selectCouponUserById(String orderNum);

    /**
     * 兑吧兑换结果通知接口（失败时设置订单无效）
     *
     * @param orderNum
     * @return
     */
    String activation(String orderNum, String errorMessage);

    /**
     * 兑吧兑换结果通知接口（成功设置优惠卷有效，更新虚拟商品充值状态为完成）
     *
     * @param orderNum
     * @return
     */
    String success(String orderNum);

}
