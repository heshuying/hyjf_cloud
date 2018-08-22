package com.hyjf.am.config.dao.model.customize;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiasq
 * @version NewAppQuestionCustomize, v0.1 2017/12/3 11:45
 */
public class NewAppQuestionCustomize implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String title;

    private List<NewAppAnswerCustomize> selection;

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

    public List<NewAppAnswerCustomize> getSelection() {
        return selection;
    }

    public void setSelection(List<NewAppAnswerCustomize> selection) {
        this.selection = selection;
    }
}
