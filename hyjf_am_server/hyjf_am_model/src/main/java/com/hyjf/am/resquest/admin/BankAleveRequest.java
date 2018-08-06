/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.resquest.Request;
import com.hyjf.common.paginator.Paginator;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zdj
 * @version PushMoneyRequest, v0.1 2018/7/10 19:26
 */
public class BankAleveRequest extends Request implements Serializable {


	//自动同步用生成订单id
	private String orderId;
	//userid
	private Integer userId;
	//入账日期起止
	private String startValdate;
	private String endValdate;

	//交易日期起止
	private String startInpdate;
	private String endInpdate;

	//自然日期起止
	private String startReldate;
	private String endReldate;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getStartValdate() {
		return startValdate;
	}

	public void setStartValdate(String startValdate) {
		this.startValdate = startValdate;
	}

	public String getEndValdate() {
		return endValdate;
	}

	public void setEndValdate(String endValdate) {
		this.endValdate = endValdate;
	}

	public String getStartInpdate() {
		return startInpdate;
	}

	public void setStartInpdate(String startInpdate) {
		this.startInpdate = startInpdate;
	}

	public String getEndInpdate() {
		return endInpdate;
	}

	public void setEndInpdate(String endInpdate) {
		this.endInpdate = endInpdate;
	}

	public String getStartReldate() {
		return startReldate;
	}

	public void setStartReldate(String startReldate) {
		this.startReldate = startReldate;
	}

	public String getEndReldate() {
		return endReldate;
	}

	public void setEndReldate(String endReldate) {
		this.endReldate = endReldate;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	public int limit;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private com.hyjf.common.paginator.Paginator paginator;

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}
}
