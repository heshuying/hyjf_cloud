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
        return duibaOrdersCustomizeMapper.selectDuibaOrderCount(request);
    }

    @Override
    public List<DuibaOrderVO> selectOrderList(DuibaOrderRequest request){
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

    @Override
    public DuibaOrderVO selectOrderByOrderId(String duibaOrderId){
        return duibaOrdersCustomizeMapper.selectOrderByOrderId(duibaOrderId);
    }
}
