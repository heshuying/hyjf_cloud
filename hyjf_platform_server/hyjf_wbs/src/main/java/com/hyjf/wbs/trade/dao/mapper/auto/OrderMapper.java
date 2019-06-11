package com.hyjf.wbs.trade.dao.mapper.auto;


import com.hyjf.wbs.qvo.RecoverQO;
import com.hyjf.wbs.qvo.RecoverVO;
import com.hyjf.wbs.qvo.TenderAccedeQO;
import com.hyjf.wbs.qvo.TenderAccedeVO;

import java.util.List;

public interface OrderMapper {

    List<TenderAccedeVO> getOrderInfo(TenderAccedeQO recoverQO);
}