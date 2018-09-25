/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.wrb;

import java.math.BigDecimal;

/**
 * @author fq
 * @version WrbBorrowListCustomizeVO, v0.1 2018/9/25 10:46
 */
public class WrbBorrowListCustomizeVO {
    /**
     * 标的ID
     */
    private String invest_id;
    /**
     * 标题
     */
    private String invest_title;
    /**
     * 标的路径
     */
    private String invest_url;
    /**
     * 标的期限
     */
    private Integer time_limit;
    /**
     * 标的期限描述
     */
    private String time_limit_desc;
    /**
     * 标的购买限额
     */
    private BigDecimal buy_limit;
    /**
     * 标的起投金额
     */
    private BigDecimal buy_unit;
    /**
     * 标的已投金额
     */
    private BigDecimal invested_amount;
    /**
     * 标的总额
     */
    private String total_amount;
    /**
     * 标的年化利率
     */
    private Float rate;
    /**
     * 标的进度
     */
    private String progress;
    /**
     * 标的开标时间
     */
    private String start_time;
    /**
     * 标的还款方式
     */
    private String payback_way;
    /**
     * 标的投资条件
     */
    private String invest_condition;
    /**
     * 标的描述
     */
    private String project_description;
    /**
     * 标的流标情况
     */
    private String lose_invest;

    public String getInvest_id() {
        return invest_id;
    }
    public void setInvest_id(String invest_id) {
        this.invest_id = invest_id;
    }
    public String getInvest_title() {
        return invest_title;
    }
    public void setInvest_title(String invest_title) {
        this.invest_title = invest_title;
    }
    public String getInvest_url() {
        return invest_url;
    }
    public void setInvest_url(String invest_url) {
        this.invest_url = invest_url;
    }
    public Integer getTime_limit() {
        return time_limit;
    }
    public void setTime_limit(Integer time_limit) {
        this.time_limit = time_limit;
    }
    public String getTime_limit_desc() {
        return time_limit_desc;
    }
    public void setTime_limit_desc(String time_limit_desc) {
        this.time_limit_desc = time_limit_desc;
    }
    public BigDecimal getBuy_limit() {
        return buy_limit;
    }
    public void setBuy_limit(BigDecimal buy_limit) {
        this.buy_limit = buy_limit;
    }
    public BigDecimal getBuy_unit() {
        return buy_unit;
    }
    public void setBuy_unit(BigDecimal buy_unit) {
        this.buy_unit = buy_unit;
    }
    public BigDecimal getInvested_amount() {
        return invested_amount;
    }
    public void setInvested_amount(BigDecimal invested_amount) {
        this.invested_amount = invested_amount;
    }
    public String getTotal_amount() {
        return total_amount;
    }
    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }
    public Float getRate() {
        return rate;
    }
    public void setRate(Float rate) {
        this.rate = rate;
    }
    public String getProgress() {
        return progress;
    }
    public void setProgress(String progress) {
        this.progress = progress;
    }
    public String getStart_time() {
        return start_time;
    }
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
    public String getPayback_way() {
        return payback_way;
    }
    public void setPayback_way(String payback_way) {
        this.payback_way = payback_way;
    }
    public String getInvest_condition() {
        return invest_condition;
    }
    public void setInvest_condition(String invest_condition) {
        this.invest_condition = invest_condition;
    }
    public String getProject_description() {
        return project_description;
    }
    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }
    public String getLose_invest() {
        return lose_invest;
    }
    public void setLose_invest(String lose_invest) {
        this.lose_invest = lose_invest;
    }
}
