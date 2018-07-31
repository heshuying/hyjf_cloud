/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.app;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author jun
 * @version AppTenderCreditRepayPlanListCustomizeVO, v0.1 2018/7/30 17:51
 */
public class AppTenderCreditRepayPlanListCustomizeVO extends BaseVO implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -2869301136107487471L;

    // 类型：本息还是利息
    private String repayName;

    // 金额
    private String account;

    // 时间
    private String repayTime;

    // 还款状态
    private String repayStatus;

    /**
     * 构造方法
     */
    public AppTenderCreditRepayPlanListCustomizeVO() {
        super();
    }

    public String getRepayName() {
        return repayName;
    }

    public void setRepayName(String repayName) {
        this.repayName = repayName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }
}
