package com.hyjf.cs.trade.service.myproject;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.assetmanage.AppAlreadyRepayListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.AppTenderCreditRecordListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.cs.trade.service.BaseTradeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author pangchengchao
 * @version AssetManageService, v0.1 2018/6/20 17:31
 */
public interface AppMyProjectService extends BaseTradeService {

    boolean isAllowChannelAttorn(Integer userId);

    int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest params);

    List<CurrentHoldObligatoryRightListCustomizeVO> selectAppCurrentHoldObligatoryRightList(AssetManageBeanRequest params);

    int countAlreadyRepayListRecordTotal(AssetManageBeanRequest params);

    List<AppAlreadyRepayListCustomizeVO> selectAppAlreadyRepayList(AssetManageBeanRequest params);

    int countCreditRecord(AssetManageBeanRequest params);

    List<AppTenderCreditRecordListCustomizeVO> searchAppCreditRecordList(AssetManageBeanRequest params);

    Integer selectTenderToCreditListCount(AssetManageBeanRequest params);

    JSONObject getMyProjectDetail(String borrowNid, HttpServletRequest request, String userId) ;
}
