package com.hyjf.am.vo.wdzj;

import java.util.List;

/**
 * @author hesy
 * @version BorrowListCustomizeVO, v0.1 2018/7/16 12:10
 */
public class BorrowListCustomizeVO {
    private String projectId;
    private String userName;
    private String title;
    private String amount;
    private String type;
    private String schedule;
    private String interestRate;
    private String deadline;
    private String deadlineUnit;
    private String repaymentType;
    private String loanUrl;
    private String successTime;
    private String reward;

    private List<TenderListCustomizeVO> subscribes;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDeadlineUnit() {
        return deadlineUnit;
    }

    public void setDeadlineUnit(String deadlineUnit) {
        this.deadlineUnit = deadlineUnit;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public String getLoanUrl() {
        return loanUrl;
    }

    public void setLoanUrl(String loanUrl) {
        this.loanUrl = loanUrl;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public List<TenderListCustomizeVO> getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(List<TenderListCustomizeVO> subscribes) {
        this.subscribes = subscribes;
    }
}
