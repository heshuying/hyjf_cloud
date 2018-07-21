/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import java.io.Serializable;

import com.hyjf.admin.beans.BaseRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author libin
 * @version AllocationEngineViewRequest.java, v0.1 2018年7月21日 下午3:16:33
 */
public class AllocationEngineViewRequest extends BaseRequest implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

	/** (计划专区)前台查询要素---计划配置画面无检索  */
    
	@ApiModelProperty(value = "计划编号查询")
    private String planNidSrch;
	
	@ApiModelProperty(value = "计划名称率查询")
    private String planNameSrch;
	
	@ApiModelProperty(value = "添加时间 开始")
	private String startCreateSrch;

	@ApiModelProperty(value = "添加时间 结束")
	private String endCreateSrch;
	
	@ApiModelProperty(value = "计划专区列表中的检索状态")
	private Integer configStatusSrch;
	
	/** (计划专区)前台列表要素 */
	@ApiModelProperty(value = "序号ID")
	private String id;
	
	@ApiModelProperty(value = "计划编号")
	private String planNid;
	
	@ApiModelProperty(value = "计划名称")
	private String planName;
	
	@ApiModelProperty(value = "添加时间")
	private String configAddTime;
	
	@ApiModelProperty(value = "状态")
	private String configStatus;
	
	/** (计划配置列表+Info画面)列表要素 */
	@ApiModelProperty(value = "标签编号")
	private String labelId;
	
	@ApiModelProperty(value = "标签名称")
	private String labelName;
	
	@ApiModelProperty(value = "标签添加时间")
	private String addTime;
	
	@ApiModelProperty(value = "标签状态")
	private String labelStatus;
	
	@ApiModelProperty(value = "标签排序")
	private String labelSort;
	
	@ApiModelProperty(value = "债转时间排序 0：按转让时间降序 1：按转让时间升序")
	private String transferTimeSort;
	
	@ApiModelProperty(value = "债转时间排序优先级")
	private String transferTimeSortPriority;
	
	@ApiModelProperty(value = "年化收益率排序 0：从低到高 1：从高到低")
	private String aprSort;
	
	@ApiModelProperty(value = "aprSortPriority")
	private String aprSortPriority;
	
	@ApiModelProperty(value = "标的实际支付金额排序 0：从小到大 1：从大到小")
	private String actulPaySort;
	
	@ApiModelProperty(value = "年化收益率优先级")
	private String actulPaySortPriority;
	
	@ApiModelProperty(value = "投资进度排序 0：从小到大 1：从大到小")
	private String investProgressSort;
	
	@ApiModelProperty(value = "投资进度排序 0：从小到大 1：从大到小")
	private String investProgressSortPriority;
	
	public int limit;
	
	public int getPaginatorPage() {
		return paginatorPage;
	}
	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}
	public String getPlanNidSrch() {
		return planNidSrch;
	}
	public void setPlanNidSrch(String planNidSrch) {
		this.planNidSrch = planNidSrch;
	}
	public String getPlanNameSrch() {
		return planNameSrch;
	}
	public void setPlanNameSrch(String planNameSrch) {
		this.planNameSrch = planNameSrch;
	}
	public String getStartCreateSrch() {
		return startCreateSrch;
	}
	public void setStartCreateSrch(String startCreateSrch) {
		this.startCreateSrch = startCreateSrch;
	}
	public String getEndCreateSrch() {
		return endCreateSrch;
	}
	public void setEndCreateSrch(String endCreateSrch) {
		this.endCreateSrch = endCreateSrch;
	}
	public Integer getConfigStatusSrch() {
		return configStatusSrch;
	}
	public void setConfigStatusSrch(Integer configStatusSrch) {
		this.configStatusSrch = configStatusSrch;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlanNid() {
		return planNid;
	}
	public void setPlanNid(String planNid) {
		this.planNid = planNid;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getConfigAddTime() {
		return configAddTime;
	}
	public void setConfigAddTime(String configAddTime) {
		this.configAddTime = configAddTime;
	}
	public String getConfigStatus() {
		return configStatus;
	}
	public void setConfigStatus(String configStatus) {
		this.configStatus = configStatus;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getLabelStatus() {
		return labelStatus;
	}
	public void setLabelStatus(String labelStatus) {
		this.labelStatus = labelStatus;
	}
	public String getLabelSort() {
		return labelSort;
	}
	public void setLabelSort(String labelSort) {
		this.labelSort = labelSort;
	}
	public String getTransferTimeSort() {
		return transferTimeSort;
	}
	public void setTransferTimeSort(String transferTimeSort) {
		this.transferTimeSort = transferTimeSort;
	}
	public String getTransferTimeSortPriority() {
		return transferTimeSortPriority;
	}
	public void setTransferTimeSortPriority(String transferTimeSortPriority) {
		this.transferTimeSortPriority = transferTimeSortPriority;
	}
	public String getAprSort() {
		return aprSort;
	}
	public void setAprSort(String aprSort) {
		this.aprSort = aprSort;
	}
	public String getAprSortPriority() {
		return aprSortPriority;
	}
	public void setAprSortPriority(String aprSortPriority) {
		this.aprSortPriority = aprSortPriority;
	}
	public String getActulPaySort() {
		return actulPaySort;
	}
	public void setActulPaySort(String actulPaySort) {
		this.actulPaySort = actulPaySort;
	}
	public String getActulPaySortPriority() {
		return actulPaySortPriority;
	}
	public void setActulPaySortPriority(String actulPaySortPriority) {
		this.actulPaySortPriority = actulPaySortPriority;
	}
	public String getInvestProgressSort() {
		return investProgressSort;
	}
	public void setInvestProgressSort(String investProgressSort) {
		this.investProgressSort = investProgressSort;
	}
	public String getInvestProgressSortPriority() {
		return investProgressSortPriority;
	}
	public void setInvestProgressSortPriority(String investProgressSortPriority) {
		this.investProgressSortPriority = investProgressSortPriority;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}

}
