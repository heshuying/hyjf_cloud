package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class DebtBorrowWithBLOBs extends DebtBorrow implements Serializable {
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
     * 上传的附件
     *
     * @mbggenerated
     */
    private String borrowUpfiles;

    private String diyaContents;

    private String borrowPawnDescription;

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
     * 政策及市场分析-政策支持
     *
     * @mbggenerated
     */
    private String borrowAnalysisPolicy;

    /**
     * 政策及市场分析-市场分析
     *
     * @mbggenerated
     */
    private String borrowAnalysisMarket;

    /**
     * 企业背景
     *
     * @mbggenerated
     */
    private String borrowCompany;

    /**
     * 企业信息-营业范围
     *
     * @mbggenerated
     */
    private String borrowCompanyScope;

    /**
     * 企业信息-经营状况
     *
     * @mbggenerated
     */
    private String borrowCompanyBusiness;

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

    public String getBorrowUpfiles() {
        return borrowUpfiles;
    }

    public void setBorrowUpfiles(String borrowUpfiles) {
        this.borrowUpfiles = borrowUpfiles == null ? null : borrowUpfiles.trim();
    }

    public String getDiyaContents() {
        return diyaContents;
    }

    public void setDiyaContents(String diyaContents) {
        this.diyaContents = diyaContents == null ? null : diyaContents.trim();
    }

    public String getBorrowPawnDescription() {
        return borrowPawnDescription;
    }

    public void setBorrowPawnDescription(String borrowPawnDescription) {
        this.borrowPawnDescription = borrowPawnDescription == null ? null : borrowPawnDescription.trim();
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

    public String getBorrowAnalysisPolicy() {
        return borrowAnalysisPolicy;
    }

    public void setBorrowAnalysisPolicy(String borrowAnalysisPolicy) {
        this.borrowAnalysisPolicy = borrowAnalysisPolicy == null ? null : borrowAnalysisPolicy.trim();
    }

    public String getBorrowAnalysisMarket() {
        return borrowAnalysisMarket;
    }

    public void setBorrowAnalysisMarket(String borrowAnalysisMarket) {
        this.borrowAnalysisMarket = borrowAnalysisMarket == null ? null : borrowAnalysisMarket.trim();
    }

    public String getBorrowCompany() {
        return borrowCompany;
    }

    public void setBorrowCompany(String borrowCompany) {
        this.borrowCompany = borrowCompany == null ? null : borrowCompany.trim();
    }

    public String getBorrowCompanyScope() {
        return borrowCompanyScope;
    }

    public void setBorrowCompanyScope(String borrowCompanyScope) {
        this.borrowCompanyScope = borrowCompanyScope == null ? null : borrowCompanyScope.trim();
    }

    public String getBorrowCompanyBusiness() {
        return borrowCompanyBusiness;
    }

    public void setBorrowCompanyBusiness(String borrowCompanyBusiness) {
        this.borrowCompanyBusiness = borrowCompanyBusiness == null ? null : borrowCompanyBusiness.trim();
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