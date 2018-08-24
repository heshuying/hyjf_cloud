/**
 * 投资信息
 */

package com.hyjf.am.trade.dao.mapper.customize;

import java.util.Map;

import com.hyjf.am.trade.dao.model.customize.BorrowTenderInfoCustomize;


public interface BorrowTenderInfoCustomizeMapper {
	
	/**
	 * 取得标的信息
	 * @param paramMap
	 * @return
	 */
	BorrowTenderInfoCustomize getBorrowTenderInfo(Map<String, Object> paramMap);

	/**
	 * 取得标的信息
	 * @param paramMap
	 * @return
	 */
	Integer getCouponProfitTime(Map<String, Object> paramMap);
	
	/**
     * 根据borrowNid查询所发标的投资总金额
     * @param params
     * @return
     */
	String countMoneyByBorrowId(Map<String, Object> params);

}
