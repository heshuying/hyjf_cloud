package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.trade.dao.model.customize.AppAccountTradeListCustomize;

public interface AppAccountTradeCustomizeMapper {

	List<AppAccountTradeListCustomize> selectTradeTypeList();
}