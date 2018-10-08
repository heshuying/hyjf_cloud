package com.hyjf.admin.beans.request;

import java.io.Serializable;

/**
 * @author pangchengchao
 * @version RepayRequestBean, v0.1 2018/8/29 16:29
 */
public class RepayRequestBean implements Serializable {
    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;

    private String password;

    private String nid;

    private String userId;// 借款人ID
    /**
     * nid
     *
     * @return the nid
     */

    public String getNid() {
        return nid;
    }

    /**
     * @param nid
     *            the nid to set
     */

    public void setNid(String nid) {
        this.nid = nid;
    }

    public RepayRequestBean() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
