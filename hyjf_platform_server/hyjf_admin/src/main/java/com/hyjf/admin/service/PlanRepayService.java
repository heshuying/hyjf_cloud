package com.hyjf.admin.service;

import com.hyjf.am.response.trade.HjhRepayResponse;
import com.hyjf.am.resquest.admin.HjhRepayRequest;

/**
 * 订单退出 Service
 * @Author : huanghui
 */
public interface PlanRepayService {

    /**
     * 获取返回结果集
     * @param request
     * @return
     */
    HjhRepayResponse selectHjhRepayList(HjhRepayRequest request);

}
