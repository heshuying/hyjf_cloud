/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author libin
 * @version BidApplyQueryViewRequest.java, v0.1 2018年10月19日 下午5:25:52
 */
public class BidApplyQueryViewRequest extends BaseRequest implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	
	@ApiModelProperty(value = "检索条件:电子账号 长度19 投资人电子账号")
	private String accountId;
	
	@ApiModelProperty(value = "检索条件:原订单号  长度30 原投标订单号")
	private String orgOrderId;
	
	@ApiModelProperty(value = "结果")
	private JSONObject result;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getOrgOrderId() {
		return orgOrderId;
	}

	public void setOrgOrderId(String orgOrderId) {
		this.orgOrderId = orgOrderId;
	}

	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}
}
