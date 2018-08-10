/**
 * Description:项目详情查询所用vo
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */
package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

public class AppHzcProjectDetailCustomize implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = 8219831069954985000L;

	/* 项目名称 borrowName */
	private String borrowName;
	/* 所在地区 borrowAddress */
	private String borrowAddress;
	/* 项目类型 borrowType */
	private String borrowType;
	/* 预估价值 guarantyValue */
	private String guarantyValue;
	/* 权属类别 ownershipCategory */
	private String ownershipCategory;
	/* 资产成因 assetOrigin */
	private String assetOrigin;
	/* 附件信息 attachmentInfo */
	private String attachmentInfo;

	public AppHzcProjectDetailCustomize() {
		super();
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getBorrowAddress() {
		return borrowAddress;
	}

	public void setBorrowAddress(String borrowAddress) {
		this.borrowAddress = borrowAddress;
	}

	public String getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(String borrowType) {
		this.borrowType = borrowType;
	}

	public String getGuarantyValue() {
		return guarantyValue;
	}

	public void setGuarantyValue(String guarantyValue) {
		this.guarantyValue = guarantyValue;
	}

	public String getOwnershipCategory() {
		return ownershipCategory;
	}

	public void setOwnershipCategory(String ownershipCategory) {
		this.ownershipCategory = ownershipCategory;
	}

	public String getAssetOrigin() {
		return assetOrigin;
	}

	public void setAssetOrigin(String assetOrigin) {
		this.assetOrigin = assetOrigin;
	}

	public String getAttachmentInfo() {
		return attachmentInfo;
	}

	public void setAttachmentInfo(String attachmentInfo) {
		this.attachmentInfo = attachmentInfo;
	}

}
