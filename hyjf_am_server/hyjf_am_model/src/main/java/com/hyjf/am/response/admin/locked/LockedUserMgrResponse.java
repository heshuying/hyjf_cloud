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
public class LockedUserMgrResponse extends Response<LockedUserInfoVO> {

	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public List<LockedUserInfoVO> getResultList() {
		if (super.getResultList() == null) {
			List<LockedUserInfoVO> resultList = Lists.newArrayList();
			setResultList(resultList);
		}
		return super.getResultList();
	}
}
