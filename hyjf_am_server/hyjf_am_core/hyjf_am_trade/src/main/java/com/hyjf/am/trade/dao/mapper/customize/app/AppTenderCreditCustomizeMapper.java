package com.hyjf.am.trade.dao.mapper.customize.app;

import com.hyjf.am.trade.dao.model.customize.trade.AppTenderToCreditListCustomize;

import java.util.List;
import java.util.Map;

public interface AppTenderCreditCustomizeMapper {

    List<AppTenderToCreditListCustomize> selectTenderToCreditList(Map<String,Object> params);
}
