package com.hyjf.admin.beans;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverBean, v0.1 2018/7/2 14:54
 */
public class BorrowRecoverBean {
    /**
     * 借款编号 检索条件
     */
    private String borrowNidSrch;
    /**
     * 资产来源 检索条件
     */
    private String instCodeSrch;
    /**
     * 检索条件 计划编号
     */
    private String planNidSrch;
    /**
     * 投资人 检索条件
     */
    private String usernameSrch;
    /**
     * 投资订单号 检索条件
     */
    private String orderNumSrch;

    /**
     * 合作机构编号  检索条件
     */
    private String instCodeOrgSrch;
    /**
     * 放款状态 检索条件
     */
    private String isRecoverSrch;
    /**
     * 投资时间 检索条件
     */
    private String timeRecoverStartSrch;
    /**
     * 投资时间 检索条件
     */
    private String timeRecoverEndSrch;
    /**
     * 放款时间 检索条件
     */
    private String timeStartSrch;
    /**
     * 放款时间 检索条件
     */
    private String timeEndSrch;

    /**
     * 放款批次号
     */
    private String loanBatchNo;

    /**
     * 放款订单号
     */
    private String loanOrdid;

    /**
     * 检索条件 当前页数
     */
    private int currPage=0;
    /**
     * 检索条件 每页条数
     */
    private int pageSize=10;

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getPlanNidSrch() {
        return planNidSrch;
    }

    public void setPlanNidSrch(String planNidSrch) {
        this.planNidSrch = planNidSrch;
    }

    public String getUsernameSrch() {
        return usernameSrch;
    }

    public void setUsernameSrch(String usernameSrch) {
        this.usernameSrch = usernameSrch;
    }

    public String getOrderNumSrch() {
        return orderNumSrch;
    }

    public void setOrderNumSrch(String orderNumSrch) {
        this.orderNumSrch = orderNumSrch;
    }

    public String getInstCodeOrgSrch() {
        return instCodeOrgSrch;
    }

    public void setInstCodeOrgSrch(String instCodeOrgSrch) {
        this.instCodeOrgSrch = instCodeOrgSrch;
    }

    public String getIsRecoverSrch() {
        return isRecoverSrch;
    }

    public void setIsRecoverSrch(String isRecoverSrch) {
        this.isRecoverSrch = isRecoverSrch;
    }

    public String getTimeRecoverStartSrch() {
        return timeRecoverStartSrch;
    }

    public void setTimeRecoverStartSrch(String timeRecoverStartSrch) {
        this.timeRecoverStartSrch = timeRecoverStartSrch;
    }

    public String getTimeRecoverEndSrch() {
        return timeRecoverEndSrch;
    }

    public void setTimeRecoverEndSrch(String timeRecoverEndSrch) {
        this.timeRecoverEndSrch = timeRecoverEndSrch;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public String getLoanBatchNo() {
        return loanBatchNo;
    }

    public void setLoanBatchNo(String loanBatchNo) {
        this.loanBatchNo = loanBatchNo;
    }

    public String getLoanOrdid() {
        return loanOrdid;
    }

    public void setLoanOrdid(String loanOrdid) {
        this.loanOrdid = loanOrdid;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
