/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.message;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.datacollect.*;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author tanyy
 * @version OperationReportRequest.java, v0.1 2018年7月23日 下午3:15:12
 */
public class OperationReportRequest extends BasePage implements Serializable{

	/**
	 * 前台开始时间接收
	 */
	@ApiModelProperty(value = "前台开始时间接收")
	private String startCreate;
	/**
	 * 前台结束时间接收
	 */
	@ApiModelProperty(value = "前台结束时间接收")
	private String endCreate;
	/**
	 * 运营报告类型
	 */
	@ApiModelProperty(value = "运营报告类型")
	private String typeSearch;

	protected int limitStart = -1;

	protected int limitEnd = -1;


	public String getStartCreate() {
		return startCreate;
	}

	public void setStartCreate(String startCreate) {
		this.startCreate = startCreate;
	}

	public String getEndCreate() {
		return endCreate;
	}

	public void setEndCreate(String endCreate) {
		this.endCreate = endCreate;
	}

	public String getTypeSearch() {
		return typeSearch;
	}

	public void setTypeSearch(String typeSearch) {
		this.typeSearch = typeSearch;
	}

	public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	public int getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	//运营报告的id
	@ApiModelProperty(value = "运营报告的id")
	private String operationReportId;
	@ApiModelProperty(value = "是否发布")
	private Integer isRelease;
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
	public String getOperationReportId() {
		return operationReportId;
	}

	public void setOperationReportId(String operationReportId) {
		this.operationReportId = operationReportId;
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

	public QuarterOperationReportVO getQuarterOperationReport() {
		return quarterOperationReport;
	}

	public void setQuarterOperationReport(QuarterOperationReportVO quarterOperationReport) {
		this.quarterOperationReport = quarterOperationReport;
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

	public Integer getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}
}
