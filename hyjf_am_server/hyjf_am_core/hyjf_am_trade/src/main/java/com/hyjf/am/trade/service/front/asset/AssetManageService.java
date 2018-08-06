package com.hyjf.am.trade.service.front.asset;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.WechatMyProjectRequest;
import com.hyjf.am.trade.dao.model.customize.app.AppAlreadyRepayListCustomize;
import com.hyjf.am.trade.dao.model.customize.app.AppTenderCreditRecordListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.*;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.assetmanage.AppMyPlanCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.QueryMyProjectVO;

import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version AssetManageService, v0.1 2018/6/24 14:49
 */

public interface AssetManageService  extends BaseService {
    List<CurrentHoldObligatoryRightListCustomize> selectCurrentHoldObligatoryRightList(AssetManageBeanRequest request);

    int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest request);

    int selectRepaymentListTotal(AssetManageBeanRequest request);

    int countCreditRecordTotal(AssetManageBeanRequest request);

    List<RepayMentListCustomize> selectRepaymentList(AssetManageBeanRequest request);

    List<TenderCreditDetailCustomize> selectCreditRecordList(AssetManageBeanRequest request);

    List<CurrentHoldPlanListCustomize> selectCurrentHoldPlanList(AssetManageBeanRequest request);

    int countCurrentHoldPlanTotal(AssetManageBeanRequest request);

    List<RepayMentPlanListCustomize> selectRepayMentPlanList(AssetManageBeanRequest request);

    int countRepayMentPlanTotal(AssetManageBeanRequest request);

    QueryMyProjectVO selectWechatCurrentHoldObligatoryRightList(WechatMyProjectRequest request);

    QueryMyProjectVO selectWechatRepaymentList(WechatMyProjectRequest request);

    QueryMyProjectVO selectWechatCreditRecordList(WechatMyProjectRequest request);

    QueryMyProjectVO selectWechatCurrentHoldPlanList(WechatMyProjectRequest request);

    QueryMyProjectVO selectWechatRepayMentPlanList(WechatMyProjectRequest request);

    List<AppAlreadyRepayListCustomize> selectAppAlreadyRepayList(AssetManageBeanRequest request);

    List<AppTenderCreditRecordListCustomize> searchAppCreditRecordList(AssetManageBeanRequest request);

    int selectTenderToCreditListCount(AssetManageBeanRequest request);

    List<AppMyPlanCustomize> selectAppMyPlanList(AssetManageBeanRequest request);

    int countAppMyPlan(AssetManageBeanRequest request);

    List<AppAlreadyRepayListCustomize> selectAlreadyRepayList(AssetManageBeanRequest request);

    List<AppTenderToCreditListCustomize> selectTenderToCreditList(Map<String,Object> params);
}
