package com.hyjf.am.vo.task.autoissue;

/**
 * 自动关联计划消息体
 * @author zhangyk
 * @date 2018/7/17 15:53
 */
public class AutoIssueMsg {

    private String borrowNid;

    private String creditNid;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }
}
