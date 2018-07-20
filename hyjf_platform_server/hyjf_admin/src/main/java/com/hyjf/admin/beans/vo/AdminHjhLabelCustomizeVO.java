/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.hyjf.am.vo.BaseVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author libin
 * @version AdminHjhLabelCustomizeVO.java, v0.1 2018年7月20日 上午10:25:35
 */
public class AdminHjhLabelCustomizeVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "标签编号")
	private Integer id;
	
	@ApiModelProperty(value = "标签是否使用 0：在引擎中未使用，1：在引擎中使用")  
    private String labelisUsed;
	
	@ApiModelProperty(value = "标签名称")
    private String labelName;

	@ApiModelProperty(value = "标的期限最小")
    private Integer labelTermStart;

	@ApiModelProperty(value = "标的期限最大")
    private Integer labelTermEnd;

	@ApiModelProperty(value = "日/月")
    private String labelTermType;

	@ApiModelProperty(value = "标的实际利率最小")
    private BigDecimal labelAprStart;

	@ApiModelProperty(value = "标的实际利率最大")
    private BigDecimal labelAprEnd;

	@ApiModelProperty(value = "还款方式")
    private String borrowStyle;

	@ApiModelProperty(value = "还款方式名称")
    private String borrowStyleName;

	@ApiModelProperty(value = "标的实际支付金额最小")
    private BigDecimal labelPaymentAccountStart;

	@ApiModelProperty(value = "标的实际支付金额最大")
    private BigDecimal labelPaymentAccountEnd;

	@ApiModelProperty(value = "资产来源Code")
    private String instCode;

	@ApiModelProperty(value = "资产来源名称")
    private String instName;

	@ApiModelProperty(value = "产品类型Code")
    private Integer assetType;

	@ApiModelProperty(value = "产品类型名称")
    private String assetTypeName;

	@ApiModelProperty(value = "项目类型Code")
    private Integer projectType;

	@ApiModelProperty(value = "项目类型名称")
    private String projectTypeName;

	@ApiModelProperty(value = "标的是否发生债转")
    private Integer isCredit;

	@ApiModelProperty(value = "标的是否逾期")
    private Integer isLate;

	@ApiModelProperty(value = "债转最大次数")
    private Integer creditSumMax;

	@ApiModelProperty(value = "推送时间开始")
    private Date pushTimeStart;

	@ApiModelProperty(value = "推送时间结束")
    private Date pushTimeEnd;

    @ApiModelProperty(value = "剩余最小天数")
    private Integer remainingDaysStart;

    @ApiModelProperty(value = "剩余最大天数")
    private Integer remainingDaysEnd;
    
    @ApiModelProperty(value = "标签状态")
    private Integer labelState;

    private Integer createUserId;

    private Integer createTime;

    private Integer updateUserId;

    private Integer updateTime;

    private Integer delFlg;
    
    private Integer delFlag;
    
    private String pushTimeStartString;

    private String pushTimeEndString;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Integer getLabelTermStart() {
		return labelTermStart;
	}

	public void setLabelTermStart(Integer labelTermStart) {
		this.labelTermStart = labelTermStart;
	}

	public Integer getLabelTermEnd() {
		return labelTermEnd;
	}

	public void setLabelTermEnd(Integer labelTermEnd) {
		this.labelTermEnd = labelTermEnd;
	}

	public String getLabelTermType() {
		return labelTermType;
	}

	public void setLabelTermType(String labelTermType) {
		this.labelTermType = labelTermType;
	}

	public BigDecimal getLabelAprStart() {
		return labelAprStart;
	}

	public void setLabelAprStart(BigDecimal labelAprStart) {
		this.labelAprStart = labelAprStart;
	}

	public BigDecimal getLabelAprEnd() {
		return labelAprEnd;
	}

	public void setLabelAprEnd(BigDecimal labelAprEnd) {
		this.labelAprEnd = labelAprEnd;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getBorrowStyleName() {
		return borrowStyleName;
	}

	public void setBorrowStyleName(String borrowStyleName) {
		this.borrowStyleName = borrowStyleName;
	}

	public BigDecimal getLabelPaymentAccountStart() {
		return labelPaymentAccountStart;
	}

	public void setLabelPaymentAccountStart(BigDecimal labelPaymentAccountStart) {
		this.labelPaymentAccountStart = labelPaymentAccountStart;
	}

	public BigDecimal getLabelPaymentAccountEnd() {
		return labelPaymentAccountEnd;
	}

	public void setLabelPaymentAccountEnd(BigDecimal labelPaymentAccountEnd) {
		this.labelPaymentAccountEnd = labelPaymentAccountEnd;
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

	public Integer getAssetType() {
		return assetType;
	}

	public void setAssetType(Integer assetType) {
		this.assetType = assetType;
	}

	public String getAssetTypeName() {
		return assetTypeName;
	}

	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public Integer getIsCredit() {
		return isCredit;
	}

	public void setIsCredit(Integer isCredit) {
		this.isCredit = isCredit;
	}

	public Integer getIsLate() {
		return isLate;
	}

	public void setIsLate(Integer isLate) {
		this.isLate = isLate;
	}

	public Integer getCreditSumMax() {
		return creditSumMax;
	}

	public void setCreditSumMax(Integer creditSumMax) {
		this.creditSumMax = creditSumMax;
	}

	public Date getPushTimeStart() {
		return pushTimeStart;
	}

	public void setPushTimeStart(Date pushTimeStart) {
		this.pushTimeStart = pushTimeStart;
	}

	public Date getPushTimeEnd() {
		return pushTimeEnd;
	}

	public void setPushTimeEnd(Date pushTimeEnd) {
		this.pushTimeEnd = pushTimeEnd;
	}

	public Integer getRemainingDaysStart() {
		return remainingDaysStart;
	}

	public void setRemainingDaysStart(Integer remainingDaysStart) {
		this.remainingDaysStart = remainingDaysStart;
	}

	public Integer getRemainingDaysEnd() {
		return remainingDaysEnd;
	}

	public void setRemainingDaysEnd(Integer remainingDaysEnd) {
		this.remainingDaysEnd = remainingDaysEnd;
	}

	public Integer getLabelState() {
		return labelState;
	}

	public void setLabelState(Integer labelState) {
		this.labelState = labelState;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(Integer delFlg) {
		this.delFlg = delFlg;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getLabelisUsed() {
		return labelisUsed;
	}

	public void setLabelisUsed(String labelisUsed) {
		this.labelisUsed = labelisUsed;
	}

	public String getPushTimeStartString() {
		return pushTimeStartString;
	}

	public void setPushTimeStartString(String pushTimeStartString) {
		this.pushTimeStartString = pushTimeStartString;
	}

	public String getPushTimeEndString() {
		return pushTimeEndString;
	}

	public void setPushTimeEndString(String pushTimeEndString) {
		this.pushTimeEndString = pushTimeEndString;
	}

}
