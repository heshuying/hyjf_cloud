package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.api.EchartsResultVO;
import com.hyjf.am.vo.api.MonthDataStatisticsVO;
import com.hyjf.am.vo.api.OperMonthPerformanceDataVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class UserLargeScreenTwoResultBean implements Serializable {

    @ApiModelProperty(value = "坐席日规模业绩（新客组）")
    private List<EchartsResultVO> dayScalePerformanceListNew;
    @ApiModelProperty(value = "坐席日规模业绩（老客组）")
    private List<EchartsResultVO> dayScalePerformanceListOld;
    @ApiModelProperty(value = "坐席日回款情况（新客组）")
    private List<EchartsResultVO> dayReceivedPaymentsNew;
    @ApiModelProperty(value = "坐席日回款情况（老客组）")
    private List<EchartsResultVO> dayReceivedPaymentsOld;
    @ApiModelProperty(value = "本月数据统计（新客组）")
    private List<MonthDataStatisticsVO> monthDataStatisticsNew;
    @ApiModelProperty(value = "本月数据统计（老客组）")
    private List<MonthDataStatisticsVO> monthDataStatisticsOld;
    @ApiModelProperty(value = "运营部月度业绩数据")
    private OperMonthPerformanceDataVO operMonthPerformanceData;

    public List<EchartsResultVO> getDayScalePerformanceListNew() {
        return dayScalePerformanceListNew;
    }

    public void setDayScalePerformanceListNew(List<EchartsResultVO> dayScalePerformanceListNew) {
        this.dayScalePerformanceListNew = dayScalePerformanceListNew;
    }

    public List<EchartsResultVO> getDayScalePerformanceListOld() {
        return dayScalePerformanceListOld;
    }

    public void setDayScalePerformanceListOld(List<EchartsResultVO> dayScalePerformanceListOld) {
        this.dayScalePerformanceListOld = dayScalePerformanceListOld;
    }

    public List<EchartsResultVO> getDayReceivedPaymentsNew() {
        return dayReceivedPaymentsNew;
    }

    public void setDayReceivedPaymentsNew(List<EchartsResultVO> dayReceivedPaymentsNew) {
        this.dayReceivedPaymentsNew = dayReceivedPaymentsNew;
    }

    public List<EchartsResultVO> getDayReceivedPaymentsOld() {
        return dayReceivedPaymentsOld;
    }

    public void setDayReceivedPaymentsOld(List<EchartsResultVO> dayReceivedPaymentsOld) {
        this.dayReceivedPaymentsOld = dayReceivedPaymentsOld;
    }

    public List<MonthDataStatisticsVO> getMonthDataStatisticsNew() {
        return monthDataStatisticsNew;
    }

    public void setMonthDataStatisticsNew(List<MonthDataStatisticsVO> monthDataStatisticsNew) {
        this.monthDataStatisticsNew = monthDataStatisticsNew;
    }

    public List<MonthDataStatisticsVO> getMonthDataStatisticsOld() {
        return monthDataStatisticsOld;
    }

    public void setMonthDataStatisticsOld(List<MonthDataStatisticsVO> monthDataStatisticsOld) {
        this.monthDataStatisticsOld = monthDataStatisticsOld;
    }

    public OperMonthPerformanceDataVO getOperMonthPerformanceData() {
        return operMonthPerformanceData;
    }

    public void setOperMonthPerformanceData(OperMonthPerformanceDataVO operMonthPerformanceData) {
        this.operMonthPerformanceData = operMonthPerformanceData;
    }
}
