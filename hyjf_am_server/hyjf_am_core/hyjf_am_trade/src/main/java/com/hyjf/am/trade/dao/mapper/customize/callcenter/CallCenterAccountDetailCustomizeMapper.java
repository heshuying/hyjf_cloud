package com.hyjf.am.trade.dao.mapper.customize.callcenter;

import com.hyjf.am.resquest.callcenter.CallCenterAccountDetailRequest;
import com.hyjf.am.trade.dao.model.customize.callcenter.CallCenterAccountDetailCustomize;

import java.util.List;


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
