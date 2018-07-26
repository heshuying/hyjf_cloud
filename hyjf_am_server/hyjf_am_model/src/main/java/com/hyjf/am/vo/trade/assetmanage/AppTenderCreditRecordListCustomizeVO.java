package com.hyjf.am.vo.trade.assetmanage;

/**
 * @author pangchengchao
 * @version AppTenderCreditRecordListCustomizeVO, v0.1 2018/7/25 14:51
 */
public class AppTenderCreditRecordListCustomizeVO {
    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 7610889796240275940L;

    /**
     * 债转编号 实际是原标号  bid_nid  接口被很多地方用，不太好改
     */
    private String creditNid;

    /**
     * 债转编号
     */
    private String realCreditNid;

    /**
     * 债转编号画面表示用:HZR+债转编号
     */
    private String creditNidPage;

    /**
     * 债转时间
     */
    private String creditTime;

    /**
     * 债权本金
     */
    private String creditCapital;

    /**
     * 已转让本金
     */
    private String creditCapitalAssigned;

    /**
     * 债转状态 转让中 已还款  50%
     */
    private String creditStatus;

    /**
     * 债转状态 - 转让中 已完成 已结束
     */
    private String creditStatusDesc;

    /**
     * 债转进度
     */
    private String creditProcess;

    /**
     * 债转详情URL
     */
    private String creditRecordInfoUrl;

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getCreditNidPage() {
        return creditNidPage;
    }

    public void setCreditNidPage(String creditNidPage) {
        this.creditNidPage = creditNidPage;
    }

    public String getCreditTime() {
        return creditTime;
    }

    public void setCreditTime(String creditTime) {
        this.creditTime = creditTime;
    }

    public String getCreditCapital() {
        return creditCapital;
    }

    public void setCreditCapital(String creditCapital) {
        this.creditCapital = creditCapital;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getCreditProcess() {
        return creditProcess;
    }

    public void setCreditProcess(String creditProcess) {
        this.creditProcess = creditProcess;
    }

    public String getCreditRecordInfoUrl() {
        return creditRecordInfoUrl;
    }

    public void setCreditRecordInfoUrl(String creditRecordInfoUrl) {
        this.creditRecordInfoUrl = creditRecordInfoUrl;
    }

    public String getCreditCapitalAssigned() {
        return creditCapitalAssigned;
    }

    public void setCreditCapitalAssigned(String creditCapitalAssigned) {
        this.creditCapitalAssigned = creditCapitalAssigned;
    }

    public String getCreditStatusDesc() {
        return creditStatusDesc;
    }

    public void setCreditStatusDesc(String creditStatusDesc) {
        this.creditStatusDesc = creditStatusDesc;
    }

    public String getRealCreditNid() {
        return realCreditNid;
    }

    public void setRealCreditNid(String realCreditNid) {
        this.realCreditNid = realCreditNid;
    }
}
