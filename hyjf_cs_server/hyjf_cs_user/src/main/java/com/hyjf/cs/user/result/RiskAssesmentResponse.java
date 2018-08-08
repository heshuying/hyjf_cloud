package com.hyjf.cs.user.result;

import com.hyjf.am.vo.config.NewAppQuestionCustomizeVO;

import java.util.List;

/**
 * @author xiasq
 * @version RiskAssesmentResponse, v0.1 2017/12/3 11:01
 */
public class RiskAssesmentResponse extends BaseResultBeanFrontEnd {

    private String couponResult;

    private List<NewAppQuestionCustomizeVO> questionList;

    //测评状态
    private String resultStatus; // 0:未测评  1：已测评

    //测评类型
    private String resultType;

    //类型文字描述
    private String resultText;

    //优惠券发放结果
    private String sendResult;

    public String getCouponResult() {
        return couponResult;
    }

    public void setCouponResult(String couponResult) {
        this.couponResult = couponResult;
    }

    public List<NewAppQuestionCustomizeVO> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<NewAppQuestionCustomizeVO> questionList) {
        this.questionList = questionList;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public String getSendResult() {
        return sendResult;
    }

    public void setSendResult(String sendResult) {
        this.sendResult = sendResult;
    }
}
