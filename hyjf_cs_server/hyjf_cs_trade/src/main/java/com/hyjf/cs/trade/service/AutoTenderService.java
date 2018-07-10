/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;

import java.util.List;

/**
 * 自动投资
 * @author liubin
 * @version AutoTenderService, v0.1 2018/6/28 14:07
 */
public interface AutoTenderService extends BaseTradeService{

    List<HjhAccedeVO> selectPlanJoinList();

    boolean AutoTenderForOneAccede(HjhAccedeVO hjhAccede);
}
