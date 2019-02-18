package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModelProperty;

public class AemsTransPasswordResultBean extends BaseMapBean {
	

	@ApiModelProperty(value = "异常编号")
	private String errorCode;

	@ApiModelProperty(value = "发送优惠券的数量")
	private Integer couponCount;

	@ApiModelProperty(value = "返回调用方的url")
	private String callBackAction;

	@ApiModelProperty(value = "返回数据")
    public Object data;

	@ApiModelProperty(value = "返回状态")
    public String status;

	@ApiModelProperty(value = "返回状态描述")
	public String statusDesc;
	

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }


    public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getCallBackAction() {
		return callBackAction;
	}

	public void setCallBackAction(String callBackAction) {
		this.callBackAction = callBackAction;
	}

	public Integer getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}
	
}
