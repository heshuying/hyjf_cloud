/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.mc.hgdatareport.bifa;

import com.hyjf.cs.message.bean.mc.hgdatareport.base.BaseHgDataReportEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author jun
 * @version 散标, v0.1 2018/11/27 14:01
 */
@Document(collection = "ht_bifa_borrowinfo")
public class BifaBorrowInfoEntity extends BaseHgDataReportEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 产品登记类别
     */
    private String product_reg_type;
    /**
     * 产品名称
     */
    private String product_name;
    /**
     * 产品分类
     */
    private String product_mark;
    /**
     * 借款人性别
     */
    private String borrow_sex;
    /**
     * 金额
     */
    private String amount;
    /**
     * 月利率
     */
    private String rate;
    /**
     * 期限类型
     */
    private String term_type;
    /**
     * 期限
     */
    private String term;
    /**
     * 还款类型
     */
    private String pay_type;
    /**
     * 手续费(服务费)
     */
    private String service_cost;
    /**
     * 风险保证金
     */
    private String risk_margin;
    /**
     * 借款类型
     */
    private String loan_type;
    /**
     * 借款人信用评级
     */
    private String loan_credit_rating;
    /**
     * 担保信息
     */
    private String security_info;
    /**
     * 抵押/质押物描述
     */
    private String collateral_desc;
    /**
     * 抵押/质押物、估值、平均处置周期
     */
    private String collateral_info;
    /**
     * 逾期期限
     */
    private String overdue_limmit;
    /**
     * 坏账期限
     */
    private String bad_debt_limmit;
    /**
     * 最小投资金额
     */
    private String amount_limmts;
    /**
     * 最大投资金额
     */
    private String amount_limmtl;
    /**
     * 是否允许债权转让
     */
    private String allow_transfer;
    /**
     * 封闭期
     */
    private String close_limmit;
    /**
     * 担保方式
     */
    private String security_type;
    /**
     * 项目来源
     */
    private String project_source;
    /**
     * 原产品链接
     */
    private String source_product_url;
    /**
     * 姓名身份证的sha256
     */
    private String borrow_name_idcard_digest;
    /**
     * 借款金额
     */
    private String borrowamt;
    /**
     * 借款开始日期
     */
    private String begindate;
    /**
     * 借款结束日期
     */
    private String enddate;

    public String getProduct_reg_type() {
        return product_reg_type;
    }

    public void setProduct_reg_type(String product_reg_type) {
        this.product_reg_type = product_reg_type;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_mark() {
        return product_mark;
    }

    public void setProduct_mark(String product_mark) {
        this.product_mark = product_mark;
    }

    public String getBorrow_sex() {
        return borrow_sex;
    }

    public void setBorrow_sex(String borrow_sex) {
        this.borrow_sex = borrow_sex;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTerm_type() {
        return term_type;
    }

    public void setTerm_type(String term_type) {
        this.term_type = term_type;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getService_cost() {
        return service_cost;
    }

    public void setService_cost(String service_cost) {
        this.service_cost = service_cost;
    }

    public String getRisk_margin() {
        return risk_margin;
    }

    public void setRisk_margin(String risk_margin) {
        this.risk_margin = risk_margin;
    }

    public String getLoan_type() {
        return loan_type;
    }

    public void setLoan_type(String loan_type) {
        this.loan_type = loan_type;
    }

    public String getLoan_credit_rating() {
        return loan_credit_rating;
    }

    public void setLoan_credit_rating(String loan_credit_rating) {
        this.loan_credit_rating = loan_credit_rating;
    }

    public String getSecurity_info() {
        return security_info;
    }

    public void setSecurity_info(String security_info) {
        this.security_info = security_info;
    }

    public String getCollateral_desc() {
        return collateral_desc;
    }

    public void setCollateral_desc(String collateral_desc) {
        this.collateral_desc = collateral_desc;
    }

    public String getCollateral_info() {
        return collateral_info;
    }

    public void setCollateral_info(String collateral_info) {
        this.collateral_info = collateral_info;
    }

    public String getOverdue_limmit() {
        return overdue_limmit;
    }

    public void setOverdue_limmit(String overdue_limmit) {
        this.overdue_limmit = overdue_limmit;
    }

    public String getBad_debt_limmit() {
        return bad_debt_limmit;
    }

    public void setBad_debt_limmit(String bad_debt_limmit) {
        this.bad_debt_limmit = bad_debt_limmit;
    }

    public String getAmount_limmts() {
        return amount_limmts;
    }

    public void setAmount_limmts(String amount_limmts) {
        this.amount_limmts = amount_limmts;
    }

    public String getAmount_limmtl() {
        return amount_limmtl;
    }

    public void setAmount_limmtl(String amount_limmtl) {
        this.amount_limmtl = amount_limmtl;
    }

    public String getAllow_transfer() {
        return allow_transfer;
    }

    public void setAllow_transfer(String allow_transfer) {
        this.allow_transfer = allow_transfer;
    }

    public String getClose_limmit() {
        return close_limmit;
    }

    public void setClose_limmit(String close_limmit) {
        this.close_limmit = close_limmit;
    }

    public String getSecurity_type() {
        return security_type;
    }

    public void setSecurity_type(String security_type) {
        this.security_type = security_type;
    }

    public String getProject_source() {
        return project_source;
    }

    public void setProject_source(String project_source) {
        this.project_source = project_source;
    }

    public String getSource_product_url() {
        return source_product_url;
    }

    public void setSource_product_url(String source_product_url) {
        this.source_product_url = source_product_url;
    }

    public String getBorrow_name_idcard_digest() {
        return borrow_name_idcard_digest;
    }

    public void setBorrow_name_idcard_digest(String borrow_name_idcard_digest) {
        this.borrow_name_idcard_digest = borrow_name_idcard_digest;
    }

    public void setBorrowamt(String borrowamt) {
        this.borrowamt = borrowamt;
    }

    public String getBorrowamt() {
        return borrowamt;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getBegindate() {
        return begindate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getEnddate() {
        return enddate;
    }
}
