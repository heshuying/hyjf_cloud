package com.hyjf.wbs.trade.service.recover;

import com.hyjf.wbs.qvo.RecoverQO;
import com.hyjf.wbs.qvo.RecoverVO;
import com.hyjf.wbs.qvo.TenderAccedeQO;
import com.hyjf.wbs.qvo.TenderAccedeVO;
import com.hyjf.wbs.trade.service.BaseService;

import java.util.List;


/**
 * @Auther: kou
 * @Date: 2019-04-23 09:20
 * @Description:
 */
public interface OrderService extends BaseService {
    List<TenderAccedeVO> getOrderInfo(TenderAccedeQO tenderAccedeQO);
}
