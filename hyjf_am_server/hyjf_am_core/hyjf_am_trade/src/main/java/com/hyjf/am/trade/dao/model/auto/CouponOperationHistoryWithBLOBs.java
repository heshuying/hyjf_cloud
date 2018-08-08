package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class CouponOperationHistoryWithBLOBs extends CouponOperationHistory implements Serializable {
    private String operationContentFrom;

    private String operationContentTo;

    private static final long serialVersionUID = 1L;

    public String getOperationContentFrom() {
        return operationContentFrom;
    }

    public void setOperationContentFrom(String operationContentFrom) {
        this.operationContentFrom = operationContentFrom == null ? null : operationContentFrom.trim();
    }

    public String getOperationContentTo() {
        return operationContentTo;
    }

    public void setOperationContentTo(String operationContentTo) {
        this.operationContentTo = operationContentTo == null ? null : operationContentTo.trim();
    }
}