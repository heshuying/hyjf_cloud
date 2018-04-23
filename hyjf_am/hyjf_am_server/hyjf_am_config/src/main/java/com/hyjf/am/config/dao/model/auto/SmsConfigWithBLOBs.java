package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;

public class SmsConfigWithBLOBs extends SmsConfig implements Serializable {
    private String repayMobiles;

    private String fullMobiles;

    private static final long serialVersionUID = 1L;

    public String getRepayMobiles() {
        return repayMobiles;
    }

    public void setRepayMobiles(String repayMobiles) {
        this.repayMobiles = repayMobiles == null ? null : repayMobiles.trim();
    }

    public String getFullMobiles() {
        return fullMobiles;
    }

    public void setFullMobiles(String fullMobiles) {
        this.fullMobiles = fullMobiles == null ? null : fullMobiles.trim();
    }
}