/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yaoyong
 * @version UserPortraitScoreCustomizeVO, v0.1 2018/8/9 11:22
 */
public class UserPortraitScoreCustomizeVO extends BaseVO implements Serializable {
    private int userId;

    private String userName;

    private String sexAge;

    private double funds;

    private BigDecimal interest;

    private int  tradeNumber;

    private int customerSource;

    private String investProcess;

    private String returnActive;

    private String loginActive;

    private String inviteActive;

    private BigDecimal fundRetentionPercent;

    private String isBigCustomer;

    private String statusTab;

    private String identityLabel;

    private double vip;

    private double trust;

    private double compete;

    private double loss;

    private double intention;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSexAge() {
        return sexAge;
    }

    public void setSexAge(String sexAge) {
        this.sexAge = sexAge;
    }

    public int getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(int customerSource) {
        this.customerSource = customerSource;
    }

    public String getInvestProcess() {
        return investProcess;
    }

    public void setInvestProcess(String investProcess) {
        this.investProcess = investProcess;
    }

    public String getReturnActive() {
        return returnActive;
    }

    public void setReturnActive(String returnActive) {
        this.returnActive = returnActive;
    }

    public String getLoginActive() {
        return loginActive;
    }

    public void setLoginActive(String loginActive) {
        this.loginActive = loginActive;
    }

    public String getInviteActive() {
        return inviteActive;
    }

    public void setInviteActive(String inviteActive) {
        this.inviteActive = inviteActive;
    }


    public String getIsBigCustomer() {
        return isBigCustomer;
    }

    public void setIsBigCustomer(String isBigCustomer) {
        this.isBigCustomer = isBigCustomer;
    }

    public String getStatusTab() {
        return statusTab;
    }

    public void setStatusTab(String statusTab) {
        this.statusTab = statusTab;
    }

    public String getIdentityLabel() {
        return identityLabel;
    }

    public void setIdentityLabel(String identityLabel) {
        this.identityLabel = identityLabel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public int getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(int tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public BigDecimal getFundRetentionPercent() {
        return fundRetentionPercent;
    }

    public void setFundRetentionPercent(BigDecimal fundRetentionPercent) {
        this.fundRetentionPercent = fundRetentionPercent;
    }

    public double getVip() {
        return vip;
    }

    public void setVip(double vip) {
        this.vip = vip;
    }

    public double getTrust() {
        return trust;
    }

    public void setTrust(double trust) {
        this.trust = trust;
    }

    public double getCompete() {
        return compete;
    }

    public void setCompete(double compete) {
        this.compete = compete;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public double getIntention() {
        return intention;
    }

    public void setIntention(double intention) {
        this.intention = intention;
    }
}
