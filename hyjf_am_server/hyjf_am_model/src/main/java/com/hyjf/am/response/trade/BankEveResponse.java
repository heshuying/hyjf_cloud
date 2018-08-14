/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BankAleveVO;
import com.hyjf.am.vo.admin.BankEveVO;

/**
 * @author zdj
 * @version BankAleveResponse, v0.1 2018/7/10 11:01
 */
public class BankEveResponse extends Response<BankEveVO> {
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
