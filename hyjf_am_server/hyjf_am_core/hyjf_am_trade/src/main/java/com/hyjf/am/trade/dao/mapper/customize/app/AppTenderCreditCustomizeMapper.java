package com.hyjf.am.trade.dao.mapper.customize.app;

import com.hyjf.am.trade.dao.model.customize.app.AppTenderCreditInvestListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.AppTenderToCreditListCustomize;

import java.util.List;
import java.util.Map;

public interface AppTenderCreditCustomizeMapper {

    List<AppTenderToCreditListCustomize> selectTenderToCreditList(Map<String,Object> params);

    int countTenderCreditInvestRecordTotal(Map<String,Object> params);

    List<AppTenderCreditInvestListCustomize> searchTenderCreditInvestList(Map<String,Object> params);
}
