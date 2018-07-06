package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.sun.xml.internal.rngom.parse.host.Base;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/4
 * @Description: 汇计划-转让列表参数实体
 */
public class HjhDebtCreditListRequest extends BasePage{

    /**
     * 计划编号
     */
    private String planNid;
    private String planOrderId;
    private String planNidNew;
    private String userName;
    private String creditNid;
    private String borrowNid;
    private String repayStyle;
    private String creditStatus;
    private String repayStatus;
    private String liquidatesTimeStart;
    private String liquidatesTimeEnd;
    private String repayNextTimeStart;
    private String repayNextTimeEnd;

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getPlanOrderId() {
        return planOrderId;
    }

    public void setPlanOrderId(String planOrderId) {
        this.planOrderId = planOrderId;
    }

    public String getPlanNidNew() {
        return planNidNew;
    }

    public void setPlanNidNew(String planNidNew) {
        this.planNidNew = planNidNew;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getRepayStyle() {
        return repayStyle;
    }

    public void setRepayStyle(String repayStyle) {
        this.repayStyle = repayStyle;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getLiquidatesTimeStart() {
        return liquidatesTimeStart;
    }

    public void setLiquidatesTimeStart(String liquidatesTimeStart) {
        this.liquidatesTimeStart = liquidatesTimeStart;
    }

    public String getLiquidatesTimeEnd() {
        return liquidatesTimeEnd;
    }

    public void setLiquidatesTimeEnd(String liquidatesTimeEnd) {
        this.liquidatesTimeEnd = liquidatesTimeEnd;
    }

    public String getRepayNextTimeStart() {
        return repayNextTimeStart;
    }

    public void setRepayNextTimeStart(String repayNextTimeStart) {
        this.repayNextTimeStart = repayNextTimeStart;
    }

    public String getRepayNextTimeEnd() {
        return repayNextTimeEnd;
    }

    public void setRepayNextTimeEnd(String repayNextTimeEnd) {
        this.repayNextTimeEnd = repayNextTimeEnd;
    }
}
