/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author nxl
 * @version NifaReportLogRequest.java, v0.1 2018年8月17日 下午3:15:12
 */
public class NifaReportLogRequest extends BasePage implements Serializable{
	//文件状态
	private Integer fileUploadStatus;
	//创建时间(开始)
	private String createTimeStart;
	//创建时间(结束)
	private String createTimeEnd;
	// 反馈文件状态
	private Integer feedbackResult;
	// 数据处理日期
	private String historyDate;
	// 上传时间（开始）
	private String uploadImeStart;
	// 上传时间（结束）
	private String uploadImeEnd;

	public Integer getFileUploadStatus() {
		return fileUploadStatus;
	}

	public void setFileUploadStatus(Integer fileUploadStatus) {
		this.fileUploadStatus = fileUploadStatus;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Integer getFeedbackResult() {
		return feedbackResult;
	}

	public void setFeedbackResult(Integer feedbackResult) {
		this.feedbackResult = feedbackResult;
	}

	public String getHistoryDate() {
		return historyDate;
	}

	public void setHistoryDate(String historyDate) {
		this.historyDate = historyDate;
	}

	public String getUploadImeStart() {
		return uploadImeStart;
	}

	public void setUploadImeStart(String uploadImeStart) {
		this.uploadImeStart = uploadImeStart;
	}

	public String getUploadImeEnd() {
		return uploadImeEnd;
	}

	public void setUploadImeEnd(String uploadImeEnd) {
		this.uploadImeEnd = uploadImeEnd;
	}
}
