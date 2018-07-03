package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BaseVO;

/**
 * @author pangchengchao
 * @version BorrowRecoverRequest, v0.1 2018/7/2 14:25
 */
public class BorrowRecoverRequest{
    /**
     * 当前页码
     */
    private int currPage;

    /**
     * 当前页条数
     */
    private int pageSize;

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

    /**
     * 借款编号 检索条件
     */
    private String borrowNidSrch;
    /**
     * 借款名称 检索条件
     */
    private String borrowNameSrch;
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
     * 放款订单号 检索条件
     */
    private String loanOrdid;
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
     * 合作机构编号  检索条件
     */
    private String instCodeOrgSrch;
    /**
     * 放款批次号
     */
    private String loanBatchNo;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;


    /**
     * borrowNidSrch
     *
     * @return the borrowNidSrch
     */

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    /**
     * @param borrowNidSrch
     *            the borrowNidSrch to set
     */

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }


    /**
     * usernameSrch
     *
     * @return the usernameSrch
     */

    public String getUsernameSrch() {
        return usernameSrch;
    }

    /**
     * @param usernameSrch
     *            the usernameSrch to set
     */

    public void setUsernameSrch(String usernameSrch) {
        this.usernameSrch = usernameSrch;
    }

    /**
     * orderNumSrch
     *
     * @return the orderNumSrch
     */

    public String getOrderNumSrch() {
        return orderNumSrch;
    }

    /**
     * @param orderNumSrch
     *            the orderNumSrch to set
     */

    public void setOrderNumSrch(String orderNumSrch) {
        this.orderNumSrch = orderNumSrch;
    }

    /**
     * isRecoverSrch
     *
     * @return the isRecoverSrch
     */

    public String getIsRecoverSrch() {
        return isRecoverSrch;
    }

    /**
     * @param isRecoverSrch
     *            the isRecoverSrch to set
     */

    public void setIsRecoverSrch(String isRecoverSrch) {
        this.isRecoverSrch = isRecoverSrch;
    }

    /**
     * timeRecoverStartSrch
     *
     * @return the timeRecoverStartSrch
     */

    public String getTimeRecoverStartSrch() {
        return timeRecoverStartSrch;
    }

    /**
     * @param timeRecoverStartSrch
     *            the timeRecoverStartSrch to set
     */

    public void setTimeRecoverStartSrch(String timeRecoverStartSrch) {
        this.timeRecoverStartSrch = timeRecoverStartSrch;
    }

    /**
     * timeRecoverEndSrch
     *
     * @return the timeRecoverEndSrch
     */

    public String getTimeRecoverEndSrch() {
        return timeRecoverEndSrch;
    }

    /**
     * @param timeRecoverEndSrch
     *            the timeRecoverEndSrch to set
     */

    public void setTimeRecoverEndSrch(String timeRecoverEndSrch) {
        this.timeRecoverEndSrch = timeRecoverEndSrch;
    }

    /**
     * timeStartSrch
     *
     * @return the timeStartSrch
     */

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    /**
     * @param timeStartSrch
     *            the timeStartSrch to set
     */

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    /**
     * timeEndSrch
     *
     * @return the timeEndSrch
     */

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    /**
     * @param timeEndSrch
     *            the timeEndSrch to set
     */

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }


    public String getLoanOrdid() {
        return loanOrdid;
    }

    public void setLoanOrdid(String loanOrdid) {
        this.loanOrdid = loanOrdid;
    }

    public String getLoanBatchNo() {
        return loanBatchNo;
    }

    public void setLoanBatchNo(String loanBatchNo) {
        this.loanBatchNo = loanBatchNo;
    }

    public String getPlanNidSrch() {
        return planNidSrch;
    }

    public void setPlanNidSrch(String planNidSrch) {
        this.planNidSrch = planNidSrch;
    }

    /**
     * instCodeSrch
     * @return the instCodeSrch
     */

    public String getInstCodeSrch() {
        return instCodeSrch;

    }

    /**
     * @param instCodeSrch the instCodeSrch to set
     */

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;

    }

    public String getInstCodeOrgSrch() {
        return instCodeOrgSrch;
    }

    public void setInstCodeOrgSrch(String instCodeOrgSrch) {
        this.instCodeOrgSrch = instCodeOrgSrch;
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

    public String getBorrowNameSrch() {
        return borrowNameSrch;
    }

    public void setBorrowNameSrch(String borrowNameSrch) {
        this.borrowNameSrch = borrowNameSrch;
    }
}
