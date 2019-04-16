/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.plancapital;

import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import com.hyjf.cs.common.service.BaseService;

import java.util.List;

/**
 * 资金计划-预计新增复投/债转额相关mongo操作
 *
 * @author PC-LIUSHOUYI
 * @version HjhPlanCapitalPredictionService, v0.1 2019/4/16 14:21
 */
public interface HjhPlanCapitalPredictionService extends BaseService {
    /**
     * 预估结果插入mongo
     *
     * @param list
     * @return
     */
    boolean insertPlanCaptialPrediction(List<HjhPlanCapitalPredictionVO> list);
}
