package com.hyjf.am.market.service;

import com.hyjf.am.vo.admin.DuibaOrderVO;

/**
 * @author xiasq
 * @version AdsService, v0.1 2018/4/19 15:42
 */
public interface DuibaOrderListService {

    /**
     * @Author wenxin
     * @Description   根据兑吧订单号查询订单信息
     * @Date 2019/6/10
     * @Param
     * @return
     *
     */
    DuibaOrderVO selectOrderByOrderId(String duibaOrderId);

    /**
     * @Author wenxin
     * @Description   更新订单表信息插入优惠卷用户表主键id
     * @Date 2019/6/10
     * @Param
     * @return
     *
     */
    Integer updateOneOrderByPrimaryKey(DuibaOrderVO duibaOrderVO);

}
