package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class EvalationCustomizeVO extends BaseVO implements Serializable {
@ApiModelProperty(value = "用戶id")
    //用戶id
    public String userId;
    //用戶名
    @ApiModelProperty(value = "用戶名")
    public String userName;
    //真实姓名
    @ApiModelProperty(value = "真实姓名")
    public String realName;
    //用户手机号
    @ApiModelProperty(value = "用户手机号")
    public String mobile;
    //用戶属性
    @ApiModelProperty(value = "用戶属性")
    public String userProperty;
    //开户状态
    @ApiModelProperty(value = "开户状态")
    public String accountStatus;
    //测评状态
    @ApiModelProperty(value = "测评状态")
    public String evaluationStatus;
    //风险等级
    @ApiModelProperty(value = "风险等级")
    public String evaluationType;
    //风险测评分
    @ApiModelProperty(value = "风险测评分")
    public String evaluationScore;

    //测评时间
    @ApiModelProperty(value = "测评时间")
    private String lasttime;
    //上次测评时间
    @ApiModelProperty(value = "上次测评时间")
    private String createtime;
    //测评到期时间
    @ApiModelProperty(value = "测评到期时间")
    private String evalationTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserProperty() {
        return userProperty;
    }

    public void setUserProperty(String userProperty) {
        this.userProperty = userProperty;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }

    public String getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(String evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getEvalationTime() {
        return evalationTime;
    }

    public void setEvalationTime(String evalationTime) {
        this.evalationTime = evalationTime;
    }
}