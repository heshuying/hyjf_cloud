package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.callcenter.CallCenterAccountDetailRequest;
import com.hyjf.am.trade.dao.model.customize.CallCenterAccountDetailCustomize;


public interface CallCenterAccountDetailCustomizeMapper {
	/**
	 *
	 * 查询资金明细
	 * @author wangjun
	 * @param callCenterAccountDetailRequest
	 * @return
	 */
	public List<CallCenterAccountDetailCustomize> queryAccountDetails(CallCenterAccountDetailRequest callCenterAccountDetailRequest);

}
