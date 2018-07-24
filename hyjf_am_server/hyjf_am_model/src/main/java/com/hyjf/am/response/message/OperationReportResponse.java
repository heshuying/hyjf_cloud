/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.message;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.datacollect.*;

import java.util.List;

/**
 * @author tanyy
 * @version OperationReportResponse, v0.1 2018/7/23 14:00
 */
public class OperationReportResponse extends Response<OperationReportVO> {
    //总条数
    private Integer count;
    //跳转路径
    private String path;
    //运营报告主键
    private String operationId;
    //月度运营报告主键
    private String monthlyOperationReportId;
    private String userOperationReportId;
    private String tenthOperationReportId;
    private String yearlyOperationReportId;
    private String quarterOperationReportId;
    private String halfYearOperationReportId;
    //运营报告
    private OperationReportVO operationReport;
    //运营报告活动
    private OperationReportActivityVO operationReportActivity;
    //运营报告十大投资
    private TenthOperationReportVO tenthOperationReport;
    //运营报告用户分析
    private UserOperationReportVO userOperationReport;
    //月度运营报告
    private MonthlyOperationReportVO monthlyOperationReport;
    //季度运营报告
    private QuarterOperationReportVO quarterOperationReport;
    //半年度运营报告
    private HalfYearOperationReportVO halfYearOperationReport;
    //年度运营报告
    private YearOperationReportVO yearOperationReport;

    //精彩活动
    private List<OperationReportActivityVO> wonderfulActivities;
    //足迹
    private List<OperationReportActivityVO> footprints;
    //体验优化
    private List<OperationReportActivityVO> goodExperiences;

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

    public String getHalfYearOperationReportId() {
        return halfYearOperationReportId;
    }

    public void setHalfYearOperationReportId(String halfYearOperationReportId) {
        this.halfYearOperationReportId = halfYearOperationReportId;
    }

    public QuarterOperationReportVO getQuarterOperationReport() {
        return quarterOperationReport;
    }

    public void setQuarterOperationReport(QuarterOperationReportVO quarterOperationReport) {
        this.quarterOperationReport = quarterOperationReport;
    }

    public OperationReportVO getOperationReport() {
        return operationReport;
    }

    public void setOperationReport(OperationReportVO operationReport) {
        this.operationReport = operationReport;
    }

    public OperationReportActivityVO getOperationReportActivity() {
        return operationReportActivity;
    }

    public void setOperationReportActivity(OperationReportActivityVO operationReportActivity) {
        this.operationReportActivity = operationReportActivity;
    }

    public TenthOperationReportVO getTenthOperationReport() {
        return tenthOperationReport;
    }

    public void setTenthOperationReport(TenthOperationReportVO tenthOperationReport) {
        this.tenthOperationReport = tenthOperationReport;
    }

    public UserOperationReportVO getUserOperationReport() {
        return userOperationReport;
    }

    public void setUserOperationReport(UserOperationReportVO userOperationReport) {
        this.userOperationReport = userOperationReport;
    }

    public MonthlyOperationReportVO getMonthlyOperationReport() {
        return monthlyOperationReport;
    }

    public void setMonthlyOperationReport(MonthlyOperationReportVO monthlyOperationReport) {
        this.monthlyOperationReport = monthlyOperationReport;
    }

    public HalfYearOperationReportVO getHalfYearOperationReport() {
        return halfYearOperationReport;
    }

    public void setHalfYearOperationReport(HalfYearOperationReportVO halfYearOperationReport) {
        this.halfYearOperationReport = halfYearOperationReport;
    }

    public YearOperationReportVO getYearOperationReport() {
        return yearOperationReport;
    }

    public void setYearOperationReport(YearOperationReportVO yearOperationReport) {
        this.yearOperationReport = yearOperationReport;
    }

    public List<OperationReportActivityVO> getWonderfulActivities() {
        return wonderfulActivities;
    }

    public void setWonderfulActivities(List<OperationReportActivityVO> wonderfulActivities) {
        this.wonderfulActivities = wonderfulActivities;
    }

    public List<OperationReportActivityVO> getFootprints() {
        return footprints;
    }

    public void setFootprints(List<OperationReportActivityVO> footprints) {
        this.footprints = footprints;
    }

    public List<OperationReportActivityVO> getGoodExperiences() {
        return goodExperiences;
    }

    public void setGoodExperiences(List<OperationReportActivityVO> goodExperiences) {
        this.goodExperiences = goodExperiences;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
