package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.trade.dao.model.customize.trade.WebUserRechargeListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebUserTradeListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebUserWithdrawListCustomize;

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
}
