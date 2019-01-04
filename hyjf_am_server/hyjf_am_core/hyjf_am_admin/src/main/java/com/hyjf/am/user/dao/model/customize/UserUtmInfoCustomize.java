package com.hyjf.am.user.dao.model.customize;

import java.io.Serializable;

/**
 * @Author : huanghui
 */
public class UserUtmInfoCustomize implements Serializable {

    private Integer sourceId;

    private String sourceName;

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
