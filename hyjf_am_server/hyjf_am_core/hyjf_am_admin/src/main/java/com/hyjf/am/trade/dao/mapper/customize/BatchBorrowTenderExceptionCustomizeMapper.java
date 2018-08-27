package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.trade.dao.model.customize.BatchBorrowTenderCustomize;

/**
 * 
 * @author jijun 20180623 投资掉单异常处理类
 *
 */
public interface BatchBorrowTenderExceptionCustomizeMapper {

	/**
	 * 查询掉单的投资记录
	 * 
	 * @return
	 */
	List<BatchBorrowTenderCustomize> queryAuthCodeBorrowTenderList();

}
