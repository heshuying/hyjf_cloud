package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class AemsEvaluationResultBean implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 6529261663208896558L;

    @ApiModelProperty(value = "资产编号")
	private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
	
	
	
}
