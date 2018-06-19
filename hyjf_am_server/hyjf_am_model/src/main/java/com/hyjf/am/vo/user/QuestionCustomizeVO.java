package com.hyjf.am.vo.user;

import java.io.Serializable;
import java.util.List;

public class QuestionCustomizeVO implements Serializable {
    private Integer id;

    private String typeString;

    private String question;
    
    private String sort;
    private List<AnswerCustomizeVO> answers;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   

   

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public List<AnswerCustomizeVO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerCustomizeVO> answers) {
        this.answers = answers;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
    
    
}