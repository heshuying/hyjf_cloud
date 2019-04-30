package com.hyjf.wbs.trade.service.recover.impl;

import com.hyjf.wbs.qvo.RecoverQO;
import com.hyjf.wbs.qvo.RecoverVO;
import com.hyjf.wbs.qvo.TenderAccedeQO;
import com.hyjf.wbs.qvo.TenderAccedeVO;
import com.hyjf.wbs.trade.service.impl.BaseServiceImpl;
import com.hyjf.wbs.trade.service.recover.OrderService;
import com.hyjf.wbs.trade.service.recover.RecoverService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: kou
 * @Date: 2019-04-18 09:24
 * @Description:
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {

    @Override
    public List<TenderAccedeVO> getOrderInfo(TenderAccedeQO tenderAccedeQO){
        List<TenderAccedeVO> tenderAccedeVOS = orderMapper.getOrderInfo(tenderAccedeQO);
        if (tenderAccedeVOS != null && tenderAccedeVOS.size() > 0) {
            return tenderAccedeVOS;
        }
        return null;
    }
}
