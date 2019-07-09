package com.hyjf.am.vo.activity;

import java.io.Serializable;

/**
 * @author xiasq
 * @version ActivityUserGuessVO, v0.1 2019/4/22 14:37
 */
public class ActivityUserGuessVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer grade;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
