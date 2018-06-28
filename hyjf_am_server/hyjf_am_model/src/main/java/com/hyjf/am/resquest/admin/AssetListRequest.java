package com.hyjf.am.resquest.admin;

import java.io.Serializable;

/**
 * @author libin
 * @version AssetListRequest, v0.1 2018/6/27 
 */
public class AssetListRequest implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	// 资产编号
	String assetIdSrch;
	// 资产来源
	String instCodeSrch;
	// 产品类型
	String assetTypeSrch;
	// 项目编号
	String borrowNidSrch;
	// 计划编号
	String planNidSrch;
	// 用户名
	String userNameSrch;
	//标的标签
	String labelNameSrch;
	// 开户状态
	String bankOpenAccountSrch;
	// 审核状态
	String verifyStatusSrch;
	// 项目状态
	String statusSrch;
	// 推送时间
	String recieveTimeStartSrch;
	// 推送时间
	String recieveTimeEndSrch;
	/*private List<AssetListCustomize> recordList;*/
	// 检索条件 limitStart
	private int limitStart = -1;
	// 检索条件 limitEnd
	private int limitEnd = -1;
/*	*//**
	 * 翻页机能用的隐藏变量
	 *//*
	private int paginatorPage = 1;*/

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	/*private Paginator paginator;*/

/*	public int getPaginatorPage() {
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
	}*/

	/**
	 * assetIdSrch
	 * @return the assetIdSrch
	 */
	public String getAssetIdSrch() {
		return assetIdSrch;
	}

	/**
	 * @param assetIdSrch the assetIdSrch to set
	 */
	public void setAssetIdSrch(String assetIdSrch) {
		this.assetIdSrch = assetIdSrch;
	}

	/**
	 * instCodeSrch
	 * @return the instCodeSrch
	 */
	public String getInstCodeSrch() {
		return instCodeSrch;
	}

	/**
	 * @param instCodeSrch the instCodeSrch to set
	 */
	public void setInstCodeSrch(String instCodeSrch) {
		this.instCodeSrch = instCodeSrch;
	}

	/**
	 * assetTypeSrch
	 * @return the assetTypeSrch
	 */
	public String getAssetTypeSrch() {
		return assetTypeSrch;
	}

	/**
	 * @param assetTypeSrch the assetTypeSrch to set
	 */
	public void setAssetTypeSrch(String assetTypeSrch) {
		this.assetTypeSrch = assetTypeSrch;
	}

	/**
	 * borrowNidSrch
	 * @return the borrowNidSrch
	 */
	public String getBorrowNidSrch() {
		return borrowNidSrch;
	}

	/**
	 * @param borrowNidSrch the borrowNidSrch to set
	 */
	public void setBorrowNidSrch(String borrowNidSrch) {
		this.borrowNidSrch = borrowNidSrch;
	}

	/**
	 * planNidSrch
	 * @return the planNidSrch
	 */
	public String getPlanNidSrch() {
		return planNidSrch;
	}

	/**
	 * @param planNidSrch the planNidSrch to set
	 */
	public void setPlanNidSrch(String planNidSrch) {
		this.planNidSrch = planNidSrch;
	}

	/**
	 * userNameSrch
	 * @return the userNameSrch
	 */
	public String getUserNameSrch() {
		return userNameSrch;
	}

	/**
	 * @param userNameSrch the userNameSrch to set
	 */
	public void setUserNameSrch(String userNameSrch) {
		this.userNameSrch = userNameSrch;
	}

	/**
	 * bankOpenAccountSrch
	 * @return the bankOpenAccountSrch
	 */
	public String getBankOpenAccountSrch() {
		return bankOpenAccountSrch;
	}

	/**
	 * @param bankOpenAccountSrch the bankOpenAccountSrch to set
	 */
	public void setBankOpenAccountSrch(String bankOpenAccountSrch) {
		this.bankOpenAccountSrch = bankOpenAccountSrch;
	}

	/**
	 * verifyStatusSrch
	 * @return the verifyStatusSrch
	 */
	public String getVerifyStatusSrch() {
		return verifyStatusSrch;
	}

	/**
	 * @param verifyStatusSrch the verifyStatusSrch to set
	 */
	public void setVerifyStatusSrch(String verifyStatusSrch) {
		this.verifyStatusSrch = verifyStatusSrch;
	}

	/**
	 * statusSrch
	 * @return the statusSrch
	 */
	public String getStatusSrch() {
		return statusSrch;
	}

	/**
	 * @param statusSrch the statusSrch to set
	 */
	public void setStatusSrch(String statusSrch) {
		this.statusSrch = statusSrch;
	}

	/**
	 * recieveTimeStartSrch
	 * @return the recieveTimeStartSrch
	 */
	public String getRecieveTimeStartSrch() {
		return recieveTimeStartSrch;
	}

	/**
	 * @param recieveTimeStartSrch the recieveTimeStartSrch to set
	 */
	public void setRecieveTimeStartSrch(String recieveTimeStartSrch) {
		this.recieveTimeStartSrch = recieveTimeStartSrch;
	}

	/**
	 * recieveTimeEndSrch
	 * @return the recieveTimeEndSrch
	 */
	public String getRecieveTimeEndSrch() {
		return recieveTimeEndSrch;
	}

	/**
	 * @param recieveTimeEndSrch the recieveTimeEndSrch to set
	 */
	public void setRecieveTimeEndSrch(String recieveTimeEndSrch) {
		this.recieveTimeEndSrch = recieveTimeEndSrch;
	}

/*	*//**
	 * recordList
	 * @return the recordList
	 *//*
	public List<AssetListCustomize> getRecordList() {
		return recordList;
	}

	*//**
	 * @param recordList the recordList to set
	 *//*
	public void setRecordList(List<AssetListCustomize> recordList) {
		this.recordList = recordList;
	}*/

	/**
	 * limitStart
	 * @return the limitStart
	 */
	public int getLimitStart() {
		return limitStart;
	}

	/**
	 * @param limitStart the limitStart to set
	 */
	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * limitEnd
	 * @return the limitEnd
	 */
	public int getLimitEnd() {
		return limitEnd;
	}

	/**
	 * @param limitEnd the limitEnd to set
	 */
	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	public String getLabelNameSrch() {
		return labelNameSrch;
	}

	public void setLabelNameSrch(String labelNameSrch) {
		this.labelNameSrch = labelNameSrch;
	}


}
