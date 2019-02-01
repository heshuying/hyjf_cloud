/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.bifa;

/**
 * @author jun
 * @version BifaOperationDataEntityVO, v0.1 2019/1/21 16:22
 */
public class BifaOperationDataEntityVO extends BaseHgDataReportEntityVO {

    private static final long serialVersionUID = 1L;
    /**
     * 上传日期
     */
    private String upload_date;
    /**
     * 统计开始时间
     */
    private String data_begin_period;
    /**
     * 统计结束时间
     */
    private String data_end_period;
    /**
     * 累计借贷金额(元)
     */
    private String total_loan_money;
    /**
     * 累计借款笔数
     */
    private String total_loan_num;
    /**
     * 累计借贷余额
     */
    private String total_loan_balance_money;
    /**
     * 累计借贷余额笔数
     */
    private String total_loan_balance_num;
    /**
     * 累计借款人数
     */
    private String total_borrow_users;
    /**
     * 累计投资人数
     */
    private String total_invest_users;
    /**
     * 当前借款人数
     */
    private String cur_borrow_users;
    /**
     * 当前投资人数
     */
    private String cur_invest_users;
    /**
     * 平台前十大融资人融资待还余额占比
     */
    private String topten_repay_rate;
    /**
     * 平台单一融资人最大融资待还余额占比
     */
    private String top_repay_rate;
    /**
     * 关联关系借款总额
     */
    private String related_loan_money;
    /**
     * 关联关系借款笔数
     */
    private String related_loan_num;
    /**
     * 逾期笔数
     */
    private String overdue_loan_num;
    /**
     * 逾期欠款总额
     */
    private String overdue_loan_money;
    /**
     * 逾期90天以上的笔数
     */
    private String overdue_ninety_loan_num;
    /**
     * 逾期90天以上的总额
     */
    private String overdue_ninety_loan_money;
    /**
     * 风险保证金代偿总额
     */
    private String payed_risk_money;
    /**
     * 风险保证金代偿笔数
     */
    private String payed_risk_num;
    /**
     * total_recharge
     */
    private String total_recharge;
    /**
     * 提现手续费
     */
    private String total_deposit;
    /**
     * 身份认证费
     */
    private String identity_auth_fee;
    /**
     * 学历认证费
     */
    private String degree_auth_fee;
    /**
     * 视频认证费
     */
    private String video_auth_fee;
    /**
     * 利息管理费
     */
    private String interest_fee;
    /**
     * 债权转让手续费
     */
    private String transer_fee;
    /**
     * 服务费
     */
    private String service_fee;


    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public String getData_begin_period() {
        return data_begin_period;
    }

    public void setData_begin_period(String data_begin_period) {
        this.data_begin_period = data_begin_period;
    }

    public String getData_end_period() {
        return data_end_period;
    }

    public void setData_end_period(String data_end_period) {
        this.data_end_period = data_end_period;
    }

    public String getTotal_loan_money() {
        return total_loan_money;
    }

    public void setTotal_loan_money(String total_loan_money) {
        this.total_loan_money = total_loan_money;
    }

    public String getTotal_loan_num() {
        return total_loan_num;
    }

    public void setTotal_loan_num(String total_loan_num) {
        this.total_loan_num = total_loan_num;
    }

    public String getTotal_loan_balance_money() {
        return total_loan_balance_money;
    }

    public void setTotal_loan_balance_money(String total_loan_balance_money) {
        this.total_loan_balance_money = total_loan_balance_money;
    }

    public String getTotal_loan_balance_num() {
        return total_loan_balance_num;
    }

    public void setTotal_loan_balance_num(String total_loan_balance_num) {
        this.total_loan_balance_num = total_loan_balance_num;
    }

    public String getTotal_borrow_users() {
        return total_borrow_users;
    }

    public void setTotal_borrow_users(String total_borrow_users) {
        this.total_borrow_users = total_borrow_users;
    }

    public String getTotal_invest_users() {
        return total_invest_users;
    }

    public void setTotal_invest_users(String total_invest_users) {
        this.total_invest_users = total_invest_users;
    }

    public String getCur_borrow_users() {
        return cur_borrow_users;
    }

    public void setCur_borrow_users(String cur_borrow_users) {
        this.cur_borrow_users = cur_borrow_users;
    }

    public String getCur_invest_users() {
        return cur_invest_users;
    }

    public void setCur_invest_users(String cur_invest_users) {
        this.cur_invest_users = cur_invest_users;
    }

    public String getTopten_repay_rate() {
        return topten_repay_rate;
    }

    public void setTopten_repay_rate(String topten_repay_rate) {
        this.topten_repay_rate = topten_repay_rate;
    }

    public String getTop_repay_rate() {
        return top_repay_rate;
    }

    public void setTop_repay_rate(String top_repay_rate) {
        this.top_repay_rate = top_repay_rate;
    }

    public String getRelated_loan_money() {
        return related_loan_money;
    }

    public void setRelated_loan_money(String related_loan_money) {
        this.related_loan_money = related_loan_money;
    }

    public String getRelated_loan_num() {
        return related_loan_num;
    }

    public void setRelated_loan_num(String related_loan_num) {
        this.related_loan_num = related_loan_num;
    }

    public String getOverdue_loan_num() {
        return overdue_loan_num;
    }

    public void setOverdue_loan_num(String overdue_loan_num) {
        this.overdue_loan_num = overdue_loan_num;
    }

    public String getOverdue_loan_money() {
        return overdue_loan_money;
    }

    public void setOverdue_loan_money(String overdue_loan_money) {
        this.overdue_loan_money = overdue_loan_money;
    }

    public String getOverdue_ninety_loan_num() {
        return overdue_ninety_loan_num;
    }

    public void setOverdue_ninety_loan_num(String overdue_ninety_loan_num) {
        this.overdue_ninety_loan_num = overdue_ninety_loan_num;
    }

    public String getOverdue_ninety_loan_money() {
        return overdue_ninety_loan_money;
    }

    public void setOverdue_ninety_loan_money(String overdue_ninety_loan_money) {
        this.overdue_ninety_loan_money = overdue_ninety_loan_money;
    }

    public String getPayed_risk_money() {
        return payed_risk_money;
    }

    public void setPayed_risk_money(String payed_risk_money) {
        this.payed_risk_money = payed_risk_money;
    }

    public String getPayed_risk_num() {
        return payed_risk_num;
    }

    public void setPayed_risk_num(String payed_risk_num) {
        this.payed_risk_num = payed_risk_num;
    }

    public String getTotal_recharge() {
        return total_recharge;
    }

    public void setTotal_recharge(String total_recharge) {
        this.total_recharge = total_recharge;
    }

    public String getTotal_deposit() {
        return total_deposit;
    }

    public void setTotal_deposit(String total_deposit) {
        this.total_deposit = total_deposit;
    }

    public String getIdentity_auth_fee() {
        return identity_auth_fee;
    }

    public void setIdentity_auth_fee(String identity_auth_fee) {
        this.identity_auth_fee = identity_auth_fee;
    }

    public String getDegree_auth_fee() {
        return degree_auth_fee;
    }

    public void setDegree_auth_fee(String degree_auth_fee) {
        this.degree_auth_fee = degree_auth_fee;
    }

    public String getVideo_auth_fee() {
        return video_auth_fee;
    }

    public void setVideo_auth_fee(String video_auth_fee) {
        this.video_auth_fee = video_auth_fee;
    }

    public String getInterest_fee() {
        return interest_fee;
    }

    public void setInterest_fee(String interest_fee) {
        this.interest_fee = interest_fee;
    }

    public String getTranser_fee() {
        return transer_fee;
    }

    public void setTranser_fee(String transer_fee) {
        this.transer_fee = transer_fee;
    }

    public String getService_fee() {
        return service_fee;
    }

    public void setService_fee(String service_fee) {
        this.service_fee = service_fee;
    }
}
