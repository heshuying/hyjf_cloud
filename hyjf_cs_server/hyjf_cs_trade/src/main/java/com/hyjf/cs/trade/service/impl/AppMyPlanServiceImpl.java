package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.assetmanage.AppMyPlanCustomizeVO;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.AppMyPlanService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version MyPlanServiceImpl, v0.1 2018/7/26 10:31
 */
@Service
public class AppMyPlanServiceImpl extends BaseTradeServiceImpl implements AppMyPlanService {
    @Override
    public Integer countAppMyPlan(AssetManageBeanRequest params) {
        return amTradeClient.countAppMyPlan(params);
    }

    @Override
    public List<AppMyPlanCustomizeVO> selectAppMyPlanList(AssetManageBeanRequest params) {
        return amTradeClient.selectAppMyPlanList(params);
    }
}
