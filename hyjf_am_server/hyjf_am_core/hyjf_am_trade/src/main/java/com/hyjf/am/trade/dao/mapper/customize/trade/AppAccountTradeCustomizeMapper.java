package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.trade.dao.model.customize.app.AppAccountTradeListCustomize;

import java.util.List;

public interface AppAccountTradeCustomizeMapper {

	List<AppAccountTradeListCustomize> selectTradeTypeList();
}