package com.hyjf.am.config.dao.model.customize;

import java.io.Serializable;

/**
 * @author xiasq
 * @version NewAppAnswerCustomize, v0.1 2017/12/3 11:46
 */
public class NewAppAnswerCustomize  implements Serializable {
    private static final long serialVersionUID = 1L;

    private String quesId;

    private String quesText;

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getQuesText() {
        return quesText;
    }

    public void setQuesText(String quesText) {
        this.quesText = quesText;
    }
}
