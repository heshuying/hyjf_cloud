package com.hyjf.callcenter.service;

import com.hyjf.am.resquest.callcenter.CallCenterAccountDetailRequest;
import com.hyjf.am.vo.callcenter.CallCenterAccountDetailVO;

import java.util.List;

/**
 * @author wangjun
 * @version SrchCapitalInfoService, v0.1 2018/6/13 15:32
 */
public interface SrchCapitalInfoService {
	
	/**
	 * 资金明细（列表）
	 *
	 * @param callCenterAccountDetailRequest
	 * @return
	 */
	public List<CallCenterAccountDetailVO> queryAccountDetails(CallCenterAccountDetailRequest callCenterAccountDetailRequest);

}
