package com.hyjf.cs.user.bean;


import io.swagger.annotations.ApiModelProperty;

public class ThirdPartyTransPasswordRequestBean extends BaseBean {
	/**
	 * 优惠券详情服务
	 */
	private String host;

	@ApiModelProperty(value = "账号")
	private String bankAccount;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
    
    
	
}
