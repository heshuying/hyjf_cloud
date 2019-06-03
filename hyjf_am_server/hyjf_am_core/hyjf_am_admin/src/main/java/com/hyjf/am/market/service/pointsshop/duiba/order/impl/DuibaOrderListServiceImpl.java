/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.pointsshop.duiba.order.impl;

import com.hyjf.am.market.dao.mapper.customize.market.DuibaOrdersCustomizeMapper;
import com.hyjf.am.market.service.pointsshop.duiba.order.DuibaOrderListService;
import com.hyjf.am.resquest.admin.DuibaOrderRequest;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.DuibaOrderVO;
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
    private DuibaOrdersCustomizeMapper duibaOrdersCustomizeMapper;

    @Override
    public Integer selectOrderListCount(DuibaOrderRequest request){
        // 根据页签查询不同的count数量 （只有查看异常订单页签时才会产生数据量不一样的数据）
        if (Validator.isNotNull(request.getOrderTypeTab())) {
            // 检索异常订单
            request.setOrderTypeTab("3");
        }
        if (Validator.isNotNull(request.getCompletionTimeSerachStart())) {
            request.setCompletionTimeSerachStart(String.valueOf(GetDate.getDayStart10(request.getCompletionTimeSerachStart())));
        }
        if (Validator.isNotNull(request.getCompletionTimeSerachEnd())) {
            request.setCompletionTimeSerachEnd(String.valueOf(GetDate.getDayEnd10(request.getCompletionTimeSerachEnd())));
        }
        if (Validator.isNotNull(request.getOrderTimeSerachStart())) {
            request.setOrderTimeSerachStart(String.valueOf(GetDate.getDayStart10(request.getOrderTimeSerachStart())));
        }
        if (Validator.isNotNull(request.getOrderTimeSerachEnd())) {
            request.setOrderTimeSerachEnd(String.valueOf(GetDate.getDayEnd10(request.getOrderTimeSerachEnd())));
        }
        return duibaOrdersCustomizeMapper.selectDuibaOrderCount(request);
    }

    @Override
    public List<DuibaOrderVO> selectOrderList(DuibaOrderRequest request){
        // 根据页签查询不同的count数量 （只有查看异常订单页签时才会产生数据量不一样的数据）
        if (Validator.isNotNull(request.getOrderTypeTab())) {
            // 检索异常订单
            request.setOrderTypeTab("3");
        }
        if (Validator.isNotNull(request.getCompletionTimeSerachStart())) {
            request.setCompletionTimeSerachStart(String.valueOf(GetDate.getDayStart10(request.getCompletionTimeSerachStart())));
        }
        if (Validator.isNotNull(request.getCompletionTimeSerachEnd())) {
            request.setCompletionTimeSerachEnd(String.valueOf(GetDate.getDayEnd10(request.getCompletionTimeSerachEnd())));
        }
        if (Validator.isNotNull(request.getOrderTimeSerachStart())) {
            request.setOrderTimeSerachStart(String.valueOf(GetDate.getDayStart10(request.getOrderTimeSerachStart())));
        }
        if (Validator.isNotNull(request.getOrderTimeSerachEnd())) {
            request.setOrderTimeSerachEnd(String.valueOf(GetDate.getDayEnd10(request.getOrderTimeSerachEnd())));
        }
        return duibaOrdersCustomizeMapper.selectDuibaOrderList(request);
    }

    @Override
    public DuibaOrderVO findOneOrder(Integer orderId){
        return duibaOrdersCustomizeMapper.findOneOrder(orderId);
    }

    @Override
    public Integer updateOneOrderByPrimaryKey(DuibaOrderVO duibaOrderVO){
        return duibaOrdersCustomizeMapper.updateOneOrderByPrimaryKey(duibaOrderVO);
    }

}
