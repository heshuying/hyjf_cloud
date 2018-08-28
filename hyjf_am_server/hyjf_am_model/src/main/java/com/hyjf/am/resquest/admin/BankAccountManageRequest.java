/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageRequest, v0.1 2018/6/29 10:17
 */
public class BankAccountManageRequest implements Serializable {

    private static final long serialVersionUID = -8745040452862245048L;


    @ApiModelProperty(value = "用户名查询")
    private String userNameSrch;

    @ApiModelProperty(value = "部门查询")
    private String combotreeSrch;

    @ApiModelProperty(value = "部门拼接(代码将单个部门拼接成list)")
    private String[] combotreeListSrch;

    @ApiModelProperty(value = "电子账号(检索用)")
    private String accountSrch;

    @ApiModelProperty(value = "会员等级(检索用)")
    private String vipSrch;

    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

    private int initQuery;

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getCombotreeSrch() {
        return combotreeSrch;
    }

    public void setCombotreeSrch(String combotreeSrch) {
        this.combotreeSrch = combotreeSrch;
    }

    public String[] getCombotreeListSrch() {
        return combotreeListSrch;
    }

    public void setCombotreeListSrch(String[] combotreeListSrch) {
        this.combotreeListSrch = combotreeListSrch;
    }

    public String getAccountSrch() {
        return accountSrch;
    }

    public void setAccountSrch(String accountSrch) {
        this.accountSrch = accountSrch;
    }

    public String getVipSrch() {
        return vipSrch;
    }

    public void setVipSrch(String vipSrch) {
        this.vipSrch = vipSrch;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public int getInitQuery() {
        return initQuery;
    }

    public void setInitQuery(int initQuery) {
        this.initQuery = initQuery;
    }
}
