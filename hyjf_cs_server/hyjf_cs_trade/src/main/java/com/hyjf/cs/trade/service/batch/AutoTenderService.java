/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch;

import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.List;

/**
 * 自动出借
 * @author liubin
 * @version AutoTenderService, v0.1 2018/6/28 14:07
 */
public interface AutoTenderService extends BaseTradeService {

    /**
     * 取得自动出借用加入计划列表
     * @return
     */
    List<HjhAccedeVO> selectPlanJoinList();

    /**
     * 汇计划加入订单 自动出借/复投
     * @param hjhAccede
     * @return
     */
    Integer autoTenderForOneAccede(HjhAccedeVO hjhAccede);
}
