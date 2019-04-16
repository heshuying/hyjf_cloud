/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.plancapital.impl;

import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.message.bean.ic.HjhPlanCapitalPrediction;
import com.hyjf.cs.message.mongo.ic.HjhPlanCapitalPredictionDao;
import com.hyjf.cs.message.service.plancapital.HjhPlanCapitalPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资金计划-预计新增复投/债转额相关mongo操作
 *
 * @author PC-LIUSHOUYI
 * @version HjhPlanCapitalPredictionServiceImpl, v0.1 2019/4/16 14:21
 */
@Service
public class HjhPlanCapitalPredictionServiceImpl extends BaseServiceImpl implements HjhPlanCapitalPredictionService {

    @Autowired
    private HjhPlanCapitalPredictionDao planCapitalPredictionDao;

    /**
     * 预估结果插入mongo
     *
     * @param list
     * @return
     */
    @Override
    public boolean insertPlanCaptialPrediction(List<HjhPlanCapitalPredictionVO> list) {
        List<HjhPlanCapitalPrediction> inList = CommonUtils.convertBeanList(list, HjhPlanCapitalPrediction.class);
        planCapitalPredictionDao.insertAll(inList);
        return true;
    }
}
