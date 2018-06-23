package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BatchBorrowTenderCustomize;

import java.util.List;

/**
 * 
 * @author jijun 20180623
 * 投资掉单异常处理类
 *
 */
public interface BatchBorrowTenderExceptionCustomizeMapper {

	/**
	 * 查询掉单的投资记录
	 * @return
	 */
	public List<BatchBorrowTenderCustomize> queryAuthCodeBorrowTenderList();
	
}
