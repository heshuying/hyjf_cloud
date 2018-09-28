package com.hyjf.am.trade.service.admin.hjhplan;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;

/**
 * 汇计划-订单退出 Service
 * @Author : huanghui
 */
public interface HjhRepayService {

    /**
     * 获取总数
     * @param params
     * @return
     */
    Integer getRepayCount(Map<String, Object> params);

    List<HjhRepayVO> selectByExample(Map<String, Object> params);

    List<HjhRepayVO> selectByAccedeOrderId(String accedeOrderId);

}
