/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.ContentQualifyVO;

/**
 * @author fuqiang
 * @version ContentQualifyResponse, v0.1 2018/7/11 9:35
 */
public class ContentQualifyResponse extends Response<ContentQualifyVO> {
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
