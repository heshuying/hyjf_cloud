/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.app;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author jun
 * @version AppProjectInvestListCustomizeVO, v0.1 2018/7/26 10:58
 */
public class AppProjectInvestListCustomizeVO extends BaseVO implements Serializable {


    /**
     * 序列化id
     */
    private static final long serialVersionUID = -4720030760960740262L;
    /**
     * 用户名
     */
    private String userName;
    /**
     *投资金额
     */
    private String account;
    /**
     *投资时间
     */
    private String investTime;
    /**
     * 平台
     */
    private Integer client;


    private String clientName;


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public AppProjectInvestListCustomizeVO() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }
}
