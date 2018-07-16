package com.hyjf.am.trade.dao.mapper.customize.trade;


import com.hyjf.am.trade.dao.model.customize.admin.BorrowInvestCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.AutoReqRepayBorrowCustomize;

import java.util.List;

public interface AutoReqRepayBorrowCustomizeMapper {

	/**
	 * 当然应还款列表
	 * 
	 * @param borrowInvestCustomize
	 * @return
	 */
	List<BorrowInvestCustomize> selectBorrowInvestList(BorrowInvestCustomize borrowInvestCustomize);

	/**
	 * 投资明细记录 总数COUNT
	 * 
	 * @param borrowInvestCustomize
	 * @return
	 */
	Long countBorrowInvest(BorrowInvestCustomize borrowInvestCustomize);

	/**
	 * 导出投资明细列表
	 * 
	 * @param borrowInvestCustomize
	 * @return
	 */
	List<BorrowInvestCustomize> exportBorrowInvestList(BorrowInvestCustomize borrowInvestCustomize);

	/**
	 * 投资金额合计
	 * 
	 * @param borrowInvestCustomize
	 * @return
	 */
	String sumBorrowInvestAccount(BorrowInvestCustomize borrowInvestCustomize);

	/**
	 * 取得本日应还款标的列表
	 * @return
	 */
	List<AutoReqRepayBorrowCustomize> getAutoReqRepayBorrow();

}