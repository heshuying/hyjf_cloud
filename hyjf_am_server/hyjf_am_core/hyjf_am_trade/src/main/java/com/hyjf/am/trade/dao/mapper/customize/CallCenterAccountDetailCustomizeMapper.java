package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.callcenter.CallCenterAccountDetailRequest;
import com.hyjf.am.trade.dao.model.customize.CallCenterAccountDetailCustomize;

import java.util.List;


public interface CallCenterAccountDetailCustomizeMapper {
	/**
	 *
	 * 查询资金明细
	 * @author wangjun
	 * @param callCenterAccountDetailRequest
	 * @return
	 */
	List<CallCenterAccountDetailCustomize> queryAccountDetails(CallCenterAccountDetailRequest callCenterAccountDetailRequest);


	/**
	 * 测评获取冻结金额和代收本经明细
	 *
	 * @param callCenterAccountDetailRequest
	 * @return
	 */
	CallCenterAccountDetailCustomize queryAccountEvalDetail(CallCenterAccountDetailRequest callCenterAccountDetailRequest);

}
