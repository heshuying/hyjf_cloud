package com.hyjf.cs.user.bean;

import java.util.List;

public class AemsSyncUserInfoResult extends BaseResultBean {

    private List<AccountBean> data;

    public  static class AccountBean{
        //电子账号
        private String accountId;
        //计划待收金额
        private String planAwaitAmount;
        //计划待收本金
        private String planAwaitCapital;
        //计划待收收益
        private String planAwaitInterest;
        //散标待收金额
        private String borrowAwaitAmount;
        //散标待收本金
        private String borrowAwaitCapital;
        //散标待收收益
        private String borrowAwaitInterest;
        //可用余额
        private String balanceAmount;
        //冻结金额
        private String frozenAmount;
        //资产总额
        private String totalAmount;
        //累计投资金额
        private String investSum;
        //累计收益
        private String interestSum;

        public String getPlanAwaitAmount() {
            return planAwaitAmount;
        }

        public void setPlanAwaitAmount(String planAwaitAmount) {
            this.planAwaitAmount = planAwaitAmount;
        }

        public String getPlanAwaitCapital() {
            return planAwaitCapital;
        }

        public void setPlanAwaitCapital(String planAwaitCapital) {
            this.planAwaitCapital = planAwaitCapital;
        }

        public String getPlanAwaitInterest() {
            return planAwaitInterest;
        }

        public void setPlanAwaitInterest(String planAwaitInterest) {
            this.planAwaitInterest = planAwaitInterest;
        }

        public String getBorrowAwaitAmount() {
            return borrowAwaitAmount;
        }

        public void setBorrowAwaitAmount(String borrowAwaitAmount) {
            this.borrowAwaitAmount = borrowAwaitAmount;
        }

        public String getBorrowAwaitCapital() {
            return borrowAwaitCapital;
        }

        public void setBorrowAwaitCapital(String borrowAwaitCapital) {
            this.borrowAwaitCapital = borrowAwaitCapital;
        }

        public String getBorrowAwaitInterest() {
            return borrowAwaitInterest;
        }

        public void setBorrowAwaitInterest(String borrowAwaitInterest) {
            this.borrowAwaitInterest = borrowAwaitInterest;
        }

        public String getBalanceAmount() {
            return balanceAmount;
        }

        public void setBalanceAmount(String balanceAmount) {
            this.balanceAmount = balanceAmount;
        }

        public String getFrozenAmount() {
            return frozenAmount;
        }

        public void setFrozenAmount(String frozenAmount) {
            this.frozenAmount = frozenAmount;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getInvestSum() {
            return investSum;
        }

        public void setInvestSum(String investSum) {
            this.investSum = investSum;
        }

        public String getInterestSum() {
            return interestSum;
        }

        public void setInterestSum(String interestSum) {
            this.interestSum = interestSum;
        }
    }

    @Override
    public List<AccountBean> getData() {
        return data;
    }

    public void setData(List<AccountBean> data) {
        this.data = data;
    }
}
