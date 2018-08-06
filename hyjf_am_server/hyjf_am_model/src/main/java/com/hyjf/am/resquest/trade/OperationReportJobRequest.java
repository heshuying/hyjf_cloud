/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author tanyy
 * @version OperationReportJobRequest, v0.1 2018/7/4 14:22
 */
public class OperationReportJobRequest extends Request {
    private Date date;
    private int firstAge;
    private int endAge;
    private Date beginDate;
    private Date endDate;
    private int startMonth;
    private int endMonth;
    private int  intervalMonth;
    private int userId;

    List<OperationReportJobVO> operationReportJobVOList;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFirstAge() {
        return firstAge;
    }

    public void setFirstAge(int firstAge) {
        this.firstAge = firstAge;
    }

    public int getEndAge() {
        return endAge;
    }

    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getIntervalMonth() {
        return intervalMonth;
    }

    public void setIntervalMonth(int intervalMonth) {
        this.intervalMonth = intervalMonth;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OperationReportJobVO> getOperationReportJobVOList() {
        return operationReportJobVOList;
    }

    public void setOperationReportJobVOList(List<OperationReportJobVO> operationReportJobVOList) {
        this.operationReportJobVOList = operationReportJobVOList;
    }
}
