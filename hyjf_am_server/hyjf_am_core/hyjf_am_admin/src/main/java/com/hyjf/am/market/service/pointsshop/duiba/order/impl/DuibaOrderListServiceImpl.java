/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.pointsshop.duiba.order.impl;

import com.hyjf.am.market.dao.mapper.auto.DuibaOrdersMapper;
import com.hyjf.am.market.dao.model.auto.DuibaOrders;
import com.hyjf.am.market.dao.model.auto.DuibaOrdersExample;
import com.hyjf.am.market.service.pointsshop.duiba.order.DuibaOrderListService;
import com.hyjf.am.resquest.admin.DuibaOrderRequest;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wenxin
 * @version DuibaOrderListServiceImpl, v0.1 2019/5/29 9:48
 */
@Service
public class DuibaOrderListServiceImpl extends BaseServiceImpl implements DuibaOrderListService {

    @Autowired
    private DuibaOrdersMapper duibaOrdersMapper;

    @Override
    public Integer findOrderListCount(DuibaOrderRequest request){
        return duibaOrdersMapper.countByExample(serachList(request));
    }

    @Override
    public List<DuibaOrders> findOrderList(DuibaOrderRequest request){
        return duibaOrdersMapper.selectByExample(serachList(request));
    }

    private DuibaOrdersExample serachList(DuibaOrderRequest request){
        DuibaOrdersExample duibaOrdersExample = new DuibaOrdersExample();
        DuibaOrdersExample.Criteria criteria = duibaOrdersExample.createCriteria();
        // 根据页签查询不同的count数量 （只有查看异常订单页签时才会产生数据量不一样的数据）
        if (Validator.isNotNull(request.getOrderTypeTab())&&("3").equals(request.getOrderTypeTab())) {
            // 检索异常信息不为空的异常订单
            criteria.andRechargeStateIsNotNull();
        }
        // 拼装查询条件（完成时间开始,完成时间结束）
        if (Validator.isNotNull(request.getCompletionTimeSerachStart())&&Validator.isNotNull(request.getCompletionTimeSerachEnd())){
            criteria.andCompletionTimeBetween(GetDate.getDayStart10(request.getCompletionTimeSerachStart()),GetDate.getDayEnd10(request.getCompletionTimeSerachEnd()));
        }
        // 拼装查询条件（下单时间开始,下单时间结束）
        if (Validator.isNotNull(request.getOrderTimeSerachStart())&&Validator.isNotNull(request.getOrderTimeSerachEnd())){
            criteria.andOrderStatusBetween(GetDate.getDayStart10(request.getOrderTimeSerachStart()),GetDate.getDayEnd10(request.getOrderTimeSerachEnd()));
        }
        // 拼装查询条件（兑吧订单号）
        if (Validator.isNotNull(request.getDuibaOrderIdSerach())){
            criteria.andDuibaOrderIdEqualTo(request.getDuibaOrderIdSerach());
        }
        // 拼装查询条件（汇盈订单号）
        if (Validator.isNotNull(request.getHyOrderIdSerach())){
            criteria.andHyOrderIdEqualTo(request.getHyOrderIdSerach());
        }
        // 拼装查询条件（订单兑换人用户名）
        if (Validator.isNotNull(request.getUserNameSerach())){
            criteria.andUserNameEqualTo(request.getUserNameSerach());
        }
        // 拼装查询条件（姓名）
        if (Validator.isNotNull(request.getTrueNameSerach())){
            criteria.andTrueNameEqualTo(request.getTrueNameSerach());
        }
        // 拼装查询条件（兑换内容）
        if (Validator.isNotNull(request.getExchangeContentSerach())){
            criteria.andExchangeContentEqualTo(request.getExchangeContentSerach());
        }
        // 拼装查询条件（商品类型）
        if (Validator.isNotNull(request.getProductTypeSerach())){
            criteria.andProductTypeEqualTo(request.getProductTypeSerach());
        }
        // 拼装查询条件（订单状态）
        if (Validator.isNotNull(request.getOrderStatusSerach())){
            criteria.andOrderStatusEqualTo(Integer.valueOf(request.getOrderStatusSerach()));
        }
        // 拼装查询条件（发货状态）
        if (Validator.isNotNull(request.getDeliveryStatusSerach())){
            criteria.andDeliveryStatusEqualTo(Integer.valueOf(request.getDeliveryStatusSerach()));
        }
        // 拼装查询条件（虚拟商品充值状态）
        if (Validator.isNotNull(request.getRechargeStateSerach())){
            criteria.andRechargeStateEqualTo(request.getRechargeStateSerach());
        }
        // 拼装查询条件（处理状态）
        if (Validator.isNotNull(request.getProcessingStateSerach())){
            criteria.andProcessingStateEqualTo(Integer.valueOf(request.getProcessingStateSerach()));
        }
        return duibaOrdersExample;
    }
}
