package com.hyjf.am.trade.dao.model.customize;

import java.math.BigDecimal;

/**
 * @author lisheng
 * @version WrbRecoverCustomize, v0.1 2018/11/7 16:17
 */

public class WrbRecoverCustomize {
    /** 每月回款日 例如15就是15日 */
    private String monthly_back_date = null;

    /** 下一回款日 格式2015-10-01，如是分期回款，为查询时间下一个回款日期 */
    private String next_back_date = "";

    /** 下一回款金额 */
    private BigDecimal next_back_money = BigDecimal.ZERO;

    /** 下一回款本金 */
    private BigDecimal next_back_principal = BigDecimal.ZERO;

    /** 一下回款利息 */
    private BigDecimal next_back_interest = BigDecimal.ZERO;

    /** 提前结束状态 默认：0没有提前结束
     1表示项目提前结束
     */
    private Integer back_early_state = 0;

    /** 提前结束时间 默认为空，
     项目提前结束时间：格式2015-09-01 20:30:12精确到秒
     */
    private String back_early_time = "";

    public String getMonthly_back_date() {
        return monthly_back_date;
    }

    public void setMonthly_back_date(String monthly_back_date) {
        this.monthly_back_date = monthly_back_date;
    }

    public String getNext_back_date() {
        return next_back_date;
    }

    public void setNext_back_date(String next_back_date) {
        this.next_back_date = next_back_date;
    }

    public BigDecimal getNext_back_money() {
        return next_back_money;
    }

    public void setNext_back_money(BigDecimal next_back_money) {
        this.next_back_money = next_back_money;
    }

    public BigDecimal getNext_back_principal() {
        return next_back_principal;
    }

    public void setNext_back_principal(BigDecimal next_back_principal) {
        this.next_back_principal = next_back_principal;
    }

    public BigDecimal getNext_back_interest() {
        return next_back_interest;
    }

    public void setNext_back_interest(BigDecimal next_back_interest) {
        this.next_back_interest = next_back_interest;
    }

    public Integer getBack_early_state() {
        return back_early_state;
    }

    public void setBack_early_state(Integer back_early_state) {
        this.back_early_state = back_early_state;
    }

    public String getBack_early_time() {
        return back_early_time;
    }

    public void setBack_early_time(String back_early_time) {
        this.back_early_time = back_early_time;
    }
}
