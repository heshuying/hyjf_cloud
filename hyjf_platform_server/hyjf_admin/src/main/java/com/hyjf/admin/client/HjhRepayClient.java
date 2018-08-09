package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;

import java.util.List;

/**
 * 已迁移
 * @Author : huanghui
 */
public interface HjhRepayClient {

    Integer getRepayCount(HjhRepayRequest repayRequest);
    /**
     * 获取计划退出列表
     * @param request
     * @return
     */
    public List<HjhRepayVO> selectByExample(HjhRepayRequest request);

    /**
     * 通过 accedeOrderId和planNid 精确检索还款明细
     * @param accedeOrderId
     * @return
     */
    public List<HjhRepayVO> selectByAccedeOrderId(String accedeOrderId);

}
