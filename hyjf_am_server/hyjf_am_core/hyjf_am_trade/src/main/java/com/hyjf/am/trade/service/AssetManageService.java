package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.WechatMyProjectRequest;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.dao.model.customize.trade.*;
import com.hyjf.am.vo.trade.assetmanage.QueryMyProjectVO;

import java.util.List;

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

    List<CurrentHoldObligatoryRightListCustomize> selectAppCurrentHoldObligatoryRightList(AssetManageBeanRequest request);

    List<AppAlreadyRepayListCustomize> selectAppAlreadyRepayList(AssetManageBeanRequest request);

    List<AppTenderCreditRecordListCustomize> searchAppCreditRecordList(AssetManageBeanRequest request);
}
