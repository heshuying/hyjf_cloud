package com.hyjf.am.trade.service.front.hjh;

import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;

import java.util.List;
import java.util.Map;

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

	List<HjhRepay> getPlanExitCheck();

}
