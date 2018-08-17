package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

public interface AmHjhPlanClient {

    /**
     * @Author walter.limeng
     * @Description  根据planNid查询对象
     * @Date 11:07 2018/7/17
     * @Param planNid
     * @return
     */
    HjhPlanVO getHjhPlan(String planNid);

    /**
     * @Author walter.limeng
     * @Description  取得计划加入记录
     * @Date 11:38 2018/7/17
     * @Param orderId
     * @return 
     */
    HjhAccedeVO getHjhAccede(String orderId);
}
