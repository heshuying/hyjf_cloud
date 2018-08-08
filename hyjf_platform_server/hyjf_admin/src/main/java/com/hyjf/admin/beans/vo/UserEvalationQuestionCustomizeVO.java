package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class UserEvalationQuestionCustomizeVO extends BaseVO implements Serializable {

    @ApiModelProperty(value = "问题")
    //问题
    public String question;
    //答案
    @ApiModelProperty(value = "答案")
    public String answer;
    //分数
    @ApiModelProperty(value = "分数")
    public String score;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}