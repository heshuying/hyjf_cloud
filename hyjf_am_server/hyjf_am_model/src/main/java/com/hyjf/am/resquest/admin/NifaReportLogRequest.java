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
	//上传时间(开始)
	private String createTimeStart;
	//上传时间(结束)
	private String createTimeEnd;

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
}
