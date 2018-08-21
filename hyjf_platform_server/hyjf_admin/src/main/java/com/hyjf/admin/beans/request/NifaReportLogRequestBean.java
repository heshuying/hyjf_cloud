/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author nxl
 * @version NifaReportLogRequestBean, v0.1 2018/6/22 11:36
 */
public class NifaReportLogRequestBean extends BaseRequest {
    @ApiModelProperty(value = "id(主键)")
    private Integer id;
    //文件状态
    @ApiModelProperty(value = "文件状态")
    private Integer fileUploadStatus;
    //上传时间(开始)
    @ApiModelProperty(value = "上传时间(开始)")
    private String createTimeStart;
    //上传时间(结束)
    @ApiModelProperty(value = "上传时间(结束)")
    private String createTimeEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
