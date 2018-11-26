/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.vo.HjhCommissionVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author libin
 * @version HjhCommissionViewRequest.java, v0.1 2018年8月7日 下午2:53:19
 */
public class HjhCommissionViewRequest extends HjhCommissionVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
    public int limit;

	@ApiModelProperty(value = "ids")
	private Integer ids;
	
	@ApiModelProperty(value = "planOrderId")
	private String planOrderId;

	@ApiModelProperty(value = "depIds")
    private String depIds;
    
	@ApiModelProperty(value = "planNid")
    private String planNid;
	
	@ApiModelProperty(value = "commission")
	private BigDecimal commission;
	
	@ApiModelProperty(value = "计划订单号")
	private String accedeOrderIdSearch;
	
	@ApiModelProperty(value = "计划编号")
	private String borrowNidSearch;
	
	@ApiModelProperty(value = "提成发送时间开始")
    private String startDateSend;
    
	@ApiModelProperty(value = "提成发送时间结束")
    private String endDateSend;
	
	@ApiModelProperty(value = "提成人")
	private String referernameSearch;
	
	@ApiModelProperty(value = "投资人")
	private String usernameSearch;
	
	@ApiModelProperty(value = "提成发放状态0,未发放；1，已发放")
	private String statusSearch;
	
	@ApiModelProperty(value = "计划订单锁定时间 开始")
    private String startDatePlan;
	
	@ApiModelProperty(value = "计划订单锁定时间 结束")
    private String endDatePlan;
	
	private List<HjhCommissionVO> recordList;

	public Integer getIds() {
		return ids;
	}

	public void setIds(Integer ids) {
		this.ids = ids;
	}

	public String getPlanOrderId() {
		return planOrderId;
	}

	public void setPlanOrderId(String planOrderId) {
		this.planOrderId = planOrderId;
	}

	public String getDepIds() {
		return depIds;
	}

	public void setDepIds(String depIds) {
		this.depIds = depIds;
	}

	public String getPlanNid() {
		return planNid;
	}

	public void setPlanNid(String planNid) {
		this.planNid = planNid;
	}

	public List<HjhCommissionVO> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<HjhCommissionVO> recordList) {
		this.recordList = recordList;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
