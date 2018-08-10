package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.callcenter.SrchTransferInfoRequest;
import com.hyjf.am.trade.dao.model.customize.CallCenterBorrowCreditCustomize;

public interface CallcenterBorrowCreditCustomizeMapper {
	/**
	 * 获取列表
	 * @param borrowCreditCustomize
	 * @return
	 */
	List<CallCenterBorrowCreditCustomize> getBorrowCreditList(SrchTransferInfoRequest srchTransferInfoRequest);
}
