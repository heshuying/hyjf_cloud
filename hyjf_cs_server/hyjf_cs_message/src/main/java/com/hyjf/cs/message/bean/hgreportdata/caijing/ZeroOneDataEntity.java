package com.hyjf.cs.message.bean.hgreportdata.caijing;

import java.io.Serializable;

/**
 * 零壹财经报送实体类
 * @Author: yinhui
 * @Date: 2019/6/3 10:46
 * @Version 1.0
 */
public class ZeroOneDataEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String money;
    //有效金额
    private String account;
    //出借状态
    private String status;
    //出借时间
    private String add_time;
    //出借来源
    private String bid_source;

    private String borrow_period;

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
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

    public String getBorrow_period() {
        return borrow_period;
    }

    public void setBorrow_period(String borrow_period) {
        this.borrow_period = borrow_period;
    }
}
