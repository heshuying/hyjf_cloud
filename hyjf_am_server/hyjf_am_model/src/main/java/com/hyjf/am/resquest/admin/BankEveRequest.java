/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author fuqiang
 * @version BankEveRequest, v0.1 2018/7/10 19:26
 */
public class BankEveRequest extends BasePage implements Serializable {

	@ApiModelProperty(value = "主账号检索")
	private String cardnbr;

	@ApiModelProperty(value = "系统跟踪号检索")
	private Integer seqno;

	@ApiModelProperty(value = "交易传输时间 起始")
	private String startDate;
	@ApiModelProperty(value = "交易传输时间 结束")
	private String endDate;



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
	private Paginator paginator;
	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public String getCardnbr() {
		return cardnbr;
	}

	public void setCardnbr(String cardnbr) {
		this.cardnbr = cardnbr;
	}

	public Integer getSeqno() {
		return seqno;
	}

	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
