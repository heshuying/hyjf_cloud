package com.hyjf.admin.service;

import com.hyjf.admin.beans.response.HjhRepayResponseBean;
import com.hyjf.am.response.admin.HjhRepayResponse;
import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;

import java.util.List;

/**
 * 订单退出 Service
 * @Author : huanghui
 */
public interface PlanRepayService {

    Integer selectRepayCount(HjhRepayRequest request);

    List<HjhRepayVO> selectByExample(HjhRepayRequest request);

    HjhRepayResponseBean selectByAccedeOrderId(String accedeOrderId);

}
