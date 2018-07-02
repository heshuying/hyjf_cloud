package com.hyjf.am.trade.dao.mapper.customize.batch;

import com.hyjf.am.trade.dao.model.auto.HjhAccede;

public interface BatchHjhAccedeCustomizeMapper {

	/**
	 * 
	 * 计划放款后计划订单冻结金额变化
	 * @param accede
	 * @return
	 */
    int updateOfPlanLoansTender(HjhAccede accede);

    /**
     * 
     * 更新待收收益
     * @param hjhAccede
     * @return
     */
    int updateInterest(HjhAccede hjhAccede);
}