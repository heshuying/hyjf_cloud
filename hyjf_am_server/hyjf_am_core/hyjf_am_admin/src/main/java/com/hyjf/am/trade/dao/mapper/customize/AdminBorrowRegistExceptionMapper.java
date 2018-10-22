package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.trade.dao.model.customize.BorrowRegistCustomize;

import java.util.List;

public interface AdminBorrowRegistExceptionMapper {

	/**
	 * 获取借款列表
	 * 
	 * @param borrowRegistListRequest
	 * @return
	 */
	List<BorrowRegistCustomize> selectBorrowList(BorrowRegistListRequest borrowRegistListRequest);

	/**
	 * COUNT
	 * 
	 * @param borrowRegistListRequest
	 * @return
	 */
	Integer countBorrow(BorrowRegistListRequest borrowRegistListRequest);

}