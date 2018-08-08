
package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.HjhAccedeRequest;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;

import java.util.List;

/**
 * @author jijun
 * @date 20180629
 */
public interface HjhAccedeClient {

    HjhAccedeVO getHjhAccedeByAccedeOrderId(String contract_id);

    /**
     * 计划详情
     * @author zhangyk
     * @date 2018/6/27 19:26
     */
    PlanDetailCustomizeVO getPlanDetail(String planNid);

}
