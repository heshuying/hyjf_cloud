package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;

public class ParamNameKey implements Serializable {
    private String nameClass;

    private Short nameCd;

    private static final long serialVersionUID = 1L;

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass == null ? null : nameClass.trim();
    }

    public Short getNameCd() {
        return nameCd;
    }

    public void setNameCd(Short nameCd) {
        this.nameCd = nameCd;
    }
}