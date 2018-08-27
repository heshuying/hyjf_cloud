/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: SyncUserInfoRequestBean, v0.1 2018/8/24 14:22
 */
public class SyncUserInfoRequestBean extends BaseBean implements Serializable {
    private String accountIds;

    public String getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(String accountIds) {
        this.accountIds = accountIds;
    }
}
