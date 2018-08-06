package com.hyjf.cs.user.bean;

public class BindCardPageResultBean extends BaseMapBean {
	
	/**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 3709370958884607483L;
    
    private String callBackAction;

    @Override
    public String getCallBackAction() {
        return callBackAction;
    }

    @Override
    public void setCallBackAction(String callBackAction) {
        this.callBackAction = callBackAction;
    }
    
	
}
