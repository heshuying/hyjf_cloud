package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class WebBean extends AccountWebListVO implements Serializable {

	/**
	 * serialVersionUID:
	 */
		
	private static final long serialVersionUID = 2561663838042185965L;
	
	@ApiModelProperty(value = "网站收支列表")
	private List<AccountWebListVO> recordList;

	@ApiModelProperty(value = "交易类型列表")
	private List<AccountTradeVO> tradeList;
	/**
	 * 翻页机能用的隐藏变量
	 */
	@ApiModelProperty(value = "翻页机能用的隐藏变量")
	private int paginatorPage = 1;

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	@ApiModelProperty(value = "列表画面自定义标签上的用翻页对象")
	private Paginator paginator;

	@ApiModelProperty(value = "是否具有组织架构查看权限")
	private String isOrganizationView;

	@Override
    public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	@Override
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

	public String getIsOrganizationView() {
		return isOrganizationView;
	}

	public void setIsOrganizationView(String isOrganizationView) {
		this.isOrganizationView = isOrganizationView;
	}
}















	