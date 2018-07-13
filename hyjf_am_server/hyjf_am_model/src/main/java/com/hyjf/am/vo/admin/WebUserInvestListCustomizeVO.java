/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author wangjun
 * @version WebUserInvestListCustomizeVO, v0.1 2018/7/12 10:49
 */
public class WebUserInvestListCustomizeVO extends BaseVO implements Serializable {
    public String id;

    public String realName;

    public String username;

    public String idCard;

    public String account;

    public String recoverAccountInterest;

    public String investPeriod;

    public String interest;

    public String investStartTime;

    public String investEndTime;

    public String investStartDay;

    public String investEndDay;

    public String methodNid;

    public String repayMethod;

    public String total;

    public String nid;

    public String orderId;

    public String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRecoverAccountInterest() {
        return recoverAccountInterest;
    }

    public void setRecoverAccountInterest(String recoverAccountInterest) {
        this.recoverAccountInterest = recoverAccountInterest;
    }

    public String getInvestPeriod() {
        return investPeriod;
    }

    public void setInvestPeriod(String investPeriod) {
        this.investPeriod = investPeriod;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getInvestStartTime() {
        return investStartTime;
    }

    public void setInvestStartTime(String investStartTime) {
        this.investStartTime = investStartTime;
    }

    public String getInvestEndTime() {
        return investEndTime;
    }

    public void setInvestEndTime(String investEndTime) {
        this.investEndTime = investEndTime;
    }

    public String getInvestStartDay() {
        return investStartDay;
    }

    public void setInvestStartDay(String investStartDay) {
        this.investStartDay = investStartDay;
    }

    public String getInvestEndDay() {
        return investEndDay;
    }

    public void setInvestEndDay(String investEndDay) {
        this.investEndDay = investEndDay;
    }

    public String getMethodNid() {
        return methodNid;
    }

    public void setMethodNid(String methodNid) {
        this.methodNid = methodNid;
    }

    public String getRepayMethod() {
        return repayMethod;
    }

    public void setRepayMethod(String repayMethod) {
        this.repayMethod = repayMethod;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
