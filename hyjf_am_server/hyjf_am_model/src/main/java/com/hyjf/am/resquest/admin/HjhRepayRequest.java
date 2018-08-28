package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @Author : huanghui
 */
public class HjhRepayRequest extends BasePage implements Serializable {

    /**
     * 检索条件 加入订单号
     */
    private String accedeOrderIdSrch;
    /**
     * 检索条件 计划编号
     */
    private String planNidSrch;
    /**
     * 检索条件 用户名
     */
    private String userNameSrch;
    /**
     * 检索条件 锁定期
     */
    private String debtLockPeriodSrch;
    /**
     * 检索条件 回款状态：0 未回款，1 部分回款 2 已回款'
     */
    private String repayStatusSrch;
    /**
     * 检索条件 订单状态：0 自动投标中 1锁定中 2退出中 3已退出'
     */
    private String orderStatusSrch;
    /**
     * 检索条件 还款方式
     */
    private String borrowStyleSrch;
    /**
     * 检索条件 应还日期开始
     */
    private String repayTimeStart;
    /**
     * 检索条件 应还日期结束
     */
    private String repayTimeEnd;
    /**
     * 检索条件 计划实际还款时间开始
     */
    private String actulRepayTimeStart;
    /**
     * 检索条件 计划实际还款时间结束
     */
    private String actulRepayTimeEnd;

    /**
     * 检索条件 计划状态 全部；0 发起中；1
     * 待审核；2审核不通过；3待开放；4募集中；5募集完成；6锁定中；7清算中；8清算完成，9还款，10还款中，11还款完成
     */
    private String planStatusSrch;

    /**
     * 检索条件 最迟应还款日期开始
     */
    private String repayTimeLastStart;

    /**
     * 检索条件 最迟应还款日期结束
     */
    private String repayTimeLastEnd;

    private String refereeNameSrch;

    private Integer limitStart = -1;

    private Integer limitEnd = -1;

    public String getAccedeOrderIdSrch() {
        return accedeOrderIdSrch;
    }

    public void setAccedeOrderIdSrch(String accedeOrderIdSrch) {
        this.accedeOrderIdSrch = accedeOrderIdSrch;
    }

    public String getPlanNidSrch() {
        return planNidSrch;
    }

    public void setPlanNidSrch(String planNidSrch) {
        this.planNidSrch = planNidSrch;
    }

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getDebtLockPeriodSrch() {
        return debtLockPeriodSrch;
    }

    public void setDebtLockPeriodSrch(String debtLockPeriodSrch) {
        this.debtLockPeriodSrch = debtLockPeriodSrch;
    }

    public String getRepayStatusSrch() {
        return repayStatusSrch;
    }

    public void setRepayStatusSrch(String repayStatusSrch) {
        this.repayStatusSrch = repayStatusSrch;
    }

    public String getOrderStatusSrch() {
        return orderStatusSrch;
    }

    public void setOrderStatusSrch(String orderStatusSrch) {
        this.orderStatusSrch = orderStatusSrch;
    }

    public String getBorrowStyleSrch() {
        return borrowStyleSrch;
    }

    public void setBorrowStyleSrch(String borrowStyleSrch) {
        this.borrowStyleSrch = borrowStyleSrch;
    }

    public String getRepayTimeStart() {
        return repayTimeStart;
    }

    public void setRepayTimeStart(String repayTimeStart) {
        this.repayTimeStart = repayTimeStart;
    }

    public String getRepayTimeEnd() {
        return repayTimeEnd;
    }

    public void setRepayTimeEnd(String repayTimeEnd) {
        this.repayTimeEnd = repayTimeEnd;
    }

    public String getActulRepayTimeStart() {
        return actulRepayTimeStart;
    }

    public void setActulRepayTimeStart(String actulRepayTimeStart) {
        this.actulRepayTimeStart = actulRepayTimeStart;
    }

    public String getActulRepayTimeEnd() {
        return actulRepayTimeEnd;
    }

    public void setActulRepayTimeEnd(String actulRepayTimeEnd) {
        this.actulRepayTimeEnd = actulRepayTimeEnd;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getPlanStatusSrch() {
        return planStatusSrch;
    }

    public void setPlanStatusSrch(String planStatusSrch) {
        this.planStatusSrch = planStatusSrch;
    }

    public String getRepayTimeLastStart() {
        return repayTimeLastStart;
    }

    public void setRepayTimeLastStart(String repayTimeLastStart) {
        this.repayTimeLastStart = repayTimeLastStart;
    }

    public String getRepayTimeLastEnd() {
        return repayTimeLastEnd;
    }

    public void setRepayTimeLastEnd(String repayTimeLastEnd) {
        this.repayTimeLastEnd = repayTimeLastEnd;
    }

    public String getRefereeNameSrch() {
        return refereeNameSrch;
    }

    public void setRefereeNameSrch(String refereeNameSrch) {
        this.refereeNameSrch = refereeNameSrch;
    }
}
