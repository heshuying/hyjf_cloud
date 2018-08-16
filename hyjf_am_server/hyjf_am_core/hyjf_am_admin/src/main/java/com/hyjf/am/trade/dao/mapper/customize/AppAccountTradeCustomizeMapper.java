package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.AppAccountTradeListCustomize;

import java.util.List;

public interface AppAccountTradeCustomizeMapper {

	List<AppAccountTradeListCustomize> selectTradeTypeList();
}