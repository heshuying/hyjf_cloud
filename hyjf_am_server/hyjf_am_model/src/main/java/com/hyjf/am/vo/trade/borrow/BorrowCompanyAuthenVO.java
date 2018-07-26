package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;

public class BorrowCompanyAuthenVO extends BaseVO {
    private Integer id;

    private String authenName;

    private String authenTime;

    private Integer authenSortKey;

    private String borrowNid;

    private String borrowPreNid;



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

    public String getBorrowPreNid() {
        return borrowPreNid;
    }

    public void setBorrowPreNid(String borrowPreNid) {
        this.borrowPreNid = borrowPreNid == null ? null : borrowPreNid.trim();
    }
}