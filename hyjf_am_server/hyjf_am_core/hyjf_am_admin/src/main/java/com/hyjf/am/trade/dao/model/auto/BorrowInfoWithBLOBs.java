package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class BorrowInfoWithBLOBs extends BorrowInfo implements Serializable {
    /**
     * 财务信息
     *
     * @mbggenerated
     */
    private String accountContents;

    /**
     * 借款的详情
     *
     * @mbggenerated
     */
    private String borrowContents;

    /**
     * 资金运转-用途
     *
     * @mbggenerated
     */
    private String borrowRunningUse;

    /**
     * 资金运转-来源
     *
     * @mbggenerated
     */
    private String borrowRunningSoruce;

    /**
     * 风险控制措施-机构
     *
     * @mbggenerated
     */
    private String borrowMeasuresInstit;

    /**
     * 风险控制措施-抵押物
     *
     * @mbggenerated
     */
    private String borrowMeasuresMort;

    /**
     * 险控制措施-措施
     *
     * @mbggenerated
     */
    private String borrowMeasuresMea;

    /**
     * 项目资料
     *
     * @mbggenerated
     */
    private String files;

    /**
     * 机构介绍
     *
     * @mbggenerated
     */
    private String borrowCompanyInstruction;

    /**
     * 操作流程
     *
     * @mbggenerated
     */
    private String borrowOperatingProcess;

    private static final long serialVersionUID = 1L;

    public String getAccountContents() {
        return accountContents;
    }

    public void setAccountContents(String accountContents) {
        this.accountContents = accountContents == null ? null : accountContents.trim();
    }

    public String getBorrowContents() {
        return borrowContents;
    }

    public void setBorrowContents(String borrowContents) {
        this.borrowContents = borrowContents == null ? null : borrowContents.trim();
    }

    public String getBorrowRunningUse() {
        return borrowRunningUse;
    }

    public void setBorrowRunningUse(String borrowRunningUse) {
        this.borrowRunningUse = borrowRunningUse == null ? null : borrowRunningUse.trim();
    }

    public String getBorrowRunningSoruce() {
        return borrowRunningSoruce;
    }

    public void setBorrowRunningSoruce(String borrowRunningSoruce) {
        this.borrowRunningSoruce = borrowRunningSoruce == null ? null : borrowRunningSoruce.trim();
    }

    public String getBorrowMeasuresInstit() {
        return borrowMeasuresInstit;
    }

    public void setBorrowMeasuresInstit(String borrowMeasuresInstit) {
        this.borrowMeasuresInstit = borrowMeasuresInstit == null ? null : borrowMeasuresInstit.trim();
    }

    public String getBorrowMeasuresMort() {
        return borrowMeasuresMort;
    }

    public void setBorrowMeasuresMort(String borrowMeasuresMort) {
        this.borrowMeasuresMort = borrowMeasuresMort == null ? null : borrowMeasuresMort.trim();
    }

    public String getBorrowMeasuresMea() {
        return borrowMeasuresMea;
    }

    public void setBorrowMeasuresMea(String borrowMeasuresMea) {
        this.borrowMeasuresMea = borrowMeasuresMea == null ? null : borrowMeasuresMea.trim();
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files == null ? null : files.trim();
    }

    public String getBorrowCompanyInstruction() {
        return borrowCompanyInstruction;
    }

    public void setBorrowCompanyInstruction(String borrowCompanyInstruction) {
        this.borrowCompanyInstruction = borrowCompanyInstruction == null ? null : borrowCompanyInstruction.trim();
    }

    public String getBorrowOperatingProcess() {
        return borrowOperatingProcess;
    }

    public void setBorrowOperatingProcess(String borrowOperatingProcess) {
        this.borrowOperatingProcess = borrowOperatingProcess == null ? null : borrowOperatingProcess.trim();
    }
}