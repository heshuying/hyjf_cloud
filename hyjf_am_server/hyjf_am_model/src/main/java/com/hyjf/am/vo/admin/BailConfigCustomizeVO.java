/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigCustomizeVO, v0.1 2018/9/26 16:21
 */
public class BailConfigCustomizeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "机构编号")
    private String instCode;

    @ApiModelProperty(value = "机构名称")
    private String instName;

    @ApiModelProperty(value = "保证金金额")
    private BigDecimal bailTatol;

    @ApiModelProperty(value = "保证金比例")
    private Integer bailRate;

    @ApiModelProperty(value = "授信周期开始")
    private String timestart;

    @ApiModelProperty(value = "授信周期结束")
    private String timeend;

    @ApiModelProperty(value = "新增授信额度（元）")
    private BigDecimal newCreditLine;

    @ApiModelProperty(value = "在贷授信额度（元）")
    private BigDecimal loanCreditLine;

    @ApiModelProperty(value = "日推标额度（元）")
    private BigDecimal dayMarkLine;

    @ApiModelProperty(value = "月推标额度（元）")
    private BigDecimal monthMarkLine;

    @ApiModelProperty(value = "发标额度上限（元）")
    private BigDecimal pushMarkLine;

    @ApiModelProperty(value = "发标已发额度（元）")
    private BigDecimal loanMarkLine;

    @ApiModelProperty(value = "发标额度余额（元）")
    private BigDecimal remainMarkLine;

    @ApiModelProperty(value = "已还本金")
    private BigDecimal repayedCapital;

    @ApiModelProperty(value = "历史标的放款总额")
    private BigDecimal hisLoanTotal;

    @ApiModelProperty(value = "周期内发标已发额度")
    private BigDecimal cycLoanTotal;

    @ApiModelProperty(value = "在贷余额")
    private BigDecimal loanBalance;

    @ApiModelProperty(value = "未用额度是否累计")
    private Integer isAccumulate;

    @ApiModelProperty(value = "说明")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private Integer createUserId;

    @ApiModelProperty(value = "修改人")
    private Integer updateUserId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标识")
    private Integer delFlg;

    private int limitStart = -1;
    private int limitEnd = -1;

    private int initQuery;

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

    public int getInitQuery() {
        return initQuery;
    }

    public void setInitQuery(int initQuery) {
        this.initQuery = initQuery;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public BigDecimal getBailTatol() {
        return bailTatol;
    }

    public void setBailTatol(BigDecimal bailTatol) {
        this.bailTatol = bailTatol;
    }

    public Integer getBailRate() {
        return bailRate;
    }

    public void setBailRate(Integer bailRate) {
        this.bailRate = bailRate;
    }

    public String getTimestart() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart = timestart;
    }

    public String getTimeend() {
        return timeend;
    }

    public void setTimeend(String timeend) {
        this.timeend = timeend;
    }

    public BigDecimal getNewCreditLine() {
        return newCreditLine;
    }

    public void setNewCreditLine(BigDecimal newCreditLine) {
        this.newCreditLine = newCreditLine;
    }

    public BigDecimal getLoanCreditLine() {
        return loanCreditLine;
    }

    public void setLoanCreditLine(BigDecimal loanCreditLine) {
        this.loanCreditLine = loanCreditLine;
    }

    public BigDecimal getDayMarkLine() {
        return dayMarkLine;
    }

    public void setDayMarkLine(BigDecimal dayMarkLine) {
        this.dayMarkLine = dayMarkLine;
    }

    public BigDecimal getMonthMarkLine() {
        return monthMarkLine;
    }

    public void setMonthMarkLine(BigDecimal monthMarkLine) {
        this.monthMarkLine = monthMarkLine;
    }

    public BigDecimal getPushMarkLine() {
        return pushMarkLine;
    }

    public void setPushMarkLine(BigDecimal pushMarkLine) {
        this.pushMarkLine = pushMarkLine;
    }

    public BigDecimal getLoanMarkLine() {
        return loanMarkLine;
    }

    public void setLoanMarkLine(BigDecimal loanMarkLine) {
        this.loanMarkLine = loanMarkLine;
    }

    public BigDecimal getRemainMarkLine() {
        return remainMarkLine;
    }

    public void setRemainMarkLine(BigDecimal remainMarkLine) {
        this.remainMarkLine = remainMarkLine;
    }

    public BigDecimal getRepayedCapital() {
        return repayedCapital;
    }

    public void setRepayedCapital(BigDecimal repayedCapital) {
        this.repayedCapital = repayedCapital;
    }

    public BigDecimal getHisLoanTotal() {
        return hisLoanTotal;
    }

    public void setHisLoanTotal(BigDecimal hisLoanTotal) {
        this.hisLoanTotal = hisLoanTotal;
    }

    public BigDecimal getCycLoanTotal() {
        return cycLoanTotal;
    }

    public void setCycLoanTotal(BigDecimal cycLoanTotal) {
        this.cycLoanTotal = cycLoanTotal;
    }

    public BigDecimal getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(BigDecimal loanBalance) {
        this.loanBalance = loanBalance;
    }

    public Integer getIsAccumulate() {
        return isAccumulate;
    }

    public void setIsAccumulate(Integer isAccumulate) {
        this.isAccumulate = isAccumulate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
}
