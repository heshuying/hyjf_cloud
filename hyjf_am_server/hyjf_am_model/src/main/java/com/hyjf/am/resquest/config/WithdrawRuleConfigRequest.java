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

    private String withdrawMoney;

    private String withdrawTime;

    private Integer isHoliday;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(String withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public String getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(String withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public Integer getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(Integer isHoliday) {
        this.isHoliday = isHoliday;
    }
}
