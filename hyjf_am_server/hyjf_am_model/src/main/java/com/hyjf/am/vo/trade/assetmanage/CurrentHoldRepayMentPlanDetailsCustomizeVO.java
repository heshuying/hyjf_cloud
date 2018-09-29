/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.assetmanage;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.common.util.CustomConstants;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * @author wangjun
 * @version CurrentHoldRepayMentPlanDetailsCustomizeVO, v0.1 2018/8/16 14:35
 */
public class CurrentHoldRepayMentPlanDetailsCustomizeVO extends BaseVO implements Serializable {
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
        return recoverAccountWait;
    }

    public void setRecoverAccountWait(String recoverAccountWait) {
        this.recoverAccountWait = recoverAccountWait;
    }

    public String getRecoverCapitalWait() {
        return recoverCapitalWait;
    }

    public void setRecoverCapitalWait(String recoverCapitalWait) {
        this.recoverCapitalWait = recoverCapitalWait;
    }

    public String getRecoverInterestWait() {
        return recoverInterestWait;
    }

    public void setRecoverInterestWait(String recoverInterestWait) {
        this.recoverInterestWait = recoverInterestWait;
    }

    public String getRecoverAccountYes() {
        return recoverAccountYes;
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
