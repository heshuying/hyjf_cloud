package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.trade.dao.model.customize.AppTenderCreditInvestListCustomize;
import com.hyjf.am.trade.dao.model.customize.AppTenderToCreditListCustomize;

public interface AppTenderCreditCustomizeMapper {

    List<AppTenderToCreditListCustomize> selectTenderToCreditList(Map<String,Object> params);

    int countTenderCreditInvestRecordTotal(Map<String,Object> params);

    List<AppTenderCreditInvestListCustomize> searchTenderCreditInvestList(Map<String,Object> params);
}
