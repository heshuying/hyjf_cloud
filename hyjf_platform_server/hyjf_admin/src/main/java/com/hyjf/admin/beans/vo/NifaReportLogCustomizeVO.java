package com.hyjf.admin.beans.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 汇计划加入明细
 * 
 * @author nxl
 * @date 2018年8月12日 上午10:19:06
 */
public class NifaReportLogCustomizeVO {
	@ApiModelProperty(value = "id")
	private Integer id;
	@ApiModelProperty(value = "文件包信息")
	private String packageInformation;
	@ApiModelProperty(value = "上传时间")
	private String uploadTime;
	@ApiModelProperty(value = "文件上传状态 0：未处理 1：成功 2：失败")
	private Integer fileUploadStatus;
	@ApiModelProperty(value = "文件解析反馈 0：未处理 1：成功 2：失败")
	private Integer feedbackResult;
	@ApiModelProperty(value = "上传文件包名")
	private String uploadName;
	@ApiModelProperty(value = "反馈文件包名")
	private String feedbackName;
	@ApiModelProperty(value = "上传文件包路径")
	private String uploadPath;
	@ApiModelProperty(value = "反馈文件包路径")
	private String feedbackPath;
	@ApiModelProperty(value = "创建人")
	private Integer createUserId;
	@ApiModelProperty(value = "修改人")
	private Integer updateUserId;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "最后修改时间")
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPackageInformation() {
		return packageInformation;
	}

	public void setPackageInformation(String packageInformation) {
		this.packageInformation = packageInformation;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Integer getFileUploadStatus() {
		return fileUploadStatus;
	}

	public void setFileUploadStatus(Integer fileUploadStatus) {
		this.fileUploadStatus = fileUploadStatus;
	}

	public Integer getFeedbackResult() {
		return feedbackResult;
	}

	public void setFeedbackResult(Integer feedbackResult) {
		this.feedbackResult = feedbackResult;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public String getFeedbackName() {
		return feedbackName;
	}

	public void setFeedbackName(String feedbackName) {
		this.feedbackName = feedbackName;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getFeedbackPath() {
		return feedbackPath;
	}

	public void setFeedbackPath(String feedbackPath) {
		this.feedbackPath = feedbackPath;
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
}
