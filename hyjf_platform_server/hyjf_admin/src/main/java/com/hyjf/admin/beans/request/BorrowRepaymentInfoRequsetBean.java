package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

import java.io.Serializable;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoRequsetBean, v0.1 2018/7/7 14:18
 */
public class BorrowRepaymentInfoRequsetBean extends BaseRequest implements Serializable {
    /**
     * 借款编号 检索条件
     */
    private String borrowNidSrch;
    /**
     * 借款编号 检索条件
     */
    private String planNidSrch;
    /**
     * 借款标题 检索条件
     */
    private String borrowNameSrch;
    /**
     * 用户名 检索条件
     */
    private String usernameSrch;
    /**
     * 推荐人 检索条件
     */
    private String referrerNameSrch;
    /**
     * 还款方式 检索条件
     */
    private String borrowStyleSrch;
    /**
     * 操作平台 检索条件
     */
    private String clientSrch;
    /**
     * 渠道 检索条件
     */
    private String utmIdSrch;

    /**
     * 还款批次号 检索条件
     */
    private String repayBatchNo;
    /**
     * 投资时间 检索条件
     */
    private String timeStartSrch;
    /**
     * 投资时间 检索条件
     */
    private String timeEndSrch;

    /**
     * 还款日期 检索条件
     */
    private String yesTimeStartSrch;
    /**
     * 还款日期 检索条件
     */
    private String yesTimeEndSrch;

    /**
     * 列表来源标识 0：还款明细 1：批次还款-查看按钮
     */
    private  int serchFlag = 0;

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getPlanNidSrch() {
        return planNidSrch;
    }

    public void setPlanNidSrch(String planNidSrch) {
        this.planNidSrch = planNidSrch;
    }

    public String getBorrowNameSrch() {
        return borrowNameSrch;
    }

    public void setBorrowNameSrch(String borrowNameSrch) {
        this.borrowNameSrch = borrowNameSrch;
    }

    public String getUsernameSrch() {
        return usernameSrch;
    }

    public void setUsernameSrch(String usernameSrch) {
        this.usernameSrch = usernameSrch;
    }

    public String getReferrerNameSrch() {
        return referrerNameSrch;
    }

    public void setReferrerNameSrch(String referrerNameSrch) {
        this.referrerNameSrch = referrerNameSrch;
    }

    public String getBorrowStyleSrch() {
        return borrowStyleSrch;
    }

    public void setBorrowStyleSrch(String borrowStyleSrch) {
        this.borrowStyleSrch = borrowStyleSrch;
    }

    public String getClientSrch() {
        return clientSrch;
    }

    public void setClientSrch(String clientSrch) {
        this.clientSrch = clientSrch;
    }

    public String getUtmIdSrch() {
        return utmIdSrch;
    }

    public void setUtmIdSrch(String utmIdSrch) {
        this.utmIdSrch = utmIdSrch;
    }

    public String getRepayBatchNo() {
        return repayBatchNo;
    }

    public void setRepayBatchNo(String repayBatchNo) {
        this.repayBatchNo = repayBatchNo;
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

    public String getYesTimeStartSrch() {
        return yesTimeStartSrch;
    }

    public void setYesTimeStartSrch(String yesTimeStartSrch) {
        this.yesTimeStartSrch = yesTimeStartSrch;
    }

    public String getYesTimeEndSrch() {
        return yesTimeEndSrch;
    }

    public void setYesTimeEndSrch(String yesTimeEndSrch) {
        this.yesTimeEndSrch = yesTimeEndSrch;
    }

    public int getSerchFlag() {
        return serchFlag;
    }

    public void setSerchFlag(int serchFlag) {
        this.serchFlag = serchFlag;
    }
}
