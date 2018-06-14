/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.callcenter;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.callcenter.CallCenterServiceUsersVO;

import java.util.List;

/**
 * @author wangjun
 * @version CallCenterServiceUsersRequest, v0.1 2018/6/13 11:01
 */
public class CallCenterServiceUsersRequest extends BaseVO  {
	private List<CallCenterServiceUsersVO> callCenterServiceUsersVOList;

	public List<CallCenterServiceUsersVO> getCallCenterServiceUsersVOList() {
		return callCenterServiceUsersVOList;
	}

	public void setCallCenterServiceUsersVOList(List<CallCenterServiceUsersVO> callCenterServiceUsersVOList) {
		this.callCenterServiceUsersVOList = callCenterServiceUsersVOList;
	}
}
