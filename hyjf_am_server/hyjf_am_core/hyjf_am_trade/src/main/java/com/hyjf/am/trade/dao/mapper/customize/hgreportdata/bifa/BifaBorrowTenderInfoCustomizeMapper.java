/**
 * 出借信息
 */

package com.hyjf.am.trade.dao.mapper.customize.hgreportdata.bifa;

import com.hyjf.am.trade.dao.model.bifa.UserIdAccountSumBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BifaBorrowTenderInfoCustomizeMapper {

	/**
	 * 已开户且出借>0的用户
	 * @param startDate
	 * @param endDate
	 * @return
	 */
    List<UserIdAccountSumBean> getBorrowTenderAccountSum(@Param("startDate") Integer startDate,@Param("endDate") Integer endDate);
}
