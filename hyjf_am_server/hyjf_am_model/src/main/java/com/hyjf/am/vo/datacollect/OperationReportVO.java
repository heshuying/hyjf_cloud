package com.hyjf.am.vo.datacollect;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tanyy
 * @version OperationReportVO, v0.1 2018/7/23 16:38
 */
public class OperationReportVO extends BaseVO implements Serializable {

    //运营报告id
    @ApiModelProperty(value = "运营报告id页面以前用ids")
    private String ids;
    @ApiModelProperty(value = "发布时间时分秒格式")
    private String releaseTimeStr;
    @ApiModelProperty(value = "报告类型名")
    private String typeRealName;
    @ApiModelProperty(value = "排序用的月份")
    private int sortMonth;
    @ApiModelProperty(value = "排序用的天")
    private int sortDay;
    @ApiModelProperty(value = "运营报告id")
    private String id;
    @ApiModelProperty(value = "中文名")
    private String cnName;
    @ApiModelProperty(value = "英文名")
    private String enName;
    @ApiModelProperty(value = "排序用的年")
    private String year;
    @ApiModelProperty(value = "报告类型")
    private Integer operationReportType;
    @ApiModelProperty(value = "累计交易额")
    private BigDecimal allAmount;
    @ApiModelProperty(value = "累计赚取收益")
    private BigDecimal allProfit;
    @ApiModelProperty(value = "平台注册人数")
    private BigDecimal registNum;

    private Integer successDealNum;

    private BigDecimal operationAmount;

    private BigDecimal operationProfit;
    @ApiModelProperty(value = "是否发布")
    private Integer isRelease;
    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;
    @ApiModelProperty(value = "发布时间毫秒")
    private Integer releaseTime;

    private Integer updateTime;

    private Integer updateUserId;

    private Integer createTime;

    private Integer createUserId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName == null ? null : cnName.trim();
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName == null ? null : enName.trim();
    }

    public Integer getOperationReportType() {
        return operationReportType;
    }

    public void setOperationReportType(Integer operationReportType) {
        this.operationReportType = operationReportType;
    }

    public BigDecimal getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(BigDecimal allAmount) {
        this.allAmount = allAmount;
    }

    public BigDecimal getAllProfit() {
        return allProfit;
    }

    public void setAllProfit(BigDecimal allProfit) {
        this.allProfit = allProfit;
    }

    public BigDecimal getRegistNum() {
        return registNum;
    }

    public void setRegistNum(BigDecimal registNum) {
        this.registNum = registNum;
    }

    public Integer getSuccessDealNum() {
        return successDealNum;
    }

    public void setSuccessDealNum(Integer successDealNum) {
        this.successDealNum = successDealNum;
    }

    public BigDecimal getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(BigDecimal operationAmount) {
        this.operationAmount = operationAmount;
    }

    public BigDecimal getOperationProfit() {
        return operationProfit;
    }

    public void setOperationProfit(BigDecimal operationProfit) {
        this.operationProfit = operationProfit;
    }

    public Integer getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(Integer isRelease) {
        this.isRelease = isRelease;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Integer releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getReleaseTimeStr() {
        return releaseTimeStr;
    }

    public void setReleaseTimeStr(String releaseTimeStr) {
        this.releaseTimeStr = releaseTimeStr;
    }

    public String getTypeRealName() {
        return typeRealName;
    }

    public void setTypeRealName(String typeRealName) {
        this.typeRealName = typeRealName;
    }

    public int getSortMonth() {
        return sortMonth;
    }

    public void setSortMonth(int sortMonth) {
        this.sortMonth = sortMonth;
    }

    public int getSortDay() {
        return sortDay;
    }

    public void setSortDay(int sortDay) {
        this.sortDay = sortDay;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
