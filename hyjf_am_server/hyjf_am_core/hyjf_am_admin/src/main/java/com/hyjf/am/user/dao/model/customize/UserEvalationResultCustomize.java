package com.hyjf.am.user.dao.model.customize;

import com.hyjf.am.user.dao.model.auto.UserEvalationResult;

import java.io.Serializable;

public class UserEvalationResultCustomize extends UserEvalationResult implements Serializable {
    private String typeString;

    private static final long serialVersionUID = 1L;

    public String getTypeString() {
        return getEvalType();
    }

}