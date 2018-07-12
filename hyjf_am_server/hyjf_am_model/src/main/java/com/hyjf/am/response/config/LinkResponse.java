/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.LinkVO;

/**
 * @author fuqiang
 * @version LinkResponse, v0.1 2018/7/12 10:13
 */
public class LinkResponse extends Response<LinkVO> {
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
