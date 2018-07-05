package com.hyjf.cs.user.controller.api.evaluation;

import java.io.Serializable;

public class ThirdPartyEvaluationResultBean implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 6529261663208896558L;
	
	private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
	
	
	
}
