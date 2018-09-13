package com.hyjf.am.vo.task.issuerecover;

import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;

import java.io.Serializable;

public class BorrowWithBLOBs extends BorrowAndInfoVO implements Serializable {
    private String accountContents;

    private String borrowContents;

    private String borrowUpfiles;

    private String diyaContents;

    private String borrowPawnDescription;

    private String borrowRunningUse;

    private String borrowRunningSoruce;

    private String borrowMeasuresInstit;

    private String borrowMeasuresMort;

    private String borrowMeasuresMea;

    private String borrowAnalysisPolicy;

    private String borrowAnalysisMarket;

    private String borrowCompany;

    private String borrowCompanyScope;

    private String borrowCompanyBusiness;

    private String files;

    private String borrowCompanyInstruction;

    private String borrowOperatingProcess;

    private static final long serialVersionUID = 1L;

    public String getAccountContents() {
        return accountContents;
    }

    public void setAccountContents(String accountContents) {
        this.accountContents = accountContents;
    }

    public String getBorrowContents() {
        return borrowContents;
    }

    public void setBorrowContents(String borrowContents) {
        this.borrowContents = borrowContents;
    }

    public String getBorrowUpfiles() {
        return borrowUpfiles;
    }

    public void setBorrowUpfiles(String borrowUpfiles) {
        this.borrowUpfiles = borrowUpfiles;
    }

    public String getDiyaContents() {
        return diyaContents;
    }

    public void setDiyaContents(String diyaContents) {
        this.diyaContents = diyaContents;
    }

    public String getBorrowPawnDescription() {
        return borrowPawnDescription;
    }

    public void setBorrowPawnDescription(String borrowPawnDescription) {
        this.borrowPawnDescription = borrowPawnDescription;
    }

    public String getBorrowRunningUse() {
        return borrowRunningUse;
    }

    public void setBorrowRunningUse(String borrowRunningUse) {
        this.borrowRunningUse = borrowRunningUse;
    }

    public String getBorrowRunningSoruce() {
        return borrowRunningSoruce;
    }

    public void setBorrowRunningSoruce(String borrowRunningSoruce) {
        this.borrowRunningSoruce = borrowRunningSoruce;
    }

    public String getBorrowMeasuresInstit() {
        return borrowMeasuresInstit;
    }

    public void setBorrowMeasuresInstit(String borrowMeasuresInstit) {
        this.borrowMeasuresInstit = borrowMeasuresInstit;
    }

    public String getBorrowMeasuresMort() {
        return borrowMeasuresMort;
    }

    public void setBorrowMeasuresMort(String borrowMeasuresMort) {
        this.borrowMeasuresMort = borrowMeasuresMort;
    }

    public String getBorrowMeasuresMea() {
        return borrowMeasuresMea;
    }

    public void setBorrowMeasuresMea(String borrowMeasuresMea) {
        this.borrowMeasuresMea = borrowMeasuresMea;
    }

    public String getBorrowAnalysisPolicy() {
        return borrowAnalysisPolicy;
    }

    public void setBorrowAnalysisPolicy(String borrowAnalysisPolicy) {
        this.borrowAnalysisPolicy = borrowAnalysisPolicy;
    }

    public String getBorrowAnalysisMarket() {
        return borrowAnalysisMarket;
    }

    public void setBorrowAnalysisMarket(String borrowAnalysisMarket) {
        this.borrowAnalysisMarket = borrowAnalysisMarket;
    }

    public String getBorrowCompany() {
        return borrowCompany;
    }

    public void setBorrowCompany(String borrowCompany) {
        this.borrowCompany = borrowCompany;
    }

    public String getBorrowCompanyScope() {
        return borrowCompanyScope;
    }

    public void setBorrowCompanyScope(String borrowCompanyScope) {
        this.borrowCompanyScope = borrowCompanyScope;
    }

    public String getBorrowCompanyBusiness() {
        return borrowCompanyBusiness;
    }

    public void setBorrowCompanyBusiness(String borrowCompanyBusiness) {
        this.borrowCompanyBusiness = borrowCompanyBusiness;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getBorrowCompanyInstruction() {
        return borrowCompanyInstruction;
    }

    public void setBorrowCompanyInstruction(String borrowCompanyInstruction) {
        this.borrowCompanyInstruction = borrowCompanyInstruction;
    }

    public String getBorrowOperatingProcess() {
        return borrowOperatingProcess;
    }

    public void setBorrowOperatingProcess(String borrowOperatingProcess) {
        this.borrowOperatingProcess = borrowOperatingProcess;
    }
}
