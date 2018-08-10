/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.PushMoneyVO;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author fuqiang
 * @version PushMoneyResponse, v0.1 2018/7/10 11:01
 */
public class PushMoneyResponse extends Response<PushMoneyVO> {
	private int count;

	private Map<String,Object> map;

	private int cuttype;

	private int cnt;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public int getCuttype() {
		return cuttype;
	}

	public void setCuttype(int cuttype) {
		this.cuttype = cuttype;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
}
