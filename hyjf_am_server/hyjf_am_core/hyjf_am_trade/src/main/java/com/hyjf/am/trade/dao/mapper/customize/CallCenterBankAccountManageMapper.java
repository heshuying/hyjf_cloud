package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.callcenter.CallCenterBankAccountManageRequest;
import com.hyjf.am.trade.dao.model.customize.CallCenterBankAccountManageCustomize;

import java.util.List;

public interface CallCenterBankAccountManageMapper {
	/**
	 * 获取列表
	 * @param borrowCreditCustomize
	 * @return
	 */
	List<CallCenterBankAccountManageCustomize> queryAccountInfos(CallCenterBankAccountManageRequest callCenterBankAccountManageRequest);
}
