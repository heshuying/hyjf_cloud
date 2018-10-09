package com.hyjf.am.trade.dao.mapper.customize.admin;


import com.hyjf.am.trade.dao.model.auto.IncreaseInterestInvestExample;

/**
 * @author PC-LIUSHOUYI
 */

public interface IncreaseInterestInvestCustomizeMapper {

	/**
	 * 取得合计金额
	 * 
	 * @param example
	 * @return
	 */
	public String sumAccount(IncreaseInterestInvestExample example);
}

	