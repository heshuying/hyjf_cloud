package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.trade.dao.model.customize.trade.WebUserRechargeListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebUserTradeListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebUserWithdrawListCustomize;

import java.util.List;

/**
 * @author pangchengchao
 * @version TradeDetailService, v0.1 2018/6/27 15:45
 */
public interface TradeDetailService  extends BaseService {
    List<WebUserTradeListCustomize> searchUserTradeList(TradeDetailBeanRequest request);

    int countUserTradeRecordTotal(TradeDetailBeanRequest request);

    List<WebUserRechargeListCustomize> searchUserRechargeList(TradeDetailBeanRequest request);

    int countUserRechargeRecordTotal(TradeDetailBeanRequest request);

    List<WebUserWithdrawListCustomize> searchUserWithdrawList(TradeDetailBeanRequest request);

    int countUserWithdrawRecordTotal(TradeDetailBeanRequest request);
}
