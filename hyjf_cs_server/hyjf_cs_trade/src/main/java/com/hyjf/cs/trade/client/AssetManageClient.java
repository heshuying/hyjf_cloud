package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldPlanListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentPlanListCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version AssetManageClient, v0.1 2018/6/24 11:14
 */
public interface AssetManageClient {
    List<CurrentHoldObligatoryRightListCustomizeVO> selectCurrentHoldObligatoryRightList(AssetManageBeanRequest request);

    int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest request);

    int selectRepaymentListTotal(AssetManageBeanRequest request);

    int countCreditRecordTotal(AssetManageBeanRequest request);

    List<TenderAgreementVO> selectTenderAgreementByNid(String nid);

    List<RepayMentListCustomizeVO> selectRepaymentList(AssetManageBeanRequest request);

    List<TenderCreditDetailCustomizeVO> selectCreditRecordList(AssetManageBeanRequest request);

    int countCurrentHoldPlanTotal(AssetManageBeanRequest request);

    List<CurrentHoldPlanListCustomizeVO> selectCurrentHoldPlanList(AssetManageBeanRequest request);

    Integer countRepayMentPlanTotal(AssetManageBeanRequest request);

    List<RepayMentPlanListCustomizeVO> selectRepayMentPlanList(AssetManageBeanRequest request);

}
