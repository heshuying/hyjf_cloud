/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.model.customize;

import java.io.Serializable;

/**
 * @author fq
 * @version PlatformUserCountCustomize, v0.1 2018/8/13 9:49
 */
public class PlatformUserCountCustomize implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 平台 */
    private String client;
    /** 注册数 */
    private Integer registNumber;
    /** 开户数 */
    private Integer accountNumber;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Integer getRegistNumber() {
        return registNumber;
    }

    public void setRegistNumber(Integer registNumber) {
        this.registNumber = registNumber;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }
}
