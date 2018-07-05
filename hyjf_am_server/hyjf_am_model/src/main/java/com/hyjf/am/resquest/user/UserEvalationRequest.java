/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

/**
 * @author zhangqingqing
 * @version UserEvalationRequest, v0.1 2018/6/17 23:36
 */
public class UserEvalationRequest {

    private Integer userId;
    private String userAnswer;
    private Integer countScore;
    private String behaviorId;

    public String getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(String behaviorId) {
        this.behaviorId = behaviorId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Integer getCountScore() {
        return countScore;
    }

    public void setCountScore(Integer countScore) {
        this.countScore = countScore;
    }
}
