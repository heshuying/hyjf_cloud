package com.hyjf.am.vo.trade;

/**
 * @author pangchengchao
 * @version DebtBorrowCommonCustomizeVO, v0.1 2018/11/6 14:15
 */
public class DebtBorrowCommonCustomizeVO {
    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;
    /**
     * 排序
     */
    private String sort;
    /**
     * 排序列
     */
    private String col;

    /**
     * 检索条件 借款编号
     */
    private String borrowNidSrch;

    /**
     * 检索条件 借款标题
     */
    private String borrowNameSrch;
    /**
     * 检索条件 用户名
     */
    private String usernameSrch;

    /**
     * 检索条件 时间开始
     */
    private String timeStartSrch;

    /**
     * 检索条件 时间结束
     */
    private String timeEndSrch;

    /**
     * 检索条件 放款开始
     */
    private String recoverTimeStartSrch;

    /**
     * 检索条件 放款结束
     */
    private String recoverTimeEndSrch;

    /**
     * 检索条件 状态
     */
    private String statusSrch;

    /**
     * 检索条件 还款方式
     */
    private String borrowStyleSrch;

    /**
     * 检索条件 项目类型
     */
    private String projectTypeSrch;

    /**
     * 检索条件 是否交保证金
     */
    private String isBailSrch;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    // EXCEL 导出用
    private String borrowNid;
    private String userId;
    private String username;
    private String borrowName;
    private String borrowProjectTypeName;
    private String account;
    private String borrowPeriod;
    private String borrowApr;
    private String borrowStyle;
    private String borrowServiceScale;
    private String borrowManagerScale;
    private String borrowMeasuresInstit;
    private String borrowAccountYes;
    private String borrowAccountWait;
    private String borrowAccountScale;
    private String status;
    private String addtime;
    private String verifyOverTime;
    private String ontime;
    private String bookingBeginTime;
    private String bookingEndTime;
    private String verifyTime;
    private String borrowValidTime;
    private String borrowFullTime;
    private String reverifyTime;
    private String recoverLastTime;
    private String repayLastTime;
    private String applicant; // 项目申请人

    // EXCEL 导出用

    /**
     * isBailSrch
     *
     * @return the isBailSrch
     */

    public String getIsBailSrch() {
        return isBailSrch;
    }

    /**
     * recoverTimeStartSrch
     *
     * @return the recoverTimeStartSrch
     */

    public String getRecoverTimeStartSrch() {
        return recoverTimeStartSrch;
    }

    /**
     * @param recoverTimeStartSrch
     *            the recoverTimeStartSrch to set
     */

    public void setRecoverTimeStartSrch(String recoverTimeStartSrch) {
        this.recoverTimeStartSrch = recoverTimeStartSrch;
    }

    /**
     * recoverTimeEndSrch
     *
     * @return the recoverTimeEndSrch
     */

    public String getRecoverTimeEndSrch() {
        return recoverTimeEndSrch;
    }

    /**
     * @param recoverTimeEndSrch
     *            the recoverTimeEndSrch to set
     */

    public void setRecoverTimeEndSrch(String recoverTimeEndSrch) {
        this.recoverTimeEndSrch = recoverTimeEndSrch;
    }

    /**
     * borrowNid
     *
     * @return the borrowNid
     */

    public String getBorrowNid() {
        return borrowNid;
    }

    /**
     * @param borrowNid
     *            the borrowNid to set
     */

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    /**
     * userId
     *
     * @return the userId
     */

    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * username
     *
     * @return the username
     */

    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * borrowName
     *
     * @return the borrowName
     */

    public String getBorrowName() {
        return borrowName;
    }

    /**
     * @param borrowName
     *            the borrowName to set
     */

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    /**
     * borrowProjectTypeName
     *
     * @return the borrowProjectTypeName
     */

    public String getBorrowProjectTypeName() {
        return borrowProjectTypeName;
    }

    /**
     * @param borrowProjectTypeName
     *            the borrowProjectTypeName to set
     */

    public void setBorrowProjectTypeName(String borrowProjectTypeName) {
        this.borrowProjectTypeName = borrowProjectTypeName;
    }

    /**
     * account
     *
     * @return the account
     */

    public String getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * borrowPeriod
     *
     * @return the borrowPeriod
     */

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    /**
     * @param borrowPeriod
     *            the borrowPeriod to set
     */

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    /**
     * borrowApr
     *
     * @return the borrowApr
     */

    public String getBorrowApr() {
        return borrowApr;
    }

    /**
     * @param borrowApr
     *            the borrowApr to set
     */

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    /**
     * borrowStyle
     *
     * @return the borrowStyle
     */

    public String getBorrowStyle() {
        return borrowStyle;
    }

    /**
     * @param borrowStyle
     *            the borrowStyle to set
     */

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    /**
     * borrowServiceScale
     *
     * @return the borrowServiceScale
     */

    public String getBorrowServiceScale() {
        return borrowServiceScale;
    }

    /**
     * @param borrowServiceScale
     *            the borrowServiceScale to set
     */

    public void setBorrowServiceScale(String borrowServiceScale) {
        this.borrowServiceScale = borrowServiceScale;
    }

    /**
     * borrowManagerScale
     *
     * @return the borrowManagerScale
     */

    public String getBorrowManagerScale() {
        return borrowManagerScale;
    }

    /**
     * @param borrowManagerScale
     *            the borrowManagerScale to set
     */

    public void setBorrowManagerScale(String borrowManagerScale) {
        this.borrowManagerScale = borrowManagerScale;
    }

    /**
     * borrowMeasuresInstit
     *
     * @return the borrowMeasuresInstit
     */

    public String getBorrowMeasuresInstit() {
        return borrowMeasuresInstit;
    }

    /**
     * @param borrowMeasuresInstit
     *            the borrowMeasuresInstit to set
     */

    public void setBorrowMeasuresInstit(String borrowMeasuresInstit) {
        this.borrowMeasuresInstit = borrowMeasuresInstit;
    }

    /**
     * borrowAccountYes
     *
     * @return the borrowAccountYes
     */

    public String getBorrowAccountYes() {
        return borrowAccountYes;
    }

    /**
     * @param borrowAccountYes
     *            the borrowAccountYes to set
     */

    public void setBorrowAccountYes(String borrowAccountYes) {
        this.borrowAccountYes = borrowAccountYes;
    }

    /**
     * borrowAccountWait
     *
     * @return the borrowAccountWait
     */

    public String getBorrowAccountWait() {
        return borrowAccountWait;
    }

    /**
     * @param borrowAccountWait
     *            the borrowAccountWait to set
     */

    public void setBorrowAccountWait(String borrowAccountWait) {
        this.borrowAccountWait = borrowAccountWait;
    }

    /**
     * borrowAccountScale
     *
     * @return the borrowAccountScale
     */

    public String getBorrowAccountScale() {
        return borrowAccountScale;
    }

    /**
     * @param borrowAccountScale
     *            the borrowAccountScale to set
     */

    public void setBorrowAccountScale(String borrowAccountScale) {
        this.borrowAccountScale = borrowAccountScale;
    }

    /**
     * status
     *
     * @return the status
     */

    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * addtime
     *
     * @return the addtime
     */

    public String getAddtime() {
        return addtime;
    }

    /**
     * @param addtime
     *            the addtime to set
     */

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    /**
     * verifyTime
     *
     * @return the verifyTime
     */

    public String getVerifyTime() {
        return verifyTime;
    }

    /**
     * @param verifyTime
     *            the verifyTime to set
     */

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }

    /**
     * ontime
     *
     * @return the ontime
     */

    public String getOntime() {
        return ontime;
    }

    /**
     * @param ontime
     *            the ontime to set
     */

    public void setOntime(String ontime) {
        this.ontime = ontime;
    }

    /**
     * verifyOverTime
     *
     * @return the verifyOverTime
     */

    public String getVerifyOverTime() {
        return verifyOverTime;
    }

    /**
     * @param verifyOverTime
     *            the verifyOverTime to set
     */

    public void setVerifyOverTime(String verifyOverTime) {
        this.verifyOverTime = verifyOverTime;
    }

    /**
     * borrowValidTime
     *
     * @return the borrowValidTime
     */

    public String getBorrowValidTime() {
        return borrowValidTime;
    }

    /**
     * @param borrowValidTime
     *            the borrowValidTime to set
     */

    public void setBorrowValidTime(String borrowValidTime) {
        this.borrowValidTime = borrowValidTime;
    }

    /**
     * borrowFullTime
     *
     * @return the borrowFullTime
     */

    public String getBorrowFullTime() {
        return borrowFullTime;
    }

    /**
     * @param borrowFullTime
     *            the borrowFullTime to set
     */

    public void setBorrowFullTime(String borrowFullTime) {
        this.borrowFullTime = borrowFullTime;
    }

    /**
     * reverifyTime
     *
     * @return the reverifyTime
     */

    public String getReverifyTime() {
        return reverifyTime;
    }

    /**
     * @param reverifyTime
     *            the reverifyTime to set
     */

    public void setReverifyTime(String reverifyTime) {
        this.reverifyTime = reverifyTime;
    }

    /**
     * recoverLastTime
     *
     * @return the recoverLastTime
     */

    public String getRecoverLastTime() {
        return recoverLastTime;
    }

    /**
     * @param recoverLastTime
     *            the recoverLastTime to set
     */

    public void setRecoverLastTime(String recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }

    /**
     * repayLastTime
     *
     * @return the repayLastTime
     */

    public String getRepayLastTime() {
        return repayLastTime;
    }

    /**
     * @param repayLastTime
     *            the repayLastTime to set
     */

    public void setRepayLastTime(String repayLastTime) {
        this.repayLastTime = repayLastTime;
    }

    /**
     * @param isBailSrch
     *            the isBailSrch to set
     */

    public void setIsBailSrch(String isBailSrch) {
        this.isBailSrch = isBailSrch;
    }

    /**
     * borrowStyleSrch
     *
     * @return the borrowStyleSrch
     */

    public String getBorrowStyleSrch() {
        return borrowStyleSrch;
    }

    /**
     * @param borrowStyleSrch
     *            the borrowStyleSrch to set
     */

    public void setBorrowStyleSrch(String borrowStyleSrch) {
        this.borrowStyleSrch = borrowStyleSrch;
    }

    /**
     * projectTypeSrch
     *
     * @return the projectTypeSrch
     */

    public String getProjectTypeSrch() {
        return projectTypeSrch;
    }

    /**
     * @param projectTypeSrch
     *            the projectTypeSrch to set
     */

    public void setProjectTypeSrch(String projectTypeSrch) {
        this.projectTypeSrch = projectTypeSrch;
    }

    /**
     * statusSrch
     *
     * @return the statusSrch
     */

    public String getStatusSrch() {
        return statusSrch;
    }

    /**
     * @param statusSrch
     *            the statusSrch to set
     */

    public void setStatusSrch(String statusSrch) {
        this.statusSrch = statusSrch;
    }

    /**
     * limitStart
     *
     * @return the limitStart
     */

    public int getLimitStart() {
        return limitStart;
    }

    /**
     * @param limitStart
     *            the limitStart to set
     */

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    /**
     * limitEnd
     *
     * @return the limitEnd
     */

    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * @param limitEnd
     *            the limitEnd to set
     */

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

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
     * borrowNameSrch
     *
     * @return the borrowNameSrch
     */

    public String getBorrowNameSrch() {
        return borrowNameSrch;
    }

    /**
     * @param borrowNameSrch
     *            the borrowNameSrch to set
     */

    public void setBorrowNameSrch(String borrowNameSrch) {
        this.borrowNameSrch = borrowNameSrch;
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

    /**
     * serialversionuid
     *
     * @return the serialversionuid
     */

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * sort
     *
     * @return the sort
     */

    public String getSort() {
        return sort;
    }

    /**
     * @param sort
     *            the sort to set
     */

    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * col
     *
     * @return the col
     */

    public String getCol() {
        return col;
    }

    /**
     * @param col
     *            the col to set
     */

    public void setCol(String col) {
        this.col = col;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getBookingBeginTime() {
        return bookingBeginTime;
    }

    public void setBookingBeginTime(String bookingBeginTime) {
        this.bookingBeginTime = bookingBeginTime;
    }

    public String getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(String bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

}
