package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @Author : huanghui
 */
public class WebUserRepayTransferRequest extends BasePage implements Serializable {

    /** 标的NID */
    private String borrowNid;

    private String verificationFlag;

    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getVerificationFlag() {
        return verificationFlag;
    }

    public void setVerificationFlag(String verificationFlag) {
        this.verificationFlag = verificationFlag;
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
}
