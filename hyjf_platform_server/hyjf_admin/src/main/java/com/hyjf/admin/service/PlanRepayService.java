package com.hyjf.admin.service;

import com.hyjf.admin.beans.response.HjhRepayResponseBean;
import com.hyjf.am.resquest.admin.HjhRepayRequest;

/**
 * 订单退出 Service
 * @Author : huanghui
 */
public interface PlanRepayService {


    HjhRepayResponseBean selectByExample(HjhRepayRequest request);

    HjhRepayResponseBean selectByAccedeOrderId(String accedeOrderId);

}
