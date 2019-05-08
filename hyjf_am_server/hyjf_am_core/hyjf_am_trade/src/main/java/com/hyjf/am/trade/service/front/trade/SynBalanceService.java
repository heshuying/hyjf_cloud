package com.hyjf.am.trade.service.front.trade;

import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.resquest.trade.SynBalanceBeanRequest;
import com.hyjf.am.trade.dao.model.auto.UnderLineRecharge;

import java.util.List;

/**
 * @author pangchengchao
 * @version SynBalanceService, v0.1 2018/6/20 9:58
 */
public interface SynBalanceService {
    boolean insertAccountDetails(SynBalanceBeanRequest synBalanceBeanRequest);

    List<UnderLineRecharge> getUnderLineRechargeListByCode(UnderLineRechargeRequest request);
}
