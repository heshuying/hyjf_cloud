/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

import com.hyjf.common.util.CustomConstants;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author wangjun
 * @version CurrentHoldRepayMentPlanDetailsCustomize, v0.1 2018/8/16 14:29
 */
public class CurrentHoldRepayMentPlanDetailsCustomize implements Serializable {
    DecimalFormat df = CustomConstants.DF_FOR_VIEW;
    /**
     * 待收本息总额
     */
    private String recoverAccountWait;
    /**
     * 待收本金总额
     */
    private String recoverCapitalWait;
    /**
     * 待收利息总额
     */
    private String recoverInterestWait;
    /**
     * 已收本息总额
     */
    private String recoverAccountYes;
    /**
     * 投资时间
     */
    private String createTime;
    /**
     * 项目编号
     */
    private String borrowNid;

    public String getRecoverAccountWait() {
        return df.format(new BigDecimal(recoverAccountWait));
    }

    public void setRecoverAccountWait(String recoverAccountWait) {
        this.recoverAccountWait = recoverAccountWait;
    }

    public String getRecoverCapitalWait() {
        return df.format(new BigDecimal(recoverCapitalWait));
    }

    public void setRecoverCapitalWait(String recoverCapitalWait) {
        this.recoverCapitalWait = recoverCapitalWait;
    }

    public String getRecoverInterestWait() {
        return df.format(new BigDecimal(recoverInterestWait));
    }

    public void setRecoverInterestWait(String recoverInterestWait) {
        this.recoverInterestWait = recoverInterestWait;
    }

    public String getRecoverAccountYes() {
        return df.format(new BigDecimal(recoverAccountYes));
    }

    public void setRecoverAccountYes(String recoverAccountYes) {
        this.recoverAccountYes = recoverAccountYes;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
