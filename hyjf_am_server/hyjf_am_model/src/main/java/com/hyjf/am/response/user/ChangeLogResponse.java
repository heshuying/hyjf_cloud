/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.ChangeLogVO;

/**
 * @author DongZeShan
 * @version AdminUserAuthListResponse.java, v0.1 2018年6月24日 下午2:40:41
 */
public class ChangeLogResponse extends Response<ChangeLogVO> {
	private int recordTotal;

	public int getRecordTotal() {
		return recordTotal;
	}

	public void setRecordTotal(int recordTotal) {
		this.recordTotal = recordTotal;
	}
}
