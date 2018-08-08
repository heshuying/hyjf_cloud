/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import java.io.Serializable;
import java.util.List;

import com.hyjf.admin.beans.vo.HjhCommissionVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author libin
 * @version HjhCommissionViewRequest.java, v0.1 2018年8月7日 下午2:53:19
 */
public class HjhCommissionViewRequest extends HjhCommissionVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "ids")
	private Integer ids;
	
	@ApiModelProperty(value = "planOrderId")
	private String planOrderId;

	@ApiModelProperty(value = "depIds")
    private String depIds;
    
	@ApiModelProperty(value = "planNid")
    private String planNid;
	
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
}
