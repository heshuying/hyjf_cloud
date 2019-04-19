/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.config;

import com.hyjf.am.resquest.Request;

import java.io.Serializable;

/**
 * 提现规则配置Request
 *
 * @author liuyang
 * @version WithdrawRuleConfigRequest, v0.1 2019/4/19 16:23
 */
public class WithdrawRuleConfigRequest extends Request implements Serializable {
    private static final long serialVersionUID = 9152500911491496789L;

    private Integer userType;

    private String withdrawmoney;

    private String withdrawTime;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getWithdrawmoney() {
        return withdrawmoney;
    }

    public void setWithdrawmoney(String withdrawmoney) {
        this.withdrawmoney = withdrawmoney;
    }

    public String getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(String withdrawTime) {
        this.withdrawTime = withdrawTime;
    }
}
