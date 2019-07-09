package com.hyjf.am.resquest.admin;

import java.io.Serializable;

/**
 * @Author: yinhui
 * @Date: 2019/4/16 17:11
 * @Version 1.0
 */
public class AdminUserWorkFlowRequest implements Serializable {

    private static final long serialVersionUID = 387630498860089653L;

    public Integer[] adminuserId;

    public Integer[] getAdminuserId() {
        return adminuserId;
    }

    public void setAdminuserId(Integer[] adminuserId) {
        this.adminuserId = adminuserId;
    }
}
