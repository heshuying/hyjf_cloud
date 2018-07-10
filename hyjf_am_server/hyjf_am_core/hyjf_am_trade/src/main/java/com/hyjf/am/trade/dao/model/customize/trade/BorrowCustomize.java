package com.hyjf.am.trade.dao.model.customize.trade;

/**
 * @author xiasq
 * @version BorrowCustomize, v0.1 2018/7/10 16:06
 * 集成borrow 和 borrow_info字段的实体 可以合并查询
 */
public class BorrowCustomize {
    /** 标的id */
    private int id;
    /** 借款人 */
    private int userId;
    /** 借款有效时间 */
    private int borrowValidTime;
    /** 标的编号 */
    private String borrowNid;
    /** 标的编号排序 */
    private String borrowPreNid;
    /** 新的标的编号排序 */
    private String borrowPreNidNew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBorrowValidTime() {
        return borrowValidTime;
    }

    public void setBorrowValidTime(int borrowValidTime) {
        this.borrowValidTime = borrowValidTime;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowPreNid() {
        return borrowPreNid;
    }

    public void setBorrowPreNid(String borrowPreNid) {
        this.borrowPreNid = borrowPreNid;
    }

    public String getBorrowPreNidNew() {
        return borrowPreNidNew;
    }

    public void setBorrowPreNidNew(String borrowPreNidNew) {
        this.borrowPreNidNew = borrowPreNidNew;
    }
}
