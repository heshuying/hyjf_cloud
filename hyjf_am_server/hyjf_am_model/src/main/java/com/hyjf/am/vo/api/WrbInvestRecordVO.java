package com.hyjf.am.vo.api;

import java.math.BigDecimal;

/**
 * @author lisheng
 * @version WrbInvestRecordVO, v0.1 2018/11/7 16:34
 */

public class WrbInvestRecordVO {
    /** 投资时间 */
    private String invest_time = "";

    /** 投资金额 */
    private BigDecimal invest_money = BigDecimal.ZERO;

    /** 已回款本金 */
    private BigDecimal all_back_principal = BigDecimal.ZERO;

    /** 已回款利息 */
    private BigDecimal all_back_interest = BigDecimal.ZERO;

    /** 预期收益 */
    private BigDecimal all_interest = BigDecimal.ZERO;

    /**投资奖励 */
    private BigDecimal invest_reward = BigDecimal.ZERO;

    /** 投资记录id */
    private String invest_record_id = "";

    /** 项目标题 */
    private String project_title = "";

    /** 项目id */
    private String project_id = "";

    /** 项目url */
    private String project_url = "";

    /** 项目利率 */
    private float project_rate = 0;

    /** 项目期限描述 折算成天 */
    private int project_timelimit = 0;

    /** 项目期限描述 4个月 */
    private String project_timelimit_desc = "";

    /** 投资状态 -2:流标 -1:投资中（未满标）0:收益中 1:已回款完毕 2:已逾期 3:提前回款 */
    private Integer invest_status = null;

    /** 是否自动投标 0:默认值（不是自动投标）1：自动投标 */
    private Integer is_auto_bid = 0;

    /** 投资来源 用户投资行为发生处0:未知,1:PC ,2:APP ,3:H5 */
    private Integer suorce;

    /** 每月回款日 例如15就是15日 */
    private String monthly_back_date = "";

    /** 下一回款日 格式2015-10-01，如是分期回款，为查询时间下一个回款日期 */
    private String next_back_date = "";

    /** 下一回款金额 */
    private BigDecimal next_back_money = BigDecimal.ZERO;

    /** 下一回款本金 */
    private BigDecimal next_back_principal = BigDecimal.ZERO;

    /** 一下回款利息 */
    private BigDecimal next_back_interest = BigDecimal.ZERO;

    /** 还款方式 如：等额本息 按月付息 一次性还本付息 */
    private String payback_way = "";

    /** 转让状态 0未转让
     1表示转让
     */
    private Integer attorn_state = 0;

    /** 转让时间 默认为空，
     已转让：格式2015-09-01 20:30:12精确到秒
     */
    private String attorn_time = "";

    /** 提前结束状态 默认：0没有提前结束
     1表示项目提前结束
     */
    private Integer back_early_state = 0;

    /** 提前结束时间 默认为空，
     项目提前结束时间：格式2015-09-01 20:30:12精确到秒
     */
    private String back_early_time = "";

    public String getInvest_time() {
        return invest_time;
    }

    public void setInvest_time(String invest_time) {
        this.invest_time = invest_time;
    }

    public BigDecimal getInvest_money() {
        return invest_money;
    }

    public void setInvest_money(BigDecimal invest_money) {
        this.invest_money = invest_money;
    }

    public BigDecimal getAll_back_principal() {
        return all_back_principal;
    }

    public void setAll_back_principal(BigDecimal all_back_principal) {
        this.all_back_principal = all_back_principal;
    }

    public BigDecimal getAll_back_interest() {
        return all_back_interest;
    }

    public void setAll_back_interest(BigDecimal all_back_interest) {
        this.all_back_interest = all_back_interest;
    }

    public BigDecimal getAll_interest() {
        return all_interest;
    }

    public void setAll_interest(BigDecimal all_interest) {
        this.all_interest = all_interest;
    }

    public BigDecimal getInvest_reward() {
        return invest_reward;
    }

    public void setInvest_reward(BigDecimal invest_reward) {
        this.invest_reward = invest_reward;
    }

    public String getInvest_record_id() {
        return invest_record_id;
    }

    public void setInvest_record_id(String invest_record_id) {
        this.invest_record_id = invest_record_id;
    }

    public String getProject_title() {
        return project_title;
    }

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_url() {
        return project_url;
    }

    public void setProject_url(String project_url) {
        this.project_url = project_url;
    }

    public float getProject_rate() {
        return project_rate;
    }

    public void setProject_rate(float project_rate) {
        this.project_rate = project_rate;
    }

    public int getProject_timelimit() {
        return project_timelimit;
    }

    public void setProject_timelimit(int project_timelimit) {
        this.project_timelimit = project_timelimit;
    }

    public String getProject_timelimit_desc() {
        return project_timelimit_desc;
    }

    public void setProject_timelimit_desc(String project_timelimit_desc) {
        this.project_timelimit_desc = project_timelimit_desc;
    }

    public Integer getInvest_status() {
        return invest_status;
    }

    public void setInvest_status(Integer invest_status) {
        this.invest_status = invest_status;
    }

    public Integer getIs_auto_bid() {
        return is_auto_bid;
    }

    public void setIs_auto_bid(Integer is_auto_bid) {
        this.is_auto_bid = is_auto_bid;
    }

    public Integer getSuorce() {
        return suorce;
    }

    public void setSuorce(Integer suorce) {
        this.suorce = suorce;
    }

    public String getPayback_way() {
        return payback_way;
    }

    public void setPayback_way(String payback_way) {
        this.payback_way = payback_way;
    }

    public Integer getAttorn_state() {
        return attorn_state;
    }

    public void setAttorn_state(Integer attorn_state) {
        this.attorn_state = attorn_state;
    }

    public String getAttorn_time() {
        return attorn_time;
    }

    public void setAttorn_time(String attorn_time) {
        this.attorn_time = attorn_time;
    }

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
