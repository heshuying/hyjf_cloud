package com.hyjf.am.statistics.bean;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author : huanghui
 */
@Document(collection = "t_hjh_plan_capital")
public class HjhPlanCapital implements Serializable {

    private Integer id;
    private Date date;
    private String plan_nid;
    private String plan_name;
    private Integer lock_period;
    private Integer is_month;
    private BigDecimal reinvest_account;
    private BigDecimal credit_account;
    private Integer create_user;
    private Integer create_time;
    private Integer update_user;
    private Integer update_time;
    private Integer del_flg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlan_nid() {
        return plan_nid;
    }

    public void setPlan_nid(String plan_nid) {
        this.plan_nid = plan_nid;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public Integer getLock_period() {
        return lock_period;
    }

    public void setLock_period(Integer lock_period) {
        this.lock_period = lock_period;
    }

    public Integer getIs_month() {
        return is_month;
    }

    public void setIs_month(Integer is_month) {
        this.is_month = is_month;
    }

    public BigDecimal getReinvest_account() {
        return reinvest_account;
    }

    public void setReinvest_account(BigDecimal reinvest_account) {
        this.reinvest_account = reinvest_account;
    }

    public BigDecimal getCredit_account() {
        return credit_account;
    }

    public void setCredit_account(BigDecimal credit_account) {
        this.credit_account = credit_account;
    }

    public Integer getCreate_user() {
        return create_user;
    }

    public void setCreate_user(Integer create_user) {
        this.create_user = create_user;
    }

    public Integer getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Integer create_time) {
        this.create_time = create_time;
    }

    public Integer getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(Integer update_user) {
        this.update_user = update_user;
    }

    public Integer getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Integer update_time) {
        this.update_time = update_time;
    }

    public Integer getDel_flg() {
        return del_flg;
    }

    public void setDel_flg(Integer del_flg) {
        this.del_flg = del_flg;
    }
}
