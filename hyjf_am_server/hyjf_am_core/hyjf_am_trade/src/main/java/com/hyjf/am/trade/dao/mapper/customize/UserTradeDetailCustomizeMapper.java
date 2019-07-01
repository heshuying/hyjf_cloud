package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.AppTradeListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserRechargeListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserTradeListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserWithdrawListCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version UserTradeDetailCustomizeMapper, v0.1 2018/6/27 16:05
 */
public interface UserTradeDetailCustomizeMapper {
    List<WebUserTradeListCustomize> selectUserTradeList(Map<String, Object> params);

    int countUserTradeRecordTotal(Map<String, Object> params);

    List<WebUserRechargeListCustomize> searchUserRechargeList(Map<String, Object> params);

    int countUserRechargeRecordTotal(Map<String, Object> params);

    List<WebUserWithdrawListCustomize> selectUserWithdrawList(Map<String, Object> params);

    int countUserWithdrawRecordTotal(Map<String, Object> params);


    int countTradeDetailListRecordTotal(Map<String, Object> params);

    List<AppTradeListCustomize> searchAppTradeDetailList(Map<String,Object> params);

    String getPlanNameByAccedeOrderId(String accedeOrderId);
}
