package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class DebtCompanyAuthen implements Serializable {
    private Integer id;

    /**
     * 认证项目名称
     *
     * @mbggenerated
     */
    private String authenName;

    /**
     * 认证时间
     *
     * @mbggenerated
     */
    private String authenTime;

    /**
     * 展示顺序
     *
     * @mbggenerated
     */
    private Integer authenSortKey;

    /**
     * 借款编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 借款预编号
     *
     * @mbggenerated
     */
    private Integer borrowPreNid;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthenName() {
        return authenName;
    }

    public void setAuthenName(String authenName) {
        this.authenName = authenName == null ? null : authenName.trim();
    }

    public String getAuthenTime() {
        return authenTime;
    }

    public void setAuthenTime(String authenTime) {
        this.authenTime = authenTime == null ? null : authenTime.trim();
    }

    public Integer getAuthenSortKey() {
        return authenSortKey;
    }

    public void setAuthenSortKey(Integer authenSortKey) {
        this.authenSortKey = authenSortKey;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getBorrowPreNid() {
        return borrowPreNid;
    }

    public void setBorrowPreNid(Integer borrowPreNid) {
        this.borrowPreNid = borrowPreNid;
    }
}