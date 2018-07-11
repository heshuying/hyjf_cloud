/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.FddTempletCustomizeVO;

/**
 * @author fuqiang
 * @version FddTempletCustomizeResponse, v0.1 2018/7/10 16:43
 */
public class FddTempletCustomizeResponse extends Response<FddTempletCustomizeVO> {
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
