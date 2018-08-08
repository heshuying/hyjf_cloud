package com.hyjf.cs.user.bean;

public class ThirdPartyEvaluationRequestBean extends BaseBean {
    private String mobile;
    //用户测评得分
    private String evalationType;

    public String getEvalationType() {
        return evalationType;
    }

    public void setEvalationType(String evalationType) {
        this.evalationType = evalationType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    
    
}
