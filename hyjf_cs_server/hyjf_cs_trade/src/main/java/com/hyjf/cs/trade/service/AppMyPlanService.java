package com.hyjf.cs.trade.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.assetmanage.AppMyPlanCustomizeVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author pangchengchao
 * @version MyPlanService, v0.1 2018/7/26 10:30
 */
public interface AppMyPlanService extends BaseTradeService{
    Integer countAppMyPlan(AssetManageBeanRequest params);

    List<AppMyPlanCustomizeVO> selectAppMyPlanList(AssetManageBeanRequest params);

    JSONObject getMyPlanDetail(String borrowNid, HttpServletRequest request, String userId);
}
