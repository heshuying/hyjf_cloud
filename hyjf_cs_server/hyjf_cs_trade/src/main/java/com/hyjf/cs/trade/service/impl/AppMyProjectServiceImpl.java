package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.assetmanage.AppAlreadyRepayListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.AppTenderCreditRecordListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.cs.trade.service.AppMyProjectService;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version AppMyprojectServiceImpl, v0.1 2018/7/25 14:13
 */
@Service
public class AppMyProjectServiceImpl extends BaseTradeServiceImpl implements AppMyProjectService {

    @Override
    public boolean isAllowChannelAttorn(Integer userId) {
        // 根据userId获取用户注册推广渠道
        UtmPlatVO utmPlat = amUserClient.selectUtmPlatByUserId(userId);
        if (utmPlat != null && utmPlat.getAttornFlag() == 0)
            return false;
        return true;
    }

    @Override
    public int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest params) {
        return amTradeClient.selectCurrentHoldObligatoryRightListTotal(params);
    }

    @Override
    public List<CurrentHoldObligatoryRightListCustomizeVO> selectAppCurrentHoldObligatoryRightList(AssetManageBeanRequest params) {
        return amTradeClient.selectCurrentHoldObligatoryRightList(params);
    }

    @Override
    public int countAlreadyRepayListRecordTotal(AssetManageBeanRequest params) {
        return amTradeClient.selectRepaymentListTotal(params);
    }

    @Override
    public List<AppAlreadyRepayListCustomizeVO> selectAppAlreadyRepayList(AssetManageBeanRequest params) {
        return amTradeClient.selectAppAlreadyRepayList(params);
    }

    @Override
    public int countCreditRecord(AssetManageBeanRequest params) {
        return amTradeClient.countCreditRecordTotal(params);
    }

    @Override
    public List<AppTenderCreditRecordListCustomizeVO> searchAppCreditRecordList(AssetManageBeanRequest params) {
        return amTradeClient.searchAppCreditRecordList(params);
    }

    @Override
    public Integer selectTenderToCreditListCount(AssetManageBeanRequest params) {
        return amTradeClient.selectTenderToCreditListCount(params);
    }
}
