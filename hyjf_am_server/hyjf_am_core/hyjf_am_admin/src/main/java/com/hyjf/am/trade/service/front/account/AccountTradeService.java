package com.hyjf.am.trade.service.front.account;

import com.hyjf.am.trade.dao.model.auto.AccountTrade;
import com.hyjf.am.trade.dao.model.customize.AppAccountTradeListCustomize;

import java.util.List;

/**
 * @author pangchengchao
 * @version AccountTradeService, v0.1 2018/6/27 11:10
 */
public interface AccountTradeService {
    List<AccountTrade> selectTradeTypes();

    List<AppAccountTradeListCustomize> searchAppTradeTypes();
}
