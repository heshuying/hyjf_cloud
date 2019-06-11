/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.hgreportdata.caijing;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version ZeroOneBorrowDataVO, v0.1 2019/6/4 15:38
 */
public class ZeroOneBorrowDataVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //标编号
    private String id;

    //标的链接
    private String link;

    //标题
    private String title;

    //用户名
    private String username;

    //用户编号
    private String userid;

    //计划编号
    private String planNid;

    //借款人类型
    private Integer companyOrPersonal;

    //借款金额
    private Double amount;

    //借款期限
    private String borrow_period;

    //借款年利率
    private Double interest;

    //资产类型
    private String asset_type;

    //借款类型
    private String borrow_type;

    //产品类型
    private String product_type;

    //还款方式
    private String repay_type;

    //完成百分比
    private Double percentage;

    //标状态
    private String bid_state;

    //出借奖励
    private Double reward;

    //担保奖励
    private Double guarantee;

    //信用等级
    private String credit;

    //发标时间
    private String verify_time;

    //成功时间
    private String reverify_time;

    //出借次数
    private Integer invest_count;

    //借款详情
    private String borrow_detail;

    //借款用途
    private String attribute1;

    //扩展字段2
    private String attribute2;

    //扩展字段3
    private String attribute3;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBorrow_period() {
        return borrow_period;
    }

    public void setBorrow_period(String borrow_period) {
        this.borrow_period = borrow_period;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public String getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(String asset_type) {
        this.asset_type = asset_type;
    }

    public String getBorrow_type() {
        return borrow_type;
    }

    public void setBorrow_type(String borrow_type) {
        this.borrow_type = borrow_type;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getRepay_type() {
        return repay_type;
    }

    public void setRepay_type(String repay_type) {
        this.repay_type = repay_type;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getBid_state() {
        return bid_state;
    }

    public void setBid_state(String bid_state) {
        this.bid_state = bid_state;
    }

    public Double getReward() {
        return reward;
    }

    public void setReward(Double reward) {
        this.reward = reward;
    }

    public Double getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(Double guarantee) {
        this.guarantee = guarantee;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getVerify_time() {
        return verify_time;
    }

    public void setVerify_time(String verify_time) {
        this.verify_time = verify_time;
    }

    public String getReverify_time() {
        return reverify_time;
    }

    public void setReverify_time(String reverify_time) {
        this.reverify_time = reverify_time;
    }

    public Integer getInvest_count() {
        return invest_count;
    }

    public void setInvest_count(Integer invest_count) {
        this.invest_count = invest_count;
    }

    public String getBorrow_detail() {
        return borrow_detail;
    }

    public void setBorrow_detail(String borrow_detail) {
        this.borrow_detail = borrow_detail;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public Integer getCompanyOrPersonal() {
        return companyOrPersonal;
    }

    public void setCompanyOrPersonal(Integer companyOrPersonal) {
        this.companyOrPersonal = companyOrPersonal;
    }
}
