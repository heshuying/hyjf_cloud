/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author fq
 * @version PlatformUserCountCustomizeVO, v0.1 2018/8/13 9:27
 */
public class PlatformUserCountCustomizeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 平台 */
    private String client;
    /** 注册数 */
    private String registNumber;
    /** 开户数 */
    private String accountNumber;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getRegistNumber() {
        return registNumber;
    }

    public void setRegistNumber(String registNumber) {
        this.registNumber = registNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
