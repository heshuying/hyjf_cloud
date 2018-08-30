/**
 * Description:用户开户记录列表前端查询所用vo
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.admin.UserTransferVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 */

public class TransferListRequest extends UserTransferVO implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 7768418442884796575L;
	/** 操作时间开始值 */
	@ApiModelProperty(value = "操作时间开始值")
	private String opreateTimeStart;
	/** 操作时间结束值 */
	@ApiModelProperty(value = "操作时间结束值")
	private String opreateTimeEnd;
	/** 转账时间开始值 */
	@ApiModelProperty(value = "转账时间开始值")
	private String transferTimeStart;
	/** 转账时间结束值 */
	@ApiModelProperty(value = "转账时间结束值")
	private String transferTimeEnd;

	@ApiModelProperty(value = "")
	private String outUserNameSrch;

	@ApiModelProperty(value = "订单号")
	private String orderIdSrch;

	@ApiModelProperty(value = "状态")
	private String statusSrch;

	@ApiModelProperty(value = "")
	private String reconciliationIdSrch;

	@ApiModelProperty(value = "类型")
	private String transferTypeSrch;
	@ApiModelProperty(value = "列表")
	private List<UserTransferVO> recordList;

	/**
	 * 当前页码
	 */
	@ApiModelProperty(value = "当前页")
	private int currPage;

	/**
	 * 当前页条数
	 */
	@ApiModelProperty(value = "当前页条数")
	private int pageSize;

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;



	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public String getOpreateTimeStart() {
		return opreateTimeStart;
	}

	public void setOpreateTimeStart(String opreateTimeStart) {
		this.opreateTimeStart = opreateTimeStart;
	}

	public String getOpreateTimeEnd() {
		return opreateTimeEnd;
	}

	public void setOpreateTimeEnd(String opreateTimeEnd) {
		this.opreateTimeEnd = opreateTimeEnd;
	}

	public String getTransferTimeStart() {
		return transferTimeStart;
	}

	public void setTransferTimeStart(String transferTimeStart) {
		this.transferTimeStart = transferTimeStart;
	}

	public String getTransferTimeEnd() {
		return transferTimeEnd;
	}

	public void setTransferTimeEnd(String transferTimeEnd) {
		this.transferTimeEnd = transferTimeEnd;
	}

	public List<UserTransferVO> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<UserTransferVO> recordList) {
		this.recordList = recordList;
	}

	public String getOutUserNameSrch() {
		return outUserNameSrch;
	}

	public void setOutUserNameSrch(String outUserNameSrch) {
		this.outUserNameSrch = outUserNameSrch;
	}

	public String getOrderIdSrch() {
		return orderIdSrch;
	}

	public void setOrderIdSrch(String orderIdSrch) {
		this.orderIdSrch = orderIdSrch;
	}

	public String getStatusSrch() {
		return statusSrch;
	}

	public void setStatusSrch(String statusSrch) {
		this.statusSrch = statusSrch;
	}

	public String getReconciliationIdSrch() {
		return reconciliationIdSrch;
	}

	public void setReconciliationIdSrch(String reconciliationIdSrch) {
		this.reconciliationIdSrch = reconciliationIdSrch;
	}

	public String getTransferTypeSrch() {
		return transferTypeSrch;
	}

	public void setTransferTypeSrch(String transferTypeSrch) {
		this.transferTypeSrch = transferTypeSrch;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
