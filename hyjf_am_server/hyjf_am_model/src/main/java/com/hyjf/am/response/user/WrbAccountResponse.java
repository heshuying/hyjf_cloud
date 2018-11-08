package com.hyjf.am.response.user;

import com.hyjf.am.response.WrbResponse;

import java.math.BigDecimal;

/**
 * @author lisheng
 * @version WrbAccountResponse, v0.1 2018/11/7 10:07
 */

public class WrbAccountResponse extends WrbResponse {
    /** 平台用户id */
    private String pf_user_id;

    /** 账户总额 */
    private BigDecimal all_balance = BigDecimal.ZERO;

    /** 可用余额 */
    private BigDecimal available_balance = BigDecimal.ZERO;

    /** 冻结金额 */
    private BigDecimal frozen_money = BigDecimal.ZERO;

    /** 奖励金额 */
    private String reward = "";

    /** 待收本金 */
    private BigDecimal investing_principal = BigDecimal.ZERO;

    /** 待收利息 */
    private BigDecimal investing_interest = BigDecimal.ZERO;

    /** 累计已回款收益 */
    private BigDecimal earned_interest = BigDecimal.ZERO;

    /** 活期金额 */
    private BigDecimal current_money = BigDecimal.ZERO;

    public String getPf_user_id() {
        return pf_user_id;
    }

    public void setPf_user_id(String pf_user_id) {
        this.pf_user_id = pf_user_id;
    }

    public BigDecimal getAll_balance() {
        return all_balance;
    }

    public void setAll_balance(BigDecimal all_balance) {
        this.all_balance = all_balance;
    }

    public BigDecimal getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(BigDecimal available_balance) {
        this.available_balance = available_balance;
    }

    public BigDecimal getFrozen_money() {
        return frozen_money;
    }

    public void setFrozen_money(BigDecimal frozen_money) {
        this.frozen_money = frozen_money;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public BigDecimal getInvesting_principal() {
        return investing_principal;
    }

    public void setInvesting_principal(BigDecimal investing_principal) {
        this.investing_principal = investing_principal;
    }

    public BigDecimal getInvesting_interest() {

        return investing_interest;
    }

    public void setInvesting_interest(BigDecimal investing_interest) {

        this.investing_interest = investing_interest;
    }

    public BigDecimal getEarned_interest() {
        return earned_interest;
    }

    public void setEarned_interest(BigDecimal earned_interest) {

        this.earned_interest = earned_interest;
    }

    public BigDecimal getCurrent_money() {

        return current_money;
    }

    public void setCurrent_money(BigDecimal current_money) {

        this.current_money = current_money;
    }
}
