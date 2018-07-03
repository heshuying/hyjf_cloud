/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.hyjf.am.vo.BaseVO;

/**
 * @author libin
 * @version HjhLabelCustomizeVO.java, v0.1 2018年6月30日 下午2:20:50
 */
public class HjhLabelCustomizeVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
    
    private String labelisUsed;

    private String labelName;

    private Integer labelTermStart;

    private Integer labelTermEnd;

    private String labelTermType;

    private BigDecimal labelAprStart;

    private BigDecimal labelAprEnd;

    private String borrowStyle;

    private String borrowStyleName;

    private BigDecimal labelPaymentAccountStart;

    private BigDecimal labelPaymentAccountEnd;

    private String instCode;

    private String instName;

    private Integer assetType;

    private String assetTypeName;

    private Integer projectType;

    private String projectTypeName;

    private Integer isCredit;

    private Integer isLate;

    private Integer creditSumMax;

    private Date pushTimeStart;

    private Date pushTimeEnd;

    private Integer remainingDaysStart;

    private Integer remainingDaysEnd;

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
