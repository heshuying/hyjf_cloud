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
	
	// �ʲ����
	String assetIdSrch;
	// �ʲ���Դ
	String instCodeSrch;
	// ��Ʒ����
	String assetTypeSrch;
	// ��Ŀ���
	String borrowNidSrch;
	// �ƻ����
	String planNidSrch;
	// �û���
	String userNameSrch;
	//��ı�ǩ
	String labelNameSrch;
	// ����״̬
	String bankOpenAccountSrch;
	// ���״̬
	String verifyStatusSrch;
	// ��Ŀ״̬
	String statusSrch;
	// ����ʱ��
	String recieveTimeStartSrch;
	// ����ʱ��
	String recieveTimeEndSrch;
	/*private List<AssetListCustomize> recordList;*/
	// �������� limitStart
	private int limitStart = -1;
	// �������� limitEnd
	private int limitEnd = -1;
/*	*//**
	 * ��ҳ�����õ����ر���
	 *//*
	private int paginatorPage = 1;*/

	/**
	 * �б����Զ����ǩ�ϵ��÷�ҳ����paginator
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
