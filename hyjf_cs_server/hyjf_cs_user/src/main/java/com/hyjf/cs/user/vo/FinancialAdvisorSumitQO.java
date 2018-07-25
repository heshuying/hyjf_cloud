package com.hyjf.cs.user.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by cui on 2018/3/28.
 */
public class FinancialAdvisorSumitQO {

    @ApiModelProperty("答案")
    private String userAnswer;

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
