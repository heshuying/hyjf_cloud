package com.hyjf.am.config.dao.model.customize;

import java.io.Serializable;
import java.util.List;

public class QuestionCustomize implements Serializable {
    private Integer id;

    private String type;

    private String question;
    
    private String sort;
    private List<AnswerCustomize> answers;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public List<AnswerCustomize> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerCustomize> answers) {
        this.answers = answers;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
    
    
}