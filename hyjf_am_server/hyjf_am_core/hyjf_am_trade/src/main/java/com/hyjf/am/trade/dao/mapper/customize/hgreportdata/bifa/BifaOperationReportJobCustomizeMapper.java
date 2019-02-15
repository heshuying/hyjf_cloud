package com.hyjf.am.trade.dao.mapper.customize.hgreportdata.bifa;

import java.util.Date;

/**
 * 统计运营报告的相关数据
 * 
 * @author tanyy
 *
 */
public interface BifaOperationReportJobCustomizeMapper {

	/**
	 * 累计借贷余额笔数
	 * @return
	 */
	int getLoanBalanceNum();

}
