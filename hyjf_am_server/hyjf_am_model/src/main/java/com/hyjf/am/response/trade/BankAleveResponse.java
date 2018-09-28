/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BankAleveVO;

/**
 * @author zdj
 * @version BankAleveResponse, v0.1 2018/7/10 11:01
 */
public class BankAleveResponse extends Response<BankAleveVO> {
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
