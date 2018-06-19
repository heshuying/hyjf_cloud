/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version UserEvalationRequest, v0.1 2018/6/17 23:36
 */
public class UserEvalationRequest {
    private List<String> answerList;
    private List<String> questionList;
    private EvalationVO evalation;
    private int countScore;
    private Integer userId;
    private UserEvalationResultVO userEvalationResultVO;

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public List<String> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<String> questionList) {
        this.questionList = questionList;
    }

    public EvalationVO getEvalation() {
        return evalation;
    }

    public void setEvalation(EvalationVO evalation) {
        this.evalation = evalation;
    }

    public int getCountScore() {
        return countScore;
    }

    public void setCountScore(int countScore) {
        this.countScore = countScore;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserEvalationResultVO getUserEvalationResultVO() {
        return userEvalationResultVO;
    }

    public void setUserEvalationResultVO(UserEvalationResultVO userEvalationResultVO) {
        this.userEvalationResultVO = userEvalationResultVO;
    }
}
