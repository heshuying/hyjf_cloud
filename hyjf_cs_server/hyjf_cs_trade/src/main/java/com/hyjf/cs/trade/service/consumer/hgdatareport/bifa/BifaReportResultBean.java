/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa;

import java.io.Serializable;

/**
 * @author jun
 * @version BifaCommonConstants, v0.1 2018/11/30 14:05
 */
public class BifaReportResultBean implements Serializable {

    private String message;
    private String reCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReCode() {
        return reCode;
    }

    public void setReCode(String reCode) {
        this.reCode = reCode;
    }
}
