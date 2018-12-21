package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.BatchBorrowTenderCustomize;

import java.util.List;

/**
 * 
 * @author jijun 20180623 出借掉单异常处理类
 *
 */
public interface BatchBorrowTenderExceptionCustomizeMapper {

	/**
	 * 查询掉单的出借记录
	 * 
	 * @return
	 */
	List<BatchBorrowTenderCustomize> queryAuthCodeBorrowTenderList();

}
