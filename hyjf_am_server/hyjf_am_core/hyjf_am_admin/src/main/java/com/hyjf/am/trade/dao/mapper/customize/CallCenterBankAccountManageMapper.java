package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.callcenter.CallCenterBankAccountManageRequest;
import com.hyjf.am.trade.dao.model.customize.CallCenterBankAccountManageCustomize;

public interface CallCenterBankAccountManageMapper {
	/**
	 * 获取列表
	 * @param borrowCreditCustomize
	 * @return
	 */
	List<CallCenterBankAccountManageCustomize> queryAccountInfos(CallCenterBankAccountManageRequest callCenterBankAccountManageRequest);
}
