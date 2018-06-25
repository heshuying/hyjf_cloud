package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.trade.dao.model.customize.trade.CurrentHoldObligatoryRightListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.RepayMentListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.TenderCreditDetailCustomize;

import java.util.List;

/**
 * @author pangchengchao
 * @version AssetManageService, v0.1 2018/6/24 14:49
 */

public interface AssetManageService {
    List<CurrentHoldObligatoryRightListCustomize> selectCurrentHoldObligatoryRightList(AssetManageBeanRequest request);

    int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest request);

    int selectRepaymentListTotal(AssetManageBeanRequest request);

    int countCreditRecordTotal(AssetManageBeanRequest request);

    List<RepayMentListCustomize> selectRepaymentList(AssetManageBeanRequest request);

    List<TenderCreditDetailCustomize> selectCreditRecordList(AssetManageBeanRequest request);
}
