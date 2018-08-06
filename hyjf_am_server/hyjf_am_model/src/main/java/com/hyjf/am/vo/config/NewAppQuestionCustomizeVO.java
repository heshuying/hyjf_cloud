package com.hyjf.am.vo.config;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiasq
 * @version NewAppQuestionCustomize, v0.1 2017/12/3 11:45
 */
public class NewAppQuestionCustomizeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String title;

    private List<NewAppAnswerCustomizeVO> selection;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<NewAppAnswerCustomizeVO> getSelection() {
        return selection;
    }

    public void setSelection(List<NewAppAnswerCustomizeVO> selection) {
        this.selection = selection;
    }
}
