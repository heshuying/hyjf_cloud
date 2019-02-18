package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModelProperty;

public class AemsEvaluationRequestBean extends BaseBean{

    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "用户测评得分")
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
