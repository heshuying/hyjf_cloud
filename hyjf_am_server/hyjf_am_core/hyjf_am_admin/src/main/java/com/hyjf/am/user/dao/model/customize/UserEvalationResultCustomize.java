package com.hyjf.am.user.dao.model.customize;

import java.io.Serializable;

import com.hyjf.am.user.dao.model.auto.UserEvalationResult;

public class UserEvalationResultCustomize extends UserEvalationResult implements Serializable {
    private String typeString;

    private static final long serialVersionUID = 1L;

    public String getTypeString() {
        return getEvalType();
    }

}