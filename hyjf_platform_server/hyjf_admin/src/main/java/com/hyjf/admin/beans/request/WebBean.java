package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.statistics.AccountWebListVO;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.common.paginator.Paginator;

import java.io.Serializable;
import java.util.List;

public class WebBean extends AccountWebListVO implements Serializable {

	/**
	 * serialVersionUID:
	 */
		
	private static final long serialVersionUID = 2561663838042185965L;
	
	
	private List<AccountWebListVO> recordList;
	
	private List<AccountTradeVO> tradeList;
	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private Paginator paginator;

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<AccountWebListVO> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<AccountWebListVO> recordList) {
		this.recordList = recordList;
	}

	public List<AccountTradeVO> getTradeList() {
		return tradeList;
	}

	public void setTradeList(List<AccountTradeVO> tradeList) {
		this.tradeList = tradeList;
	}
}















	