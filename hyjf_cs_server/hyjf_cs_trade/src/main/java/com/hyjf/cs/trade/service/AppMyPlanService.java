package com.hyjf.cs.trade.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.assetmanage.AppMyPlanCustomizeVO;
import com.hyjf.cs.trade.bean.app.MyPlanDetailResultBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author pangchengchao
 * @version MyPlanService, v0.1 2018/7/26 10:30
 */
public interface AppMyPlanService extends BaseTradeService{
    Integer countAppMyPlan(AssetManageBeanRequest params);

    List<AppMyPlanCustomizeVO> selectAppMyPlanList(AssetManageBeanRequest params);

    MyPlanDetailResultBean getMyPlanDetail(Integer couponType, String type, String orderId, HttpServletRequest request, String userId);

}
