package com.hyjf.am.vo.api;

import java.math.BigDecimal;

/**
 * @author lisheng
 * @version WrbDaySumCustomize, v0.1 2018/11/7 10:52
 */

public class WrbDaySumCustomize {
    /**
     * 投资人数
     */
    private Integer lend_count;

    /**
     * 借款人数
     */
    private Integer borrow_count;

    /**
     * 投资总额
     */
    private BigDecimal invest_all_money;

    /**
     * 总待收本金(风车)
     */
    private BigDecimal fc_all_wait_back_money;

    /**
     * 总待收本金
     */
    private BigDecimal all_wait_back_money;

    public Integer getLend_count() {
        return lend_count;
    }

    public void setLend_count(Integer lend_count) {
        this.lend_count = lend_count;
    }

    public Integer getBorrow_count() {
        return borrow_count;
    }

    public void setBorrow_count(Integer borrow_count) {
        this.borrow_count = borrow_count;
    }

    public BigDecimal getInvest_all_money() {
        return invest_all_money;
    }

    public void setInvest_all_money(BigDecimal invest_all_money) {
        this.invest_all_money = invest_all_money;
    }

    public BigDecimal getFc_all_wait_back_money() {
        return fc_all_wait_back_money;
    }

    public void setFc_all_wait_back_money(BigDecimal fc_all_wait_back_money) {
        this.fc_all_wait_back_money = fc_all_wait_back_money;
    }

    public BigDecimal getAll_wait_back_money() {
        return all_wait_back_money;
    }

    public void setAll_wait_back_money(BigDecimal all_wait_back_money) {
        this.all_wait_back_money = all_wait_back_money;
    }
}
