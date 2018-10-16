/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin.locked;

import com.google.common.collect.Lists;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;

import java.util.List;

/**
 * @author cui
 * @version LockedUserMgrResponse, v0.1 2018/9/21 10:38
 */
public class LockedUserMgrResponse {

	private int count;

	private List<LockedUserInfoVO> resultList;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<LockedUserInfoVO> getResultList() {
		if (resultList == null) {
			resultList = Lists.newArrayList();
		}
		return resultList;
	}

	public void setResultList(List<LockedUserInfoVO> resultList) {
		this.resultList = resultList;
	}
}
