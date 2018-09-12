/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.common.util.GetDate;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author fuqiang
 * @version FddTempletCustomizeVO, v0.1 2018/7/10 16:09
 */
public class FddTempletCustomizeVO extends BaseVO {
	private Integer id;

	private String templetId;

	private Integer protocolType;
	@ApiModelProperty(value = "是否开启")
	private Integer isActive;

	private Integer caFlag;

	private Integer certificateTime;
	@ApiModelProperty(value = "备注")
	private String remark;

	private Integer createUserId;

	private String createUserName;

	private Integer updateUserId;

	private String updateUserName;

	private Date createTime;

	private Date updateTime;

	private String certificateTimeT;

	private String createTimeT;

	private String updateTimeT;

	private static final long serialVersionUID = 1L;

	public String getCertificateTimeT() {
		if(certificateTime != null){
			return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(certificateTime);
		}
		return null;
	}

	public String getCreateTimeT() {

		return GetDate.date2Str(createTime, GetDate.datetimeFormat);
	}

	public String getUpdateTimeT() {
		return GetDate.date2Str(updateTime, GetDate.datetimeFormat);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTempletId() {
		return templetId;
	}

	public void setTempletId(String templetId) {
		this.templetId = templetId == null ? null : templetId.trim();
	}

	public Integer getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(Integer protocolType) {
		this.protocolType = protocolType;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Integer getCaFlag() {
		return caFlag;
	}

	public void setCaFlag(Integer caFlag) {
		this.caFlag = caFlag;
	}

	public Integer getCertificateTime() {
		return certificateTime;
	}

	public void setCertificateTime(Integer certificateTime) {
		this.certificateTime = certificateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName == null ? null : createUserName.trim();
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName == null ? null : updateUserName.trim();
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

	/**
	 * 协议类型名称
	 */
	@ApiModelProperty(value = "协议类型名称")
	private String protocolTypeName;

	/**
	 * CA认证名称
	 */
	private String caFlagName;

	protected int limitStart = -1;

	protected int limitEnd = -1;

	public String getProtocolTypeName() {
		return protocolTypeName;
	}

	public void setProtocolTypeName(String protocolTypeName) {
		this.protocolTypeName = protocolTypeName;
	}

	public String getCaFlagName() {
		return caFlagName;
	}

	public void setCaFlagName(String caFlagName) {
		this.caFlagName = caFlagName;
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
}
