/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.message;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.datacollect.OperationReportVO;

/**
 * @author tanyy
 * @version OperationReportResponse, v0.1 2018/7/23 14:00
 */
public class OperationReportResponse extends Response<OperationReportVO> {
    private Integer count;
    private String operationId;
    private String monthlyOperationReportId;
    private String userOperationReportId;
    private String tenthOperationReportId;
    private String yearlyOperationReportId;
    private String quarterOperationReportId;
    private String halfYearOperationReport;
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getMonthlyOperationReportId() {
        return monthlyOperationReportId;
    }

    public void setMonthlyOperationReportId(String monthlyOperationReportId) {
        this.monthlyOperationReportId = monthlyOperationReportId;

    }

    public String getUserOperationReportId() {
        return userOperationReportId;
    }

    public void setUserOperationReportId(String userOperationReportId) {
        this.userOperationReportId = userOperationReportId;
    }

    public String getTenthOperationReportId() {
        return tenthOperationReportId;
    }

    public void setTenthOperationReportId(String tenthOperationReportId) {
        this.tenthOperationReportId = tenthOperationReportId;
    }

    public String getYearlyOperationReportId() {
        return yearlyOperationReportId;
    }

    public void setYearlyOperationReportId(String yearlyOperationReportId) {
        this.yearlyOperationReportId = yearlyOperationReportId;
    }

    public String getQuarterOperationReportId() {
        return quarterOperationReportId;
    }

    public void setQuarterOperationReportId(String quarterOperationReportId) {
        this.quarterOperationReportId = quarterOperationReportId;
    }

    public String getHalfYearOperationReport() {
        return halfYearOperationReport;
    }

    public void setHalfYearOperationReport(String halfYearOperationReport) {
        this.halfYearOperationReport = halfYearOperationReport;
    }
}
