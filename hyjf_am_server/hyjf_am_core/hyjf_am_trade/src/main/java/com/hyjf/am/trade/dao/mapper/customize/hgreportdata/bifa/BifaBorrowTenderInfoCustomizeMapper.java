/**
 * 出借信息
 */

package com.hyjf.am.trade.dao.mapper.customize.hgreportdata.bifa;

import com.hyjf.am.trade.dao.model.bifa.UserIdAccountSumBean;
import com.hyjf.am.trade.dao.model.customize.BorrowTenderInfoCustomize;

import java.util.List;
import java.util.Map;


public interface BifaBorrowTenderInfoCustomizeMapper {

	/**
	 * 已开户且出借>0的用户
	 * @param startDate
	 * @param endDate
	 * @return
	 */
    List<UserIdAccountSumBean> getBorrowTenderAccountSum(Integer startDate, Integer endDate);
}
