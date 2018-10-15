/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author libin
 * @version HjhLabelRequest.java, v0.1 2018年6月30日 下午1:58:35
 */
public class HjhLabelRequest extends BasePage implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	// 标签ID
	private Integer labelIdSrch;
    // 标签名称
    private String labelNameSrch;
	// 资产来源
	private String instCodeSrch;
	// 产品类型  Integer
	private String assetTypeSrch;
	// 标签状态  Integer
	private String labelStateSrch;
	// 創建時間開始
	private String createTimeStartSrch;
    // 創建時間結束
	private String createTimeEndSrch;
	// 项目类型 Integer
	private String projectTypeSrch;
	// 还款方式
	private String borrowStyleSrch;
	// 使用状态 Integer
	private Integer engineIdSrch;
	
	public int limit;

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
	/*private String pushTimeStartString;*/
    /*private String pushTimeEndString;*/
	public String getLabelNameSrch() {
		return labelNameSrch;
	}
	public void setLabelNameSrch(String labelNameSrch) {
		this.labelNameSrch = labelNameSrch;
	}
	public String getInstCodeSrch() {
		return instCodeSrch;
	}
	public void setInstCodeSrch(String instCodeSrch) {
		this.instCodeSrch = instCodeSrch;
	}
	public String getAssetTypeSrch() {
		return assetTypeSrch;
	}
	public void setAssetTypeSrch(String assetTypeSrch) {
		this.assetTypeSrch = assetTypeSrch;
	}
	public String getLabelStateSrch() {
		return labelStateSrch;
	}
	public void setLabelStateSrch(String labelStateSrch) {
		this.labelStateSrch = labelStateSrch;
	}
	public String getCreateTimeStartSrch() {
		return createTimeStartSrch;
	}
	public void setCreateTimeStartSrch(String createTimeStartSrch) {
		this.createTimeStartSrch = createTimeStartSrch;
	}
	public String getCreateTimeEndSrch() {
		return createTimeEndSrch;
	}
	public void setCreateTimeEndSrch(String createTimeEndSrch) {
		this.createTimeEndSrch = createTimeEndSrch;
	}
	public String getProjectTypeSrch() {
		return projectTypeSrch;
	}
	public void setProjectTypeSrch(String projectTypeSrch) {
		this.projectTypeSrch = projectTypeSrch;
	}
	public String getBorrowStyleSrch() {
		return borrowStyleSrch;
	}
	public void setBorrowStyleSrch(String borrowStyleSrch) {
		this.borrowStyleSrch = borrowStyleSrch;
	}
	public Integer getEngineIdSrch() {
		return engineIdSrch;
	}
	public void setEngineIdSrch(Integer engineIdSrch) {
		this.engineIdSrch = engineIdSrch;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public Integer getLabelIdSrch() {
		return labelIdSrch;
	}
	public void setLabelIdSrch(Integer labelIdSrch) {
		this.labelIdSrch = labelIdSrch;
	}
}
