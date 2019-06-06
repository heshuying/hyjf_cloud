package com.hyjf.wbs.trade.dao.model.customize;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiasq
 * @version BorrowCustomize, v0.1 2018/7/10 16:06
 * 集成borrow 和 borrow_info字段的实体 可以合并查询
 */
public class BorrowCustomize {

    private Integer deadlineNum;
    private BigDecimal referenceIncome;
    private Integer deadlineUnit;
    private Integer investAmount;
    private String planNid;
    private Date publishTime;


    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public Integer getDeadlineNum() {
        return deadlineNum;
    }

    public void setDeadlineNum(Integer deadlineNum) {
        this.deadlineNum = deadlineNum;
    }

    public BigDecimal getReferenceIncome() {
        return referenceIncome;
    }

    public void setReferenceIncome(BigDecimal referenceIncome) {
        this.referenceIncome = referenceIncome;
    }

    public Integer getDeadlineUnit() {
        return deadlineUnit;
    }

    public void setDeadlineUnit(Integer deadlineUnit) {
        this.deadlineUnit = deadlineUnit;
    }

    public Integer getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(Integer investAmount) {
        this.investAmount = investAmount;
    }
}
