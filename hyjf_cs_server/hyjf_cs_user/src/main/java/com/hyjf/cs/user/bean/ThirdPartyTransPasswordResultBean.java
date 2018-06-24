package com.hyjf.cs.user.bean;


public class ThirdPartyTransPasswordResultBean extends BaseMapBean {
	
	
	/**
	 * 异常编号
	 */
	private String errorCode;
	
	/**
	 * 发送优惠券的数量
	 */
	private Integer couponCount;
	
	/**
	 * 返回调用方的url
	 */
	private String callBackAction;
	/** 返回数据 **/
    public Object data;
    public String status;
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

	@Override
    public String getCallBackAction() {
		return callBackAction;
	}

	@Override
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
