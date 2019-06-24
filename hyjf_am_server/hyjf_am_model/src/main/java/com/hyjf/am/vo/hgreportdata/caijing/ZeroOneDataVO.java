package com.hyjf.am.vo.hgreportdata.caijing;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: yinhui
 * @Date: 2019/6/3 14:50
 * @Version 1.0
 */
public class ZeroOneDataVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //用户id
    private Integer userIds;

    //表的主键
    private String invest_id;
    //标编号
    private String id;
    //标的链接
    private String link;
    //用户名
    private String username;
    //用户编号
    private String userid;
    //出借方式
    private String type;
    //出借金额
    private BigDecimal money;
    //有效金额
    private BigDecimal account;
    //出借状态
    private String status;
    //出借时间
    private String add_time;
    //出借来源
    private String bid_source;

    //冻结订单号
    private String repay_id;
    //实际借款天数
    private String actual_period;
    //还款日期
    private String advanced_time;
    //提前还款总额
    private BigDecimal advanced_amount;

    public String getInvest_id() {
        return invest_id;
    }

    public void setInvest_id(String invest_id) {
        this.invest_id = invest_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getBid_source() {
        return bid_source;
    }

    public void setBid_source(String bid_source) {
        this.bid_source = bid_source;
    }

    public Integer getUserIds() {
        return userIds;
    }

    public void setUserIds(Integer userIds) {
        this.userIds = userIds;
    }

    public String getRepay_id() {
        return repay_id;
    }

    public void setRepay_id(String repay_id) {
        this.repay_id = repay_id;
    }

    public String getActual_period() {
        return actual_period;
    }

    public void setActual_period(String actual_period) {
        this.actual_period = actual_period;
    }

    public String getAdvanced_time() {
        return advanced_time;
    }

    public void setAdvanced_time(String advanced_time) {
        this.advanced_time = advanced_time;
    }

    public BigDecimal getAdvanced_amount() {
        return advanced_amount;
    }

    public void setAdvanced_amount(BigDecimal advanced_amount) {
        this.advanced_amount = advanced_amount;
    }
}
